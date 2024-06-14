package utils

func ReverseSlice(array []float64) []float64 {
	var reverse []float64

	for i := len(array) - 1; i >= 0; i-- {
		reverse = append(reverse, array[i])
	}

	return reverse
}
