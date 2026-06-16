package org.example.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.observer.ArrayObserver;
import org.example.observer.impl.ArrayObserverImpl;
import org.example.repository.ArrayRepository;
import org.example.specification.ArraySpecification;
import org.example.warehouse.Warehouse;
import org.example.warehouse.impl.WarehouseImpl;

import java.util.*;

public class ArrayRepositoryImpl implements ArrayRepository {

    private static final Logger logger = LogManager.getLogger(ArrayRepositoryImpl.class);
    private static ArrayRepositoryImpl instance;

    private final Map<UUID, IntegerArray> entityStorage;
    private final Warehouse warehouse;
    private final ArrayObserver warehouseObserver;

    private ArrayRepositoryImpl() {
        this.entityStorage = new HashMap<>();
        this.warehouse = WarehouseImpl.getInstance();
        this.warehouseObserver = new ArrayObserverImpl(warehouse);
    }

    public static ArrayRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ArrayRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void add(IntegerArray array) throws ArrayValidationException {
        if (array == null) {
            logger.error("Cannot add null array");
            throw new ArrayValidationException("Array cannot be null");
        }

        UUID id = array.getId();

        if (entityStorage.containsKey(id)) {
            logger.warn("Array with id {} already exists", id);
            throw new ArrayValidationException("Entity already exists");
        }

        array.addObserver(warehouseObserver);
        entityStorage.put(id, array);
        warehouse.updateStats(id, array);

        logger.info("Array added and observer subscribed: {}", id);
    }

    @Override
    public void update(IntegerArray array) throws ArrayValidationException {
        if (array == null) {
            logger.error("Cannot update null array");
            throw new ArrayValidationException("Array cannot be null");
        }

        UUID id = array.getId();

        if (!entityStorage.containsKey(id)) {
            logger.warn("Array with id {} not found", id);
            throw new ArrayValidationException("Entity not found");
        }

        array.addObserver(warehouseObserver);
        entityStorage.put(id, array);
        warehouse.updateStats(id, array);

        logger.info("Array updated: {}", id);
    }

    @Override
    public void remove(UUID id) throws ArrayValidationException {
        IntegerArray array = entityStorage.get(id);

        if (array != null) {
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

    @Override
    public List<IntegerArray> findBySpecification(ArraySpecification specification) {
        List<IntegerArray> result = new ArrayList<>();
        for (IntegerArray array : entityStorage.values()) {
            if (specification.specify(array)) {
                result.add(array);
            }
        }
        logger.info("Found {} arrays by specification", result.size());
        return result;
    }

    @Override
    public List<IntegerArray> findAllSorted(Comparator<IntegerArray> comparator) {
        List<IntegerArray> sorted = new ArrayList<>(entityStorage.values());
        sorted.sort(comparator);
        logger.info("Sorted {} arrays", sorted.size());
        return sorted;
    }
}