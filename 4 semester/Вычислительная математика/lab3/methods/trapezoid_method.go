package methods

func TrapezoidMethod(f func(float64) float64, a, b, e float64, n int, h float64) float64 {
	var result float64
	var x float64

	result = (f(a) + f(b)) / 2
	x = a + h
	for i := 1; i < int(n); i++ {
		result = f(x) + result
		x = x + h
	}

	return result * h
}
