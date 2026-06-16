package org.example.specification;

import org.example.entity.IntegerArray;
import org.example.specification.impl.ByIdSpecification;
import org.example.specification.impl.FindByMaxGreaterThanSpecification;
import org.example.specification.impl.FindByMinLessThanSpecification;
import org.example.specification.impl.FindBySumLessThanSpecification;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ArraySpecificationTest {

    @Test
    void testAllSpecifications()  {
        IntegerArray array = new IntegerArray(new Integer[]{1, 2, 3, 4, 5});

        assertAll("Specifications",
                () -> assertTrue(new ByIdSpecification(array.getId()).specify(array)),
                () -> assertFalse(new ByIdSpecification(UUID.randomUUID()).specify(array)),
                () -> assertTrue(new FindByMaxGreaterThanSpecification(3).specify(array)),
                () -> assertFalse(new FindByMaxGreaterThanSpecification(10).specify(array)),
                () -> assertTrue(new FindByMinLessThanSpecification(2).specify(array)),
                () -> assertFalse(new FindByMinLessThanSpecification(0).specify(array)),
                () -> assertTrue(new FindBySumLessThanSpecification(20).specify(array)),
                () -> assertFalse(new FindBySumLessThanSpecification(10).specify(array))
        );
    }
}