package methods

import (
	"fmt"
	"os"
)

func RectangleMethod(f func(float64) float64, a, b, e float64, n int, h float64) (int, func(f func(float64) float64, a float64, b float64, e float64, n int, h float64) float64) {
	fmt.Println("Выберите модификацию для интегрирования:")
	fmt.Println("1. Левые")
	fmt.Println("2. Средние")
	fmt.Println("3. Правые")

	var integral float64

	var selectionMode string
	for {
		fmt.Println("Выберите модификацию (1-3):")
		fmt.Fscan(os.Stdin, &selectionMode)
		switch selectionMode {
		case "1":
			fmt.Println("Выбрана модификация - левые")
			return 1, LeftMethod
		case "2":
			fmt.Println("Выбрана модификация - средние")
			return 2, MiddleMethod
			fmt.Println(integral)
		case "3":
			fmt.Println("Выбрана модификация - правые")
			return 1, RightMethod
			fmt.Println(integral)
		default:
			fmt.Println("Укажите число, соответствующее предложенному варианту!")
			continue
		}
		break
	}
	return 0, nil
}

func LeftMethod(f func(float64) float64, a, b, e float64, n int, h float64) float64 {
	var sumY float64

	sumY = 0
	x := a
	for i := 0; i < int(n); i++ {
		sumY = f(x) + sumY
		x = x + h
	}
	sumY = sumY * h
	return sumY
}

func RightMethod(f func(float64) float64, a, b, e float64, n int, h float64) float64 {
	var sumY float64

	sumY = 0
	x := a + h
	for i := 1; i <= int(n); i++ {
		sumY = f(x) + sumY
		x = x + h
	}
	return sumY * h
}

func MiddleMethod(f func(float64) float64, a, b, e float64, n int, h float64) float64 {
	var sumY float64

	sumY = 0
	x := a + (h / 2)
	for i := 0; i < int(n); i++ {
		sumY = f(x) + sumY
		x = x + h
	}
	return sumY * h
}
