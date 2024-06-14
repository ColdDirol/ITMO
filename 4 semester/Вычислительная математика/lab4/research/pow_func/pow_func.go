package pow_func

import (
	"compMathLab4/graph"
	"compMathLab4/utils"
	"fmt"
	"math"
)

func solveSystem(sumLnX, sumLnY, sumLnXY, sumLnXX, n float64) (float64, float64) {
	return utils.Rounding(math.Exp((sumLnY*sumLnXX - sumLnXY*sumLnX) / (n*sumLnXX - sumLnX*sumLnX))),
		utils.Rounding((n*sumLnXY - sumLnX*sumLnY) / (n*sumLnXX - sumLnX*sumLnX))
}

func p(a, b float64, xValues []float64) []float64 {
	pValues := make([]float64, len(xValues))
	for i := 0; i < len(xValues); i++ {
		pValues[i] = utils.Rounding(a * math.Pow(xValues[i], b))
	}
	return pValues
}

func PowerFunc(xValues, yValues []float64) (float64, []float64) {
	fmt.Println()
	fmt.Println()
	fmt.Println("Аппроксимация с помощью степенной функции:")
	if err := utils.Verify(xValues); err != nil {
		fmt.Println(err)
		return 0, []float64{}
	}

	sumLnX := utils.SumLnPow(xValues, 1)
	sumLnXX := utils.SumLnPow(xValues, 2)
	sumLnY := utils.SumLnPow(yValues, 1)
	sumLnXY := utils.SumCoupleLn(xValues, yValues)
	n := float64(len(xValues))

	a, b := solveSystem(sumLnX, sumLnY, sumLnXY, sumLnXX, n)

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

	fmt.Printf("ΣLn(X): %g, ΣLn(Y): %g, ΣLn(XY): %g, ΣLn(XX): %g, N: %g \n", sumLnX, sumLnY, sumLnXY, sumLnXX, n)
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

	path := "output/pow.png"
	f := func(x float64) float64 {
		return a * math.Pow(x, b)
	}
	graph.Create(path, xValues, yValues, f, "pow")
	fmt.Println("График построен:", path)

	return detCoefficient, []float64{a, b}
}
