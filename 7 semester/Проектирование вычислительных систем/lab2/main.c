/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.c
  * @brief          : UART Code Lock with interrupt/polling modes (USART6)
  ******************************************************************************
  */
/* USER CODE END Header */

#include "main.h"
#include <string.h>
#include <ctype.h>

/* USER CODE BEGIN Includes */
#include "usart.h"
/* USER CODE END Includes */

/* USER CODE BEGIN PTD */
typedef enum {
  IDLE,
  LOCK_SUCCESS,
  BLOCK,
  CHANGE_PASSWORD
} state_t;
/* USER CODE END PTD */

/* USER CODE BEGIN PD */
#define GREEN_LED_GPIO      GPIOD
#define GREEN_LED_PIN       GPIO_PIN_13
#define RED_LED_GPIO        GPIOD
#define RED_LED_PIN         GPIO_PIN_15
#define YELLOW_LED_GPIO     GPIOD
#define YELLOW_LED_PIN      GPIO_PIN_14
#define BUTTON_GPIO         GPIOC
#define BUTTON_PIN          GPIO_PIN_15

#define DEBOUNCE_MS         20
#define BLINK_MS            100
#define LONG_BLINK_MS       1000
#define GREEN_ON_MS         2000
#define TIMEOUT_MS          5000
#define BLOCK_MS            5000
#define BLOCK_BLINK_PERIOD  1000
#define BLOCK_BLINK_ON      500

#define SECRET_LEN          8
#define UART_RX_BUFFER_SIZE 128
#define UART_TX_BUFFER_SIZE 256

/* USER CODE END PD */

/* USER CODE BEGIN PV */

// Password storage
static char secret[SECRET_LEN + 1] = "12345678"; // Default password
static char new_password[SECRET_LEN + 1] = {0};
static uint8_t new_password_pos = 0;

// State machine
static state_t state = IDLE;
static uint8_t input_pos = 0;
static uint8_t wrong_count = 0;
static uint32_t last_input_tick = 0;
static uint32_t green_off_tick = 0;
static uint32_t red_off_tick = 0;
static uint32_t yellow_off_tick = 0;
static uint32_t block_start = 0;
static uint32_t block_end = 0;

// Button handling
static uint8_t button_debounced = 0;
static uint32_t button_last_trigger = 0;
static uint8_t button_last_raw = 0;
static uint8_t mode_with_interrupts = 1; // 1 = with interrupts, 0 = polling

// UART buffers for interrupt mode
uint8_t uart_rx_buffer[UART_RX_BUFFER_SIZE];
volatile uint16_t uart_rx_head = 0;
volatile uint16_t uart_rx_tail = 0;
uint8_t uart_tx_buffer[UART_TX_BUFFER_SIZE];
volatile uint16_t uart_tx_head = 0;
volatile uint16_t uart_tx_tail = 0;
volatile uint8_t uart_tx_busy = 0;

// Single byte for UART RX interrupt
uint8_t uart_rx_byte;

/* USER CODE END PV */

/* Private function prototypes */
void SystemClock_Config(void);
static void MX_GPIO_Init(void);
void MX_USART6_UART_Init(void);

/* USER CODE BEGIN PFP */
static void LED_On(GPIO_TypeDef* GPIOx, uint16_t GPIO_Pin);
static void LED_Off(GPIO_TypeDef* GPIOx, uint16_t GPIO_Pin);
static void Button_Update(uint32_t now);
static void UART_SendString(const char* str);
static int UART_Available(void);
static char UART_ReadChar(void);
static void Process_Input(char c);
static void Start_TX_Interrupt(void);
/* USER CODE END PFP */

/* USER CODE BEGIN 0 */

static void LED_On(GPIO_TypeDef* GPIOx, uint16_t GPIO_Pin) {
  HAL_GPIO_WritePin(GPIOx, GPIO_Pin, GPIO_PIN_SET);
}

static void LED_Off(GPIO_TypeDef* GPIOx, uint16_t GPIO_Pin) {
  HAL_GPIO_WritePin(GPIOx, GPIO_Pin, GPIO_PIN_RESET);
}

