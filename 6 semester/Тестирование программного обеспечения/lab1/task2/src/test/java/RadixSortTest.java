import org.junit.jupiter.api.Test;
import vladimir.RadixSort;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RadixSortTest {

    @Test
    void testRadixSortWithPositiveValues() {
        Integer[] arr = {170, 45, 75, 90, 802, 24, 2, 66};
        Integer[] expected = {2, 24, 45, 66, 75, 90, 170, 802};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRadixSortWithNegativeValues() {
        Integer[] arr = {-1, 170, 45, 75, 90, 802, 24, 2, 66};
        Integer[] expected = {-1, 2, 24, 45, 66, 75, 90, 170, 802};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRadixSortWithSingleElement() {
        Integer[] arr = {42};
        Integer[] expected = {42};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRadixSortWithNegativeNumbers() {
        Integer[] arr = {-5, -1, -3, -2};
        Integer[] expected = {-5, -3, -2, -1};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRadixSortWithEmptyArray() {
        Integer[] arr = {};
        Integer[] expected = {};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRadixSortWithAlreadySortedArray() {
        Integer[] arr = {1, 2, 3, 4, 5};
        Integer[] expected = {1, 2, 3, 4, 5};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRadixSortWithRandomArray() {
        Random random = new Random();
        Integer[] arr = new Integer[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(201) - 100;
        }

        Integer[] arrCopy = Arrays.copyOf(arr, arr.length);

        RadixSort.radixSort(arr);

        Arrays.sort(arrCopy);

        assertArrayEquals(arrCopy, arr);
    }

    @Test
    void testRadixSortWithNullValues() {
        Integer[] arr = {3, null, 1, 2, null, 4};

        assertThrows(NullPointerException.class, () -> {
            RadixSort.radixSort(arr);
        });
    }

    @Test
    void testRadixSortWithAllNullValues() {
        Integer[] arr = {null, null, null};

        assertThrows(NullPointerException.class, () -> {
            RadixSort.radixSort(arr);
        });
    }
}