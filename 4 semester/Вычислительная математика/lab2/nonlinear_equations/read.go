package nonlinear_equations

import (
	"bufio"
	"fmt"
	"lab2/nonlinear_equations/tools"
	"lab2/utils"
	"os"
	"strconv"
	"strings"
)

func ReadFileForChordMethod(filename string) (float64, float64, float64, []float64) {
	var (
		e            float64
		a            float64
		b            float64
		coefficients []float64
	)

	file, err := os.Open(filename)
	if err != nil {
		fmt.Println("Ошибка при открытии файла:", err)
		os.Exit(1)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	// Чтение первой строки, содержащей e
	if scanner.Scan() {
		line := scanner.Text()
		e, err = strconv.ParseFloat(line, 64)
		if err != nil {
			fmt.Println("Ошибка при чтении числа e:", err)
			os.Exit(1)
		}
		fmt.Println("e =", e)
	} else {
		fmt.Println("Файл пуст или отсутствует строка с числом e")
		os.Exit(1)
	}

	// Чтение третьей строки, содержащей x1, x2, ..., xn
	if scanner.Scan() {
		line := scanner.Text()
		coefficientStrings := strings.Fields(line)
		coefficients = make([]float64, len(coefficientStrings))

		for i, xStr := range coefficientStrings {
			x, err := strconv.ParseFloat(xStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа x%d: %v\n", i+1, err)
				os.Exit(1)
			}
			coefficients[i] = x
		}
	} else {
		fmt.Println("Отсутствует третья строка с данными x")
		os.Exit(1)
	}

	fmt.Println("Считанная функция:")
	PrintSliceAsFunction(coefficients)

	utils.ReverseSlice(coefficients)
	tools.CreateGraphWithIntervals(-10, 10, coefficients)
	fmt.Println("График создан")

	fmt.Println()
	CheckRoots(coefficients)
	fmt.Println()

	// Чтение второй строки, содержащей a и b
	if scanner.Scan() {
		line := scanner.Text()
		parts := strings.Fields(line)
		if len(parts) != 2 {
			fmt.Println("Неверный формат второй строки файла")
			os.Exit(1)
		}

		a, err = strconv.ParseFloat(parts[0], 64)
		if err != nil {
			fmt.Println("Ошибка при чтении числа a:", err)
			os.Exit(1)
		}

		b, err = strconv.ParseFloat(parts[1], 64)
		if err != nil {
			fmt.Println("Ошибка при чтении числа b:", err)
			os.Exit(1)
		}

		fmt.Println("a =", a)
		fmt.Println("b =", b)
	} else {
		fmt.Println("Отсутствует вторая строка с данными a и b")
		os.Exit(1)
	}

	if err := scanner.Err(); err != nil {
		fmt.Println("Ошибка при сканировании файла:", err)
		os.Exit(1)
	}

	return e, a, b, coefficients
}

func ReadConsoleForChordMethod() (float64, float64, float64, []float64) {
	var (
		e            float64
		a            float64
		b            float64
		coefficients []float64
	)

	// Создание сканера для чтения из стандартного ввода
	scanner := bufio.NewScanner(os.Stdin)
	var err error

	// Чтение первой строки, содержащей e
	fmt.Println("Введите e:")
	for scanner.Scan() {
		line := scanner.Text()
		e, err = strconv.ParseFloat(line, 64)
		if err != nil {
			continue
		}
		fmt.Println("e =", e)
		break
	}

	// Чтение третьей строки, содержащей x1, x2, ..., xn
	fmt.Println("Введите коэффициенты x1, x2, ..., xn через пробел:")
	for scanner.Scan() {
		line := scanner.Text()
		coefficientStrings := strings.Fields(line)
		coefficients = make([]float64, len(coefficientStrings))

		for i, xStr := range coefficientStrings {
			x, err := strconv.ParseFloat(xStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа x%d: %v\n", i+1, err)
				fmt.Println("Введите коэффициенты x1, x2, ..., xn через пробел:")
				continue
			}
			coefficients[i] = x
		}
		break
	}

	fmt.Println("Считанная функция:")
	PrintSliceAsFunction(coefficients)

	utils.ReverseSlice(coefficients)
	tools.CreateGraphWithIntervals(-10, 10, coefficients)
	fmt.Println("График создан")

	fmt.Println()
	CheckRoots(coefficients)
	fmt.Println()

	// Чтение второй строки, содержащей a и b
	fmt.Println("Введите a и b через пробел:")
	for scanner.Scan() {
		line := scanner.Text()
		parts := strings.Fields(line)
		if len(parts) != 2 {
			fmt.Println("Неверный формат второй строки")
			fmt.Println("Введите a и b через пробел:")
			continue
		}

		a, err = strconv.ParseFloat(parts[0], 64)
		if err != nil {
			fmt.Println("Ошибка при чтении числа a:", err)
			fmt.Println("Введите a и b через пробел:")
			continue
		}

		b, err = strconv.ParseFloat(parts[1], 64)
		if err != nil {
			fmt.Println("Ошибка при чтении числа b:", err)
			fmt.Println("Введите a и b через пробел:")
			continue
		}

		fmt.Println("a =", a)
		fmt.Println("b =", b)
		break
	}

	if err = scanner.Err(); err != nil {
		fmt.Println("Ошибка при сканировании стандартного ввода:", err)
		os.Exit(1)
	}

	return e, a, b, coefficients
}

func ReadFileForNewtonMethod(filename string) (float64, float64, float64, []float64) {
	var (
		e            float64
		a            float64
		b            float64
		coefficients []float64
	)

	// Открытие файла для чтения
	file, err := os.Open(filename)
	if err != nil {
		fmt.Println("Ошибка при открытии файла:", err)
		os.Exit(1)
	}
	defer file.Close()

	// Создание сканера для чтения из файла
	scanner := bufio.NewScanner(file)

	// Чтение первой строки, содержащей e
	if scanner.Scan() {
		line := scanner.Text()
		e, err = strconv.ParseFloat(line, 64)
		if err != nil {
			fmt.Println("Ошибка при чтении числа e:", err)
			os.Exit(1)
		}
		fmt.Println("e =", e)
	} else {
		fmt.Println("Файл пуст или отсутствует строка с числом e")
		os.Exit(1)
	}

	// Чтение третьей строки, содержащей x1, x2, ..., xn
	if scanner.Scan() {
		line := scanner.Text()
		coefficientStrings := strings.Fields(line)
		coefficients = make([]float64, len(coefficientStrings))

		for i, xStr := range coefficientStrings {
			x, err := strconv.ParseFloat(xStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа x%d: %v\n", i+1, err)
				os.Exit(1)
			}
			coefficients[i] = x
		}
	} else {
		fmt.Println("Отсутствует третья строка с данными x")
		os.Exit(1)
	}

	fmt.Println("Считанная функция:")
	PrintSliceAsFunction(coefficients)

	utils.ReverseSlice(coefficients)
	tools.CreateGraphWithIntervals(-10, 10, coefficients)
	fmt.Println("График создан")

	fmt.Println()
	CheckRoots(coefficients)
	fmt.Println()

	// Чтение второй строки, содержащей a и b
	if scanner.Scan() {
		line := scanner.Text()
		parts := strings.Fields(line)
		if len(parts) != 2 {
			fmt.Println("Неверный формат второй строки файла")
			os.Exit(1)
		}

		a, err = strconv.ParseFloat(parts[0], 64)
		if err != nil {
			fmt.Println("Ошибка при чтении числа a:", err)
			os.Exit(1)
		}

		b, err = strconv.ParseFloat(parts[1], 64)
		if err != nil {
			fmt.Println("Ошибка при чтении числа b:", err)
			os.Exit(1)
		}

		fmt.Println("a =", a)
		fmt.Println("b =", b)
	} else {
		fmt.Println("Отсутствует вторая строка с данными a и b")
		os.Exit(1)
	}

	if err := scanner.Err(); err != nil {
		fmt.Println("Ошибка при сканировании файла:", err)
		os.Exit(1)
	}

	return e, a, b, coefficients
}

func ReadConsoleForNewtonMethod() (float64, float64, float64, []float64) {
	var (
		e            float64
		a            float64
		b            float64
		coefficients []float64
	)

	// Создание сканера для чтения из стандартного ввода
	scanner := bufio.NewScanner(os.Stdin)
	var err error

	// Чтение первой строки, содержащей e
	fmt.Println("Введите e:")
	for scanner.Scan() {
		line := scanner.Text()
		e, err = strconv.ParseFloat(line, 64)
		if err != nil {
			continue
		}
		fmt.Println("e =", e)
		break
	}

	// Чтение третьей строки, содержащей x1, x2, ..., xn
	fmt.Println("Введите коэффициенты x1, x2, ..., xn через пробел:")
	for scanner.Scan() {
		line := scanner.Text()
		coefficientStrings := strings.Fields(line)
		coefficients = make([]float64, len(coefficientStrings))

		for i, xStr := range coefficientStrings {
			x, err := strconv.ParseFloat(xStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа x%d: %v\n", i+1, err)
				fmt.Println("Введите коэффициенты x1, x2, ..., xn через пробел:")
				continue
			}
			coefficients[i] = x
		}

		break
	}

	fmt.Println("Считанная функция:")
	PrintSliceAsFunction(coefficients)

	utils.ReverseSlice(coefficients)
	tools.CreateGraphWithIntervals(-10, 10, coefficients)
	fmt.Println("График создан")

	fmt.Println()
	CheckRoots(coefficients)
	fmt.Println()

	// Чтение второй строки, содержащей a и b
	fmt.Println("Введите a и b через пробел:")
	for scanner.Scan() {
		line := scanner.Text()
		parts := strings.Fields(line)
		if len(parts) != 2 {
			fmt.Println("Неверный формат второй строки")
			fmt.Println("Введите a и b через пробел:")
			continue
		}

		a, err = strconv.ParseFloat(parts[0], 64)
		if err != nil {
			fmt.Println("Ошибка при чтении числа a:", err)
			fmt.Println("Введите a и b через пробел:")
			continue
		}

		b, err = strconv.ParseFloat(parts[1], 64)
		if err != nil {
			fmt.Println("Ошибка при чтении числа b:", err)
			fmt.Println("Введите a и b через пробел:")
			continue
		}

		fmt.Println("a =", a)
		fmt.Println("b =", b)
		break
	}

	if err := scanner.Err(); err != nil {
		fmt.Println("Ошибка при сканировании стандартного ввода:", err)
		os.Exit(1)
	}

	return e, a, b, coefficients
}

func PrintSliceAsFunction(coefficients []float64) {
	startCoeff := len(coefficients) - 1

	for i := 0; i <= len(coefficients)-1; i++ {
		if startCoeff == 0 {
			fmt.Printf("%g", coefficients[i])
		} else {
			fmt.Printf("%g*x^%d + ", coefficients[i], startCoeff)
		}
		startCoeff--
	}
	fmt.Println()
}

func CheckRoots(coefficients []float64) {
	a := -10
	b := 10

	var prevValue float64
	for i := a; i < b; i++ {
		x1 := float64(i)
		x2 := float64(i + 1)

		y1 := tools.FindFunctrion(x1, coefficients)
		y2 := tools.FindFunctrion(x2, coefficients)

		if y1*y2 < 0 && prevValue != y1 {
			fmt.Printf("Корени в интервале: [%d;%d]\n", i, i+1)
			continue
		}

		prevValue = y1
	}
}
