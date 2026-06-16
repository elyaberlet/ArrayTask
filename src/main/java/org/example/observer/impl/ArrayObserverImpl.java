package org.example.observer.impl;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.observer.ArrayObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.warehouse.Warehouse;

public class ArrayObserverImpl implements ArrayObserver {

    private static final Logger logger = LogManager.getLogger(ArrayObserverImpl.class);
    private final Warehouse warehouse;

    public ArrayObserverImpl(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void onArrayChanged(IntegerArray array) {
        if (array == null) {
            logger.error("Array is null in observer");
            return;
        }

        try {
            warehouse.updateStats(array.getId(), array);
        } catch (ArrayValidationException e) {
            logger.error("Failed to update warehouse for array {}: {}", array.getId(), e.getMessage());
        }
    }
}