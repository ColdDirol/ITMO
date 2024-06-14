package system_of_nonlinear_equations

import (
	"fmt"
	"lab2/system_of_nonlinear_equations/methods"
	"os"
)

const (
	NewtonMethodForSystemOfNonlinearEquations = "1"
)

func SolvingSystemOfNonlinearEquation() {
	fmt.Println("Выберите метод решения системы нелинейных уравнений:")
	fmt.Println("1. Метод Ньютона")

	var solvingMethod string

	for {
		fmt.Fscan(os.Stdin, &solvingMethod)
		switch solvingMethod {
		case NewtonMethodForSystemOfNonlinearEquations:
			methods.NewtonMethod()
		default:
			fmt.Printf("Укажите число, соответствующее: %s\n", NewtonMethodForSystemOfNonlinearEquations)
		}
	}
}
