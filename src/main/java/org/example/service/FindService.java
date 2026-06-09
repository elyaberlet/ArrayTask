package org.example.service;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;

import java.util.Optional;

public interface FindService {
    Optional<Integer> findMin(IntegerArray array) throws ArrayValidationException;

    Optional<Integer> findMax(IntegerArray array) throws ArrayValidationException;

    Optional<Integer> findSum(IntegerArray array) throws ArrayValidationException;

    Optional<Double> findAverage(IntegerArray array) throws ArrayValidationException;
}
