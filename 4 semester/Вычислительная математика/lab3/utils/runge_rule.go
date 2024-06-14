package utils

import "math"

func RungeRule(I_h, I_h_2 float64, k int) float64 {
	if k <= 0 {
		return math.Abs(I_h_2 - I_h)
	} else {
		return math.Abs(I_h_2-I_h) / (math.Pow(2, float64(k)) - 1)
	}
}
