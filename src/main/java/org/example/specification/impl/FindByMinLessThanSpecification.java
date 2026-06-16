// FindByMinLessThanSpecificationImpl.java
package org.example.specification.impl;

import org.example.entity.ArrayStatistics;
import org.example.entity.IntegerArray;
import org.example.specification.ArraySpecification;
import org.example.warehouse.Warehouse;
import org.example.warehouse.impl.WarehouseImpl;

public class FindByMinLessThanSpecification implements ArraySpecification {
    private final int threshold;
    private final Warehouse warehouse;

    public FindByMinLessThanSpecification(int threshold) {
        this.threshold = threshold;
        this.warehouse = WarehouseImpl.getInstance();
    }

    @Override
    public boolean specify(IntegerArray array) {
        return warehouse.getStatistics(array.getId())
                .map(ArrayStatistics::min)
                .orElse(Integer.MAX_VALUE) < threshold;
    }
}