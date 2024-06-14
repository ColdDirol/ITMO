package methods

import (
	"fmt"
	"lab2/nonlinear_equations/tools"
	"math"
	"os"
)

const (
	fixed   = "fixed"
	unfixed = "unfixed"
)

func ChordMethod(e, a, b float64, coefficients, derivative1, derivative2 []float64) {
	var (
		x             float64
		prev_x        float64
		f_a           float64
		f_b           float64
		f_x           float64
		differenceAbs float64
	)

	iterationsLimit := 1000

	fmt.Println("[ # ]	[ a ]		[ b ]		[ x ]		[ F(a) ]		[ F(b) ]		[ F(x) ]		[ |x_i+1 - x_i| ]")

	for i := 0; i <= iterationsLimit; i++ {
		// Подсчет
		x = ((a*tools.FindFunctrion(b, coefficients) - b*tools.FindFunctrion(a, coefficients)) / (tools.FindFunctrion(b, coefficients) - tools.FindFunctrion(a, coefficients)))
		f_a = tools.FindFunctrion(a, coefficients)
		f_b = tools.FindFunctrion(b, coefficients)
		f_x = tools.FindFunctrion(x, coefficients)

		if i == 0 {
			differenceAbs = math.Abs(math.Abs(a) - math.Abs(x))
		} else {
			differenceAbs = math.Abs(math.Abs(x) - math.Abs(prev_x))
		}

		fmt.Printf("%d	%5f	%5f	%5f	%5f		%5f		%5f		%5f\n", i, a, b, x, f_a, f_b, f_x, differenceAbs)

		// Выход, если:
		if differenceAbs <= e {
			fmt.Println("Причина:", "|x_i+1 - x_i| <= e")
			//os.Exit(0)
		}
		if math.Abs(a-b) <= e {
			fmt.Println("Причина:", "|a - b| <= e")
			//os.Exit(0)
		}
		if math.Abs(f_x) <= e {
			fmt.Println("Причина:", "|f(x)| <= e")
			//os.Exit(0)
		}
		if differenceAbs <= e || math.Abs(a-b) <= e || math.Abs(f_x) <= e {
			fmt.Println("Ответ:", x)
			fmt.Println("Значение функции:", f_x)
			os.Exit(1)
		}

		if tools.FindFunctrion(x, coefficients)*tools.FindFunctrion(b, coefficients) < 0 {
			a = x
		} else if tools.FindFunctrion(x, coefficients)*tools.FindFunctrion(a, coefficients) < 0 {
			b = x
		}

		prev_x = x
	}

	fmt.Printf("Лимит в %d итераций исчерпан\n", iterationsLimit)
	fmt.Println("Остановка")
	os.Exit(1)
}
