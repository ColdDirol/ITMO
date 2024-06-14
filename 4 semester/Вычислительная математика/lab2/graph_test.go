package main

import (
	"gonum.org/v1/plot"
	"gonum.org/v1/plot/plotter"
	"gonum.org/v1/plot/vg"
	"testing"
)

func TestCreateGraphTest(t *testing.T) {
	// Указание диапазона для построения графика (от a до b)
	a := -5.0
	b := 5.0

	// Создание нового графика
	p := plot.New()

	// Создание точек для построения графика
	var pts plotter.XYs
	for x := a; x <= b; x += 0.1 {
		// Вычисление значения y для каждой точки x
		y := x + 1
		// Добавление точки к графику
		pts = append(pts, plotter.XY{X: x, Y: y})
	}

	// Создание линии из точек
	line, err := plotter.NewLine(pts)
	if err != nil {
		panic(err)
	}

	// Добавление линии к графику
	p.Add(line)

	// Установка диапазона по осям X и Y
	p.X.Min = a
	p.X.Max = b
	p.Y.Min = -10
	p.Y.Max = 10

	// Сохранение графика в файл
	if err := p.Save(10*vg.Inch, 10*vg.Inch, "graph.png"); err != nil {
		panic(err)
	}
}
