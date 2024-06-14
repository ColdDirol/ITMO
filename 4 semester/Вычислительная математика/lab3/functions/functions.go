package functions

import "math"

func F1(x float64) float64 {
	return 5*math.Pow(x, 3) - 2*math.Pow(x, 2) + 3*x - 15
}

func F2(x float64) float64 {
	return 2*math.Pow(x, 3) - 9*math.Pow(x, 2) - 7*x + 11
}

func F3(x float64) float64 {
	return 1*math.Pow(x, 3) + 2*math.Pow(x, 2) - 3*x - 12
}

func F4(x float64) float64 {
	return 4*math.Pow(x, 3) - 5*math.Pow(x, 2) + 6*x - 7
}

func F5(x float64) float64 {
	return 3*math.Pow(x, 3) + 5*math.Pow(x, 2) + 3*x - 6
}

func F6(x float64) float64 {
	return 2*math.Pow(x, 3) - 3*math.Pow(x, 2) - 5*x + 27
}

func F7(x float64) float64 {
	return 1 / math.Sqrt(x)
}

func AD7(x float64) float64 {
	return 2 * math.Sqrt(x)
}

func F8(x float64) float64 {
	return 1 / (1 - x)
}

func AD8(x float64) float64 {
	return -math.Log(math.Abs(x - 1))
}

func Ethlone1(a, b, x float64) float64 {
	return 1 / x
}

func Ethlone2(a, b, x float64) float64 {
	return 1 / (b - x)
}

func Ethlone3(a, b, x float64) float64 {
	return 1 / (x - a)
}
