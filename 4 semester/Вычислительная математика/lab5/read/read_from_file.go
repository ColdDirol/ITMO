package read

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func FromFileWithFindingX(path string) (findingValues []float64, xValues []float64, yValues []float64, err error) {
	// Открываем файл
	file, err := os.Open(path)
	if err != nil {
		return nil, nil, nil, err
	}
	defer file.Close()

	// Создаем новый сканер для чтения файла построчно
	scanner := bufio.NewScanner(file)

	// Читаем остальные строки
	for scanner.Scan() {
		// Разбиваем строку на отдельные значения
		parts := strings.Fields(scanner.Text())

		// Проверяем название (X или Y)
		switch parts[0] {
		case "F":
			// Пропускаем первый элемент (название)
			for _, part := range parts[1:] {
				// Преобразуем значение в float64
				val, err := strconv.ParseFloat(part, 64)
				if err != nil {
					return nil, nil, nil, err
				}
				// Добавляем значение в срез xValues
				findingValues = append(findingValues, val)
			}
		case "X":
			// Пропускаем первый элемент (название)
			for _, part := range parts[1:] {
				// Преобразуем значение в float64
				val, err := strconv.ParseFloat(part, 64)
				if err != nil {
					return nil, nil, nil, err
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
					return nil, nil, nil, err
				}
				// Добавляем значение в срез yValues
				yValues = append(yValues, val)
			}
		}
	}

	// Проверяем ошибки сканирования
	if err := scanner.Err(); err != nil {
		return nil, nil, nil, err
	}

	if len(xValues) != len(yValues) {
		fmt.Println("Ошибка - должно быть одинаковое количество X и Y")
		os.Exit(1)
	}

	return findingValues, xValues, yValues, nil
}

func FromFileWithX(path string) (x float64, xValues []float64, yValues []float64, err error) {
	// Открываем файл
	file, err := os.Open(path)
	if err != nil {
		return 0, nil, nil, err
	}
	defer file.Close()

	// Создаем новый сканер для чтения файла построчно
	scanner := bufio.NewScanner(file)

	// Считываем первую строку для значения X
	if scanner.Scan() {
		line := scanner.Text()
		parts := strings.Fields(line)
		//if len(parts) < 2 {
		//	return 0, nil, nil, errors.New("invalid format: first line must contain X value")
		//}
		x, err = strconv.ParseFloat(strings.Split(parts[0], "=")[1], 64)
		if err != nil {
			return 0, nil, nil, err
		}
	}

	// Читаем остальные строки
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
					return 0, nil, nil, err
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
					return 0, nil, nil, err
				}
				// Добавляем значение в срез yValues
				yValues = append(yValues, val)
			}
		}
	}

	// Проверяем ошибки сканирования
	if err := scanner.Err(); err != nil {
		return 0, nil, nil, err
	}

	if len(xValues) != len(yValues) {
		fmt.Println("Ошибка - должно быть одинаковое количество X и Y")
		os.Exit(1)
	}

	return x, xValues, yValues, nil
}

func FromFileOnlyWithXValuesYValues(path string) (xValues []float64, yValues []float64, err error) {
	// Открываем файл
	file, err := os.Open(path)
	if err != nil {
		return nil, nil, err
	}
	defer file.Close()

	// Создаем новый сканер для чтения файла построчно
	scanner := bufio.NewScanner(file)

	// Читаем остальные строки
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

	if len(xValues) != len(yValues) {
		fmt.Println("Ошибка - должно быть одинаковое количество X и Y")
		os.Exit(1)
	}

	return xValues, yValues, nil
}
