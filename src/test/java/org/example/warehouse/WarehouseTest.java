package org.example.warehouse;

import org.example.entity.ArrayStatistics;
import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.warehouse.impl.WarehouseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    private Warehouse warehouse;
    private UUID testId;
    private IntegerArray array;
    private IntegerArray emptyArray;

    @BeforeEach
    void setUp() {
        warehouse = WarehouseImpl.getInstance();
        testId = UUID.randomUUID();
        array = new IntegerArray(new Integer[]{5, 2, 8, 1, 9, 3});
        emptyArray = new IntegerArray(new Integer[]{});
    }

    @Test
    void testUpdateStatsWithValidArray() throws ArrayValidationException {
        warehouse.updateStats(testId, array);

        Optional<ArrayStatistics> stats = warehouse.getStatistics(testId);

        assertAll("Update stats with valid array",
                () -> assertTrue(stats.isPresent()),
                () -> assertEquals(28, stats.get().sum()),
                () -> assertEquals(28.0 / 6, stats.get().average(), 0.001),
                () -> assertEquals(1, stats.get().min()),
                () -> assertEquals(9, stats.get().max()),
                () -> assertEquals(6, stats.get().size())
        );
    }

    @Test
    void testUpdateStatsWithEmptyArray() throws ArrayValidationException {
        warehouse.updateStats(testId, emptyArray);

        Optional<ArrayStatistics> stats = warehouse.getStatistics(testId);

        assertAll("Update stats with empty array",
                () -> assertTrue(stats.isPresent()),
                () -> assertEquals(0, stats.get().sum()),
                () -> assertEquals(0.0, stats.get().average()),
                () -> assertEquals(0, stats.get().min()),
                () -> assertEquals(0, stats.get().max()),
                () -> assertEquals(0, stats.get().size())
        );
    }

    @Test
    void testUpdateStatsOverwritesExisting() throws ArrayValidationException {
        IntegerArray firstArray = new IntegerArray(new Integer[]{1, 2, 3});
        IntegerArray secondArray = new IntegerArray(new Integer[]{10, 20, 30});

        warehouse.updateStats(testId, firstArray);
        warehouse.updateStats(testId, secondArray);

        Optional<ArrayStatistics> stats = warehouse.getStatistics(testId);

        assertAll("Overwrite existing stats",
                () -> assertTrue(stats.isPresent()),
                () -> assertEquals(60, stats.get().sum()),
                () -> assertEquals(20.0, stats.get().average()),
                () -> assertEquals(10, stats.get().min()),
                () -> assertEquals(30, stats.get().max()),
                () -> assertEquals(3, stats.get().size())
        );
    }

    @Test
    void testGetStatisticsForExistingId() throws ArrayValidationException {
        warehouse.updateStats(testId, array);

        Optional<ArrayStatistics> stats = warehouse.getStatistics(testId);

        assertTrue(stats.isPresent());
        assertEquals(28, stats.get().sum());
    }

    @Test
    void testGetStatisticsForNonExistingId() {
        Optional<ArrayStatistics> stats = warehouse.getStatistics(UUID.randomUUID());

        assertTrue(stats.isEmpty());
    }

    @Test
    void testRemoveStats() throws ArrayValidationException {
        warehouse.updateStats(testId, array);
        assertTrue(warehouse.getStatistics(testId).isPresent());

        warehouse.removeStats(testId);

        assertTrue(warehouse.getStatistics(testId).isEmpty());
    }

    @Test
    void testRemoveStatsForNonExistingId() {
        assertDoesNotThrow(() -> warehouse.removeStats(UUID.randomUUID()));
    }

    @Test
    void testSingletonReturnsSameInstance() {
        WarehouseImpl instance1 = WarehouseImpl.getInstance();
        WarehouseImpl instance2 = WarehouseImpl.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    void testUpdateStatsWithSingleElementArray() throws ArrayValidationException {
        IntegerArray singleArray = new IntegerArray(new Integer[]{42});
        warehouse.updateStats(testId, singleArray);

        Optional<ArrayStatistics> stats = warehouse.getStatistics(testId);

        assertAll("Single element array",
                () -> assertTrue(stats.isPresent()),
                () -> assertEquals(42, stats.get().sum()),
                () -> assertEquals(42.0, stats.get().average()),
                () -> assertEquals(42, stats.get().min()),
                () -> assertEquals(42, stats.get().max()),
                () -> assertEquals(1, stats.get().size())
        );
    }

    @Test
    void testUpdateStatsWithNegativeNumbers() throws ArrayValidationException {
        IntegerArray negativeArray = new IntegerArray(new Integer[]{-5, -2, -8, -1});
        warehouse.updateStats(testId, negativeArray);

        Optional<ArrayStatistics> stats = warehouse.getStatistics(testId);

        assertAll("Negative numbers",
                () -> assertTrue(stats.isPresent()),
                () -> assertEquals(-16, stats.get().sum()),
                () -> assertEquals(-4.0, stats.get().average()),
                () -> assertEquals(-8, stats.get().min()),
                () -> assertEquals(-1, stats.get().max()),
                () -> assertEquals(4, stats.get().size())
        );
    }
}