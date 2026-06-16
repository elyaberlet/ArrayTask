package org.example.service.impl;

import org.apache.logging.log4j.LogManager;
import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.service.ArrayFindService;

import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class FindServiceImpl implements ArrayFindService {

    private static final Logger logger = LogManager.getLogger(FindServiceImpl.class);

    @Override
    public Optional<Integer> findMin(IntegerArray array) throws ArrayValidationException {
        logger.debug("Finding minimum value in array");

        if (array.isEmpty()) {
            logger.warn("Array is empty, returning empty Optional");
            return Optional.empty();
        }

        int minValue = array.getElement(0);
        for (int i = 1; i < array.length(); i++) {
            int element = array.getElement(i);
            if (element < minValue) {
                minValue = element;
            }
        }
        logger.info("Minimum value found: {}", minValue);
        return Optional.of(minValue);
    }

    @Override
    public Optional<Integer> findMax(IntegerArray array) throws ArrayValidationException {
        logger.debug("Finding maximum value in array");

        if (array.isEmpty()) {
            logger.warn("Array is empty, returning empty Optional");
            return Optional.empty();
        }

        int max = array.getElement(0);
        for (int i = 1; i < array.length(); i++) {
            int current = array.getElement(i);
            if (current > max) {
                max = current;
            }
        }

        logger.info("Maximum value found: {}", max);
        return Optional.of(max);
    }

    @Override
    public Optional<Integer> findSum(IntegerArray array) throws ArrayValidationException {
        logger.debug("Calculating sum of array elements");

        if (array.isEmpty()) {
            logger.warn("Array is empty, returning empty Optional");
            return Optional.empty();
        }

        int sum = 0;
        for (int i = 0; i < array.length(); i++) {
            sum += array.getElement(i);
        }

        logger.info("Sum of array elements: {}", sum);
        return Optional.of(sum);
    }

    @Override
    public Optional<Double> findAverage(IntegerArray array) throws ArrayValidationException {
        logger.debug("Calculating average of array elements");

        if (array.isEmpty()) {
            logger.warn("Array is empty, returning empty Optional");
            return Optional.empty();
        }

        int sum = 0;
        for (int i = 0; i < array.length(); i++) {
            sum += array.getElement(i);
        }
        double average = (double) sum / array.length();

        logger.info("Average of array elements: {}", average);
        return Optional.of(average);
    }
}
