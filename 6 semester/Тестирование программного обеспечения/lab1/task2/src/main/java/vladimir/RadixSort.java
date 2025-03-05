package vladimir;

import java.util.Arrays;

public class RadixSort {

    // Вспомогательная функция для получения максимального значения в массиве
    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    // Функция для выполнения сортировки подсчетом по разряду
    private static void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        // Подсчет количества элементов в каждом разряде
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        // Изменение count[i] так, чтобы count[i] содержал фактическую позицию цифры в output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Построение выходного массива
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // Копирование выходного массива в arr[], чтобы arr[] содержал отсортированные числа по текущему разряду
        System.arraycopy(output, 0, arr, 0, n);
    }

    // Основная функция для выполнения Radix Sort
    public static void radixSort(int[] arr) {
        // Разделение массива на отрицательные и неотрицательные числа
        int[] negativeNumbers = Arrays.stream(arr).filter(x -> x < 0).toArray();
        int[] nonNegativeNumbers = Arrays.stream(arr).filter(x -> x >= 0).toArray();

        // Преобразование отрицательных чисел в положительные для сортировки
        for (int i = 0; i < negativeNumbers.length; i++) {
            negativeNumbers[i] = Math.abs(negativeNumbers[i]);
        }

        // Сортировка неотрицательных чисел
        if (nonNegativeNumbers.length > 0) {
            int max = getMax(nonNegativeNumbers);
            for (int exp = 1; max / exp > 0; exp *= 10) {
                countingSort(nonNegativeNumbers, exp);
            }
        }

        // Сортировка отрицательных чисел
        if (negativeNumbers.length > 0) {
            int max = getMax(negativeNumbers);
            for (int exp = 1; max / exp > 0; exp *= 10) {
                countingSort(negativeNumbers, exp);
            }
        }

        // Восстановление отрицательных чисел и реверсирование порядка
        for (int i = 0; i < negativeNumbers.length; i++) {
            negativeNumbers[i] = -negativeNumbers[i];
        }
        reverseArray(negativeNumbers);

        // Объединение отсортированных массивов
        System.arraycopy(negativeNumbers, 0, arr, 0, negativeNumbers.length);
        System.arraycopy(nonNegativeNumbers, 0, arr, negativeNumbers.length, nonNegativeNumbers.length);
    }

    // Вспомогательная функция для реверсирования массива
    private static void reverseArray(int[] arr) {
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