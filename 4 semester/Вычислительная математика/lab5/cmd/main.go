package main

import (
	"compMathLab5/graph"
	"compMathLab5/methods/lagrange"
	"compMathLab5/methods/newton_finite_differences"
	"compMathLab5/methods/newton_separated_differences"
	"compMathLab5/read"
	"fmt"
	"os"
)

const (
	lagrange_method       = "lagrange_method"
	newton_sep_method     = "newton_sep_method"
	newton_fin_fv_method  = "newton_fin_fv_method"
	newton_fin_pol_method = "newton_fin_pol_method"
)

const (
	file    = "file"
	console = "console"
)

// 1 - Многочлен Лагранжа
// 2 - Многочлен Ньютона с разделенными разностями
// 3 - Многочлен Ньютона с конечными разностями

func solve(method, readMode, path string) {
	switch method {
	case lagrange_method:
		var (
			x       float64
			xValues []float64
			yValues []float64
		)
		if readMode == file {
			x, xValues, yValues, _ = read.FromFileWithX(path)
		}
		if readMode == console {
			x, xValues, yValues, _ = read.FromConsoleWithX()
		}

		fmt.Println()
		fmt.Println()
		fmt.Println("X:", x)
		fmt.Println("X значения:", xValues)
		fmt.Println("Y значения:", yValues)

		fmt.Println()
		fmt.Println()
		fmt.Println("Степень искомого многочлена:", len(xValues)-1)

		lagrange.WithX(x, xValues, yValues)
		graph.CreateAll(xValues, yValues)
	case newton_sep_method:
		var (
			x       float64
			xValues []float64
			yValues []float64
		)
		if readMode == file {
			x, xValues, yValues, _ = read.FromFileWithX(path)
		}
		if readMode == console {
			x, xValues, yValues, _ = read.FromConsoleWithX()
		}

		fmt.Println()
		fmt.Println()
		fmt.Println("X:", x)
		fmt.Println("X значения:", xValues)
		fmt.Println("Y значения:", yValues)

		newton_separated_differences.NewtonSeparatedDifferences(x, xValues, yValues)
		graph.CreateAll(xValues, yValues)
	case newton_fin_fv_method:
		var (
			findingValues []float64
			xValues       []float64
			yValues       []float64
		)
		if readMode == file {
			findingValues, xValues, yValues, _ = read.FromFileWithFindingX(path)
		}
		if readMode == console {
			findingValues, xValues, yValues, _ = read.FromConsoleWithFindingX()
		}

		fmt.Println()
		fmt.Println()
		fmt.Println("Ищем X:", findingValues)
		fmt.Println("X значения:", xValues)
		fmt.Println("Y значения:", yValues)

		newton_finite_differences.NewtonFiniteDifferencesWithFindingValues(findingValues, xValues, yValues)
		graph.CreateAll(xValues, yValues)
	case newton_fin_pol_method:
		var (
			xValues []float64
			yValues []float64
		)
		if readMode == file {
			xValues, yValues, _ = read.FromFileOnlyWithXValuesYValues(path)
		}
		if readMode == console {
			xValues, yValues, _ = read.FromConsoleOnlyWithXValuesYValues()
		}

		fmt.Println()
		fmt.Println()
		fmt.Println("X значения:", xValues)
		fmt.Println("Y значения:", yValues)

		newton_finite_differences.NewtonFiniteDifferencesWithoutFindingValues(xValues, yValues)
		graph.CreateAll(xValues, yValues)
	default:
		fmt.Println("Произошла ошибка - не выбран метод")
		os.Exit(1)
	}
}

func main() {
	// Путь к файлу
	path := "input.txt"
	method := selectMethod()
	readMode := selectReadMode()

	solve(method, readMode, path)

	// Читаем значения из файла
	//xValues, yValues, err := read.FromFileOnlyWithXValuesYValues(path)
	//findingValues, xValues, yValues, err := read.FromFileWithFindingX(path)
	//if err != nil {
	//	fmt.Println("Ошибка чтения файла:", err)
	//	return
	//}

	// Выводим значения
	//fmt.Println("Ищем x:", findingValues)
	//fmt.Println()
	//fmt.Println()
	//fmt.Println("X значения:", xValues)
	//fmt.Println("Y значения:", yValues)

	//newton_finite_differences.NewtonFiniteDifferencesWithFindingValues(findingValues, xValues, yValues)
	//newton_finite_differences.NewtonFiniteDifferencesWithoutFindingValues(xValues, yValues)
}

func selectMethod() (selectionMode string) {
	fmt.Println("\n\nВыберите метод:")
	fmt.Println("1. Многочлен Лагранжа")
	fmt.Println("2. Многочлен Ньютона с разделенными разностями")
	fmt.Println("3. Многочлен Ньютона с конечными разностями - нахождение приближенных значений")
	fmt.Println("4. Многочлен Ньютона с конечными разностями - построение многочлена Ньютона по X и Y")

	for {
		fmt.Println("Выберите тип ввода (1-3):")
		fmt.Fscan(os.Stdin, &selectionMode)
		switch selectionMode {
		case "1":
			fmt.Println("Выбран метод - Многочлен Лагранжа")
			return lagrange_method
		case "2":
			fmt.Println("Выбран метод - Многочлен Ньютона с разделенными разностями")
			return newton_sep_method
		case "3":
			fmt.Println("Выбран метод - Многочлен Ньютона с конечными разностями - нахождение приближенных значений")
			return newton_fin_fv_method
		case "4":
			fmt.Println("Выбран метод - Многочлен Ньютона с конечными разностями - построение многочлена Ньютона по X и Y")
			return newton_fin_pol_method
		default:
			fmt.Println("Укажите число, соответствующее предложенному варианту!")
		}
	}
}

func selectReadMode() string {
	fmt.Println("\n\nВыберите тип ввода:")
	fmt.Println("1. Ввод из файла")
	fmt.Println("2. Ввод с консоли")

	var selectionMode string
	for {
		fmt.Println("Выберите тип ввода (1-2):")
		fmt.Fscan(os.Stdin, &selectionMode)
		switch selectionMode {
		case "1":
			return file
		case "2":
			return console
		default:
			fmt.Println("Укажите число, соответствующее предложенному варианту!")
		}
	}
}
