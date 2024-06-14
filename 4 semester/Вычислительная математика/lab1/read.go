package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func matrixSeparation(data [][]float64) ([][]float64, []float64) {
	// Создание coefficient_matrix с размерами, определенными на основе данных из файла
	coefficientMatrix := make([][]float64, len(data))
	for i := range coefficientMatrix {
		coefficientMatrix[i] = make([]float64, len(data[0])-1)
	}

	// Копирование данных из data в coefficient_matrix
	for i := 0; i < len(data); i++ {
		for j := 0; j < len(data[i])-1; j++ {
			coefficientMatrix[i][j] = data[i][j]
		}
	}

	// Создание среза для result_vector
	resultVector := make([]float64, len(data))

	// Заполнение result_vector значениями из последнего столбца массива data
	for i := range data {
		resultVector[i] = data[i][len(data[i])-1]
	}

	return coefficientMatrix, resultVector
}

func readFile() ([][]float64, []float64, float64) {
	stdinScanner := bufio.NewScanner(os.Stdin)
	var filename string
	fmt.Println("\nВведите название файла: ")
	if stdinScanner.Scan() {
		filename = stdinScanner.Text()
	}

	// Открытие файла
	file, err := os.Open(filename)
	if err != nil {
		panic("Ошибка открытия файла")
	}
	defer file.Close()

	// Создание массива для хранения данных
	var data [][]float64

	// Чтение файла построчно
	scanner := bufio.NewScanner(file)

	var epsilon float64
	// Проверяем, что есть строки для чтения
	if scanner.Scan() {
		epsilon, _ = strconv.ParseFloat(scanner.Text(), 64)
	} else {
		panic("Файл пустой")
	}

	for scanner.Scan() {
		line := scanner.Text()

		// Разделение строки на отдельные числа
		parts := strings.Fields(line)
		var numbers []float64
		for _, part := range parts {
			// Преобразование строки в число
			num, err := strconv.ParseFloat(part, 64)
			if err != nil {
				panic("Ошибка преобразования строки в число")
			}
			numbers = append(numbers, num)
		}

		// Добавление чисел в массив
		data = append(data, numbers)
	}

	// Проверка наличия ошибок при сканировании файла
	if err := scanner.Err(); err != nil {
		panic("Ошибка сканирования файла")
	}

	coefficientMatrix, resultVector := matrixSeparation(data)
	return coefficientMatrix, resultVector, epsilon
}

func readConsole() ([][]float64, []float64, float64) {
	var data [][]float64
	var epsilon float64
	var coefficientCount int
	var lineNumber int

	scanner := bufio.NewScanner(os.Stdin)

	// Считываем epsilon
	fmt.Print("\nВведите точность определения (epsilon): ")
	if scanner.Scan() {
		epsilon, _ = strconv.ParseFloat(scanner.Text(), 64)
		lineNumber++
	} else {
		panic("Недостаточно данных")
	}

	// Считываем остальные строки

	fmt.Println("\nВведите коэффициенты. Пример:\nЕсли СЛАУ имеет вид:\n3x_1 + 2x_2 = 4\n10x_1 - 7x_2 = 5\nТо введите:\n3 2 4\n10 -7 5\nВведите коэффициенты построчно:")

	for i := 1; scanner.Scan(); i++ {
		line := scanner.Text()
		parts := strings.Fields(line)

		// Если это первая строка с коэффициентами, запоминаем их количество
		if lineNumber == 1 {
			coefficientCount = len(parts)
		} else {
			// Проверяем, что количество коэффициентов в каждой строке совпадает
			if len(parts) != coefficientCount {
				fmt.Println("Ошибка: количество коэффициентов в строках не совпадает")
				return nil, nil, 0
			}
		}

		var numbers []float64
		for _, part := range parts {
			num, err := strconv.ParseFloat(part, 64)
			if err != nil {
				fmt.Println("Ошибка преобразования строки в число:", err)
				return nil, nil, 0
			}
			numbers = append(numbers, num)
		}

		data = append(data, numbers)
		if i == coefficientCount-1 {
			break
		}
		lineNumber++
	}

	if err := scanner.Err(); err != nil {
		fmt.Println("Ошибка сканирования ввода:", err)
		return nil, nil, 0
	}

	coefficientMatrix, resultVector := matrixSeparation(data)
	return coefficientMatrix, resultVector, epsilon
}