static void Button_Update(uint32_t now) {
  uint8_t current_raw = (HAL_GPIO_ReadPin(BUTTON_GPIO, BUTTON_PIN) == GPIO_PIN_RESET) ? 1 : 0;

  if (current_raw != button_last_raw) {
    button_last_trigger = now;
    button_last_raw = current_raw;
  } else {
    if (now - button_last_trigger >= DEBOUNCE_MS && current_raw != button_debounced) {
      button_debounced = current_raw;
      if (!button_debounced) { // Button released - toggle mode
        mode_with_interrupts = !mode_with_interrupts;

        if (mode_with_interrupts) {
          __HAL_UART_ENABLE_IT(&huart6, UART_IT_RXNE);
          __HAL_UART_ENABLE_IT(&huart6, UART_IT_TXE);
          HAL_UART_Receive_IT(&huart6, &uart_rx_byte, 1);
          UART_SendString("\r\n=== Mode: WITH INTERRUPTS ===\r\n");
        } else {
          __HAL_UART_DISABLE_IT(&huart6, UART_IT_RXNE);
          __HAL_UART_DISABLE_IT(&huart6, UART_IT_TXE);
          HAL_UART_AbortReceive(&huart6);
          UART_SendString("\r\n=== Mode: POLLING (NO INTERRUPTS) ===\r\n");
        }
      }
    }
  }
}

// ===== UART DRIVER WITH INTERRUPTS =====
static void UART_SendString(const char* str) {
  if (mode_with_interrupts) {
    // Interrupt mode - buffer data
    while (*str) {
      uint16_t next_head = (uart_tx_head + 1) % UART_TX_BUFFER_SIZE;
      while (next_head == uart_tx_tail); // Wait if buffer full

      uart_tx_buffer[uart_tx_head] = *str++;
      uart_tx_head = next_head;
    }
    Start_TX_Interrupt();
  } else {
    // Polling mode - direct send
    HAL_UART_Transmit(&huart6, (uint8_t*)str, strlen(str), HAL_MAX_DELAY);
  }
}

static void Start_TX_Interrupt(void) {
  if (!uart_tx_busy && uart_tx_head != uart_tx_tail) {
    uart_tx_busy = 1;
    __HAL_UART_ENABLE_IT(&huart6, UART_IT_TXE);
  }
}

static int UART_Available(void) {
  if (mode_with_interrupts) {
    return (uart_rx_head != uart_rx_tail);
  } else {
    // Polling mode - check if data ready
    return (__HAL_UART_GET_FLAG(&huart6, UART_FLAG_RXNE) == SET);
  }
}

static char UART_ReadChar(void) {
  if (mode_with_interrupts) {
    if (uart_rx_head == uart_rx_tail) {
      return 0;
    }
    char c = uart_rx_buffer[uart_rx_tail];
    uart_rx_tail = (uart_rx_tail + 1) % UART_RX_BUFFER_SIZE;
    return c;
  } else {
    if (__HAL_UART_GET_FLAG(&huart6, UART_FLAG_RXNE) == SET) {
      return (char)(huart6.Instance->DR & 0xFF);
    }
    return 0;
  }
}

// ===== INPUT PROCESSING =====
static void Process_Input(char c) {
  uint32_t tick = HAL_GetTick();

  // Echo character
  char echo[2] = {c, '\0'};
  UART_SendString(echo);

  // Handle states
  if (state == CHANGE_PASSWORD) {
    if (c == '\r' || c == '\n') {
      if (new_password_pos > 0) {
        new_password[new_password_pos] = '\0';
        UART_SendString("\r\nActivate this password? (y/n): ");
        state = IDLE; // Will handle 'y' in IDLE state
      }
    } else if (new_password_pos < SECRET_LEN) {
      if (isalnum(c)) {
        new_password[new_password_pos++] = toupper(c);
        if (new_password_pos == SECRET_LEN) {
          new_password[SECRET_LEN] = '\0';
          UART_SendString("\r\nActivate this password? (y/n): ");
          state = IDLE;
        }
      }
    }
    return;
  }

  if (state == BLOCK) {
    return; // Ignore input during block
  }

  // Check for password change command
  if (c == '+' && input_pos == 0) {
    state = CHANGE_PASSWORD;
    new_password_pos = 0;
    UART_SendString("\r\nEnter new password (8 chars): ");
    return;
  }

  // Check for confirmation of new password
  if (new_password_pos > 0 && new_password[0] != '\0') {
    if (c == 'y' || c == 'Y') {
      strcpy(secret, new_password);
      memset(new_password, 0, sizeof(new_password));
      new_password_pos = 0;
      UART_SendString("\r\nPassword updated!\r\n");
      return;
    } else if (c == 'n' || c == 'N') {
      memset(new_password, 0, sizeof(new_password));
      new_password_pos = 0;
      UART_SendString("\r\nPassword change cancelled.\r\n");
      return;
    }
  }

  // Process password input
  if (isalnum(c) && state == IDLE) {
    c = toupper(c);

    if (input_pos == 0) {
      last_input_tick = tick;
    }

    if (c == secret[input_pos]) {
      // Correct character
      LED_On(YELLOW_LED_GPIO, YELLOW_LED_PIN);
      yellow_off_tick = tick + BLINK_MS;
      input_pos++;

      if (input_pos == SECRET_LEN) {
        // Password complete!
        LED_On(GREEN_LED_GPIO, GREEN_LED_PIN);
        green_off_tick = tick + GREEN_ON_MS;
        state = LOCK_SUCCESS;
        input_pos = 0;
        wrong_count = 0;
        UART_SendString("\r\nLOCK OPENED!\r\n");
      }
    } else {
      // Wrong character
      LED_On(RED_LED_GPIO, RED_LED_PIN);
      red_off_tick = tick + BLINK_MS;
      input_pos = 0;
      wrong_count++;
      UART_SendString("\r\nWRONG!\r\n");

      if (wrong_count >= 3) {
        state = BLOCK;
        block_start = tick + BLINK_MS;
        block_end = block_start + BLOCK_MS;
        wrong_count = 0;
        UART_SendString("BLOCKED for 5 seconds!\r\n");
      }
    }

    last_input_tick = tick;
  }
}

