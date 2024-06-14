package read

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func FromConsoleWithFindingX() (findingValues []float64, xValues []float64, yValues []float64, err error) {
	scanner := bufio.NewScanner(os.Stdin)

	fmt.Println("Введите X, значения которых мы будем искать:")
	if scanner.Scan() {
		line := scanner.Text()
		findingValuesStrings := strings.Fields(line)
		findingValues = make([]float64, len(findingValuesStrings))

		for i, xStr := range findingValuesStrings {
			f, err := strconv.ParseFloat(xStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа x%d: %v\n", i+1, err)
				fmt.Println("Введите значения X через пробел:")
				return nil, nil, nil, err
			}
			findingValues[i] = f
		}
	}

	fmt.Println("Введите значения X через пробел:")
	if scanner.Scan() {
		line := scanner.Text()
		xValuesStrings := strings.Fields(line)
		xValues = make([]float64, len(xValuesStrings))

		for i, xStr := range xValuesStrings {
			x, err := strconv.ParseFloat(xStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа x%d: %v\n", i+1, err)
				fmt.Println("Введите значения X через пробел:")
				return nil, nil, nil, err
			}
			xValues[i] = x
		}
	}

	fmt.Println("Введите значения Y через пробел:")
	if scanner.Scan() {
		line := scanner.Text()
		yValuesStrings := strings.Fields(line)
		yValues = make([]float64, len(yValuesStrings))

		for i, yStr := range yValuesStrings {
			y, err := strconv.ParseFloat(yStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа y%d: %v\n", i+1, err)
				fmt.Println("Введите значения Y через пробел:")
				return nil, nil, nil, err
			}
			yValues[i] = y
		}
	}

	if len(xValues) != len(yValues) {
		fmt.Println("Ошибка - должно быть одинаковое количество X и Y")
		os.Exit(1)
	}

	return
}

func FromConsoleOnlyWithXValuesYValues() (xValues []float64, yValues []float64, err error) {
	scanner := bufio.NewScanner(os.Stdin)

	fmt.Println("Введите значения X через пробел:")
	if scanner.Scan() {
		line := scanner.Text()
		xValuesStrings := strings.Fields(line)
		xValues = make([]float64, len(xValuesStrings))

		for i, xStr := range xValuesStrings {
			x, err := strconv.ParseFloat(xStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа x%d: %v\n", i+1, err)
				fmt.Println("Введите значения X через пробел:")
				return nil, nil, err
			}
			xValues[i] = x
		}
	}

	fmt.Println("Введите значения Y через пробел:")
	if scanner.Scan() {
		line := scanner.Text()
		yValuesStrings := strings.Fields(line)
		yValues = make([]float64, len(yValuesStrings))

		for i, yStr := range yValuesStrings {
			y, err := strconv.ParseFloat(yStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа y%d: %v\n", i+1, err)
				fmt.Println("Введите значения Y через пробел:")
				return nil, nil, err
			}
			yValues[i] = y
		}
	}

	if len(xValues) != len(yValues) {
		fmt.Println("Ошибка - должно быть одинаковое количество X и Y")
		os.Exit(1)
	}

	return xValues, yValues, nil
}

func FromConsoleWithX() (x float64, xValues []float64, yValues []float64, err error) {
	// Создание сканера для чтения из стандартного ввода
	scanner := bufio.NewScanner(os.Stdin)

	// Чтение первой строки, содержащей e
	fmt.Println("Введите x:")
	for scanner.Scan() {
		line := scanner.Text()
		x, err = strconv.ParseFloat(line, 64)
		if err != nil {
			continue
		}
		fmt.Println("x =", x)
		break
	}

	fmt.Println("Введите значения X через пробел:")
	if scanner.Scan() {
		line := scanner.Text()
		xValuesStrings := strings.Fields(line)
		xValues = make([]float64, len(xValuesStrings))

		for i, xStr := range xValuesStrings {
			x, err := strconv.ParseFloat(xStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа x%d: %v\n", i+1, err)
				fmt.Println("Введите значения X через пробел:")
				return 0, nil, nil, err
			}
			xValues[i] = x
		}
	}

	fmt.Println("Введите значения Y через пробел:")
	if scanner.Scan() {
		line := scanner.Text()
		yValuesStrings := strings.Fields(line)
		yValues = make([]float64, len(yValuesStrings))

		for i, yStr := range yValuesStrings {
			y, err := strconv.ParseFloat(yStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа y%d: %v\n", i+1, err)
				fmt.Println("Введите значения Y через пробел:")
				return 0, nil, nil, err
			}
			yValues[i] = y
		}
	}

	if len(xValues) != len(yValues) {
		fmt.Println("Ошибка - должно быть одинаковое количество X и Y")
		os.Exit(1)
	}

	return
}
