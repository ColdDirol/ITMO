package linear_func

import (
	"compMathLab4/graph"
	"compMathLab4/utils"
	"fmt"
	"math"
)

func solveSystem(sumX, sumXX, sumY, sumXY, n float64) (float64, float64) {
	return utils.Rounding((n*sumXY - sumX*sumY) / (n*sumXX - sumX*sumX)),
		utils.Rounding((sumXX*sumY - sumX*sumXY) / (n*sumXX - sumX*sumX))
}

func p(a, b float64, xValues []float64) []float64 {
	pValues := make([]float64, len(xValues))
	for i := 0; i < len(xValues); i++ {
		pValues[i] = utils.Rounding(a*xValues[i] + b)
	}
	return pValues
}

func correlationCoefficient(xValues, yValues []float64) float64 {
	averageX := utils.Average(xValues)
	averageY := utils.Average(yValues)

	var numerator float64 = 0
	for i := 0; i < len(xValues); i++ {
		numerator += (xValues[i] - averageX) * (yValues[i] - averageY)
	}

	var sumX float64 = 0
	for i := 0; i < len(xValues); i++ {
		sumX += math.Pow(xValues[i]-averageX, 2)
	}

	var sumY float64 = 0
	for i := 0; i < len(xValues); i++ {
		sumY += math.Pow(yValues[i]-averageY, 2)
	}

	var denominator float64 = math.Sqrt(sumX * sumY)
	return utils.Rounding(numerator / denominator)
}

func LinearFunc(xValues, yValues []float64) (float64, []float64) {
	fmt.Println()
	fmt.Println()
	fmt.Println("Аппроксимация с помощью линейной функции:")

	sumX := utils.SumPow(xValues, 1)
	sumXX := utils.SumPow(xValues, 2)
	sumY := utils.SumPow(yValues, 1)
	sumXY := utils.SumCouple(xValues, 1, yValues, 1)
	n := float64(len(xValues))

	a, b := solveSystem(sumX, sumXX, sumY, sumXY, n)

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
	corrCoefficient := correlationCoefficient(xValues, yValues)
	detCoefficient := utils.DeterminationCoefficient(pValues, yValues)

	fmt.Printf("ΣX: %g, ΣXX: %g, ΣY: %g, ΣXY: %g, N: %g \n", sumX, sumXX, sumY, sumXY, n)
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
	fmt.Println("Коэффициент корреляции Пирсона:", corrCoefficient)
	fmt.Println("Достоверность аппроксимации:", detCoefficient)
	fmt.Println(utils.DeterminationMessage(detCoefficient))

	path := "output/linear.png"
	f := func(x float64) float64 {
		return a*x + b
	}
	graph.Create(path, xValues, yValues, f, "linear")
	fmt.Println("График построен:", path)

	return detCoefficient, []float64{a, b}
}
