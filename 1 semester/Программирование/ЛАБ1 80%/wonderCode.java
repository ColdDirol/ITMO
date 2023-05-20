public class wonderCode {
    public static float randomInBorders(float left, float right) {
        return (float) (left + Math.random() * (right - left));
    }

    public static void main(String[] args) {
        // ввод 1
        short s[] = new short[18];
        for (int i = 0; i < s.length; i++) {
            s[i] = (short) (19 - i);
        }
        
        // ввод 2
        float x[] = new float[14];
        for (int i = 0; i < x.length; i++) {
            x[i] = randomInBorders(-11.0F, 10.0F);
        }

        double[][] b = new double[18][14]; //создание двумерного массива

        // обработка / ввод 3;
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 14; j++) {
                if (s[i] == 7) {
                    b[i][j] = Math.sin(Math.pow(Math.pow(Math.E, x[j]), 1.0 / 3));
                } else if (s[i] >= 3 && s[i] <= 6 || s[i] == 8 || s[i] == 10 || s[i] == 12 || s[i] == 15 || s[i] == 16) {
                    b[i][j] = Math.pow(0.5 - Math.atan(Math.pow(Math.E, -Math.abs(0.1f))), 3);
                } else {
                    b[i][j] = Math.pow(Math.atan(Math.sin(Math.asin(1 / Math.pow(Math.E, Math.abs(x[j]))))) / 4, 2);
                }
            }
        }

        // вывод
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < x.length; j++) {
                if (b[i][j] < 0) System.out.printf("%.5f ", b[i][j]);
                else System.out.printf(" %.5f ", b[i][j]);
            }
            System.out.println();
        }
    }
}