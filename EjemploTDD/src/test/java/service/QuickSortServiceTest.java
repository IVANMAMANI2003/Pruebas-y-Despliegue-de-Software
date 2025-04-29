package service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuickSortServiceTest {


    private QuickSortService quickSortService;

    @BeforeEach
    void setUp() {
        quickSortService = new QuickSortServiceImpl();
    }

    @Test
    void testQuickSortNormalArray() {
        int[] arr = {5, 3, 8, 4, 2};
        int[] expected = {2, 3, 4, 5, 8};

        quickSortService.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortEmptyArray() {
        int[] arr = {};
        int[] expected = {};

        quickSortService.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortSingleElement() {
        int[] arr = {42};
        int[] expected = {42};

        quickSortService.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        quickSortService.quickSort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortWithDuplicates() {
        int[] arr = {3, 6, 8, 3, 2, 7, 3};
        int[] expected = {2, 3, 3, 3, 6, 7, 8};

        quickSortService.quickSort(arr);

        assertArrayEquals(expected, arr);
    }
    @Test
    void testQuickSortNullArray() {
        int[] arr = null, expected = null;

        quickSortService.quickSort(arr);
        assertArrayEquals(expected, arr);

    }
}
