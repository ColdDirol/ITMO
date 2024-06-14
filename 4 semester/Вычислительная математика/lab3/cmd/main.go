package main

import (
	"bufio"
	"compMathLab3/functions"
	"compMathLab3/methods"
	"compMathLab3/utils"
	"fmt"
	"math"
	"os"
	"strconv"
)

const (
	// 1 2 = 3.58
	f1text = "5x^3 - 2x^2 + 3x - 15"
	// 1 3 = -44
	f2text = "2x^3 - 9x^2 - 7x + 11"
	// 1 2 = -8.08
	f3text = "1x^3 + 2x^2 - 3x - 12"
	// 0 2 = 0.67
	f4text = "4x^3 - 5x^2 + 6x - 7"
	// 1 2 = 21.42
	f5text = "3x^3 + 5x^2 + 3x - 6"
	// 3 5 = 188
	f6text = "2x^3 - 3x^2 - 5x + 27"
	// 0 1 = 2
	f7text = "1 / sqrt(x)"
	// 1 2 = не существует
	f8text = "1 / (1 - x)"
)

type integralType string

func main() {
	selectEquation, typeOfIntegral, f, ad := selectEquation()
	a, b, e, n := selectValues(selectEquation)

	if typeOfIntegral == integralType("unusual") {
		fmt.Println("Выбран несобственный интеграл")
		isConvergence := utils.CheckConvergence(f, ad, a, b)
		if !isConvergence {
			fmt.Println("Интеграла не существует")
			os.Exit(1)
		}
		//h := (b - a) / float64(n)
		//k, method := selectMethod(ad, a, b, e, n, h)
		//compute(k, method, f, a, b, e, n, h)
		computeImproperIntegral(ad, a, b)
	} else {
		h := (b - a) / float64(n)
		k, method := selectMethod(f, a, b, e, n, h)
		if method == nil {
			fmt.Println("Ошибка")
			fmt.Println("Выбранный метод решения равен nil!")
			os.Exit(1)
		}
		compute(k, method, f, a, b, e, n, h)
	}
}

func selectEquation() (string, integralType, func(float64) float64, func(float64) float64) {
	fmt.Println("Выберите уравнение для интегрирования:")
	fmt.Println("1.", f1text)
	fmt.Println("2.", f2text)
	fmt.Println("3.", f3text)
	fmt.Println("4.", f4text)
	fmt.Println("5.", f5text)
	fmt.Println("6.", f6text)
	fmt.Println("7.", f7text)
	fmt.Println("8.", f8text)

	var selectionMode string
	for {
		fmt.Println("Выберите уравнение (1-8):")
		fmt.Fscan(os.Stdin, &selectionMode)
		switch selectionMode {
		case "1":
			fmt.Println("Выбрано уравнение:")
			return selectionMode, integralType("usual"), functions.F1, nil
		case "2":
			return selectionMode, integralType("usual"), functions.F2, nil
		case "3":
			return selectionMode, integralType("usual"), functions.F3, nil
		case "4":
			return selectionMode, integralType("usual"), functions.F4, nil
		case "5":
			return selectionMode, integralType("usual"), functions.F5, nil
		case "6":
			return selectionMode, integralType("usual"), functions.F6, nil
		case "7":
			return selectionMode, integralType("unusual"), functions.F7, functions.AD7
		case "8":
			return selectionMode, integralType("unusual"), functions.F8, functions.AD8
		default:
			fmt.Println("Укажите число, соответствующее предложенному варианту!")
		}
	}
}

func selectValues(selectedEquation string) (float64, float64, float64, int) {
	var a float64
	var b float64
	var e float64
	var n int = 4

	scanner := bufio.NewScanner(os.Stdin)
	var err error

	fmt.Println("Введите a:")
	for scanner.Scan() {
		line := scanner.Text()
		a, err = strconv.ParseFloat(line, 64)
		if err != nil {
			continue
		}
		fmt.Println("a =", a)
		break
	}

	fmt.Println("Введите b:")
	for scanner.Scan() {
		line := scanner.Text()
		b, err = strconv.ParseFloat(line, 64)
		if err != nil {
			continue
		}
		fmt.Println("b =", b)
		break
	}

	if selectedEquation != "7" || selectedEquation != "8" {
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
	}

	fmt.Println("n =", n)
	return a, b, e, n
}

func selectMethod(f func(float64) float64, a, b, e float64, n int, h float64) (int, func(f func(float64) float64, a float64, b float64, e float64, n int, h float64) float64) {
	fmt.Println("Выберите метод для интегрирования:")
	fmt.Println("1. Метод прямоугольников")
	fmt.Println("2. Метод трапеции")
	fmt.Println("3. Метод Симпсона")

	var selectionMode string
	for {
		fmt.Println("Выберите метод (1-3):")
		fmt.Fscan(os.Stdin, &selectionMode)
		switch selectionMode {
		case "1":
			fmt.Println("Выбран метод прямоугольников")
			return methods.RectangleMethod(f, a, b, e, n, h)
		case "2":
			fmt.Println("Выбран метод трапеций")
			return 2, methods.TrapezoidMethod
		case "3":
			fmt.Println("Выбран метод Симпсона")
			return 4, methods.SimpsonMethod
		default:
			fmt.Println("Укажите число, соответствующее предложенному варианту!")
		}
	}
}

func compute(k int, method func(f func(float64) float64, a float64, b float64, e float64, n int, h float64) float64, f func(float64) float64, a, b, e float64, n int, h float64) {
	var (
		I_h   float64
		I_h_2 float64
	)
	maxLimit := 250
	for i := n; i <= maxLimit; i++ {
		I_h = method(f, a, b, e, n, h)
		h = h / 2
		n = n * 2
		I_h_2 = method(f, a, b, e, n, h)

		if math.Abs(utils.RungeRule(I_h, I_h_2, k)) <= e {
			fmt.Println()
			fmt.Println("e :", e)
			fmt.Println("(I_h_2 - I_h) / (2^k - 1) :", utils.RungeRule(I_h, I_h_2, k))
			fmt.Println("(I_h_2 - I_h) / (2^k - 1) <= e")
			//n = i
			break
		}

		if i == maxLimit {
			fmt.Println("Число итераций превысило лимит:", maxLimit)
			break
		}
	}

	fmt.Println("Вычисленный интеграл:")
	fmt.Println(I_h)

	//fmt.Println(I_h_2)

	fmt.Println("Число разбиения интервала интегрирования для достижения требуемой точности:")
	fmt.Println(n)
}

func computeImproperIntegral(ad func(float64) float64, a float64, b float64) {
	fmt.Println("Вычисленный интеграл:", ad(b)-ad(a))
}
