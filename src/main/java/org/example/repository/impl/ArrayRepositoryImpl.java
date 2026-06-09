package org.example.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.observer.ArrayObserver;
import org.example.repository.ArrayRepository;
import org.example.warehouse.impl.WarehouseImpl;

import java.util.*;

public class ArrayRepositoryImpl implements ArrayRepository {

    private static final Logger logger = LogManager.getLogger(ArrayRepositoryImpl.class);
    private static final ArrayRepositoryImpl INSTANCE = new ArrayRepositoryImpl();

    private final Map<UUID, IntegerArray> entityStorage;
    private final List<ArrayObserver> observers;
    private final WarehouseImpl warehouse;

    private ArrayRepositoryImpl() {
        this.entityStorage = new HashMap<>();
        this.observers = new ArrayList<>();
        this.warehouse = WarehouseImpl.getInstance();
    }

    public static ArrayRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(IntegerArray array) throws ArrayValidationException {
        UUID id = array.getId();

        if (entityStorage.containsKey(id)) {
            logger.warn("Array with id {} already exists", id);
            throw new ArrayValidationException("Entity already exists");
        }

        entityStorage.put(id, array);
        warehouse.updateStats(id, array);
        logger.info("Array added: {}", id);
    }

    @Override
    public void update(IntegerArray array) throws ArrayValidationException {
        UUID id = array.getId();

        if (!entityStorage.containsKey(id)) {
            logger.warn("Array with id {} not found", id);
            throw new ArrayValidationException("Entity not found");
        }

        entityStorage.put(id, array);
        notifyObservers(array);
        warehouse.updateStats(id, array);
        logger.info("Array updated: {}", id);
    }

    @Override
    public void remove(UUID id) throws ArrayValidationException {
        if (entityStorage.containsKey(id)) {
            notifyObservers(entityStorage.get(id));
            entityStorage.remove(id);
            warehouse.removeStats(id);
            logger.info("Array removed: {}", id);
        }
    }

    @Override
    public Optional<IntegerArray> findById(UUID id) {
        return Optional.ofNullable(entityStorage.get(id));
    }

    @Override
    public List<IntegerArray> findAll() {
        return new ArrayList<>(entityStorage.values());
    }

    @Override
    public int size() {
        return entityStorage.size();
    }

    public void addObserver(ArrayObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ArrayObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(IntegerArray array) throws ArrayValidationException {
        for (ArrayObserver observer : observers) {
            observer.onArrayChanged(array);
        }
    }
}