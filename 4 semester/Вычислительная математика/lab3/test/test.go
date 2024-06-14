package main

import (
	"compMathLab3/utils"
	"fmt"
	"math"
)

func f1(x float64) float64 {
	return 1 / math.Sqrt(x) // 2
}

func f2(x float64) float64 {
	return 1 / (1 - x)
}

func f3(x float64) float64 {
	return 1 / x
}

func checkConvergenceTest(f func(float64) float64, a, b float64) {
	fmt.Println(f(0.0000001), f(0.0000002))

	if f(0.0000001)/f(0.0000002) >= 1.4 {
		fmt.Println("Функция расходится")
	} else {
		fmt.Println("Функция сходится")
	}
}

func main() {
	a := 0.0
	b := 1.0
	checkConvergenceTest(f2, a, b)
}

func compute(k int, method func(f func(float64) float64, a float64, b float64, e float64, n int, h float64) float64, f func(float64) float64, a, b, e float64, n int, h float64) {
	var (
		I_h   float64
		I_h_2 float64
	)
	for i := n; i <= 1000; i++ {
		I_h = method(f, a, b, e, n, h)
		h = h / 2
		n = n * 2
		I_h_2 = method(f, a, b, e, n, h)

		if math.Abs(utils.RungeRule(I_h, I_h_2, k)) <= e {
			fmt.Println("(I_h_2 - I_h) / (2^k - 1) :", utils.RungeRule(I_h, I_h_2, k))
			n = i
			break
		}

		if i == 1000 {
			fmt.Println("Превышено число операций.")
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
