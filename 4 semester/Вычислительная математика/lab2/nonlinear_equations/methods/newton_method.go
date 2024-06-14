package methods

import (
	"fmt"
	"lab2/nonlinear_equations/tools"
	"math"
	"os"
)

func NewtonMethod(e, a, b float64, coefficients, derivative1, derivative2 []float64) {
	var (
		xi            float64
		f_xi          float64
		d1_xi         float64
		x_i_plus_1    float64
		differenceAbs float64
	)

	// Условие сходимости
	if (tools.FindFunctrion(a, derivative1)*tools.FindFunctrion(b, derivative1) > 0) &&
		(tools.FindFunctrion(a, derivative2)*tools.FindFunctrion(b, derivative2) > 0) {
	} else {
		fmt.Println("Не удалось установить начальное приближение!")
		os.Exit(1)
	}

	if tools.FindFunctrion(a, coefficients)*tools.FindFunctrion(a, derivative2) > 0 {
		xi = a
	} else if tools.FindFunctrion(b, coefficients)*tools.FindFunctrion(b, derivative2) > 0 {
		xi = b
	} else {
		fmt.Println("Не удалось установить начальное приближение!")
		os.Exit(1)
	}

	iterationsLimit := 1000

	fmt.Println("[ # ]	[ x_i ]		[ f'(x_i) ]		[ f(x_i) ]		[ x_i+1 ]		[|x_i+1 - x_i|]")

	for i := 0; i <= iterationsLimit; i++ {
		f_xi = tools.FindFunctrion(xi, coefficients)
		d1_xi = tools.FindFunctrion(xi, derivative1)
		if d1_xi == 0 {
			fmt.Println("Стоп")
			fmt.Println("Причина:", "f'(xi) == 0")
			fmt.Println("Ответ:", "не найден из-за невыполнения условия сходимости!")
			os.Exit(0)
		}
		x_i_plus_1 = xi - (f_xi / d1_xi)
		differenceAbs = math.Abs(x_i_plus_1 - xi)
		fmt.Printf("%d	%5f	%5f		%5f		%5f		%5f\n", i, xi, f_xi, d1_xi, x_i_plus_1, differenceAbs)

		if differenceAbs <= e {
			fmt.Println("Стоп")
			fmt.Println("Причина:", "|x_i+1 - x_i| <= e")
			fmt.Println("Значение функции:", f_xi)
			fmt.Println("Ответ:", x_i_plus_1)
			os.Exit(0)
		} else if math.Abs(f_xi/d1_xi) <= e {
			fmt.Println("Стоп")
			fmt.Println("Причина:", "|f(x_i) / f'(x_i)| <= e")
			fmt.Println("Значение функции:", f_xi)
			fmt.Println("Ответ:", x_i_plus_1)
			os.Exit(0)
		} else if math.Abs(f_xi) <= e {
			fmt.Println("Стоп")
			fmt.Println("Причина:", "|f(x_i)| <= e")
			fmt.Println("Значение функции:", f_xi)
			fmt.Println("Ответ:", x_i_plus_1)
			os.Exit(0)
		}

		xi = x_i_plus_1
	}

	fmt.Printf("Лимит в %d итераций исчерпан\n", iterationsLimit)
	fmt.Println("Остановка")
	os.Exit(1)
}
