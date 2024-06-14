package utils

import (
	"fmt"
	"math"
)

func Rounding(val float64) float64 {
	return math.Round(val*100000) / 100000
}

func FindMiddleStep(xValues []float64) (h float64) {
	for i := 0; i < len(xValues)-1; i++ {
		h += (xValues[i+1] - xValues[i])
	}

	h /= float64(len(xValues) - 1)

	return
}

func FindMiddle(xValues []float64) (m float64) {
	for _, v := range xValues {
		m += v
	}
	return m / float64(len(xValues))
}

func Factorial(n int) int {
	if n < 0 {
		fmt.Println("Ошибка: отрицательный аргумент")
		return -1 // Возвращаем ошибку
	}
	if n == 0 || n == 1 {
		return 1 // Факториал от 0 и 1 равен 1
	}
	result := 1
	for i := 2; i <= n; i++ {
		result *= i
	}
	return result
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

func MulArrays(coefficientsA, coefficientsB []float64) []float64 {
	result := make([]float64, len(coefficientsA)+len(coefficientsB)-1)

	for i := range coefficientsA {
		for j := range coefficientsB {
			result[i+j] += coefficientsA[i] * coefficientsB[j]
		}
	}

	return result
}
func CalculateCoefficientsForNewton(xValues []float64) (coefficients [][]float64) {
	var elements [][]float64
	var sub []float64

	for i := 0; i < len(xValues)-1; i++ {
		elements = append(elements, []float64{1, -1 * xValues[i]})
	}

	for i := 0; i < len(xValues)-1; i++ {
		if i == 0 {
			sub = elements[0]
			coefficients = append(coefficients, sub)
			continue
		}
		sub = MulArrays(sub, elements[i])
		coefficients = append(coefficients, sub)
	}

	return
}
func CalculateCoefficientsForLagrange(xValues []float64) (coefficients [][]float64) {
	var elements [][]float64
	var sub []float64

	for k := 0; k < len(xValues); k++ {
		elements = elements[:0]
		sub = sub[:0]
		for i := 0; i < len(xValues); i++ {
			if i != k {
				elements = append(elements, []float64{1, -1 * xValues[i]})
			}
		}
		//fmt.Println("elements:", elements)

		for i := 0; i < len(xValues)-1; i++ {
			if i == 0 {
				sub = elements[0]
				//fmt.Println("sub:", sub)
				continue
			}
			sub = MulArrays(sub, elements[i])
			//fmt.Println("sub:", sub)
		}
		coefficients = append(coefficients, sub)
	}
	return
}
func MulCoeffOnArray(coeff float64, coefficients []float64) (answer []float64) {
	for _, val := range coefficients {
		answer = append(answer, val*coeff)
	}
	return answer
}

func SumArrays(arr1, arr2 []float64) []float64 {
	if len(arr1) != len(arr2) {
		panic("Arrays must have the same length")
	}

	result := make([]float64, len(arr1))
	for i := range arr1 {
		result[i] = Rounding(arr1[i] + arr2[i])
	}
	return result
}
