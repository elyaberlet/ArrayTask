package org.example.comparator;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;

import java.util.Comparator;

public class ArrayComparator {

    public static Comparator<IntegerArray> byId() {
        return Comparator.comparing(IntegerArray::getId);
    }

    public static Comparator<IntegerArray> byFirstElement() {
        return (a1, a2) -> {
            try {
                return Integer.compare(a1.getElement(0), a2.getElement(0));
            } catch (ArrayValidationException e) {
                return 0;
            }
        };
    }

    public static Comparator<IntegerArray> byLength() {
        return Comparator.comparingInt(IntegerArray::length);
    }

    public static Comparator<IntegerArray> bySum() {
        return (a1, a2) -> {
            try {
                int sum1 = calculateSum(a1);
                int sum2 = calculateSum(a2);
                return Integer.compare(sum1, sum2);
            } catch (ArrayValidationException e) {
                return 0;
            }
        };
    }

    private static int calculateSum(IntegerArray array) throws ArrayValidationException {
        int sum = 0;
        for (int i = 0; i < array.length(); i++) {
            sum += array.getElement(i);
        }
        return sum;
    }
}