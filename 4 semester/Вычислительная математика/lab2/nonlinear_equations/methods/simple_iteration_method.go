package methods

import (
	"fmt"
	"lab2/nonlinear_equations/tools"
	"math"
	"os"
)

func phiFunction(x, lambda float64, coefficients []float64) float64 {
	return x + lambda*tools.FindFunctrion(x, coefficients)
}

func phiDerivativeFunction(x, lambda float64, coefficients []float64) float64 {
	return 1 + lambda*tools.FindFunctrion(x, coefficients)
}

func SimpleIterationMethod(e, a, b float64, coefficients, derivative1, derivative2 []float64) {
	// Начальное приближение lambda
	lambda := initialLambda(a, b, derivative1)

	fmt.Println("Лямбда:", lambda)

	var (
		xi            float64
		x_i_plus_1    float64
		phi_x_plus_1  float64
		f_x_plus_1    float64
		differenceAbs float64
	)

	xi = a

	iterationsLimit := 1000

	fmt.Println("phi'(a)", phiDerivativeFunction(a, lambda, derivative1))
	fmt.Println("phi'(b)", phiDerivativeFunction(b, lambda, derivative1))

	fmt.Println("Index\tx_i\t\tx_i+1\t\t\tphi(x_i+1)\t\t\tf(x_i+1)\t\t\t|x_i+1 - x_i|")

	for i := 1; i <= iterationsLimit; i++ {
		x_i_plus_1 = phiFunction(xi, lambda, coefficients)
		phi_x_plus_1 = phiFunction(x_i_plus_1, lambda, coefficients)
		f_x_plus_1 = tools.FindFunctrion(x_i_plus_1, coefficients)
		differenceAbs = math.Abs(x_i_plus_1 - xi)

		fmt.Printf("%d\t%5f\t%5f\t\t%5f\t\t\t%5f\t\t\t%5ft\n", i, xi, x_i_plus_1, phi_x_plus_1, f_x_plus_1, differenceAbs)

		if i == iterationsLimit {
			fmt.Println("Количство итераций превышено")
		}

		if math.Abs(f_x_plus_1) <= e {
			fmt.Println("Стоп")
			fmt.Println("Причина:", "|f(x_i+1)| <= e")
			fmt.Println("Ответ:", x_i_plus_1)
			fmt.Println("Значение функции:", f_x_plus_1)
			fmt.Println("Ответ найден за:", i+1, "итераций")
			os.Exit(0)
		}

		xi = x_i_plus_1
	}
}

func initialLambda(a, b float64, derivative1 []float64) float64 {
	// Выбор начального приближения lambda
	maxDerivative := math.Max(math.Abs(tools.FindFunctrion(a, derivative1)), math.Abs(tools.FindFunctrion(b, derivative1)))
	if tools.FindFunctrion(a, derivative1) > 0 && tools.FindFunctrion(b, derivative1) > 0 {
		return -1 / maxDerivative
	} else {
		return 1 / maxDerivative
	}
}
