package newton_finite_differences

import (
	"compMathLab5/utils"
	"fmt"
	"math"
)

func TransposeMatrix(matrix [][]float64) [][]float64 {
	// Проверяем, что матрица не пустая
	if len(matrix) == 0 {
		fmt.Println("Ошибка: матрица пустая")
		return nil
	}

	// Определяем количество строк и столбцов
	rows := len(matrix)
	cols := len(matrix[0])

	// Создаем новую матрицу для результата
	transposed := make([][]float64, cols)
	for i := range transposed {
		transposed[i] = make([]float64, rows)
	}

	// Транспонируем матрицу
	for i := 0; i < rows; i++ {
		for j := 0; j < cols; j++ {
			transposed[j][i] = matrix[i][j]
		}
	}

	return transposed
}

func Diffs(xValues, yValues []float64) (matrix [][]float64) {
	matrix = append(matrix, yValues)

	count := len(xValues) - 1
	var (
		curr []float64
		prev []float64 = yValues
	)

	for count > 0 {
		for i := 0; i < count; i++ {
			for j := count; j > 0; j-- {
				if j == 0 {
					break
				}
				curr = append(curr, utils.Rounding(prev[j]-prev[j-1]))
			}
			prev = utils.ReverseSlice(curr)

			for len(prev) < len(yValues) {
				prev = append(prev, 0)
			}

			matrix = append(matrix, prev)
			curr = curr[:0]
			count--
		}
	}

	matrix = TransposeMatrix(matrix)

	return
}

func interpolateForward(x float64, xValues []float64, diff [][]float64) (y float64) {
	fmt.Println("X:", x)

	order := 0
	for i := 0; i < len(xValues); i++ {
		if xValues[i] > x {
			order = i - 1
			break
		}
	}

	n := len(xValues) - (order + 1)
	fmt.Println("N:", n)

	//fmt.Println("Порядок:", order)

	t := utils.Rounding((x - xValues[order]) / utils.FindMiddleStep(xValues))
	fmt.Println("t:", t)

	var sub []float64
	var mul float64 = 1
	for i := 1; i < len(xValues)-(order+1); i++ {
		mul *= t - float64(i)
		sub = append(sub, utils.Rounding(mul))
	}

	y += diff[order][0]
	if len(xValues) == 1 {
		return y
	}
	//fmt.Println("y:", y)
	y += t * diff[order][1]
	if len(xValues) == 2 {
		return y
	}
	//fmt.Println("y:", y)

	count := 0
	for i := 2; i < len(diff[order])-order; i++ {
		y += ((t * sub[count]) / float64(utils.Factorial(i))) * diff[order][i]
		//fmt.Println("y:", y)
		count++
	}

	fmt.Println("Ответ:", y)

	r := math.Abs(t*sub[len(sub)-1]/float64(utils.Factorial(n+1))) * diff[0][n]
	fmt.Println("Погрешность интерполяции:", r)

	return y
}

func interpolateBackward(x float64, xValues []float64, matrix [][]float64) (y float64) {
	fmt.Println("X:", x)

	order := 0
	for i := 0; i < len(xValues); i++ {
		if xValues[i] > x {
			order = i
			break
		}
	}

	n := order
	fmt.Println("N:", n)
	t := utils.Rounding((x - xValues[order]) / utils.FindMiddleStep(xValues))
	fmt.Println("t:", t)

	order = int(math.Abs(float64(order - 4)))

	var diff []float64
	rows := len(matrix)
	cols := len(matrix[0])

	// Перебираем элементы по диагонали
	for i := 0; i < rows && i < cols; i++ {
		diff = append(diff, matrix[rows-i-1][i])
	}

	//fmt.Println("diff:", diff)
	var sub []float64
	var mul float64 = 1
	for i := 1; i < len(xValues)-(order+1); i++ {
		mul *= t + float64(i)
		sub = append(sub, utils.Rounding(mul))
	}
	//fmt.Println("sub:", sub)

	y += diff[0]
	if len(xValues) == 1 {
		return y
	}
	y += t * diff[1]
	if len(xValues) == 2 {
		return y
	}

	count := 0
	for i := 2; i < len(diff); i++ {
		y += ((t * sub[count]) / float64(utils.Factorial(i))) * diff[i]
		count++
	}

	fmt.Println("Ответ:", y)

	r := math.Abs(t*sub[len(sub)-1]/float64(utils.Factorial(n+1))) * diff[n-1]
	fmt.Println("Погрешность интерполяции:", r)

	return y
}

