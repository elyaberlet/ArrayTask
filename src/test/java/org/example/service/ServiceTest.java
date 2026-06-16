package org.example.service;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.service.impl.FindServiceImpl;
import org.example.service.impl.SortServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private FindServiceImpl findService;
    private SortServiceImpl sortService;
    private IntegerArray array;
    private IntegerArray emptyArray;
    private IntegerArray singleElementArray;

    @BeforeEach
    void setUp() {
        findService = new FindServiceImpl();
        sortService = new SortServiceImpl();
        array = new IntegerArray(new Integer[]{5, 2, 8, 1, 9, 3});
        emptyArray = new IntegerArray(new Integer[]{});
        singleElementArray = new IntegerArray(new Integer[]{42});
    }

    @Test
    void testFindMin() {
        assertAll("Find min",
                () -> assertEquals(1, findService.findMin(array).orElseThrow()),
                () -> assertTrue(findService.findMin(emptyArray).isEmpty()),
                () -> assertEquals(42, findService.findMin(singleElementArray).orElseThrow())
        );
    }

    @Test
    void testFindMax() {
        assertAll("Find max",
                () -> assertEquals(9, findService.findMax(array).orElseThrow()),
                () -> assertTrue(findService.findMax(emptyArray).isEmpty()),
                () -> assertEquals(42, findService.findMax(singleElementArray).orElseThrow())
        );
    }

    @Test
    void testFindSum() {
        assertAll("Find sum",
                () -> assertEquals(28, findService.findSum(array).orElseThrow()),
                () -> assertTrue(findService.findSum(emptyArray).isEmpty()),
                () -> assertEquals(42, findService.findSum(singleElementArray).orElseThrow())
        );
    }

    @Test
    void testFindAverage() {
        assertAll("Find average",
                () -> assertEquals(28.0 / 6, findService.findAverage(array).orElseThrow(), 0.001),
                () -> assertTrue(findService.findAverage(emptyArray).isEmpty()),
                () -> assertEquals(42.0, findService.findAverage(singleElementArray).orElseThrow(), 0.001)
        );
    }

    @Test
    void testBubbleSort() throws ArrayValidationException {
        IntegerArray testArray = new IntegerArray(new Integer[]{5, 2, 8, 1, 9, 3});
        sortService.sortBubble(testArray);
        assertArrayEquals(new Integer[]{1, 2, 3, 5, 8, 9}, testArray.getArray());
    }

    @Test
    void testBubbleSortWithSingleElement() throws ArrayValidationException {
        IntegerArray testArray = new IntegerArray(new Integer[]{42});
        sortService.sortBubble(testArray);
        assertArrayEquals(new Integer[]{42}, testArray.getArray());
    }

    @Test
    void testBubbleSortWithEmptyArray() throws ArrayValidationException {
        IntegerArray testArray = new IntegerArray(new Integer[]{});
        sortService.sortBubble(testArray);
        assertArrayEquals(new Integer[]{}, testArray.getArray());
    }

    @Test
    void testInsertionSort() throws ArrayValidationException {
        IntegerArray testArray = new IntegerArray(new Integer[]{5, 2, 8, 1, 9, 3});
        sortService.sortInsertion(testArray);
        assertArrayEquals(new Integer[]{1, 2, 3, 5, 8, 9}, testArray.getArray());
    }

    @Test
    void testInsertionSortWithSingleElement() throws ArrayValidationException {
        IntegerArray testArray = new IntegerArray(new Integer[]{42});
        sortService.sortInsertion(testArray);
        assertArrayEquals(new Integer[]{42}, testArray.getArray());
    }

    @Test
    void testInsertionSortWithEmptyArray() throws ArrayValidationException {
        IntegerArray testArray = new IntegerArray(new Integer[]{});
        sortService.sortInsertion(testArray);
        assertArrayEquals(new Integer[]{}, testArray.getArray());
    }

    @Test
    void testBothSortsProduceSameResult() throws ArrayValidationException {
        IntegerArray array1 = new IntegerArray(new Integer[]{5, 2, 8, 1, 9, 3});
        IntegerArray array2 = new IntegerArray(new Integer[]{5, 2, 8, 1, 9, 3});

        sortService.sortBubble(array1);
        sortService.sortInsertion(array2);

        assertArrayEquals(array1.getArray(), array2.getArray());
    }
}