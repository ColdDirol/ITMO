package test

import (
	"compMathLab4/utils"
	"fmt"
	"testing"
)

//func solveSystem(sumX, sumX2, sumX3, sumX4, sumY, sumXY, sumX2Y, n float64) (float64, float64, float64) {

// Решение системы уравнений
//

// Возвращение коэффициентов
//return utils.Rounding(coefficients[0]),
//	utils.Rounding(coefficients[1]),
//	utils.Rounding(coefficients[2])
//}

func TestPF2DFunc(t *testing.T) {
	xValues := []float64{1, 2}
	yValues := []float64{1, 2}

	sumX := utils.SumPow(xValues, 1)
	sumX2 := utils.SumPow(xValues, 2)
	sumX3 := utils.SumPow(xValues, 3)
	sumX4 := utils.SumPow(xValues, 4)
	sumY := utils.SumPow(yValues, 1)
	sumXY := utils.SumCouple(xValues, 1, yValues, 1)
	sumX2Y := utils.SumCouple(xValues, 2, yValues, 1)
	n := float64(len(xValues))

	// Формирование матрицы коэффициентов системы
	A := [][]float64{
		{float64(n), sumX, sumX2},
		{sumX, sumX2, sumX3},
		{sumX2, sumX3, sumX4},
	}

	// Формирование матрицы свободных членов
	B := []float64{sumY, sumXY, sumX2Y}

	fmt.Println(A)
	fmt.Println(B)

	coefficients := utils.GaussianElimination(A, B)

	//a0, a1, a2 := solveSystem(sumX, sumX2, sumX3, sumX4, sumY, sumXY, sumX2Y, n)

	fmt.Println(coefficients)
}
