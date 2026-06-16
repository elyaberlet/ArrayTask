// FindBySumLessThanSpecificationImpl.java
package org.example.specification.impl;

import org.example.entity.ArrayStatistics;
import org.example.entity.IntegerArray;
import org.example.specification.ArraySpecification;
import org.example.warehouse.Warehouse;
import org.example.warehouse.impl.WarehouseImpl;

public class FindBySumLessThanSpecification implements ArraySpecification {
    private final int threshold;
    private final Warehouse warehouse;

    public FindBySumLessThanSpecification(int threshold) {
        this.threshold = threshold;
        this.warehouse = WarehouseImpl.getInstance();
    }

    @Override
    public boolean specify(IntegerArray array) {
        return warehouse.getStatistics(array.getId())
                .map(ArrayStatistics::sum)
                .orElse(0) < threshold;
    }
}