import org.junit.jupiter.api.Test;
import vladimir.RadixSort;

import static org.junit.jupiter.api.Assertions.*;

class RadixSortTest {

    @Test
    void testRadixSort() {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};
        int[] expected = {2, 24, 45, 66, 75, 90, 170, 802};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRadixSortWithSingleElement() {
        int[] arr = {42};
        int[] expected = {42};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRadixSortWithNegativeNumbers() {
        int[] arr = {-5, -1, -3, -2};
        int[] expected = {-5, -3, -2, -1};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRadixSortWithEmptyArray() {
        int[] arr = {};
        int[] expected = {};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRadixSortWithAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        RadixSort.radixSort(arr);
        assertArrayEquals(expected, arr);
    }
}