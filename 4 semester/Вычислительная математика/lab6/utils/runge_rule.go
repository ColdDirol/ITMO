package utils

import "math"

func RungeRule(yh, yhd2 float64, n int) float64 {
	return Rounding(math.Abs(yh-yhd2) / (math.Pow(2, float64(n)) - 1))
}

func Epsilon(y, yCorr float64) float64 {
	return Rounding(math.Abs(y - yCorr))
}
