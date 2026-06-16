package org.example.repository;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.repository.impl.ArrayRepositoryImpl;
import org.example.specification.ArraySpecification;
import org.example.specification.impl.ByIdSpecification;
import org.example.specification.impl.FindByMaxGreaterThanSpecification;
import org.example.specification.impl.FindByMinLessThanSpecification;
import org.example.specification.impl.FindBySumLessThanSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ArrayRepositoryTest {

    private ArrayRepositoryImpl repository;
    private IntegerArray array1;
    private IntegerArray array2;
    private IntegerArray array3;

    @BeforeEach
    void setUp() {
        repository = ArrayRepositoryImpl.getInstance();

        array1 = new IntegerArray(new Integer[]{1, 2, 3, 4, 5});
        array2 = new IntegerArray(new Integer[]{10, 20, 30});
        array3 = new IntegerArray(new Integer[]{5, 10, 15, 20});
    }

    @Test
    void testAddDuplicateThrowsException() throws ArrayValidationException {
        repository.add(array1);

        assertThrows(ArrayValidationException.class, () -> repository.add(array1));
    }

    @Test
    void testAddNullThrowsException() {
        assertThrows(ArrayValidationException.class, () -> repository.add(null));
    }

    @Test
    void testUpdate() throws ArrayValidationException {
        repository.add(array1);
        IntegerArray updatedArray = new IntegerArray(new Integer[]{9, 8, 7, 6, 5});

        repository.update(updatedArray);

        Optional<IntegerArray> found = repository.findById(updatedArray.getId());
        assertTrue(found.isPresent());
        assertArrayEquals(new Integer[]{9, 8, 7, 6, 5}, found.get().getArray());
    }

    @Test
    void testUpdateNonExistentThrowsException() {
        assertThrows(ArrayValidationException.class, () -> repository.update(array1));
    }

    @Test
    void testRemove() throws ArrayValidationException {
        repository.add(array1);
        repository.add(array2);

        repository.remove(array1.getId());

        assertAll("Remove array",
                () -> assertEquals(1, repository.size()),
                () -> assertFalse(repository.findById(array1.getId()).isPresent()),
                () -> assertTrue(repository.findById(array2.getId()).isPresent())
        );
    }

    @Test
    void testRemoveNonExistentDoesNothing() throws ArrayValidationException {
        repository.add(array1);
        int sizeBefore = repository.size();

        repository.remove(UUID.randomUUID());

        assertEquals(sizeBefore, repository.size());
    }

    @Test
    void testFindById() throws ArrayValidationException {
        repository.add(array1);
        repository.add(array2);

        Optional<IntegerArray> found = repository.findById(array1.getId());

        assertAll("Find by id",
                () -> assertTrue(found.isPresent()),
                () -> assertEquals(array1.getId(), found.get().getId()),
                () -> assertArrayEquals(array1.getArray(), found.get().getArray())
        );
    }

    @Test
    void testFindByIdNotFound() {
        Optional<IntegerArray> found = repository.findById(UUID.randomUUID());
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindAll() throws ArrayValidationException {
        repository.add(array1);
        repository.add(array2);
        repository.add(array3);

        List<IntegerArray> all = repository.findAll();

        assertAll("Find all",
                () -> assertEquals(3, all.size()),
                () -> assertTrue(all.contains(array1)),
                () -> assertTrue(all.contains(array2)),
                () -> assertTrue(all.contains(array3))
        );
    }

    @Test
    void testSize() throws ArrayValidationException {
        assertEquals(0, repository.size());

        repository.add(array1);
        assertEquals(1, repository.size());

        repository.add(array2);
        assertEquals(2, repository.size());

        repository.remove(array1.getId());
        assertEquals(1, repository.size());
    }

    @Test
    void testFindBySpecificationById() throws ArrayValidationException {
        repository.add(array1);
        repository.add(array2);

        ArraySpecification spec = new ByIdSpecification(array1.getId());
        List<IntegerArray> result = repository.findBySpecification(spec);

        assertAll("Find by id specification",
                () -> assertEquals(1, result.size()),
                () -> assertEquals(array1.getId(), result.get(0).getId())
        );
    }

    @Test
    void testFindBySpecificationByMaxGreaterThan() throws ArrayValidationException {
        repository.add(array1);
        repository.add(array2);
        repository.add(array3);

        ArraySpecification spec = new FindByMaxGreaterThanSpecification(25);
        List<IntegerArray> result = repository.findBySpecification(spec);

        assertAll("Find by max > 25",
                () -> assertEquals(1, result.size()),
                () -> assertEquals(array2.getId(), result.get(0).getId())
        );
    }

    @Test
    void testFindBySpecificationByMinLessThan() throws ArrayValidationException {
        repository.add(array1);
        repository.add(array2);
        repository.add(array3);

        ArraySpecification spec = new FindByMinLessThanSpecification(3);
        List<IntegerArray> result = repository.findBySpecification(spec);

        assertAll("Find by min < 3",
                () -> assertEquals(1, result.size()),
                () -> assertEquals(array1.getId(), result.get(0).getId())
        );
    }

    @Test
    void testFindBySpecificationBySumLessThan() throws ArrayValidationException {
        repository.add(array1);
        repository.add(array2);
        repository.add(array3);

        ArraySpecification spec = new FindBySumLessThanSpecification(30);
        List<IntegerArray> result = repository.findBySpecification(spec);

        assertAll("Find by sum < 30",
                () -> assertEquals(1, result.size()),
                () -> assertEquals(array1.getId(), result.get(0).getId())
        );
    }

    @Test
    void testFindBySpecificationReturnsEmpty() throws ArrayValidationException {
        repository.add(array2);

        ArraySpecification spec = new FindBySumLessThanSpecification(10);
        List<IntegerArray> result = repository.findBySpecification(spec);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllSorted() throws ArrayValidationException {
        repository.add(array2);
        repository.add(array1);
        repository.add(array3);

        List<IntegerArray> sorted = repository.findAllSorted(Comparator.comparingInt(IntegerArray::length));

        assertAll("Find all sorted by length",
                () -> assertEquals(3, sorted.size()),
                () -> assertEquals(3, sorted.get(0).length()),
                () -> assertEquals(4, sorted.get(1).length()),
                () -> assertEquals(5, sorted.get(2).length())
        );
    }

    @Test
    void testFindAllSortedWithEmptyRepository() {
        List<IntegerArray> sorted = repository.findAllSorted(Comparator.comparingInt(IntegerArray::length));
        assertTrue(sorted.isEmpty());
    }

    @Test
    void testGetAllReturnsCopy() throws ArrayValidationException {
        repository.add(array1);

        List<IntegerArray> copy = repository.findAll();
        copy.clear();

        assertFalse(repository.findAll().isEmpty());
    }
}