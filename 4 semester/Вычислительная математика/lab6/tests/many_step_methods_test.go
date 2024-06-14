package tests

import (
	"compMathLab6/functions"
	"compMathLab6/graph"
	"compMathLab6/many_step_methods"
	"compMathLab6/utils"
	"testing"
)

func TestMilnaMethod(t *testing.T) {
	x0, y0, hInitial := 1.0, -3.4, 0.1
	n := 5
	eps := 0.01

	xValues, yValues := many_step_methods.MilneMethod(functions.F3(1), x0, y0, hInitial, n, eps, functions.F3(2))

	var answer [][]float64

	for i := range xValues {
		answer = append(answer, []float64{float64(i), xValues[i], yValues[i], ty(xValues[i])})
	}

	utils.PrintTable("Таблица решения задачи Коши методом Милна", []string{"#", "x", "y", "Точное решение"}, answer)
	graph.Create("out/milna.png", xValues, yValues)
}
