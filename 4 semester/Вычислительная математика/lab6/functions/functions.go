package functions

import (
	"compMathLab6/utils"
	"math"
)

func F1(mode int) func(x, y float64) float64 {
	switch mode {
	case 1:
		return Y1
	case 2:
		return TY1
	}
	return nil
}

func Y1(x, y float64) float64 {
	return utils.Rounding(y + (1+x)*math.Pow(y, 2))
}

func TY1(x, y float64) float64 {
	return utils.Rounding(-(math.Exp(x)) / (x * math.Exp(x)))
}

func F2(mode int) func(x, y float64) float64 {
	switch mode {
	case 1:
		return Y2
	case 2:
		return TY2
	}
	return nil
}

func Y2(x, y float64) float64 {
	return utils.Rounding(x * math.Pow(y, 2))
}

func TY2(x, y float64) float64 {
	return utils.Rounding(-2 / (math.Pow(x, 2)))
}

func F3(mode int) func(x, y float64) float64 {
	switch mode {
	case 1:
		return Y3
	case 2:
		return TY3
	}
	return nil
}

func Y3(x, y float64) float64 {
	return utils.Rounding(y - math.Sin(x))
}

func TY3(x, y float64) float64 {
	return utils.Rounding(math.Sin(x)/2 + math.Cos(x)/2 + math.Exp(x))
}
