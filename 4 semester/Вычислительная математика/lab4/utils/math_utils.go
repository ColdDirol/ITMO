package utils

import (
	"math"
)

func SumPow(values []float64, pow int) float64 {
	var sum float64
	for _, value := range values {
		sum += math.Pow(value, float64(pow))
	}
	return Rounding(sum)
}

func SumCouple(aValues []float64, aPow int, bValues []float64, bPow int) float64 {
	var sum float64
	for i := 0; i < len(aValues); i++ {
		for j := 0; j < len(bValues); j++ {
			if i == j {
				sum += math.Pow(aValues[i], float64(aPow)) * math.Pow(bValues[j], float64(bPow))
			}
		}
	}

	return Rounding(sum)
}

func SumCoupleALnB(aValues []float64, bValues []float64) float64 {
	var sum float64
	for i := 0; i < len(aValues); i++ {
		sum += aValues[i] * math.Log(bValues[i])
	}
	return sum
}

func SumCoupleLn(aValues []float64, bValues []float64) float64 {
	var sum float64
	for i := 0; i < len(aValues); i++ {
		sum += math.Log(aValues[i]) * math.Log(bValues[i])
	}
	return sum
}

func SumLnPow(values []float64, pow int) float64 {
	var sum float64
	for _, value := range values {
		sum += math.Pow(math.Log(value), float64(pow))
	}
	return sum
}

func Difference(aValues, bValues []float64) []float64 {
	var diffValues []float64
	for i := 0; i < len(aValues); i++ {
		diffValues = append(diffValues, Rounding(aValues[i]-bValues[i]))
	}
	return diffValues
}

func GaussianElimination(A [][]float64, B []float64) []float64 {
	// прямой ход приводит к верхнетреугольной матрице:
	// a b c
	// 0 b1 c1
	// 0 0 c2

	n := len(B)
	// Прямой ход метода Гаусса
	for i := 0; i < n; i++ {
		// Деление строки i на A[i][i]
		scale := 1 / A[i][i]
		for j := i; j < n; j++ {
			A[i][j] *= scale
		}
		if math.IsInf(scale, 0) || math.IsNaN(scale) {
			scale = 0
		}
		B[i] *= scale
		//fmt.Println(A[i])

		//fmt.Println(scale)
		//fmt.Println(A)
		//fmt.Println(B)

		// Вычитание i-й строки из оставшихся строк
		for k := i + 1; k < n; k++ {
			factor := A[k][i]
			for j := i; j < n; j++ {
				if math.IsInf(A[i][j], 0) || math.IsNaN(A[i][j]) {
					A[i][j] = 0
				}
				//fmt.Println("---")
				//fmt.Println(factor*A[i][j], factor, A[k][j], A[i][j])
				A[k][j] -= factor * A[i][j]
			}
			B[k] -= factor * B[i]

			//fmt.Println("---")
			//fmt.Println(A)
			//fmt.Println(B)
		}
		//fmt.Println("<>")
	}

	//fmt.Println(A)
	//fmt.Println(B)

	// Обратный ход метода Гаусса
	coefficients := make([]float64, n)
	for i := n - 1; i >= 0; i-- {
		coefficients[i] = B[i]
		for j := i + 1; j < n; j++ {
			coefficients[i] -= A[i][j] * coefficients[j]
		}
	}
	return coefficients
}

func StandardDeviation(pValues, yValues []float64) float64 {
	var sum float64
	for i := 0; i < len(pValues); i++ {
		sum += math.Pow(pValues[i]-yValues[i], 2)
	}

	return math.Sqrt(sum / float64(len(pValues)))
}

func Average(numbers []float64) float64 {
	total := 0.0
	for _, num := range numbers {
		total += num
	}
	return total / float64(len(numbers))
}

func Rounding(val float64) float64 {
	return math.Round(val*10000) / 10000
}

func DeterminationCoefficient(pValues, yValues []float64) float64 {
	averageP := Average(pValues)
	var numerator float64
	var denominator float64

	for i := 0; i < len(yValues); i++ {
		numerator += math.Pow(yValues[i]-pValues[i], 2)
	}

	for i := 0; i < len(yValues); i++ {
		denominator += math.Pow(yValues[i]-averageP, 2)
	}

	return 1 - (numerator / denominator)
}

func DeterminationMessage(detCoefficient float64) string {
	if detCoefficient >= 0.95 {
		return "Высокая точность аппроксимации"
	} else if detCoefficient >= 0.75 && detCoefficient < 0.95 {
		return "Удовлетворительная точность аппрокцимации"
	} else if detCoefficient >= 0.5 && detCoefficient < 0.75 {
		return "Низкая точность аппрокцимации"
	} else if detCoefficient >= 0 && detCoefficient < 0.5 {
		return "Точность аппрокцимации недостаточна. Модель требует изменений"
	} else {
		return "При подсчете точности аппроксимации произошла ошибка"
	}
}

func Max(values []float64) (float64, int) {
	var maxValue float64
	var maxIndex int
	for i, v := range values {
		if v > maxValue {
			maxValue = v
			maxIndex = i
		}
	}

	return maxValue, maxIndex
}

func Min(values []float64) (float64, int) {
	minValue := values[0]
	minIndex := 0
	for i, v := range values {
		if v < minValue {
			minValue = v
			minIndex = i
		}
	}
	return minValue, minIndex
}
