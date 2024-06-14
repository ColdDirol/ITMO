package utils

import (
	"fmt"
	"github.com/jedib0t/go-pretty/v6/table"
	"os"
)

func PrintTable(name string, columnNames []string, matrix [][]float64) {
	t := table.NewWriter()
	t.SetOutputMirror(os.Stdout)

	fmt.Println()
	fmt.Printf("%s:\n", name)

	var headerRowString []interface{} // Здесь мы делаем слайс типа interface{} для различных типов значений

	for _, colName := range columnNames {
		headerRowString = append(headerRowString, colName)
	}

	t.AppendHeader(headerRowString)

	// Добавляем строки с данными
	for i := 0; i < len(matrix); i++ {
		dataValues := make([]interface{}, 0)
		for j := 0; j < len(matrix[i]); j++ {
			dataValues = append(dataValues, matrix[i][j])
		}
		t.AppendRow(dataValues)
	}

	t.Render()
}
