package tservice;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.service.FindService;
import org.example.service.impl.FindServiceImpl;
import org.example.service.SortService;
import org.example.service.impl.SortServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private FindService findService;
    private SortService sortService;

    @BeforeEach
    void setUp() {
        findService = new FindServiceImpl();
        sortService = new SortServiceImpl();
    }

    @Test
    void testFindMinAndMax() throws ArrayValidationException {
        IntegerArray array = new IntegerArray(new Integer[]{5, 2, 8, 1, 9, 3});

        Optional<Integer> min = findService.findMin(array);
        Optional<Integer> max = findService.findMax(array);

        assertEquals(1, min.get());
        assertEquals(9, max.get());
    }

    @Test
    void testFindSumAndAverage() throws ArrayValidationException {
        IntegerArray array = new IntegerArray(new Integer[]{1, 2, 3, 4, 5});

        Optional<Integer> sum = findService.findSum(array);
        Optional<Double> avg = findService.findAverage(array);

        assertEquals(15, sum.get());
        assertEquals(3.0, avg.get(), 0.001);
    }

    @Test
    void testEmptyArray() throws ArrayValidationException {
        IntegerArray array = new IntegerArray(new Integer[0]);

        assertFalse(findService.findMin(array).isPresent());
        assertFalse(findService.findMax(array).isPresent());
        assertFalse(findService.findSum(array).isPresent());
        assertFalse(findService.findAverage(array).isPresent());
    }

    @Test
    void testSortBubble() throws ArrayValidationException {
        IntegerArray array = new IntegerArray(new Integer[]{5, 2, 8, 1, 3});
        sortService.sortBubble(array);

        assertArrayEquals(new Integer[]{1, 2, 3, 5, 8}, array.getArray());
    }

    @Test
    void testSortInsertion() throws ArrayValidationException {
        IntegerArray array = new IntegerArray(new Integer[]{5, 2, 8, 1, 3});
        sortService.sortInsertion(array);

        assertArrayEquals(new Integer[]{1, 2, 3, 5, 8}, array.getArray());
    }
}