package org.example.observer;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.observer.impl.ArrayObserverImpl;
import org.example.warehouse.impl.WarehouseImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayObserverTest {

    @Test
    void testOnArrayChangedDoesNotThrowException() {
        WarehouseImpl warehouse = WarehouseImpl.getInstance();
        ArrayObserverImpl observer = new ArrayObserverImpl(warehouse);
        IntegerArray array = new IntegerArray(new Integer[]{1, 2, 3});

        assertDoesNotThrow(() -> observer.onArrayChanged(array));
    }

    @Test
    void testOnArrayChangedWithNullArrayDoesNotThrow() {
        WarehouseImpl warehouse = WarehouseImpl.getInstance();
        ArrayObserverImpl observer = new ArrayObserverImpl(warehouse);

        assertDoesNotThrow(() -> observer.onArrayChanged(null));
    }
}