package test

import (
	"fmt"
	"math"
	"testing"
)

func f1(x, y float64) (float64, float64) {
	return math.Tan(x*y) - math.Pow(x, 2), 0.5*math.Pow(x, 2) + 2*math.Pow(y, 2) - 1
}

func f2(x, y float64) (float64, float64) {
	return math.Sin(x+y) - 1.4*x, math.Pow(x, 2) + math.Pow(y, 2) - 1
}

func TestNewtonMethod(t *testing.T) {
	fmt.Println(f2(0.7055595988105918, 0.7086621614818659))
}
