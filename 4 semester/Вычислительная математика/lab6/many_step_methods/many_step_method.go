package many_step_methods

import (
	"compMathLab6/one_step_methods"
	"compMathLab6/utils"
	"fmt"
	"math"
)

func computeNForHalfH(n int) int {
	return 2 * n
}

func MilneMethod(f func(float64, float64) float64, x0, y0, h float64, n int, eps float64, yPrecise func(float64, float64) float64) ([]float64, []float64) {
	x, y := one_step_methods.RungeKutta(f, x0, y0, h, n)

	for i := range x {
		fmt.Println(x[i], y[i])
	}
	fmt.Println()
	fmt.Println()

	x = x[:4]
	y = y[:4]

	h2 := h / 2
	n2 := 2 * n
	for i := 3; i <= n; i++ {
		yPredict := y[i-3] + 4*h/3*(2*f(x[i-2], y[i-2])-f(x[i-1], y[i-1])+2*f(x[i], y[i]))
		xNext := x[i] + h
		y = append(y, utils.Rounding(y[i-1]+h/3*(f(x[i-1], y[i-1])+4*f(x[i], y[i])+f(xNext, yPredict))))
		x = append(x, utils.Rounding(xNext))
	}
	epsActual := math.Abs(yPrecise(x[n], y[n]) - y[n])
	if epsActual > eps {
		x1, y1 := MilneMethod(f, x0, y0, h2, n2, eps, yPrecise)
		return x1, y1
	}
	return x, y
}

func MilneCorrector(x, y []float64, h, e float64) (xAnswer, yAnswer []float64) {
	xAnswer = append(xAnswer, utils.Rounding(x[0]))
	yAnswer = append(yAnswer, utils.Rounding(y[0]))
	tmp := x[0] + h
	for i := 0; i < len(x); i++ {
		if math.Abs(math.Abs(tmp)-math.Abs(x[i])) < e {
			fmt.Println(tmp, x[i])
			xAnswer = append(xAnswer, utils.Rounding(tmp))
			yAnswer = append(yAnswer, utils.Rounding(y[i]))
			tmp += h
		}
	}

	return
}
