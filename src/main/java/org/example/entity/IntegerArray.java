package org.example.entity;

import org.example.exception.ArrayValidationException;
import org.example.observer.ArrayObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class IntegerArray {
    private final UUID id;
    protected Integer[] array;

    private final List<ArrayObserver> observers = new ArrayList<>();

    public IntegerArray(Integer[] array) throws ArrayValidationException {
        if (array == null) {
            throw new ArrayValidationException("Array can not be null");
        }
        this.array = array.clone();
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Integer[] getArray() {
        return array.clone();
    }

    public int length() {
        return array.length;
    }

    public Integer getElement(int index) throws ArrayValidationException {
        if (index < 0 || index >= array.length) {
            throw new ArrayValidationException("Index out of bounds: " + index);
        }
        return array[index];
    }

    public void setElement(int index, Integer value) throws ArrayValidationException {
        if (index < 0 || index >= array.length) {
            throw new ArrayValidationException("Index out of bounds: " + index);
        }
        if (value == null) {
            throw new ArrayValidationException("Value cannot be null");
        }
        array[index] = value;
        notifyObservers();
    }

    public boolean isEmpty() {
        return array.length == 0;
    }

    public int[] toPrimitiveArray() {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    @Override
    public String toString() {
        return "IntegerArray { array = " + Arrays.toString(array) + " }";
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IntegerArray other = (IntegerArray) obj;
        return Arrays.equals(array, other.array);
    }

    public void addObserver(ArrayObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ArrayObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (ArrayObserver observer : observers) {
            observer.onArrayChanged(this);
        }
    }
}