package vladimir;

import java.util.Arrays;
import java.util.Objects;

public class RadixSort {

    private static int getMax(Integer[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    private static void countingSort(Integer[] arr, int exp) {
        int n = arr.length;
        Integer[] output = new Integer[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        for (int i = 0; i < n; i++) {
            if (arr[i] != null) { // Проверка на null
                count[(arr[i] / exp) % 10]++;
            }
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] != null) {
                output[count[(arr[i] / exp) % 10] - 1] = arr[i];
                count[(arr[i] / exp) % 10]--;
            }
        }

        System.arraycopy(output, 0, arr, 0, n);
    }

    public static void radixSort(Integer[] arr) {
        if (Arrays.stream(arr).anyMatch(Objects::isNull)) {
            throw new NullPointerException("Массив содержит null-значение!");
        }

        Integer[] negativeNumbers = Arrays.stream(arr)
                .filter(x -> x != null && x < 0)
                .toArray(Integer[]::new);
        Integer[] nonNegativeNumbers = Arrays.stream(arr)
                .filter(x -> x != null && x >= 0)
                .toArray(Integer[]::new);

        for (int i = 0; i < negativeNumbers.length; i++) {
            negativeNumbers[i] = Math.abs(negativeNumbers[i]);
        }

        if (nonNegativeNumbers.length > 0) {
            int max = getMax(nonNegativeNumbers);
            for (int exp = 1; max / exp > 0; exp *= 10) {
                countingSort(nonNegativeNumbers, exp);
            }
        }

        if (negativeNumbers.length > 0) {
            int max = getMax(negativeNumbers);
            for (int exp = 1; max / exp > 0; exp *= 10) {
                countingSort(negativeNumbers, exp);
            }
        }

        for (int i = 0; i < negativeNumbers.length; i++) {
            negativeNumbers[i] = -negativeNumbers[i];
        }
        reverseArray(negativeNumbers);

        System.arraycopy(negativeNumbers, 0, arr, 0, negativeNumbers.length);
        System.arraycopy(nonNegativeNumbers, 0, arr, negativeNumbers.length, nonNegativeNumbers.length);
    }

    private static void reverseArray(Integer[] arr) {
        int i = 0, j = arr.length - 1;
        while (i < j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }
}