/* USER CODE END 0 */

int main(void) {
  /* USER CODE BEGIN 1 */
  /* USER CODE END 1 */

  HAL_Init();
  SystemClock_Config();

  /* USER CODE BEGIN SysInit */
  /* USER CODE END SysInit */

  MX_GPIO_Init();
  MX_USART6_UART_Init();

  /* USER CODE BEGIN 2 */
  LED_Off(GREEN_LED_GPIO, GREEN_LED_PIN);
  LED_Off(RED_LED_GPIO, RED_LED_PIN);
  LED_Off(YELLOW_LED_GPIO, YELLOW_LED_PIN);

  // Start UART reception in interrupt mode
  HAL_UART_Receive_IT(&huart6, &uart_rx_byte, 1);

  UART_SendString("\r\n=================================\r\n");
  UART_SendString("  UART CODE LOCK SYSTEM\r\n");
  UART_SendString("=================================\r\n");
  UART_SendString("Enter 8-character password\r\n");
  UART_SendString("Press '+' to change password\r\n");
  UART_SendString("Press button to toggle interrupt mode\r\n");
  UART_SendString("=================================\r\n");
  UART_SendString("Mode: WITH INTERRUPTS\r\n");

  /* USER CODE END 2 */

  /* Infinite loop */
  /* USER CODE BEGIN WHILE */
  while (1) {
    uint32_t tick = HAL_GetTick();

    Button_Update(tick);

    // LED timing management
    if (green_off_tick && tick >= green_off_tick) {
      LED_Off(GREEN_LED_GPIO, GREEN_LED_PIN);
      green_off_tick = 0;
      if (state == LOCK_SUCCESS) {
        state = IDLE;
      }
    }
    if (red_off_tick && tick >= red_off_tick) {
      LED_Off(RED_LED_GPIO, RED_LED_PIN);
      red_off_tick = 0;
    }
    if (yellow_off_tick && tick >= yellow_off_tick) {
      LED_Off(YELLOW_LED_GPIO, YELLOW_LED_PIN);
      yellow_off_tick = 0;
    }

    // Block state blinking
    if (state == BLOCK) {
      if (tick >= block_end) {
        LED_Off(RED_LED_GPIO, RED_LED_PIN);
        state = IDLE;
        UART_SendString("\r\nUnblocked. Ready.\r\n");
      } else {
        uint32_t phase = (tick - block_start) % BLOCK_BLINK_PERIOD;
        if (phase < BLOCK_BLINK_ON) {
          LED_On(RED_LED_GPIO, RED_LED_PIN);
        } else {
          LED_Off(RED_LED_GPIO, RED_LED_PIN);
        }
      }
    }

    // Timeout handling
    if (input_pos > 0 && tick - last_input_tick >= TIMEOUT_MS) {
      LED_On(YELLOW_LED_GPIO, YELLOW_LED_PIN);
      yellow_off_tick = tick + LONG_BLINK_MS;
      input_pos = 0;
      UART_SendString("\r\nTIMEOUT! Reset.\r\n");
    }

    // Process UART input
    if (UART_Available()) {
      char c = UART_ReadChar();
      Process_Input(c);
    }

    /* USER CODE END WHILE */
    /* USER CODE BEGIN 3 */
  }
  /* USER CODE END 3 */
}

