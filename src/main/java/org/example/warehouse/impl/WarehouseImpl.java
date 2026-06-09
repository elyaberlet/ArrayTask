package org.example.warehouse.impl;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.observer.ArrayObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.warehouse.Warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class WarehouseImpl implements Warehouse {

    private static final Logger logger = LogManager.getLogger(WarehouseImpl.class);

    private static WarehouseImpl instance;

    private final Map<UUID, Integer> sumMap;
    private final Map<UUID, Double> avgMap;
    private final Map<UUID, Integer> minMap;
    private final Map<UUID, Integer> maxMap;
    private final Map<UUID, Integer> sizeMap;

    private WarehouseImpl() {
        this.sumMap = new HashMap<>();
        this.avgMap = new HashMap<>();
        this.minMap = new HashMap<>();
        this.maxMap = new HashMap<>();
        this.sizeMap = new HashMap<>();
    }

    public static WarehouseImpl getInstance() {
        if (instance == null) {
            instance = new WarehouseImpl();
        }
        return instance;
    }

    @Override
    public Optional<Integer> getSum(UUID id) {
        return Optional.ofNullable(sumMap.get(id));
    }

    @Override
    public Optional<Double> getAverage(UUID id) {
        return Optional.ofNullable(avgMap.get(id));
    }

    @Override
    public Optional<Integer> getMin(UUID id) {
        return Optional.ofNullable(minMap.get(id));
    }

    @Override
    public Optional<Integer> getMax(UUID id) {
        return Optional.ofNullable(maxMap.get(id));
    }

    @Override
    public Optional<Integer> getSize(UUID id) {
        return Optional.ofNullable(sizeMap.get(id));
    }

    @Override
    public void updateStats(UUID id, IntegerArray array) throws ArrayValidationException {
        if (array.isEmpty()) {
            logger.debug("Array is empty, saving default stats for id: {}", id);
            sumMap.put(id, 0);
            avgMap.put(id, 0.0);
            minMap.put(id, 0);
            maxMap.put(id, 0);
            sizeMap.put(id, 0);
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

        double avg = (double) sum / array.length();

        sumMap.put(id, sum);
        avgMap.put(id, avg);
        minMap.put(id, min);
        maxMap.put(id, max);
        sizeMap.put(id, array.length());

        logger.debug("Stats updated for id {}: sum={}, avg={}, min={}, max={}, size={}",
                id, sum, avg, min, max, array.length());
    }

    @Override
    public void removeStats(UUID id) {
        sumMap.remove(id);
        avgMap.remove(id);
        minMap.remove(id);
        maxMap.remove(id);
        sizeMap.remove(id);
        logger.debug("Stats removed for id: {}", id);
    }
}