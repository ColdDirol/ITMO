package methods

import (
	"fmt"
	"gonum.org/v1/plot"
	"gonum.org/v1/plot/plotter"
	"gonum.org/v1/plot/vg"
	"math"
	"os"
)

const (
	first  = "1"
	second = "2"
	third  = "3"
)

const (
	maxIter = 1000
)

// Система уравнений 1
func f1(x, y float64) (float64, float64) {
	return math.Tan(x*y) - math.Pow(x, 2), 0.5*math.Pow(x, 2) + 2*math.Pow(y, 2) - 1
}

// Производные системы уравнений 1
func jacobian1(x, y float64) (float64, float64, float64, float64) {
	df1dx := y/(math.Pow(math.Cos(x*y), 2)) - 2*x
	df1dy := x / (math.Pow(math.Cos(x*y), 2))
	df2dx := x
	df2dy := 4 * y
	return df1dx, df1dy, df2dx, df2dy
}

// Система уравнений 2
func f2(x, y float64) (float64, float64) {
	return math.Sin(x+y) - 1.4*x, math.Pow(x, 2) + math.Pow(y, 2) - 1
}

// Производные системы уравнений 2
func jacobian2(x, y float64) (float64, float64, float64, float64) {
	df1dx := math.Cos(x+y) - 1.4
	df1dy := math.Cos(x + y)
	df2dx := 2 * x
	df2dy := 2 * y
	return df1dx, df1dy, df2dx, df2dy
}

// Система уравнений 3
func f3(x, y float64) (float64, float64) {
	return math.Pow(x, 2) + math.Pow(y, 2) - 4, y - 3*math.Pow(x, 2)
}

// Производные системы уравнений 3
func jacobian3(x, y float64) (float64, float64, float64, float64) {
	df1dx := 2 * x
	df1dy := 2 * y
	df2dx := 2 * 3 * x
	df2dy := 1.0
	return df1dx, df1dy, df2dx, df2dy
}

// Метод Ньютона
func newtonMethod(f func(float64, float64) (float64, float64), jacobian func(float64, float64) (float64, float64, float64, float64), x0, y0 float64) (float64, float64, error) {
	x, y := x0, y0
	fmt.Println("\n")
	for i := 0; i < maxIter; i++ {
		fx, fy := f(x, y)
		jx, jy, jz, jw := jacobian(x, y)

		detJ := jx*jw - jy*jz
		if math.Abs(detJ) < tolerance {
			return 0, 0, fmt.Errorf("Якобиан имеет единичную матрицу")
		}

		deltaX := (jw*fx - jy*fy) / detJ
		deltaY := (-jz*fx + jx*fy) / detJ

		fmt.Println("deltaX:", deltaX)

		x -= deltaX
		y -= deltaY

		if math.Abs(deltaX) < tolerance && math.Abs(deltaY) < tolerance {
			fmt.Println("Количество итераций:", i+1)
			return x, y, nil
		}
	}
	return 0, 0, fmt.Errorf("Достигнут лимит итераций")
}

var tolerance float64

func NewtonMethod() {
	fmt.Println("Выберите решаемое уравнение:")
	fmt.Println("1:")
	fmt.Println("tgxy = x^2")
	fmt.Println("0.5x^2 + 2y^2 = 1")
	fmt.Println("2:")
	fmt.Println("sin(x+y)-1.4x = 0")
	fmt.Println("x^2 + y^2 = 1")

	var equation string

	// Начальное приближение для x и y
	var x0, y0 float64

	for {
		fmt.Fscan(os.Stdin, &equation)
		switch equation {
		case first:
			fmt.Println("Введите точность")
			fmt.Fscan(os.Stdin, &tolerance)

			fmt.Println("Введите начальное прибижение для x")
			fmt.Fscan(os.Stdin, &x0)

			fmt.Println("Введите начальное прибижение для y")
			fmt.Fscan(os.Stdin, &y0)

			x1, y1, err1 := newtonMethod(f1, jacobian1, x0, y0)

			if err1 != nil {
				fmt.Println("Ошибка решения системы 1:", err1)
				os.Exit(1)
			} else {
				fmt.Printf("Решение системы 1: x = %v, y = %v\n", x1, y1)
				//fmt.Println(f1(x1, y1))
				createGraph(equation)
				os.Exit(0)
			}
		case second:
			fmt.Println("Введите точность")
			fmt.Fscan(os.Stdin, &tolerance)

			fmt.Println("Введите начальное прибижение для x")
			fmt.Fscan(os.Stdin, &x0)

			fmt.Println("Введите начальное прибижение для y")
			fmt.Fscan(os.Stdin, &y0)

			x2, y2, err2 := newtonMethod(f2, jacobian2, x0, y0)
			if err2 != nil {
				fmt.Println("Ошибка решения системы 2:", err2)
				os.Exit(1)
			} else {
				fmt.Printf("Решение системы 2: x = %v, y = %v\n", x2, y2)

				os.Exit(0)
			}
		case third:
			fmt.Println("Введите точность")
			fmt.Fscan(os.Stdin, &tolerance)

			fmt.Println("Введите начальное прибижение для x")
			fmt.Fscan(os.Stdin, &x0)

			fmt.Println("Введите начальное прибижение для y")
			fmt.Fscan(os.Stdin, &y0)

			x3, y3, err3 := newtonMethod(f3, jacobian3, x0, y0)
			if err3 != nil {
				fmt.Println("Ошибка решения системы 2:", err3)
				os.Exit(1)
			} else {
				fmt.Printf("Решение системы 2: x = %v, y = %v\n", x3, y3)

				os.Exit(0)
			}
		default:
			fmt.Printf("Укажите число, соответствующее: %s, %s или %s\n", first, second, third)
		}
	}
}

func createGraph(variant string) {
	p := plot.New()
	p.X.Min = -10
	p.X.Max = 10
	p.Y.Min = -10
	p.Y.Max = 10

	switch variant {
	case first:
		// Создание точек для построения графика для системы уравнений 1
		var pts1 plotter.XYs
		for x := -3.0; x <= 3.0; x += 0.1 {
			for y := -3.0; y <= 3.0; y += 0.1 {
				f1x, f1y := f1(x, y)
				if math.Abs(f1x) < 10 && math.Abs(f1y) < 10 {
					pts1 = append(pts1, plotter.XY{X: x, Y: y})
				}
			}
		}

		// Создание точечного графика для системы уравнений 1
		scatter1, err := plotter.NewScatter(pts1)
		if err != nil {
			fmt.Println("Ошибка создания точечного графика:", err)
			os.Exit(1)
		}
		scatter1.GlyphStyle.Radius = vg.Points(1)

		p.Add(scatter1)
	case second:
		// Создание точек для построения графика для системы уравнений 2
		var pts2 plotter.XYs
		for x := -3.0; x <= 3.0; x += 0.1 {
			for y := -3.0; y <= 3.0; y += 0.1 {
				f2x, f2y := f2(x, y)
				if math.Abs(f2x) < 10 && math.Abs(f2y) < 10 {
					pts2 = append(pts2, plotter.XY{X: x, Y: y})
				}
			}
		}

		// Создание точечного графика для системы уравнений 2
		scatter2, err := plotter.NewScatter(pts2)
		if err != nil {
			fmt.Println("Ошибка создания точечного графика:", err)
			os.Exit(1)
		}
		scatter2.GlyphStyle.Radius = vg.Points(1)
		p.Add(scatter2)
	}

	if err := p.Save(6*vg.Inch, 6*vg.Inch, "system.png"); err != nil {
		fmt.Println("Ошибка сохранения графика:", err)
		os.Exit(1)
	}
}