func nOfFindingValues(findingValues []float64, xValues []float64, diff [][]float64) (y []float64) {
	middle := utils.FindMiddle(xValues)

	for _, v := range findingValues {
		fmt.Println()
		fmt.Println()
		if v <= middle {
			y = append(y, interpolateForward(v, xValues, diff))
		} else {
			y = append(y, interpolateBackward(v, xValues, diff))
		}
	}

	fmt.Println()
	return
}

func GetNewtonCoefficients(xValues, yValues []float64) (coefficients []float64) {
	diff := Diffs(xValues, yValues)

	//fmt.Println("diff", diff)

	step := utils.Rounding(utils.FindMiddleStep(xValues))
	//fmt.Println("step", step)

	sub := utils.CalculateCoefficientsForNewton(xValues)

	// Находим длину самой длинной строки в массиве
	maxLength := 0
	for _, row := range sub {
		if len(row) > maxLength {
			maxLength = len(row)
		}
	}

	// нули
	for i := range sub {
		for len(sub[i]) < maxLength {
			sub[i] = append([]float64{0}, sub[i]...)
		}
	}

	//fmt.Println("sub", sub)

	var tmpCoeff []float64
	diffY0 := diff[0]
	//fmt.Println("tmpCoeff", tmpCoeff)
	for i := 1; i < len(diffY0); i++ {
		if i == 0 {
			tmpCoeff = append(tmpCoeff, diffY0[i])
			continue
		}
		tmpCoeff = append(tmpCoeff, utils.Rounding(diffY0[i]/(float64(utils.Factorial(i))*math.Pow(step, float64(i)))))
		//fmt.Println("tmpCoeff", tmpCoeff)
	}

	// добавляем все кроме первого
	tmpMatrix := utils.MultiplyMatrix(sub, tmpCoeff)
	coefficients = utils.SumColumns(tmpMatrix)
	//fmt.Println("coefficients:", tmpMatrix)

	//добавляем первый
	coefficients[len(coefficients)-1] = utils.Rounding(coefficients[len(coefficients)-1] + diffY0[0])

	for i := range coefficients {
		coefficients[i] = utils.Rounding(coefficients[i])
	}

	return
}

func simpleN(xValues []float64, diff [][]float64) (coefficients []float64) {
	//order := len(xValues) - 1
	step := utils.FindMiddleStep(xValues)

	sub := utils.CalculateCoefficientsForNewton(xValues)

	// Находим длину самой длинной строки в массиве
	maxLength := 0
	for _, row := range sub {
		if len(row) > maxLength {
			maxLength = len(row)
		}
	}

	// Добавляем нули перед элементами в строках, которые короче самой длинной строки
	for i := range sub {
		for len(sub[i]) < maxLength {
			sub[i] = append([]float64{0}, sub[i]...)
		}
	}

	// Выводим квадратный массив
	//for _, row := range sub {
	//	fmt.Println(row)
	//}

	var tmpCoeff []float64
	for i := 0; i < len(sub); i++ {
		tmpCoeff = append(tmpCoeff, utils.Rounding(diff[0][i+1]/(float64(utils.Factorial(i+1))*math.Pow(step, float64(i+1)))))
	}
	// добавляем все кроме первого
	coefficients = utils.MulCoeffOnArray(tmpCoeff[0], sub[0])
	for i := 0; i < len(sub)-1; i++ {
		coefficients = utils.SumArrays(coefficients, utils.MulCoeffOnArray(tmpCoeff[i+1], sub[i+1]))
		//fmt.Println(coefficients)
	}

	//добавляем первый
	for i := 0; i < len(coefficients); i++ {
		if i == len(coefficients)-1 {
			coefficients[i] = utils.Rounding(coefficients[i] - diff[0][0])
		}
	}

	return coefficients
}

// Конечные разности
func NewtonFiniteDifferencesWithFindingValues(findingValues, xValues, yValues []float64) {
	diff := Diffs(xValues, yValues)
	printTableOfDiffs(xValues, diff)
	//fmt.Println(nOfFindingValues(findingValues, xValues, diff))
}

func NewtonFiniteDifferencesWithoutFindingValues(xValues, yValues []float64) {
	diff := Diffs(xValues, yValues)
	printTableOfDiffs(xValues, diff)
	coefficients := simpleN(xValues, diff)
	fmt.Println(coefficients)
}

func NewtonFiniteDifferencesWithoutFindingValuesWithoutPrint(xValues, yValues []float64) (coefficients []float64) {
	diff := Diffs(xValues, yValues)
	//printTableOfDiffs(xValues, diff)
	coefficients = simpleN(xValues, diff)
	//fmt.Println(coefficients)

	return
}
