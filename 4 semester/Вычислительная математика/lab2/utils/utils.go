package utils

func ReverseSlice(slice []float64) {
	// Вычисляем длину слайса
	length := len(slice)

	// Итерируемся только до середины слайса
	for i := 0; i < length/2; i++ {
		// Меняем элементы местами
		slice[i], slice[length-i-1] = slice[length-i-1], slice[i]
	}
}
