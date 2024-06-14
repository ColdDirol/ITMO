package graph

import (
	"compMathLab4/utils"
	"fmt"
	"gonum.org/v1/plot"
	"gonum.org/v1/plot/plotter"
	"gonum.org/v1/plot/vg"
	"gonum.org/v1/plot/vg/draw"
	"image/color"
	"math"
)

const (
	linear      = "linear"
	p_f_2_d     = "p_f_2_d"
	p_f_3_d     = "p_f_3_d"
	exponential = "exponential"
	logarithmic = "logarithmic"
	pow         = "pow"
)

func Create(path string, xValues, yValues []float64, f func(x float64) float64, fName string) {

	xMin, _ := utils.Min(xValues)
	xMax, _ := utils.Max(xValues)
	yMin, _ := utils.Min(yValues)
	yMax, _ := utils.Max(yValues)

	xMin -= 5 // Extend the x range by 5 units to the left
	xMax += 5 // Extend the x range by 5 units to the right
	yMin -= 5 // Extend the y range by 5 units downwards
	yMax += 5 // Extend the y range by 5 units upwards

	switch fName {
	//case linear:
	//case p_f_2_d:
	//case p_f_3_d:
	case exponential:
		//yMin = 0.1
		yMin = math.Min(math.Max(yMin, -0.1), 0.1)
		yMax = math.Max(yMax, 0.1)
	case logarithmic:
		xMin = 0.1
	case pow:
		xMin = math.Max(xMin, 0.1)
		yMin = math.Max(yMin, 0.1)
	}

	// Create a new plot
	p := plot.New()

	// Create points for plotting the graph
	var pts plotter.XYs
	for x := xMin; x <= xMax; x += 0.1 {
		y := f(x)
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

// getTicks returns tick marks from min to max with step
func getTicks(min, max, step int) []plot.Tick {
	var ticks []plot.Tick
	for i := min; i <= max; i += step {
		label := fmt.Sprintf("%d", i)
		ticks = append(ticks, plot.Tick{Value: float64(i), Label: label})
	}
	return ticks
}
