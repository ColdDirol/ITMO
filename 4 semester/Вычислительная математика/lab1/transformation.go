package main

import (
	"fmt"
	"math"
)

// перемещаем строки до диагонального приобладания
func rowSwapForDiagonalDominance(coefficientMatrix [][]float64, resultVector []float64) ([][]float64, []float64) {
	previousCoefficientMatrix := coefficientMatrix
	previouResultVector := resultVector
	numRows := len(coefficientMatrix)
	var count int = 0

	for i := 0; i < numRows; i++ {
		// Находим максимальный по модулю элемент в строке
		maxValue := math.Abs(coefficientMatrix[i][i])
		maxIndex := i

		for j := i + 1; j < numRows; j++ {
			if absValue := math.Abs(coefficientMatrix[j][i]); absValue > maxValue {
				maxValue = absValue
				maxIndex = j
			}
		}

		// Если максимальный элемент находится не на диагонали, меняем строки местами
		if maxIndex != i {
			count++
			coefficientMatrix[i], coefficientMatrix[maxIndex] = coefficientMatrix[maxIndex], coefficientMatrix[i]
			resultVector[i], resultVector[maxIndex] = resultVector[maxIndex], resultVector[i]
		}
	}

	//fmt.Println("Количество перестановок: ", count)

	if !isDiagonallyDominant(coefficientMatrix) {
		fmt.Println("Диагональное преобладание не было достигнуто!")
		return previousCoefficientMatrix, previouResultVector
	} else {
		//fmt.Println("Норма приобразованной матрицы:", matrixNorm(coefficientMatrix))
		return coefficientMatrix, resultVector
	}
}

// Функция для проверки диагонального преобладания матрицы
func isDiagonallyDominant(matrix [][]float64) bool {
	rows := len(matrix)
	cols := len(matrix[0])

	// Проверяем каждую строку матрицы
	for i := 0; i < rows; i++ {
		diagonal := math.Abs(matrix[i][i]) // Значение диагонального элемента
		sum := 0.0

		// Суммируем абсолютные значения элементов строки, исключая диагональный элемент
		for j := 0; j < cols; j++ {
			if j != i {
				sum += math.Abs(matrix[i][j])
			}
		}

		// Если абсолютное значение диагонального элемента меньше суммы абсолютных значений остальных элементов строки, то матрица не является диагонально-преобладающей
		if diagonal <= sum {
			return false
		}
	}

	return true
}

func matrixNorm(matrix [][]float64) float64 {
	rows := len(matrix)
	cols := len(matrix[0])

	var maxSum float64

	for i := 0; i < rows; i++ {
		var rowSum float64
		for j := 0; j < cols; j++ {
			rowSum += math.Abs(matrix[i][j])
		}
		if rowSum > maxSum {
			maxSum = rowSum
		}
	}

	return maxSum
}
