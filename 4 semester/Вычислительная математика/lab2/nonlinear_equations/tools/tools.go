package tools

import (
	"gonum.org/v1/plot"
	"gonum.org/v1/plot/plotter"
	"gonum.org/v1/plot/vg"
	"math"
)

func FindFunctrion(x float64, coefficients []float64) float64 {
	var y float64
	for i, val := range coefficients {
		if i == 0 {
			y += val
		} else {
			y += val * math.Pow(x, float64(i))
		}
	}

	return y
}

func FindTheDerivative(coefficients []float64) []float64 {
	var derivative []float64
	for i, val := range coefficients {
		if i == 0 {
			continue
		}
		derivative = append(derivative, val*float64(i))
	}
	return derivative
}

func CreateGraphWithIntervals(a, b float64, coefficients []float64) {
	// Создание нового графика
	p := plot.New()

	// Создание точек для построения графика
	var pts plotter.XYs
	for x := a; x <= b; x += 0.1 {
		// Вычисление значения y для каждой точки x
		y := FindFunctrion(x, coefficients)
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
