package com.vladimir.other;

public class Pow  {

    public static double pow(double base, int exponent) {
        if (exponent == 0) return 1;
        if (exponent < 0) {
            base = 1 / base;
            exponent = -exponent;
        }

        double result = 1;
        double currentPower = base;

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result *= currentPower;
            }
            currentPower *= currentPower;
            exponent >>= 1;
        }

        return result;
    }
}
