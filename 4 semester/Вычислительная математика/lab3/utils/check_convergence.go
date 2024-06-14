package utils

import (
	"compMathLab3/functions"
	"fmt"
	"math"
)

func CheckConvergence(f func(float64) float64, ad func(float64) float64, a, b float64) bool {
	var x float64 = 123
	if functions.Ethlone2(a, b, b) == f(b) {
		fmt.Println("functions.Ethlone2(a, b, x) == f(x)")
		return false
	}
	if functions.Ethlone3(a, b, x) == f(x) {
		fmt.Println("functions.Ethlone3(a, b, a) == f(a)")
		return false
	}

	aScaled := int(a * 1000)
	bScaled := int(b * 1000)
	stepScaled := int(0.001 * 1000)

	for i := aScaled; i <= bScaled; i += stepScaled {
		current1 := float64(i) / 1000
		if math.IsInf(f(current1), -1) {
			fmt.Printf("F(%g) стремится к -inf\n", current1)
			for j := aScaled; j <= bScaled; j += stepScaled {
				current2 := float64(j) / 1000
				if math.IsInf(ad(current2), -1) {
					fmt.Printf("F(%g) стремится к -inf\n", current2)
					return false
				}
				if math.IsInf(ad(current2), 1) {
					fmt.Printf("F(%g) стремится к +inf\n", current2)
					return false
				}
			}
			fmt.Printf("F(%g) стремится к %g - OK\n", current1, ad(current1))
		}
		if math.IsInf(f(current1), 1) {
			fmt.Printf("f(%g) стремится к +inf\n", current1)
			for j := aScaled; j <= bScaled; j += stepScaled {
				current2 := float64(j) / 1000
				if math.IsInf(ad(current2), -1) {
					fmt.Printf("F(%g) стремится к -inf\n", current2)
					return false
				}
				if math.IsInf(ad(current2), 1) {
					fmt.Printf("F(%g) стремится к +inf\n", current2)
					return false
				}
			}
			fmt.Printf("F(%g) стремится к %g - OK\n", current1, ad(current1))
		}
	}

	return true
}
