package main

import (
	"bufio"
	"compMathLab6/functions"
	"compMathLab6/graph"
	"compMathLab6/many_step_methods"
	"compMathLab6/one_step_methods"
	"compMathLab6/utils"
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

// 1. Метод Эйлера
// 3. Метод Рунге-Кутта 4- го порядка.
// 5. Милна

const (
	euler_method       = "euler_method"
	runge_kutta_method = "runge_kutta_method"
	milna_method       = "milna_method"
)

func main() {

	selectedMethod := selectMethod()
	x0, y0, n, h, e, fun := readConsole()

	// 1
	// x0 y0 = 1 -1
	// n h = 5 0.1
	// e = 0.01

	// 2
	// x0 y0 = 1 -2
	// n h = 5 0.1
	// e = 0.01

	// 3
	// x0 y0 = 1 3.4
	// n h = 5 0.1
	// e = 0.01

	fmt.Println("x0 =", y0)
	fmt.Println("y0 =", x0)
	fmt.Println("n =", n)
	fmt.Println("h =", h)
	fmt.Println("e =", e)

	var (
		xValues []float64
		yValues []float64
	)

	y := fun(1)
	ty := fun(2)

	fmt.Println("y(x0) =", y(x0, y0))

	switch selectedMethod {
	case euler_method:
		path := "out/simple_euler.png"
		p := 1

		xValues, yValues = one_step_methods.OneStepMethodSolve(one_step_methods.EulerMethod, y, x0, y0, h, int(n), e, p)

		var answer [][]float64

		xTmp, yTmp := one_step_methods.OneStepMethodSolve(one_step_methods.EulerMethod, y, x0, y0, h/2, int(n*2), e, p)

		for i := range xValues {
			if i != 0 {
				for j := range xTmp {
					if xValues[i] == xTmp[j] {
						answer = append(answer, []float64{float64(i), xValues[i], yValues[i], computeError(ty, xValues[i], yValues[i]), xTmp[j], yTmp[j], computeError(ty, xTmp[j], yTmp[j]), ty(xValues[i], yValues[i])})
					}
				}
				continue
			}
			answer = append(answer, []float64{float64(i), xValues[i], yValues[i], computeError(ty, xValues[i], yValues[i]), xTmp[i], yTmp[i], computeError(ty, xTmp[i], yTmp[i]), ty(xValues[i], yValues[i])})
		}

		utils.PrintTable("Таблица решения задачи Коши обычным методом Эйлера", []string{"#", "x", "y", "Погрешность", "x at h/2", "y at h/2", "Погрешность", "Точное решение"}, answer)
		graph.Create(path, xValues, yValues)
		fmt.Println()
		fmt.Println("График построен:", path)
	case runge_kutta_method:
		path := "out/runge_kutta.png"
		p := 4

		xValues, yValues = one_step_methods.OneStepMethodSolve(one_step_methods.RungeKutta, y, x0, y0, h, int(n), e, p)

		var answer [][]float64

		xTmp, yTmp := one_step_methods.OneStepMethodSolve(one_step_methods.RungeKutta, y, x0, y0, h/2, int(n*2), e, p)

		for i := range xValues {
			if i != 0 {
				for j := range xTmp {
					if xValues[i] == xTmp[j] {
						answer = append(answer, []float64{float64(i), xValues[i], yValues[i], computeError(ty, xValues[i], yValues[i]), xTmp[j], yTmp[j], computeError(ty, xTmp[j], yTmp[j]), ty(xValues[i], yValues[i])})
					}
				}
				continue
			}
			answer = append(answer, []float64{float64(i), xValues[i], yValues[i], computeError(ty, xValues[i], yValues[i]), xTmp[i], yTmp[i], computeError(ty, xTmp[i], yTmp[i]), ty(xValues[i], yValues[i])})
		}

		utils.PrintTable("Таблица решения задачи Коши методом Рунге-Кутта", []string{"#", "x", "y", "Погрешность", "x at h/2", "y at h/2", "Погрешность", "Точное решение"}, answer)
		graph.Create(path, xValues, yValues)
		fmt.Println()
		fmt.Println("График построен:", path)
	case milna_method:
		path := "out/milna.png"

		// 1 -1
		// 5 1
		// 0.01

		xValues, yValues = many_step_methods.MilneMethod(y, x0, y0, h, int(n), e, ty)

		//var answer [][]float64
		//
		//for i := range xValues {
		//	answer = append(answer, []float64{float64(i), xValues[i], yValues[i], ty(xValues[i], yValues[i]), computeError(ty, xValues[i], yValues[i])})
		//}
		//
		//utils.PrintTable("Таблица решения задачи Коши методом Милна", []string{"#", "x", "y", "Точное решение", "Погрешность"}, answer)
		//////graph.Create(path, xValues, yValues)
		//
		//fmt.Println()
		//fmt.Println()
		//fmt.Println()

		xAnswer, yAnswer := many_step_methods.MilneCorrector(xValues, yValues, h, e)
		//fmt.Println(xAnswer)
		//fmt.Println(yAnswer)
		var answer1 [][]float64

		for i := range xAnswer {
			answer1 = append(answer1, []float64{float64(i), xAnswer[i], yAnswer[i], ty(xAnswer[i], yAnswer[i]), computeError(ty, xAnswer[i], yAnswer[i])})
		}
		utils.PrintTable("Таблица решения задачи Коши методом Милна", []string{"#", "x", "y", "Точное решение", "Погрешность"}, answer1)

		graph.Create(path, xAnswer, yAnswer)
		fmt.Println()
		fmt.Println("График построен:", path)
	}
}

func selectMethod() (selectionMode string) {
	fmt.Println("\n\nВыберите метод:")
	fmt.Println("1. Метод Эйлера")
	fmt.Println("2. Метод Рунге-Кутта 4- го порядка")
	fmt.Println("3. Метод Милна")

	for {
		fmt.Println("Выберите тип ввода (1-3):")
		fmt.Fscan(os.Stdin, &selectionMode)
		switch selectionMode {
		case "1":
			fmt.Println("Выбран метод - Метод Эйлера")
			return euler_method
		case "2":
			fmt.Println("Выбран метод - Метод Рунге-Кутта 4- го порядка")
			return runge_kutta_method
		case "3":
			fmt.Println("Выбран метод - Метод Милна")
			return milna_method
		default:
			fmt.Println("Укажите число, соответствующее предложенному варианту!")
		}
	}
}

func selectFunction() func(mode int) func(x, y float64) float64 {
	fmt.Println("\n\nВыберите функцию:")
	fmt.Println("1. y' = y + (1 + x) * y^2")
	fmt.Println("2. y' = x * y^2")
	fmt.Println("3. y' = y - sinx")

	var selectionMode string

	for {
		fmt.Println("Выберите тип ввода (1-3):")
		fmt.Fscan(os.Stdin, &selectionMode)
		switch selectionMode {
		case "1":
			fmt.Println("Выбрана функция: y' = y + (1 + x) * y^2")
			return functions.F1
		case "2":
			fmt.Println("Выбрана функция: y' = x * y^2")
			return functions.F2
		case "3":
			fmt.Println("Выбрана функция: y' = y - sinx")
			return functions.F3
		default:
			fmt.Println("Укажите число, соответствующее предложенному варианту!")
		}
	}
}

func readConsole() (x0, y0, n, h, e float64, f func(mode int) func(x, y float64) float64) {
	reader := bufio.NewReader(os.Stdin)

	f = selectFunction()

	fmt.Print("Введите x0 и y0 разделенные пробелом: ")
	input, _ := reader.ReadString('\n')
	fields := strings.Fields(input)
	x0, _ = strconv.ParseFloat(fields[0], 64)
	y0, _ = strconv.ParseFloat(fields[1], 64)

	fmt.Print("Введите n и h разделенные пробелом: ")
	input, _ = reader.ReadString('\n')
	fields = strings.Fields(input)
	n, _ = strconv.ParseFloat(fields[0], 64)
	h, _ = strconv.ParseFloat(fields[1], 64)

	if h <= 0 {
		fmt.Println("ERROR! h <= 0")
		os.Exit(1)
	}

	fmt.Print("введите e: ")
	input, _ = reader.ReadString('\n')
	e, _ = strconv.ParseFloat(strings.TrimSpace(input), 64)

	return
}

func computeError(ty func(float64, float64) float64, x, y float64) float64 {
	return utils.Rounding(math.Abs(math.Abs(ty(x, y)) - math.Abs(y)))
}
