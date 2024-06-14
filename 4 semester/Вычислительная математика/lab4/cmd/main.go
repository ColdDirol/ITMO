package main

import (
	"compMathLab4/read"
	"compMathLab4/research/exponential_func"
	"compMathLab4/research/linear_func"
	"compMathLab4/research/logarithmic_func"
	"compMathLab4/research/polynomial_function_2nd_degree"
	"compMathLab4/research/polynomial_function_3nd_degree"
	"compMathLab4/research/pow_func"
	"fmt"
	"os"
)

type answer struct {
	detCoefficient       float64
	equationCoefficients []float64
}

func main() {
	selectedRead := selectReadMode()

	var xValues []float64
	var yValues []float64
	var err error

	// Читаем значения из файла
	if selectedRead == "file" {
		xValues, yValues, err = read.FromFile("input.txt")
		if err != nil {
			fmt.Println("Ошибка чтения файла:", err)
			return
		}
	} else if selectedRead == "console" {
		xValues, yValues, err = read.FromConsole()
		if err != nil {
			fmt.Println("Ошибка чтения консоли:", err)
			return
		}
	}

	if len(xValues) != len(yValues) {
		fmt.Println("Длина массива x не равна длине массива y")
		os.Exit(1)
	} else if len(xValues) == 0 || len(xValues) == 1 {
		fmt.Println("Значений недостаточно")
		os.Exit(1)
	}

	detValues := make([]answer, 6)

	det, coefficients := linear_func.LinearFunc(xValues, yValues)
	detValues[0] = answer{det, coefficients}
	det, coefficients = polynomial_function_2nd_degree.PolynomialFunction2ndDegree(xValues, yValues)
	detValues[1] = answer{det, coefficients}
	det, coefficients = polynomial_function_3nd_degree.PolynomialFunction3rdDegree(xValues, yValues)
	detValues[2] = answer{det, coefficients}
	det, coefficients = exponential_func.ExponentialFunc(xValues, yValues)
	detValues[3] = answer{det, coefficients}
	det, coefficients = logarithmic_func.LogarithmicFunc(xValues, yValues)
	detValues[4] = answer{det, coefficients}
	det, coefficients = pow_func.PowerFunc(xValues, yValues)
	detValues[5] = answer{det, coefficients}

	//fmt.Println(detValues)

	_, maxIndex := bestAnswer(detValues)
	if maxIndex == -1 {
		fmt.Println("Ошибка")
		os.Exit(1)
	}

	fmt.Println()
	fmt.Println()
	fmt.Println("---")
	fmt.Println("Наилучшая аппроксимирующая функция:")
	switch maxIndex {
	case 0:
		fmt.Printf("Линейная: %gx + %g\n", detValues[maxIndex].equationCoefficients[0], detValues[maxIndex].equationCoefficients[1])
		fmt.Println("Достоверность аппроксимации:", detValues[maxIndex].detCoefficient)
	case 1:
		fmt.Printf("Полиномиальная функция 2-й степени: %g + %g*x + %g*x^2\n",
			detValues[maxIndex].equationCoefficients[0],
			detValues[maxIndex].equationCoefficients[1],
			detValues[maxIndex].equationCoefficients[2])
		fmt.Println("Достоверность аппроксимации:", detValues[maxIndex].detCoefficient)
	case 2:
		fmt.Printf("Полиномиальная функция 3-й степени: %g + %g*x + %g*x^2 + %g*x^3\n",
			detValues[maxIndex].equationCoefficients[0],
			detValues[maxIndex].equationCoefficients[1],
			detValues[maxIndex].equationCoefficients[2],
			detValues[maxIndex].equationCoefficients[3])
		fmt.Println("Достоверность аппроксимации:", detValues[maxIndex].detCoefficient)
	case 3:
		fmt.Printf("Экспоненциальная функция: %ge^(%gx)\n", detValues[maxIndex].equationCoefficients[0], detValues[maxIndex].equationCoefficients[1])
		fmt.Println("Достоверность аппроксимации:", detValues[maxIndex].detCoefficient)
	case 4:
		fmt.Printf("Логарифмическая функция: %gln(x) + %g\n", detValues[maxIndex].equationCoefficients[0], detValues[maxIndex].equationCoefficients[1])
		fmt.Println("Достоверность аппроксимации:", detValues[maxIndex].detCoefficient)
	case 5:
		fmt.Printf("Степенная функция: %gx^%g", detValues[maxIndex].equationCoefficients[0], detValues[maxIndex].equationCoefficients[1])
		fmt.Println("Достоверность аппроксимации:", detValues[maxIndex])
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
			return "file"
		case "2":
			return "console"
		default:
			fmt.Println("Укажите число, соответствующее предложенному варианту!")
		}
	}
}

func bestAnswer(values []answer) (float64, int) {
	maxDetCoefficient := values[0].detCoefficient
	maxIndex := 0

	for i, ans := range values {
		if ans.detCoefficient > maxDetCoefficient {
			maxDetCoefficient = ans.detCoefficient
			maxIndex = i
		}
	}

	return maxDetCoefficient, maxIndex
}
