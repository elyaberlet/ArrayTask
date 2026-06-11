package org.example.factory;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;

public class IntegerArrayFactory {

    public IntegerArray createArray(Integer[] elements) {
        return new IntegerArray(elements);
    }
}