package test

import (
	lagrange2 "compMathLab5/methods/lagrange"
	"compMathLab5/methods/newton_finite_differences"
	"compMathLab5/utils"
	"fmt"
	"math"
	"testing"
)

func GetNewtonCoefficients(xValues, yValues []float64) (coefficients []float64) {
	diff := newton_finite_differences.Diffs(xValues, yValues)

	//fmt.Println("diff", diff)

	step := utils.Rounding(utils.FindMiddleStep(xValues))
	//fmt.Println("step", step)

	sub := utils.CalculateCoefficientsForNewton(xValues)

	// Находим длину самой длинной строки в массиве
	maxLength := 0
	for _, row := range sub {
		if len(row) > maxLength {
			maxLength = len(row)
		}
	}

	// нули
	for i := range sub {
		for len(sub[i]) < maxLength {
			sub[i] = append([]float64{0}, sub[i]...)
		}
	}

	//fmt.Println("sub", sub)

	var tmpCoeff []float64
	diffY0 := diff[0]
	//fmt.Println("tmpCoeff", tmpCoeff)
	for i := 1; i < len(diffY0); i++ {
		if i == 0 {
			tmpCoeff = append(tmpCoeff, diffY0[i])
			continue
		}
		tmpCoeff = append(tmpCoeff, utils.Rounding(diffY0[i]/(float64(utils.Factorial(i))*math.Pow(step, float64(i)))))
		//fmt.Println("tmpCoeff", tmpCoeff)
	}

	// добавляем все кроме первого
	tmpMatrix := utils.MultiplyMatrix(sub, tmpCoeff)
	coefficients = utils.SumColumns(tmpMatrix)
	//fmt.Println("coefficients:", tmpMatrix)

	//добавляем первый
	coefficients[len(coefficients)-1] = coefficients[len(coefficients)-1] + diffY0[0]

	return
}

func TestCmd(t *testing.T) {
	xValues := []float64{0.1, 0.2, 0.3, 0.4, 0.5}
	yValues := []float64{1.25, 2.38, 3.79, 5.44, 7.14}

	lagrange := lagrange2.GetLagrangeCoefficients(xValues, yValues)
	fmt.Println("Lagrange:", lagrange)

	newton := GetNewtonCoefficients(xValues, yValues)
	fmt.Println("Newton:", newton)
}
