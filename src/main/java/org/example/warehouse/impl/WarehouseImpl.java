package org.example.warehouse.impl;

import org.example.entity.ArrayStatistics;
import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.warehouse.Warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class WarehouseImpl implements Warehouse {

    private static WarehouseImpl instance;
    private final Map<UUID, ArrayStatistics> storage = new HashMap<>();

    private WarehouseImpl() {}

    public static WarehouseImpl getInstance() {
        if (instance == null) {
            instance = new WarehouseImpl();
        }
        return instance;
    }

    @Override
    public void updateStats(UUID id, IntegerArray array) throws ArrayValidationException {
        if (array.isEmpty()) {
            storage.put(id, new ArrayStatistics(0, 0.0, 0, 0, 0));
            return;
        }

        int sum = 0;
        int min = array.getElement(0);
        int max = array.getElement(0);

        for (int i = 0; i < array.length(); i++) {
            int val = array.getElement(i);
            sum += val;
            if (val < min) min = val;
            if (val > max) max = val;
        }

        storage.put(id, new ArrayStatistics(sum, (double) sum / array.length(), min, max, array.length()));
    }

    @Override
    public void removeStats(UUID id) {
        storage.remove(id);
    }

    @Override
    public Optional<ArrayStatistics> getStatistics(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }
}