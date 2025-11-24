/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.c
  * @brief          : Main program body
  ******************************************************************************
  * @attention
  *
  * Copyright (c) 2025 STMicroelectronics.
  * All rights reserved.
  *
  * This software is licensed under terms that can be found in the LICENSE file
  * in the root directory of this software component.
  * If no LICENSE file comes with this software, it is provided AS-IS.
  *
  ******************************************************************************
  */
/* USER CODE END Header */
/* Includes ------------------------------------------------------------------*/
#include "main.h"
//#include "gpio.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */

/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */
typedef enum {
  IDLE,
  LOCK_SUCCESS,
  BLOCK
} state_t;
/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
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
#define LONG_PRESS_MS       500
#define BLINK_MS            100
#define LONG_BLINK_MS       1000
#define GREEN_ON_MS         2000
#define TIMEOUT_MS          5000
#define BLOCK_MS            5000
#define BLOCK_BLINK_PERIOD  1000
#define BLOCK_BLINK_ON      500

#define SECRET_LEN          8
/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/
/* USER CODE BEGIN PV */
static const uint8_t secret[SECRET_LEN] = {0, 1, 0, 1, 1, 0, 0, 1}; // 0: short, 1: long

static state_t state = IDLE;
static uint8_t input_pos = 0;
static uint8_t wrong_count = 0;
static uint32_t last_input_tick = 0;
static uint32_t green_off_tick = 0;
static uint32_t red_off_tick = 0;
static uint32_t yellow_off_tick = 0;
static uint32_t block_start = 0;
static uint32_t block_end = 0;

static uint8_t button_debounced = 0;
static uint32_t button_press_start = 0;
static uint8_t button_event = 0; // 0: none, 1: short, 2: long
static uint32_t button_last_trigger = 0;
static uint8_t button_last_raw = 0;
/* USER CODE END PV */

/* Private function prototypes -----------------------------------------------*/
void SystemClock_Config(void);
static void MX_GPIO_Init(void);
/* USER CODE BEGIN PFP */
static void LED_On(GPIO_TypeDef* GPIOx, uint16_t GPIO_Pin);
static void LED_Off(GPIO_TypeDef* GPIOx, uint16_t GPIO_Pin);
static void Button_Update(uint32_t now);
/* USER CODE END PFP */

/* Private user code ---------------------------------------------------------*/
/* USER CODE BEGIN 0 */
static void LED_On(GPIO_TypeDef* GPIOx, uint16_t GPIO_Pin) {
  HAL_GPIO_WritePin(GPIOx, GPIO_Pin, GPIO_PIN_SET);
}

static void LED_Off(GPIO_TypeDef* GPIOx, uint16_t GPIO_Pin) {
  HAL_GPIO_WritePin(GPIOx, GPIO_Pin, GPIO_PIN_RESET);
}

static void Button_Update(uint32_t now) {
  uint8_t current_raw = (HAL_GPIO_ReadPin(BUTTON_GPIO, BUTTON_PIN) == GPIO_PIN_RESET) ? 1 : 0; // Инверсия: нажатие = low (active low)

  if (current_raw != button_last_raw) {
    button_last_trigger = now;
    button_last_raw = current_raw;
  } else {
    if (now - button_last_trigger >= DEBOUNCE_MS && current_raw != button_debounced) {
      button_debounced = current_raw;
      if (button_debounced) {
        button_press_start = now;
      } else {
        uint32_t press_duration = now - button_press_start;
        if (press_duration >= LONG_PRESS_MS) {
          button_event = 2;
        } else if (press_duration >= DEBOUNCE_MS) {
          button_event = 1;
        }
      }
    }
  }
}
/* USER CODE END 0 */

/**
  * @brief  The application entry point.
  * @retval int
  */
