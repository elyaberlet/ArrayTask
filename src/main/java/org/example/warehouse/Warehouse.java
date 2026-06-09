package org.example.warehouse;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;

import java.util.Optional;
import java.util.UUID;

public interface Warehouse {

    Optional<Integer> getSum(UUID id);
    Optional<Double> getAverage(UUID id);
    Optional<Integer> getMin(UUID id);
    Optional<Integer> getMax(UUID id);
    Optional<Integer> getSize(UUID id);

    void updateStats(UUID id, IntegerArray array) throws ArrayValidationException;

    void removeStats(UUID id);
}