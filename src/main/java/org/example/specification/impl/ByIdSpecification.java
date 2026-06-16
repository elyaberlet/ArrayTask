package org.example.specification.impl;

import org.example.entity.IntegerArray;
import org.example.specification.ArraySpecification;

import java.util.UUID;

public class ByIdSpecification implements ArraySpecification {
    private final UUID targetId;

    public ByIdSpecification(UUID targetId) {
        this.targetId = targetId;
    }

    @Override
    public boolean specify(IntegerArray array) {
        return array.getId().equals(targetId);
    }
}