package read

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func FromConsole() (xValues []float64, yValues []float64, err error) {
	scanner := bufio.NewScanner(os.Stdin)

	fmt.Println("Введите значения X через пробел:")
	if scanner.Scan() {
		line := scanner.Text()
		xValuesStrings := strings.Fields(line)
		xValues = make([]float64, len(xValuesStrings))

		for i, xStr := range xValuesStrings {
			x, err := strconv.ParseFloat(xStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа x%d: %v\n", i+1, err)
				fmt.Println("Введите значения X через пробел:")
				return nil, nil, err
			}
			xValues[i] = x
		}
	}

	fmt.Println("Введите значения Y через пробел:")
	if scanner.Scan() {
		line := scanner.Text()
		yValuesStrings := strings.Fields(line)
		yValues = make([]float64, len(yValuesStrings))

		for i, yStr := range yValuesStrings {
			y, err := strconv.ParseFloat(yStr, 64)
			if err != nil {
				fmt.Printf("Ошибка при чтении числа y%d: %v\n", i+1, err)
				fmt.Println("Введите значения Y через пробел:")
				return nil, nil, err
			}
			yValues[i] = y
		}
	}

	return xValues, yValues, nil
}
