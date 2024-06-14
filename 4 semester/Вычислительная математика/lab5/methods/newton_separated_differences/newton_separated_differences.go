package newton_separated_differences

import "fmt"

func comp(y1, y0, x1, x0 float64) float64 {
	return (y1 - y0) / (x1 - x0)
}

func f(x float64, xValues, yValues []float64) float64 {
	max := len(xValues)

	var prev []float64 = yValues
	var curr []float64
	var fValues []float64
	var iteration int = 1
	for {
		if max == 1 {
			break
		}
		if iteration == 1 {
			for i := 0; i < max-1; i++ {
				curr = append(curr, comp(prev[i+1], prev[i], xValues[i+1], xValues[i]))
			}
			fValues = append(fValues, curr[0])
		} else {
			for i := 0; i < max-1; i++ {
				curr = append(curr, comp(prev[i+1], prev[i], xValues[iteration], xValues[0]))
			}
			fValues = append(fValues, curr[0])
		}
		//fmt.Println(curr)
		prev = curr
		fmt.Println(curr)
		curr = curr[:0]
		max--
		iteration++
	}

	//fmt.Println(fValues)

	var diffs []float64
	var mul float64 = 1
	for i := 0; i < len(xValues); i++ {
		mul *= x - xValues[i]
		diffs = append(diffs, mul)
	}

	//fmt.Println("diffs", diffs)
	var y float64 = yValues[0]

	for i := 0; i < len(fValues); i++ {
		y += fValues[i] * diffs[i]
		//fmt.Println(y)
	}

	fmt.Println(diffs)

	return y
}

func NewtonSeparatedDifferences(x float64, xValues, yValues []float64) {
	fmt.Printf("y(%g) = %g", x, f(x, xValues, yValues))
}
