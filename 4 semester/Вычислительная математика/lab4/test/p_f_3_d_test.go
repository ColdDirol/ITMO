package test

import (
	"compMathLab4/utils"
	"fmt"
	"testing"
)

func TestPF3DFunc(t *testing.T) {
	xValues := []float64{1, 2}
	yValues := []float64{1, 2}

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

	// Формирование матрицы коэффициентов системы
	A := [][]float64{
		{float64(n), sumX, sumX2, sumX3},
		{sumX, sumX2, sumX3, sumX4},
		{sumX2, sumX3, sumX4, sumX5},
		{sumX3, sumX4, sumX5, sumX6},
	}

	// Формирование матрицы свободных членов
	B := []float64{sumY, sumXY, sumX2Y, sumX3Y}

	//fmt.Println(A)
	//fmt.Println(B)

	coefficients := utils.GaussianElimination(A, B)
	fmt.Println(coefficients)
}
