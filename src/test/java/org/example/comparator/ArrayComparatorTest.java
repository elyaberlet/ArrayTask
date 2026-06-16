package org.example.comparator;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayComparatorTest {

    private IntegerArray array1;
    private IntegerArray array2;
    private IntegerArray array3;

    @BeforeEach
    void setUp() throws ArrayValidationException {
        array1 = new IntegerArray(new Integer[]{1, 2, 3, 4, 5});
        array2 = new IntegerArray(new Integer[]{10, 20, 30});
        array3 = new IntegerArray(new Integer[]{5, 10, 15, 20});
    }

    @Test
    void testByIdComparator() {
        List<IntegerArray> list = new ArrayList<>();
        list.add(array3);
        list.add(array1);
        list.add(array2);

        list.sort(ArrayComparator.byId());

        assertAll("Sort by ID",
                () -> assertTrue(list.get(0).getId().compareTo(list.get(1).getId()) < 0),
                () -> assertTrue(list.get(1).getId().compareTo(list.get(2).getId()) < 0)
        );
    }

    @Test
    void testByFirstElementComparator() {
        List<IntegerArray> list = new ArrayList<>();
        list.add(array2);
        list.add(array1);
        list.add(array3);

        list.sort(ArrayComparator.byFirstElement());

        assertAll("Sort by first element",
                () -> assertEquals(1, list.getFirst().getElement(0)),
                () -> assertEquals(5, list.get(1).getElement(0)),
                () -> assertEquals(10, list.get(2).getElement(0))
        );
    }

    @Test
    void testByLengthComparator() {
        List<IntegerArray> list = new ArrayList<>();
        list.add(array1);
        list.add(array2);
        list.add(array3);

        list.sort(ArrayComparator.byLength());

        assertAll("Sort by length",
                () -> assertEquals(3, list.getFirst().length()),
                () -> assertEquals(4, list.get(1).length()),
                () -> assertEquals(5, list.get(2).length())
        );
    }

    @Test
    void testBySumComparator() {
        List<IntegerArray> list = new ArrayList<>();
        list.add(array1);
        list.add(array2);
        list.add(array3);

        list.sort(ArrayComparator.bySum());

        assertAll("Sort by sum",
                () -> assertEquals(15, calculateSum(list.get(0))),
                () -> assertEquals(50, calculateSum(list.get(1))),
                () -> assertEquals(60, calculateSum(list.get(2)))
        );
    }

    @Test
    void testChainedComparator() {
        List<IntegerArray> list = new ArrayList<>();
        list.add(array1);
        list.add(array2);
        list.add(array3);

        list.sort(ArrayComparator.byLength().thenComparing(ArrayComparator.bySum()));

        assertAll("Sort by length then by sum",
                () -> assertEquals(3, list.getFirst().length()),
                () -> assertEquals(4, list.get(1).length()),
                () -> assertEquals(5, list.get(2).length())
        );
    }

    private int calculateSum(IntegerArray array) {
        try {
            int sum = 0;
            for (int i = 0; i < array.length(); i++) {
                sum += array.getElement(i);
            }
            return sum;
        } catch (ArrayValidationException e) {
            return 0;
        }
    }
}