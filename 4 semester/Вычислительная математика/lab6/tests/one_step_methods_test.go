package tests

import (
	"compMathLab6/one_step_methods"
	"compMathLab6/utils"
	"fmt"
	"testing"
)

func TestRungeKuttaMethod(t *testing.T) {
	x0, y0, hInitial := 1.0, -1.0, 0.1
	n := 5
	eps := 0.01
	p := 4

	x, y := one_step_methods.OneStepMethodSolve(one_step_methods.RungeKutta, f, x0, y0, hInitial, n, eps, p)

	var answer [][]float64

	xTmp, yTmp := one_step_methods.OneStepMethodSolve(one_step_methods.RungeKutta, f, x0, y0, hInitial/2, n*2, eps, p)

	fmt.Println(len(x), len(yTmp))

	for i := range x {
		if i != 0 {
			for j := range xTmp {
				if x[i] == xTmp[j] {
					answer = append(answer, []float64{float64(i), x[i], y[i], xTmp[j], yTmp[j], ty(x[i])})
				}
			}
			continue
		}
		answer = append(answer, []float64{float64(i), x[i], y[i], xTmp[i], yTmp[i], ty(x[i])})
	}

	utils.PrintTable("Таблица решения задачи Коши методом Рунге-Кутта", []string{"#", "x", "y", "x at h/2", "y at h/2", "Точное решение"}, answer)
}

func TestSimpleEulerMethod(t *testing.T) {
	x0, y0, hInitial := 1.0, -1.0, 0.1
	n := 5
	eps := 0.01
	p := 1

	x, y := one_step_methods.OneStepMethodSolve(one_step_methods.EulerMethod, f, x0, y0, hInitial, n, eps, p)

	var answer [][]float64

	xTmp, yTmp := one_step_methods.OneStepMethodSolve(one_step_methods.EulerMethod, f, x0, y0, hInitial/2, n*2, eps, p)

	fmt.Println(len(x), len(yTmp))

	for i := range x {
		if i != 0 {
			for j := range xTmp {
				if x[i] == xTmp[j] {
					answer = append(answer, []float64{float64(i), x[i], y[i], xTmp[j], yTmp[j], ty(x[i])})
				}
			}
			continue
		}
		answer = append(answer, []float64{float64(i), x[i], y[i], xTmp[i], yTmp[i], ty(x[i])})
	}

	utils.PrintTable("Таблица решения задачи Коши обычным методом Эйлера", []string{"#", "x", "y", "x at h/2", "y at h/2", "Точное решение"}, answer)
}
