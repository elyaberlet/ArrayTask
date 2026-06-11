package org.example.reader.impl;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.factory.IntegerArrayFactory;
import org.example.parser.ArrayParser;
import org.example.reader.ArrayFileReader;
import org.example.validator.ArrayValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArrayFileReaderImpl implements ArrayFileReader {

    private final ArrayValidator validator;
    private final ArrayParser parser;
    private final IntegerArrayFactory factory;

    public ArrayFileReaderImpl(ArrayValidator validator,
                           ArrayParser parser,
                           IntegerArrayFactory factory) {
        this.validator = validator;
        this.parser = parser;
        this.factory = factory;
    }

    @Override
    public List<IntegerArray> readAllArrays(String filePath) throws ArrayValidationException {
        List<IntegerArray> arrays = new ArrayList<>();
        List<String> lines = readLinesFromFile(filePath);

        for (String line : lines) {
            IntegerArray array = processLine(line);
            if (array != null) {
                arrays.add(array);
            }
        }

        return arrays;
    }

    private List<String> readLinesFromFile(String filePath) throws ArrayValidationException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new ArrayValidationException("File not found: " + filePath);
        }

        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new ArrayValidationException("Error reading file: " + filePath, e);
        }
    }

    private IntegerArray processLine(String line) throws ArrayValidationException {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        if (!validator.isValidLine(line)) {
            return null;
        }

        List<Integer> numbers = parser.parseLine(line);

        if (numbers.isEmpty()) {
            return null;
        }

        Integer[] array = numbers.toArray(new Integer[0]);
        return factory.createArray(array);
    }
}