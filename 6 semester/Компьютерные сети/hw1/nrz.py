import matplotlib.pyplot as plt
import numpy as np

# Сообщение
message = "11001010 11000010 11010001"
message = message.replace(" ", "")  # Удаляем пробелы

# Подготовка данных для графика
bits = len(message)
x = np.zeros(bits * 2)
y = np.zeros(bits * 2)

# Линейное построение графика NRZ
for i in range(bits):
    # Две точки для каждого бита
    x[i*2] = i
    x[i*2+1] = i+1
    
    if message[i] == '1':
        y[i*2] = 1
        y[i*2+1] = 1
    else:  # Если бит = '0'
        y[i*2] = -1
        y[i*2+1] = -1

# Создание графика
plt.figure(figsize=(12, 6))
plt.plot(x, y, 'b-', linewidth=2)

# Горизонтальная линия для нуля
plt.axhline(y=0, color='r', linestyle='-', alpha=0.3)

# Добавление вертикальных линий для обозначения границ битов
for i in range(bits+1):
    plt.axvline(x=i, color='gray', linestyle='--', alpha=0.5)

# Добавление меток битов
for i in range(bits):
    plt.text(i+0.5, -1.5, message[i], horizontalalignment='center')

# Настройка осей
plt.ylim(-2, 2)
plt.yticks([-1, 0, 1], ['-1', '0', '1'])
plt.xticks(range(0, bits+1))

# Подписи
plt.title('NRZ (без возврата к нулю)')
plt.xlabel('Биты')
plt.ylabel('Уровень сигнала')
plt.grid(True, alpha=0.3)

plt.tight_layout()
plt.show()
