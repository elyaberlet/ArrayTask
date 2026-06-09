package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.factory.IntegerArrayFactory;
import org.example.parser.impl.ArrayParserImpl;
import org.example.parser.ArrayParser;
import org.example.reader.ArrayFileReaderImpl;
import org.example.reader.ArrayFileReader;
import org.example.validator.ArrayValidator;

import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String filePath = "data/arrays.txt";

        ArrayValidator validator = new ArrayValidator();
        ArrayParser parser = new ArrayParserImpl();
        IntegerArrayFactory factory = new IntegerArrayFactory();

        ArrayFileReader reader = new ArrayFileReaderImpl(validator, parser, factory);

        try {
            List<IntegerArray> arrays = reader.readAllArrays(filePath);

            logger.info("Total arrays read: " + arrays.size());

            for (int i = 0; i < arrays.size(); i++) {
                IntegerArray arr = arrays.get(i);
                logger.info("Array " + (i + 1) + ": " + arr);
            }

        } catch (ArrayValidationException e) {
            logger.error("Error: " + e.getMessage());
            System.err.println("Error: " + e.getMessage());
        }
    }
}