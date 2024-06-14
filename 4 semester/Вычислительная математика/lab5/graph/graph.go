package graph

import (
	"compMathLab5/methods/lagrange"
	"compMathLab5/methods/newton_finite_differences"
	"compMathLab5/utils"
	"fmt"
	"gonum.org/v1/plot"
	"gonum.org/v1/plot/plotter"
	"gonum.org/v1/plot/vg"
	"gonum.org/v1/plot/vg/draw"
	"image/color"
	"math"
)

type XYData struct {
	X []float64
	Y []float64
}

// Len returns the length of XYData.
func (xy XYData) Len() int {
	return len(xy.X)
}

// XY returns the X, Y values at index i.
func (xy XYData) XY(i int) (x, y float64) {
	return xy.X[i], xy.Y[i]
}

func f(x float64, coefficients []float64) (y float64) {
	for i, coef := range coefficients {
		exp := float64(len(coefficients) - i - 1)
		y += coef * math.Pow(x, exp)
	}
	return
}

func printPolynom(coefficients []float64) {
	n := len(coefficients)
	for i, coef := range coefficients {
		exp := float64(n - i - 1)
		if i < n-1 {
			fmt.Printf("%g*x^%g + ", coef, exp)
		} else {
			fmt.Printf("%g", coef)
		}
	}
	fmt.Println()
}

func Create(path string, xValues, yValues []float64, coefficients []float64) {

	xMin, _ := utils.Min(xValues)
	xMax, _ := utils.Max(xValues)
	yMin, _ := utils.Min(yValues)
	yMax, _ := utils.Max(yValues)

	xMin -= 5 // Extend the x range by 5 units to the left
	xMax += 5 // Extend the x range by 5 units to the right
	yMin -= 5 // Extend the y range by 5 units downwards
	yMax += 5 // Extend the y range by 5 units upwards

	// Create a new plot
	p := plot.New()

	// Create points for plotting the graph
	var pts plotter.XYs
	for x := xMin; x <= xMax; x += 0.1 {
		y := f(x, coefficients)
		pts = append(pts, plotter.XY{X: x, Y: y})
	}

	line, err := plotter.NewLine(pts)
	if err != nil {
		panic(err)
	}

	p.Add(line)

	var scatter plotter.XYs
	for i := range xValues {
		scatter = append(scatter, plotter.XY{X: xValues[i], Y: yValues[i]})
	}

	s, err := plotter.NewScatter(scatter)
	if err != nil {
		panic(err)
	}

	s.GlyphStyle.Color = color.RGBA{R: 255, A: 255} // Red color for the points
	s.GlyphStyle.Radius = vg.Length(3)

	// Set the shape of the points
	s.GlyphStyle.Shape = draw.CircleGlyph{}

	p.Add(s)

	p.X.Min = xMin
	p.X.Max = xMax
	p.Y.Min = yMin
	p.Y.Max = yMax

	// Set the tick marks for the x-axis with a step of 1
	p.X.Tick.Marker = plot.ConstantTicks(getTicks(int(math.Round(xMin)), int(math.Round(xMax)), 1))

	// Set the tick marks for the y-axis with a step of 1
	p.Y.Tick.Marker = plot.ConstantTicks(getTicks(int(math.Round(yMin)), int(math.Round(yMax)), 1))

	if err := p.Save(10*vg.Inch, 10*vg.Inch, path); err != nil {
		panic(err)
	}
}

func CreateAll(xValues, yValues []float64) {
	path := "out/all.png"
	// Lagrange
	coefficients1 := lagrange.GetLagrangeCoefficients(xValues, yValues)
	fmt.Println()
	fmt.Println("Лагранж:")
	printPolynom(coefficients1)
	// Newton
	coefficients2 := newton_finite_differences.GetNewtonCoefficients(xValues, yValues)
	fmt.Println("Ньютон:")
	printPolynom(coefficients2)

	xMin, _ := utils.Min(xValues)
	xMax, _ := utils.Max(xValues)
	yMin, _ := utils.Min(yValues)
	yMax, _ := utils.Max(yValues)

	xMin -= 1 // Extend the x range by 5 units to the left
	xMax += 1 // Extend the x range by 5 units to the right
	yMin -= 1 // Extend the y range by 5 units downwards
	yMax += 1 // Extend the y range by 5 units upwards

	// Create a new plot
	p := plot.New()

	// Create points for plotting the graph
	var pts1 plotter.XYs
	for x := xMin; x <= xMax; x += 0.1 {
		y := f(x, coefficients1)
		pts1 = append(pts1, plotter.XY{X: x, Y: y})
	}
	line1, err := plotter.NewLine(pts1)
	if err != nil {
		panic(err)
	}
	line1.Color = color.RGBA{R: 255, A: 255}
	p.Add(line1)

	// Create points for plotting the graph
	var pts2 plotter.XYs
	for x := xMin; x <= xMax; x += 0.1 {
		y := f(x, coefficients2)
		pts2 = append(pts2, plotter.XY{X: x, Y: y})
	}
	line2, err := plotter.NewLine(pts2)
	if err != nil {
		panic(err)
	}
	line2.Color = color.RGBA{B: 255, A: 255}
	p.Add(line2)

	var scatter plotter.XYs
	for i := range xValues {
		scatter = append(scatter, plotter.XY{X: xValues[i], Y: yValues[i]})
	}

	s, err := plotter.NewScatter(scatter)
	if err != nil {
		panic(err)
	}

	s.GlyphStyle.Color = color.RGBA{A: 255} // Green color for the points
	s.GlyphStyle.Radius = vg.Length(3)

	// Set the shape of the points
	s.GlyphStyle.Shape = draw.CircleGlyph{}

	p.Add(s)

	// Create scatter plot for points corresponding to xValues, yValues
	s1, _ := plotter.NewScatter(XYData{X: []float64{xMin - 1}, Y: []float64{yMin - 1}})
	s1.GlyphStyle.Color = color.RGBA{R: 255, A: 255}
	s1.GlyphStyle.Radius = vg.Length(3)
	s1.GlyphStyle.Shape = draw.CircleGlyph{}
	p.Add(s1)

	// Create scatter plot for points corresponding to xValues, corrYValues
	s2, _ := plotter.NewScatter(XYData{X: []float64{xMin - 1}, Y: []float64{yMin - 1}})
	s2.GlyphStyle.Color = color.RGBA{B: 255, A: 255}
	s2.GlyphStyle.Radius = vg.Length(3)
	s2.GlyphStyle.Shape = draw.CircleGlyph{}
	p.Add(s2)

	p.X.Min = xMin
	p.X.Max = xMax
	p.Y.Min = yMin
	p.Y.Max = yMax

	// Set the tick marks for the x-axis with a step of 1
	p.X.Tick.Marker = plot.ConstantTicks(getTicks(int(math.Round(xMin)), int(math.Round(xMax)), 1))

	// Set the tick marks for the y-axis with a step of 1
	p.Y.Tick.Marker = plot.ConstantTicks(getTicks(int(math.Round(yMin)), int(math.Round(yMax)), 1))

	p.Legend.Add("Многочлен Лагранжа", s1)
	p.Legend.Add("Многочлен Ньютона", s2)

	if err := p.Save(10*vg.Inch, 10*vg.Inch, path); err != nil {
		panic(err)
	}
}

// getTicks returns tick marks from min to max with step
func getTicks(min, max, step int) []plot.Tick {
	var ticks []plot.Tick
	for i := min; i <= max; i += step {
		label := fmt.Sprintf("%d", i)
		ticks = append(ticks, plot.Tick{Value: float64(i), Label: label})
	}
	return ticks
}
