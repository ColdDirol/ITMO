package newton_finite_differences

import (
	"fmt"
	"github.com/jedib0t/go-pretty/v6/table"
	"os"
)

func printTableOfDiffs(xValues []float64, matrix [][]float64) {
	t := table.NewWriter()
	t.SetOutputMirror(os.Stdout)

	fmt.Println()
	fmt.Println("Таблица конечных разностей:")

	var headerRowString []interface{} // Здесь мы делаем слайс типа interface{} для различных типов значений

	headerRowString = append(headerRowString, "#")
	headerRowString = append(headerRowString, "xi")
	headerRowString = append(headerRowString, "yi")

	headerRowString = append(headerRowString, "Δyi")
	for i := 0; i < len(matrix[0])-2; i++ {
		headerRowString = append(headerRowString, fmt.Sprintf("Δ%dyi", i+2))
	}

	t.AppendHeader(headerRowString)

	// Добавляем строки с данными
	for i := 0; i < len(matrix); i++ {
		dataValues := make([]interface{}, 0)
		dataValues = append(dataValues, i) // Номер строки
		dataValues = append(dataValues, xValues[i])
		dataValues = append(dataValues, matrix[i][0]) // xi
		dataValues = append(dataValues, matrix[i][1]) // yi

		// Добавляем значения Δyi, Δ2yi и т.д.
		for j := 2; j < len(matrix[i]); j++ {
			dataValues = append(dataValues, matrix[i][j])
		}

		t.AppendRow(dataValues)
		//t.AppendSeparator() // Separator
	}

	t.Render()
}
