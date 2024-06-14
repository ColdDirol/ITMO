package logarithmic_func

import (
	"compMathLab4/graph"
	"compMathLab4/utils"
	"fmt"
	"math"
)

func solveSystem(sumLnX, sumLnXLnX, sumY, sumYLnX, n float64) (float64, float64) {
	return utils.Rounding((sumY*sumLnXLnX - sumYLnX*sumLnX) / (n*sumLnXLnX - math.Pow(sumLnX, 2))),
		utils.Rounding((n*sumYLnX - sumY*sumLnX) / (n*sumLnXLnX - math.Pow(sumLnX, 2)))
}

func p(a, b float64, xValues []float64) []float64 {
	pValues := make([]float64, len(xValues))
	for i := 0; i < len(xValues); i++ {
		pValues[i] = utils.Rounding(a + b*math.Log(xValues[i]))
	}
	return pValues
}

func LogarithmicFunc(xValues, yValues []float64) (float64, []float64) {
	fmt.Println()
	fmt.Println()
	fmt.Println("Аппроксимация с помощью логарифмической функции:")

	if err := utils.Verify(xValues); err != nil {
		fmt.Println(err)
		return 0, []float64{}
	}

	sumLnX := utils.SumLnPow(xValues, 1)
	sumLnXLnX := utils.SumLnPow(xValues, 2)
	sumY := utils.SumPow(yValues, 1)
	sumYLnX := utils.SumCoupleALnB(yValues, xValues)
	n := float64(len(xValues))

	a, b := solveSystem(sumLnX, sumLnXLnX, sumY, sumYLnX, n)

	pValues := p(a, b, xValues)
	if utils.CheckNaN(pValues) {
		fmt.Println("Ошибка рассчета - pValues")
		return 0, []float64{}
	}
	eValues := utils.Difference(pValues, yValues)
	if utils.CheckNaN(pValues) {
		fmt.Println("Ошибка рассчета - eValues")
		return 0, []float64{}
	}

	s := utils.SumPow(eValues, 2)
	stDev := utils.StandardDeviation(pValues, yValues)
	detCoefficient := utils.DeterminationCoefficient(pValues, yValues)

	fmt.Printf("ΣLn(X): %g, ΣLn(X)Ln(X): %g, ΣY: %g, ΣYLn(X): %g, N: %g \n", sumLnX, sumLnXLnX, sumY, sumYLnX, n)
	//fmt.Println()
	fmt.Printf("a: %g, b: %g \n", a, b)
	//fmt.Println()
	fmt.Println("X:", xValues)
	fmt.Println("Y:", yValues)
	fmt.Println("P:", pValues)
	fmt.Println("E:", eValues)
	//fmt.Println()
	fmt.Println("Мера отклонения:", s)
	fmt.Println("Стандартное отклонение:", stDev)
	fmt.Println("Достоверность аппроксимации:", detCoefficient)
	fmt.Println(utils.DeterminationMessage(detCoefficient))

	path := "output/logarithmic.png"
	f := func(x float64) float64 {
		return a*math.Log(x) + b
	}
	graph.Create(path, xValues, yValues, f, "logarithmic")
	fmt.Println("График построен:", path)

	return detCoefficient, []float64{a, b}
}
