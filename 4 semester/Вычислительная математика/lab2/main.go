package main

import (
	"fmt"
	"lab2/nonlinear_equations"
	"lab2/system_of_nonlinear_equations"
	"os"
)

const (
	NonlinearEquation         = "1"
	SystemOfNonlinearEquation = "2"
)

func main() {
	fmt.Println("Выберите тип решения уравнения:")
	fmt.Println("1. Решение нелинейных уравнений")
	fmt.Println("2. Решение системы нелинейных уравнений")

	var solvingMode string
	for {
		fmt.Fscan(os.Stdin, &solvingMode)
		switch solvingMode {
		case NonlinearEquation:
			nonlinear_equations.SolvingNonlinearEquation()
		case SystemOfNonlinearEquation:
			system_of_nonlinear_equations.SolvingSystemOfNonlinearEquation()
		default:
			fmt.Printf("Укажите число, соответствующее: %s или %s\n", NonlinearEquation, SystemOfNonlinearEquation)
		}
	}
}
