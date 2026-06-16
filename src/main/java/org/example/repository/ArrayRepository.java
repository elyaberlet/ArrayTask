package org.example.repository;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.specification.ArraySpecification;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArrayRepository {

    void add(IntegerArray array) throws ArrayValidationException;

    void update(IntegerArray array) throws ArrayValidationException;

    void remove(UUID id) throws ArrayValidationException;

    Optional<IntegerArray> findById(UUID id);

    List<IntegerArray> findAll();

    int size();

    List<IntegerArray> findBySpecification(ArraySpecification specification);

    List<IntegerArray> findAllSorted(Comparator<IntegerArray> comparator);
}