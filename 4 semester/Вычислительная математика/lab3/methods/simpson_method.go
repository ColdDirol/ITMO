package methods

func SimpsonMethod(f func(float64) float64, a, b, e float64, n int, h float64) float64 {
	var integral float64
	integral += f(a) + f(b) // начальные и конечные точки

	for i := 1; i < n; i++ {
		x := a + float64(i)*h
		if i%2 == 0 {
			integral += 2 * f(x)
		} else {
			integral += 4 * f(x)
		}
	}

	integral *= h / 3.0

	return integral
}

//func SimpsonMethod(f func(float64) float64, a, b, e float64, n int, h float64) {
//	//count := 0
//	var (
//		I_h   float64
//		I_h_2 float64
//	)
//	for {
//		I_h = computeSimpsonIntegral(f, a, b, e, n, h)
//		h = h / 2
//		n = n * 2
//		I_h_2 = computeSimpsonIntegral(f, a, b, e, n, h)
//
//		if math.Abs(utils.RungeRule(I_h, I_h_2, 4)) <= e {
//			break
//		}
//	}
//
//	fmt.Println(I_h)
//	fmt.Println(I_h_2)
//	fmt.Println(n)
//}

func computeSimpsonIntegral(f func(float64) float64, a, b, e float64, n int, h float64) float64 {
	var integral float64
	integral += f(a) + f(b) // начальные и конечные точки

	for i := 1; i < n; i++ {
		x := a + float64(i)*h
		if i%2 == 0 {
			integral += 2 * f(x)
		} else {
			integral += 4 * f(x)
		}
	}

	integral *= h / 3.0

	return integral
}
