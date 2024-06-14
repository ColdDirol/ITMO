package utils

import (
	"fmt"
	"math"
)

func Verify(xValues []float64) error {
	for _, x := range xValues {
		if x <= 0 {
			return fmt.Errorf("некорректное значение x: %f (должно быть положительным)", x)
		}
	}
	return nil
}

func CheckNaN(values []float64) bool {
	for _, v := range values {
		if math.IsNaN(v) {
			return true
		}
	}
	return false
}
