package exponential_func

import (
	"compMathLab4/graph"
	"compMathLab4/utils"
	"fmt"
	"math"
)

func solveSystem(sumX, sumXX, sumLnY, sumXLnY, n float64) (float64, float64) {
	return utils.Rounding(math.Exp((sumLnY*sumXX - sumXLnY*sumX) / (n*sumXX - math.Pow(sumX, 2)))),
		utils.Rounding((n*sumXLnY - sumLnY*sumX) / (n*sumXX - math.Pow(sumX, 2)))
}

func p(a, b float64, xValues []float64) []float64 {
	pValues := make([]float64, len(xValues))
	for i := 0; i < len(xValues); i++ {
		pValues[i] = utils.Rounding(a * math.Pow(math.E, b*xValues[i]))
	}
	return pValues
}

func ExponentialFunc(xValues, yValues []float64) (float64, []float64) {
	fmt.Println()
	fmt.Println()
	fmt.Println("Аппроксимация с помощью экспоненциальной функции:")
	if err := utils.Verify(xValues); err != nil {
		fmt.Println(err)
		return 0, []float64{}
	}

	sumX := utils.SumPow(xValues, 1)
	sumXX := utils.SumPow(xValues, 2)
	sumLnY := utils.SumLnPow(yValues, 1)
	sumXLnY := utils.SumCoupleALnB(xValues, yValues)
	n := float64(len(xValues))

	a, b := solveSystem(sumX, sumXX, sumLnY, sumXLnY, n)

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

	fmt.Printf("ΣX: %g, ΣX2: %g, ΣLn(Y): %g, ΣXLn(Y): %g, N: %g \n", sumX, sumXX, sumLnY, sumXLnY, n)
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

	path := "output/exponential.png"
	f := func(x float64) float64 {
		return a * math.Exp(b*x)
	}
	graph.Create(path, xValues, yValues, f, "exponential")
	fmt.Println("График построен:", path)

	return detCoefficient, []float64{a, b}
}
