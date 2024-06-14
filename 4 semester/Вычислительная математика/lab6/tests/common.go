package tests

import (
	"compMathLab6/utils"
	"math"
)

func ty(x float64) float64 {
	return utils.Rounding(-(math.Exp(x)) / (x * math.Exp(x)))
}

func f(x, y float64) float64 {
	return utils.Rounding(y + (1+x)*math.Pow(y, 2))
}
