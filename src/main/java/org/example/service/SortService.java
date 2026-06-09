package org.example.service;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;

public interface SortService {

    void sortBubble(IntegerArray array) throws ArrayValidationException;

    void sortInsertion(IntegerArray array) throws ArrayValidationException;
}