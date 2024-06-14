package main

import (
	"fmt"
	"math"
	"os"
)

func main() {
	var (
		readMod uint8

		coefficientMatrix [][]float64
		resultVector      []float64
		epsilon           float64
	)
	fmt.Println("\n\nВыберите тип ввода:")
	fmt.Println("1. Ввод из файла")
	fmt.Println("2. Ввод с консоли")
	fmt.Println("Введите цифру: ")

	for {
		fmt.Fscan(os.Stdin, &readMod)
		if readMod != 1 && readMod != 2 {
			fmt.Println("Выберите вариант из предложенных!")
			fmt.Println("Введите цифру: ")
		} else {
			switch readMod {
			case 1:
				coefficientMatrix, resultVector, epsilon = readFile()
			case 2:
				coefficientMatrix, resultVector, epsilon = readConsole()
			}
			break
		}
	}

	if coefficientMatrix == nil || resultVector == nil || epsilon == 0 {
		fmt.Println("Даннные были введены некорректно")
		os.Exit(-1)
	}

	// преобразование к диагональному приобладанию
	coefficientMatrix, resultVector = rowSwapForDiagonalDominance(coefficientMatrix, resultVector)
	fmt.Println("\nМатрица коэффициентов (после диагонального преобрадания):")
	for _, nums := range coefficientMatrix {
		for i := range nums {
			fmt.Print(nums[i], "\t")
		}
		fmt.Println()
	}

	fmt.Println("\nВектор результатов уравнения:")
	for i := range resultVector {
		fmt.Println(resultVector[i])
	}

	fmt.Println("\nEpsilon:", epsilon)

	fmt.Println("\nВектор неизвестных:")
	for i := 1; i <= len(resultVector); i++ {
		fmt.Printf("X%d ", i)
	}

	fmt.Println("\n\nРасчет:")
	currentValues := make([]float64, len(resultVector))  // значения x1, x2, x3, ..., xn на текущей итерации
	previousValues := make([]float64, len(resultVector)) // значения x1, x2, x3, ..., xn с предыдущей итерации

	var iteration int
	for iteration = 1; iteration <= 1000; iteration++ { // Максимальное количество итераций
		var maxDiff float64 // Максимальная абсолютная величина разности

		for i := range resultVector {
			sum := 0.0

			// формула, реализующаяся в цикле:
			// new_x1 = x1 - b1 - c1 / r1
			// new_x2 = a2 - x2 - c2 / r2
			// new_x3 = a3 - b3 - x3 / r3
			// ...

			for j := range resultVector {
				// найдем сумму элементов строки без диагонального элемента
				if i != j {
					sum += coefficientMatrix[i][j] * previousValues[j]
				}
			}

			// подставляем значения строки в формулу
			if coefficientMatrix[i][i] == 0 {
				fmt.Println("СЛАУ не имеет решенией!")
				fmt.Println("Ошибка: деление на 0 -> ВЫХОД")
				os.Exit(-1)
			}
			newValue := (resultVector[i] - sum) / coefficientMatrix[i][i]
			// сохраняем новые значения x1, x2, x3, ..., xn в массив
			currentValues[i] = newValue

			diff := math.Abs(newValue - previousValues[i]) // находим абсолютную величину разности
			// сравниваем с максимальной. Если больше -> новая максимальная величина
			if diff > maxDiff {
				maxDiff = diff
			}
		}

		fmt.Printf("Итерация %d: %v %e\n", iteration, currentValues, maxDiff)

		if maxDiff < epsilon {
			break
		} // если максимальная абсолютная величина разности < вероятности -> стоп, мы нашли ответ
		// иначе - продолжаем

		copy(previousValues, currentValues) // текущие найденные значения x1, x2, x3, ..., xn на данной итерации становятся значениями с предыдущей итерации
	}

	if iteration >= 1000 {
		fmt.Println("Достигнуто максимальное количество итераций")
	} else {
		fmt.Println("\nРешение сошлось после", iteration, "итераций")
		fmt.Println("Результат:", currentValues)
		// Вычисление вектора невязки
		residual := make([]float64, len(resultVector))
		for i := range resultVector {
			sum := 0.0
			for j := range resultVector {
				sum += coefficientMatrix[i][j] * currentValues[j]
			}
			residual[i] = resultVector[i] - sum
		}

		// Вывод вектора невязки
		fmt.Println("\nВектор невязки:")
		for i := range residual {
			fmt.Print(residual[i], " ")
		}
	}
}
