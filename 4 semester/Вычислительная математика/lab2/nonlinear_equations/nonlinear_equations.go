package nonlinear_equations

import (
	"fmt"
	"lab2/nonlinear_equations/methods"
	"lab2/nonlinear_equations/tools"
	"os"
)

// Nonlinear equation methods

const (
	ChordMethodForNonlinearEquations           = "1"
	NewtonMethodForNonlinearEquations          = "2"
	SimpleIterationMethodForNonlinearEquations = "3"
)

const (
	ReadFromFile    = "1"
	ReadFromConsole = "2"
)

func SolvingNonlinearEquation() {
	fmt.Println("Выберите метод решения нелинейного уравнения:")
	fmt.Println("1. Метод хорд")
	fmt.Println("2. Метод Ньютона")
	fmt.Println("3. Метод простой итерации")

	var solvingMethod string

	for {
		fmt.Fscan(os.Stdin, &solvingMethod)
		switch solvingMethod {
		case ChordMethodForNonlinearEquations:
			fmt.Println("Выбран метод хорд")
			var (
				readMode string
			)

			fmt.Println("Выберите метод считывания данных:")
			fmt.Println("1. Считывание из файла")
			fmt.Println("2. Считывание с консоли")

			for {
				fmt.Fscan(os.Stdin, &readMode)
				switch readMode {
				case ReadFromFile:
					fmt.Println("Чтение с файла")
					e, a, b, coefficients := ReadFileForChordMethod("input")
					derivative1 := tools.FindTheDerivative(coefficients)
					derivative2 := tools.FindTheDerivative(derivative1)
					fmt.Println("Производная 1-го порядка:", derivative1)
					fmt.Println("Производная 2-го порядка:", derivative2)
					fmt.Println("\n\n")
					tools.CreateGraphWithIntervals(a, b, coefficients)
					methods.ChordMethod(e, a, b, coefficients, derivative1, derivative2)
				case ReadFromConsole:
					fmt.Println("Чтение с консоли")
					e, a, b, coefficients := ReadConsoleForChordMethod()
					derivative1 := tools.FindTheDerivative(coefficients)
					derivative2 := tools.FindTheDerivative(derivative1)
					fmt.Println("Производная 1-го порядка:", derivative1)
					fmt.Println("Производная 1-го порядка:", derivative2)
					fmt.Println("\n\n")
					tools.CreateGraphWithIntervals(a, b, coefficients)
					methods.ChordMethod(e, a, b, coefficients, derivative1, derivative2)
				default:
					fmt.Printf("Укажите число, соответствующее: %s или %s\n", ReadFromFile, ReadFromConsole)
				}
			}
		case NewtonMethodForNonlinearEquations:
			fmt.Println("Выбран метод Ньютона")
			var (
				readMode string
			)

			fmt.Println("Выберите метод считывания данных:")
			fmt.Println("1. Считывание из файла")
			fmt.Println("2. Считывание с консоли")

			for {
				fmt.Fscan(os.Stdin, &readMode)
				switch readMode {
				case ReadFromFile:
					fmt.Println("Чтение с файла")
					e, a, b, coefficients := ReadFileForNewtonMethod("input")
					derivative1 := tools.FindTheDerivative(coefficients)
					derivative2 := tools.FindTheDerivative(derivative1)
					fmt.Println("Производная 1-го порядка:", derivative1)
					fmt.Println("Производная 2-го порядка:", derivative2)
					fmt.Println("\n\n")
					tools.CreateGraphWithIntervals(a, b, coefficients)
					methods.NewtonMethod(e, a, b, coefficients, derivative1, derivative2)
				case ReadFromConsole:
					fmt.Println("Чтение с консоли")
					e, a, b, coefficients := ReadConsoleForNewtonMethod()
					derivative1 := tools.FindTheDerivative(coefficients)
					derivative2 := tools.FindTheDerivative(derivative1)
					fmt.Println("Производная 1-го порядка:", derivative1)
					fmt.Println("Производная 2-го порядка:", derivative2)
					fmt.Println("\n\n")
					tools.CreateGraphWithIntervals(a, b, coefficients)
					methods.NewtonMethod(e, a, b, coefficients, derivative1, derivative2)
				default:
					fmt.Printf("Укажите число, соответствующее: %s или %s\n", ReadFromFile, ReadFromConsole)
				}
			}
		case SimpleIterationMethodForNonlinearEquations:
			fmt.Println("Выбран метод простых итераций")
			var (
				readMode string
			)

			fmt.Println("Выберите метод считывания данных:")
			fmt.Println("1. Считывание из файла")
			fmt.Println("2. Считывание с консоли")

			for {
				fmt.Fscan(os.Stdin, &readMode)
				switch readMode {
				case ReadFromFile:
					fmt.Println("Чтение с файла")
					e, a, b, coefficients := ReadFileForNewtonMethod("input")
					derivative1 := tools.FindTheDerivative(coefficients)
					derivative2 := tools.FindTheDerivative(derivative1)
					fmt.Println("Производная 1-го порядка:", derivative1)
					fmt.Println("Производная 2-го порядка:", derivative2)
					fmt.Println("\n\n")
					tools.CreateGraphWithIntervals(a, b, coefficients)
					methods.SimpleIterationMethod(e, a, b, coefficients, derivative1, derivative2)
				case ReadFromConsole:
					fmt.Println("Чтение с консоли")
					e, a, b, coefficients := ReadConsoleForNewtonMethod()
					derivative1 := tools.FindTheDerivative(coefficients)
					derivative2 := tools.FindTheDerivative(derivative1)
					fmt.Println("Производная 1-го порядка:", derivative1)
					fmt.Println("Производная 2-го порядка:", derivative2)
					fmt.Println("\n\n")
					tools.CreateGraphWithIntervals(a, b, coefficients)
					methods.SimpleIterationMethod(e, a, b, coefficients, derivative1, derivative2)
				default:
					fmt.Printf("Укажите число, соответствующее: %s или %s\n", ReadFromFile, ReadFromConsole)
				}
			}
		default:
			fmt.Printf("Укажите число, соответствующее: %s, %s или %s\n", ChordMethodForNonlinearEquations, NewtonMethodForNonlinearEquations, SimpleIterationMethodForNonlinearEquations)
		}
		break
	}
}
