package one_step_methods

import (
	"fmt"
	"math"
)

const MAX_ITERS = 200

func rungeRule(x, y, x2, y2 []float64, p int, eps float64) bool {
	for i := range x {
		errorValue := math.Abs(y[i]-y2[2*i]) / (math.Pow(2, float64(p)) - 1)
		if errorValue > eps {
			return false
		}
	}
	return true
}

func computeNForHalfH(n int) int {
	return 2 * n
}

func OneStepMethodSolve(method func(func(float64, float64) float64, float64, float64, float64, int) ([]float64, []float64),
	f func(float64, float64) float64, x0, y0, hInitial float64, n int, eps float64, p int) ([]float64, []float64) {

	iter := 0
	for {
		if iter >= MAX_ITERS {
			x, y := method(f, x0, y0, hInitial, n)
			fmt.Println("Warning! Превышен лимит итераций:", MAX_ITERS)
			return x, y
		}

		x, y := method(f, x0, y0, hInitial, n)
		h2 := hInitial / 2
		n2 := n * 2
		x2, y2 := method(f, x0, y0, h2, n2)

		if iter <= 1 {
			fmt.Println("y[n]:", y[len(y)-1])
		}

		if rungeRule(x, y, x2, y2, p, eps) {
			return x, y
		}

		hInitial = h2
		n = n2
		iter++
	}
}
