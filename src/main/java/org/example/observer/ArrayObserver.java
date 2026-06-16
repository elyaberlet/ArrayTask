package org.example.observer;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;

public interface ArrayObserver {
    void onArrayChanged(IntegerArray array) throws ArrayValidationException;
}