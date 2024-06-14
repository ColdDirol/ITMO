package lagrange

import (
	"compMathLab5/utils"
	"fmt"
)

func l(x float64, xValues, yValues []float64) (l float64) {
	var numerator float64
	var denominator float64
	for i := 0; i < len(xValues); i++ {
		numerator = 1
		denominator = 1
		for j := 0; j < len(xValues); j++ {
			if i != j {
				numerator *= x - xValues[j]
				denominator *= xValues[i] - xValues[j]
			}
		}
		l += yValues[i] * numerator / denominator
	}
	return utils.Rounding(l)
}

func WithX(x float64, xValues, yValues []float64) {
	l := l(x, xValues, yValues)

	fmt.Println("l:", l)
}

func lWithXs(x float64, f func(float64) float64, xValues, yValues []float64) float64 {
	var (
		numerator   float64
		denominator float64
		sum         float64 = 0
	)

	for i := 0; i < len(yValues); i++ {
		numerator = 1
		denominator = 1
		for j := 0; j < len(xValues); j++ {
			if i == j {
				numerator *= yValues[i]
			} else {
				numerator *= x - xValues[j]
				denominator *= xValues[i] - xValues[j]
			}
		}

		sum += numerator / denominator
	}

	return sum
}

func ByFunction(x float64, f func(float64) float64, xValues, yValues []float64) {
	l := lWithXs(x, f, xValues, yValues)

	fmt.Println(l)
}

func GetLagrangeCoefficients(xValues, yValues []float64) []float64 {
	coeff := utils.CalculateCoefficientsForLagrange(xValues)
	//fmt.Println(coeff)

	var sub []float64
	var tmp float64
	for i := 0; i < len(yValues); i++ {
		tmp = 1
		for j := 0; j < len(xValues); j++ {
			if i != j {
				tmp *= xValues[i] - xValues[j]
			}
		}
		sub = append(sub, tmp)
	}
	//fmt.Println("sub", sub)

	coefficients := make([]float64, len(sub))

	for i := 0; i < len(coeff); i++ {
		for j := 0; j < len(coeff[0]); j++ {
			coeff[i][j] *= utils.Rounding(yValues[i] / sub[i])
		}
		coefficients = utils.SumArrays(coefficients, coeff[i])
	}
	//fmt.Println(coeff)
	//fmt.Println(coefficients)

	return coefficients
}
