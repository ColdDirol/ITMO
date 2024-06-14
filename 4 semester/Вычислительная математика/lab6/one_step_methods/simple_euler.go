package one_step_methods

import "compMathLab6/utils"

func EulerMethod(f func(float64, float64) float64, x0, y0, h float64, n int) ([]float64, []float64) {
	x := make([]float64, n+1)
	y := make([]float64, n+1)
	x[0], y[0] = utils.Rounding(x0), utils.Rounding(y0)

	for i := 1; i <= n; i++ {
		y[i] = utils.Rounding(y[i-1] + h*f(x[i-1], y[i-1]))
		x[i] = utils.Rounding(x[i-1] + h)
	}
	return x, y
}
