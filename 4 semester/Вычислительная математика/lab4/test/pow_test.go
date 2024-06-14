package test

import (
	"compMathLab4/graph"
	"fmt"
	"math"
	"testing"
)

func TestPowerFunc(t *testing.T) {
	path := "graph_test.png"

	xValues := []float64{1.2, 2.9, 4.1, 5.5, 6.7, 7.8, 9.2, 10.3}
	yValues := []float64{7.4, 9.5, 11.1, 12.9, 14.6, 17.3, 18.2, 20.7}
	f := func(x float64) float64 {
		return 6.1287 * math.Pow(x, 0.4799)
	}
	fName := "pow"

	graph.Create(path, xValues, yValues, f, fName)

	fmt.Println(f(10))
	fmt.Println(math.Pow(6.8396*math.E, 0.1111*10))
}
