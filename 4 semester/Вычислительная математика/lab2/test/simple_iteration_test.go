package test

import (
	"lab2/nonlinear_equations/methods"
	"lab2/nonlinear_equations/tools"
	"lab2/utils"
	"testing"
)

func TestSimpleIterations(t *testing.T) {
	var (
		e           float64
		a           float64
		b           float64
		coefficints []float64
	)

	e = 0.01
	a = 3
	b = 4
	coefficints = []float64{1, -3.125, -3.5, 2.458}
	utils.ReverseSlice(coefficints)

	derivative1 := tools.FindTheDerivative(coefficints)
	derivative2 := tools.FindTheDerivative(derivative1)

	methods.SimpleIterationMethod(e, a, b, coefficints, derivative1, derivative2)
}
