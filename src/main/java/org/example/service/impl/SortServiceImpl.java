package org.example.service.impl;

import org.example.entity.IntegerArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exception.ArrayValidationException;
import org.example.service.ArraySortService;

public class SortServiceImpl implements ArraySortService {

    private static final Logger logger = LogManager.getLogger(SortServiceImpl.class);

    @Override
    public void sortBubble(IntegerArray array) throws ArrayValidationException {
        logger.debug("Sorting array using bubble sort");

        if (array.isEmpty() || array.length() == 1) {
            logger.info("No sorting needed");
            return;
        }

        int length = array.length();

        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (array.getElement(j) > array.getElement(j + 1)) {
                    int temp = array.getElement(j);
                    array.setElement(j, array.getElement(j + 1));
                    array.setElement(j + 1, temp);
                }
            }
        }

        logger.info("Bubble sort completed");
    }

    @Override
    public void sortInsertion(IntegerArray array) throws ArrayValidationException {
        logger.debug("Sorting array using insertion sort");

        if (array.isEmpty() || array.length() == 1) {
            logger.info("No sorting needed");
            return;
        }

        int length = array.length();

        for (int i = 1; i < length; i++) {
            int key = array.getElement(i);
            int j = i - 1;

            while (j >= 0 && array.getElement(j) > key) {
                array.setElement(j + 1, array.getElement(j));
                j--;
            }
            array.setElement(j + 1, key);
        }

        logger.info("Insertion sort completed");
    }
}