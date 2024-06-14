package graph

import (
	"fmt"
	"gonum.org/v1/plot"
	"gonum.org/v1/plot/plotter"
	"gonum.org/v1/plot/vg"
	"gonum.org/v1/plot/vg/draw"
	"image/color"
	"math"
)

// XYData is a struct to hold X and Y values.
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

// Create generates a plot with given data.
func Create(path string, xValues, yValues []float64) {
	//path := "out/all.png"

	//fmt.Println("xValues:", xValues)
	//fmt.Println("yValues:", yValues)
	//fmt.Println("corrYValues:", corrYValues)

	xMin := math.Inf(1)
	xMax := math.Inf(-1)
	yMin := math.Inf(1)
	yMax := math.Inf(-1)

	// Find min and max values for x and y axes
	for _, x := range xValues {
		if x < xMin {
			xMin = x
		}
		if x > xMax {
			xMax = x
		}
	}
	for _, y := range yValues {
		if y < yMin {
			yMin = y
		}
		if y > yMax {
			yMax = y
		}
	}

	// Extend the ranges by 5 units
	xMin -= 0.5
	xMax += 0.5
	yMin -= 0.5
	yMax += 0.5

	// Create a new plot
	p := plot.New()

	// Create scatter plot for points corresponding to xValues, yValues
	s1, _ := plotter.NewScatter(XYData{X: xValues, Y: yValues})
	s1.GlyphStyle.Color = color.RGBA{R: 255, A: 255}
	s1.GlyphStyle.Radius = vg.Length(3)
	s1.GlyphStyle.Shape = draw.CircleGlyph{}
	p.Add(s1)

	// Interpolate and plot the curve for yValues
	line1, _ := plotter.NewLine(XYData{X: xValues, Y: yValues})
	line1.Color = color.RGBA{R: 255, A: 255}
	p.Add(line1)

	// Set the range of axes
	p.X.Min = xMin
	p.X.Max = xMax
	p.Y.Min = yMin
	p.Y.Max = yMax

	// Set the tick marks for the x-axis with a step of 1
	p.X.Tick.Marker = plot.ConstantTicks(getTicks(int(math.Round(xMin)), int(math.Round(xMax)), 1))

	// Set the tick marks for the y-axis with a step of 1
	p.Y.Tick.Marker = plot.ConstantTicks(getTicks(int(math.Round(yMin)), int(math.Round(yMax)), 1))

	// Add legend
	p.Legend.Add("Полученные ответы", s1)

	// Save the plot
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