int main(void) {

  /* USER CODE BEGIN 1 */

  /* USER CODE END 1 */

  /* MCU Configuration--------------------------------------------------------*/

  /* Reset of all peripherals, Initializes the Flash interface and the Systick. */
  HAL_Init();

  /* USER CODE BEGIN Init */

  /* USER CODE END Init */

  /* Configure the system clock */
  SystemClock_Config();

  /* USER CODE BEGIN SysInit */

  /* USER CODE END SysInit */

  /* Initialize all configured peripherals */
  MX_GPIO_Init();
  /* USER CODE BEGIN 2 */
  LED_Off(GREEN_LED_GPIO, GREEN_LED_PIN);
  LED_Off(RED_LED_GPIO, RED_LED_PIN);
  LED_Off(YELLOW_LED_GPIO, YELLOW_LED_PIN);
  /* USER CODE END 2 */

  /* Infinite loop */
  /* USER CODE BEGIN WHILE */
  while (1) {
    uint32_t tick = HAL_GetTick();

    Button_Update(tick);

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

    if (state == BLOCK) {
      if (tick >= block_end) {
        LED_Off(RED_LED_GPIO, RED_LED_PIN);
        state = IDLE;
      } else {
        uint32_t phase = (tick - block_start) % BLOCK_BLINK_PERIOD;
        if (phase < BLOCK_BLINK_ON) {
          LED_On(RED_LED_GPIO, RED_LED_PIN);
        } else {
          LED_Off(RED_LED_GPIO, RED_LED_PIN);
        }
      }
    }

    if (input_pos > 0 && tick - last_input_tick >= TIMEOUT_MS) {
      LED_On(YELLOW_LED_GPIO, YELLOW_LED_PIN);
      yellow_off_tick = tick + LONG_BLINK_MS;
      input_pos = 0;
    }

    if (button_event && state == IDLE) {
      uint8_t press_type = (button_event == 1) ? 0 : 1; // 0: short, 1: long

      if (input_pos == 0) {
        last_input_tick = tick;
      }

      if (press_type == secret[input_pos]) {
        LED_On(YELLOW_LED_GPIO, YELLOW_LED_PIN);
        yellow_off_tick = tick + BLINK_MS;
        input_pos++;
        if (input_pos == SECRET_LEN) {
          LED_On(GREEN_LED_GPIO, GREEN_LED_PIN);
          green_off_tick = tick + GREEN_ON_MS;
          state = LOCK_SUCCESS;
          input_pos = 0;
          wrong_count = 0;
        }
      } else {
        LED_On(RED_LED_GPIO, RED_LED_PIN);
        red_off_tick = tick + BLINK_MS;
        input_pos = 0;
        wrong_count++;
        if (wrong_count >= 3) {
          state = BLOCK;
          block_start = tick + BLINK_MS;
          block_end = block_start + BLOCK_MS;
          wrong_count = 0;
        }
      }

      last_input_tick = tick;
      button_event = 0;
    }
    /* USER CODE END WHILE */

    /* USER CODE BEGIN 3 */
  }
  /* USER CODE END 3 */
}

/**
  * @brief System Clock Configuration
  * @retval None
  */
void SystemClock_Config(void)
{
  RCC_OscInitTypeDef RCC_OscInitStruct = {0};
  RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};

  /** Configure the main internal regulator output voltage
  */
  __HAL_RCC_PWR_CLK_ENABLE();
  __HAL_PWR_VOLTAGESCALING_CONFIG(PWR_REGULATOR_VOLTAGE_SCALE1);

  /** Initializes the RCC Oscillators according to the specified parameters
  * in the RCC_OscInitTypeDef structure.
  */
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

  /** Activate the Over-Drive mode
  */
  if (HAL_PWREx_EnableOverDrive() != HAL_OK)
  {
    Error_Handler();
  }

  /** Initializes the CPU, AHB and APB buses clocks
  */
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
  * @param None
  * @retval None
  */
static void MX_GPIO_Init(void)
{
  GPIO_InitTypeDef GPIO_InitStruct = {0};
/* USER CODE BEGIN MX_GPIO_Init_1 */
/* USER CODE END MX_GPIO_Init_1 */

  /* GPIO Ports Clock Enable */
  __HAL_RCC_GPIOC_CLK_ENABLE();
  __HAL_RCC_GPIOH_CLK_ENABLE();
  __HAL_RCC_GPIOD_CLK_ENABLE();

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

/* USER CODE BEGIN MX_GPIO_Init_2 */
/* USER CODE END MX_GPIO_Init_2 */
}

/* USER CODE BEGIN 4 */

/* USER CODE END 4 */

/**
  * @brief  This function is executed in case of error occurrence.
  * @retval None
  */
void Error_Handler(void)
{
  /* USER CODE BEGIN Error_Handler_Debug */
  /* User can add his own implementation to report the HAL error return state */
  __disable_irq();
  while (1)
  {
  }
  /* USER CODE END Error_Handler_Debug */
}

#ifdef  USE_FULL_ASSERT
/**
  * @brief  Reports the name of the source file and the source line number
  *         where the assert_param error has occurred.
  * @param  file: pointer to the source file name
  * @param  line: assert_param error line source number
  * @retval None
  */
void assert_failed(uint8_t *file, uint32_t line)
{
  /* USER CODE BEGIN 6 */
  /* User can add his own implementation to report the file name and line number,
     ex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */
  /* USER CODE END 6 */
}
#endif /* USE_FULL_ASSERT */