/**
  * @brief System Clock Configuration
  */
void SystemClock_Config(void)
{
  RCC_OscInitTypeDef RCC_OscInitStruct = {0};
  RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};

  __HAL_RCC_PWR_CLK_ENABLE();
  __HAL_PWR_VOLTAGESCALING_CONFIG(PWR_REGULATOR_VOLTAGE_SCALE1);

  RCC_OscInitStruct.OscillatorType = RCC_OSCILLATORTYPE_HSE;
  RCC_OscInitStruct.HSEState = RCC_HSE_ON;
  RCC_OscInitStruct.PLL.PLLState = RCC_PLL_ON;
  RCC_OscInitStruct.PLL.PLLSource = RCC_PLLSOURCE_HSE;
  RCC_OscInitStruct.PLL.PLLM = 15;
  RCC_OscInitStruct.PLL.PLLN = 216;
  RCC_OscInitStruct.PLL.PLLP = RCC_PLLP_DIV2;
  RCC_OscInitStruct.PLL.PLLQ = 4;
  if (HAL_RCC_OscConfig(&RCC_OscInitStruct) != HAL_OK)
  {
    Error_Handler();
  }

  if (HAL_PWREx_EnableOverDrive() != HAL_OK)
  {
    Error_Handler();
  }

  RCC_ClkInitStruct.ClockType = RCC_CLOCKTYPE_HCLK|RCC_CLOCKTYPE_SYSCLK
                              |RCC_CLOCKTYPE_PCLK1|RCC_CLOCKTYPE_PCLK2;
  RCC_ClkInitStruct.SYSCLKSource = RCC_SYSCLKSOURCE_PLLCLK;
  RCC_ClkInitStruct.AHBCLKDivider = RCC_SYSCLK_DIV1;
  RCC_ClkInitStruct.APB1CLKDivider = RCC_HCLK_DIV4;
  RCC_ClkInitStruct.APB2CLKDivider = RCC_HCLK_DIV2;

  if (HAL_RCC_ClockConfig(&RCC_ClkInitStruct, FLASH_LATENCY_5) != HAL_OK)
  {
    Error_Handler();
  }
}

/**
  * @brief GPIO Initialization Function
  */
static void MX_GPIO_Init(void)
{
  GPIO_InitTypeDef GPIO_InitStruct = {0};

  /* GPIO Ports Clock Enable */
  __HAL_RCC_GPIOC_CLK_ENABLE();
  __HAL_RCC_GPIOH_CLK_ENABLE();
  __HAL_RCC_GPIOD_CLK_ENABLE();
  __HAL_RCC_GPIOA_CLK_ENABLE();

  /*Configure GPIO pin Output Level */
  HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13|GPIO_PIN_14|GPIO_PIN_15, GPIO_PIN_RESET);

  /*Configure GPIO pin : PC15 (button) */
  GPIO_InitStruct.Pin = GPIO_PIN_15;
  GPIO_InitStruct.Mode = GPIO_MODE_INPUT;
  GPIO_InitStruct.Pull = GPIO_PULLUP;
  HAL_GPIO_Init(GPIOC, &GPIO_InitStruct);

  /*Configure GPIO pins : PD13 PD14 PD15 (LEDs) */
  GPIO_InitStruct.Pin = GPIO_PIN_13|GPIO_PIN_14|GPIO_PIN_15;
  GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
  HAL_GPIO_Init(GPIOD, &GPIO_InitStruct);
}

/* USER CODE BEGIN 4 */

// ===== UART INTERRUPT HANDLERS =====
void HAL_UART_RxCpltCallback(UART_HandleTypeDef *huart)
{
  if (huart->Instance == USART6) {
    uint16_t next_head = (uart_rx_head + 1) % UART_RX_BUFFER_SIZE;
    if (next_head != uart_rx_tail) {
      uart_rx_buffer[uart_rx_head] = uart_rx_byte;
      uart_rx_head = next_head;
    }
    HAL_UART_Receive_IT(&huart6, &uart_rx_byte, 1);
  }
}

/* USER CODE END 4 */

void Error_Handler(void)
{
  __disable_irq();
  while (1)
  {
  }
}

#ifdef  USE_FULL_ASSERT
void assert_failed(uint8_t *file, uint32_t line)
{
}
#endif
