package read

import (
	"bufio"
	"os"
	"strconv"
	"strings"
)

func FromFile(path string) (xValues []float64, yValues []float64, err error) {
	// Открываем файл
	file, err := os.Open(path)
	if err != nil {
		return nil, nil, err
	}
	defer file.Close()

	// Создаем новый сканер для чтения файла построчно
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		// Разбиваем строку на отдельные значения
		parts := strings.Fields(scanner.Text())

		// Проверяем название (X или Y)
		switch parts[0] {
		case "X":
			// Пропускаем первый элемент (название)
			for _, part := range parts[1:] {
				// Преобразуем значение в float64
				val, err := strconv.ParseFloat(part, 64)
				if err != nil {
					return nil, nil, err
				}
				// Добавляем значение в срез xValues
				xValues = append(xValues, val)
			}
		case "Y":
			// Пропускаем первый элемент (название)
			for _, part := range parts[1:] {
				// Преобразуем значение в float64
				val, err := strconv.ParseFloat(part, 64)
				if err != nil {
					return nil, nil, err
				}
				// Добавляем значение в срез yValues
				yValues = append(yValues, val)
			}
		}
	}

	// Проверяем ошибки сканирования
	if err := scanner.Err(); err != nil {
		return nil, nil, err
	}

	return xValues, yValues, nil
}
