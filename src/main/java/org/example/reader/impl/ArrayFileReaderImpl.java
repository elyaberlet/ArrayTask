package org.example.reader.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.factory.IntegerArrayFactory;
import org.example.parser.ArrayParser;
import org.example.reader.ArrayFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArrayFileReaderImpl implements ArrayFileReader {

    private static final Logger logger = LogManager.getLogger(ArrayFileReaderImpl.class);

    private final ArrayParser parser;
    private final IntegerArrayFactory factory;

    public ArrayFileReaderImpl(ArrayParser parser,
                               IntegerArrayFactory factory) {
        this.parser = parser;
        this.factory = factory;
    }

    @Override
    public List<IntegerArray> readAllArrays(String filePath) throws ArrayValidationException {
        logger.info("Reading arrays from file: {}", filePath);

        if (filePath == null || filePath.trim().isEmpty()) {
            throw new ArrayValidationException("File path cannot be null or empty");
        }

        List<IntegerArray> arrays = new ArrayList<>();
        List<String> lines = readLinesFromFile(filePath);

        for (String line : lines) {
            IntegerArray array = processLine(line);
            if (array != null) {
                arrays.add(array);
                logger.debug("Added array with id: {}", array.getId());
            }
        }

        logger.info("Successfully read {} arrays from file", arrays.size());
        return arrays;
    }

    private List<String> readLinesFromFile(String filePath) throws ArrayValidationException {
        logger.debug("Reading lines from file: {}", filePath);

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            logger.error("File not found: {}", filePath);
            throw new ArrayValidationException("File not found: " + filePath);
        }

        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            logger.error("IO error while reading file: {}", filePath, e);
            throw new ArrayValidationException("Error reading file: " + filePath, e);
        }
    }

    private IntegerArray processLine(String line) throws ArrayValidationException {
        if (line == null || line.isBlank()) {
            logger.debug("Empty line, skipping");
            return null;
        }

        List<Integer> numbers = parser.parseLine(line.trim());

        if (numbers.isEmpty()) {
            logger.debug("No numbers parsed from line, skipping: {}", line);
            return null;
        }

        Integer[] array = numbers.toArray(new Integer[0]);
        return factory.createArray(array);
    }
}