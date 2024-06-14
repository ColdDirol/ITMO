package polynomial_function_3nd_degree

import (
	"compMathLab4/graph"
	"compMathLab4/utils"
	"fmt"
	"math"
)

func solveSystem(sumX, sumX2, sumX3, sumX4, sumX5, sumX6, sumY, sumXY, sumX2Y, sumX3Y, n float64) (float64, float64, float64, float64) {
	// Формирование матрицы коэффициентов системы
	A := [][]float64{
		{float64(n), sumX, sumX2, sumX3},
		{sumX, sumX2, sumX3, sumX4},
		{sumX2, sumX3, sumX4, sumX5},
		{sumX3, sumX4, sumX5, sumX6},
	}

	// Формирование матрицы свободных членов
	B := []float64{sumY, sumXY, sumX2Y, sumX3Y}

	// Решение системы уравнений
	coefficients := utils.GaussianElimination(A, B)

	// Возвращение коэффициентов
	return utils.Rounding(coefficients[0]),
		utils.Rounding(coefficients[1]),
		utils.Rounding(coefficients[2]),
		utils.Rounding(coefficients[3])
}

func p(a0, a1, a2, a3 float64, xValues []float64) []float64 {
	pValues := make([]float64, len(xValues))
	for i := 0; i < len(xValues); i++ {
		pValues[i] = utils.Rounding(a0 + a1*xValues[i] + a2*math.Pow(xValues[i], 2) + a3*math.Pow(xValues[i], 3))
	}
	return pValues
}

func PolynomialFunction3rdDegree(xValues, yValues []float64) (float64, []float64) {
	fmt.Println()
	fmt.Println()
	fmt.Println("Аппроксимация с помощью полиномиальной функции 3-й степени:")

	sumX := utils.SumPow(xValues, 1)
	sumX2 := utils.SumPow(xValues, 2)
	sumX3 := utils.SumPow(xValues, 3)
	sumX4 := utils.SumPow(xValues, 4)
	sumX5 := utils.SumPow(xValues, 5)
	sumX6 := utils.SumPow(xValues, 6)
	sumY := utils.SumPow(yValues, 1)
	sumXY := utils.SumCouple(xValues, 1, yValues, 1)
	sumX2Y := utils.SumCouple(xValues, 2, yValues, 1)
	sumX3Y := utils.SumCouple(xValues, 3, yValues, 1)
	n := float64(len(xValues))

	a0, a1, a2, a3 := solveSystem(sumX, sumX2, sumX3, sumX4, sumX5, sumX6, sumY, sumXY, sumX2Y, sumX3Y, n)

	pValues := p(a0, a1, a2, a3, xValues)
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

	fmt.Printf("ΣX: %g, ΣX2: %g, ΣX3: %g, ΣX4: %g, ΣX5: %g, ΣX6: %g \n", sumX, sumX2, sumX3, sumX4, sumX5, sumX6)
	fmt.Printf("ΣY: %g, ΣXY: %g, ΣX2Y: %g, ΣX3Y: %g, N: %g \n", sumY, sumXY, sumX2Y, sumX3Y, n)
	//fmt.Println()
	fmt.Printf("a0: %g, a1: %g, a2: %g, a3: %g \n", a0, a1, a2, a3)
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

	path := "output/p_f_3_d.png"
	f := func(x float64) float64 {
		return a0 + a1*x + a2*x*x + a3*x*x*x
	}
	graph.Create(path, xValues, yValues, f, "p_f_2_d")
	fmt.Println("График построен:", path)

	return detCoefficient, []float64{a0, a1, a2, a3}
}
