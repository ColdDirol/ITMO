package utils

import (
	"fmt"
	"math"
)

func FindDiscontinuity(f func(x float64) float64, a, b float64) (float64, float64, float64, float64) {
	fmt.Println(a, b)
	var aBefore, bBefore, aAfter, bAfter float64

	var found bool
	var foundAt float64
	for i := a; i <= b; i += 0.01 {
		if math.IsInf(f(i), 0) {
			found = true
			foundAt = i
			break
		}
	}

	if !found {
		return 0, 0, 0, 0 // Нет разрыва
	}

	// Проверяем, где произошел разрыв
	if foundAt == a {
		aBefore = a + math.SmallestNonzeroFloat64
		bBefore = b
		aAfter = -1
		bAfter = -1
	} else if foundAt == b {
		aBefore = a
		bBefore = b - math.SmallestNonzeroFloat64
		aAfter = -1
		bAfter = -1
	} else {
		aBefore = a
		bBefore = foundAt - 0.0001
		aAfter = foundAt + 0.0001
		bAfter = b
	}

	return aBefore, bBefore, aAfter, bAfter
}
