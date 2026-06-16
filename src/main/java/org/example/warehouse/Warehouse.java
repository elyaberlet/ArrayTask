package org.example.warehouse;

import org.example.entity.ArrayStatistics;
import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;

import java.util.Optional;
import java.util.UUID;

public interface Warehouse {

    void updateStats(UUID id, IntegerArray array) throws ArrayValidationException;

    void removeStats(UUID id);

    Optional<ArrayStatistics> getStatistics(UUID id);
}