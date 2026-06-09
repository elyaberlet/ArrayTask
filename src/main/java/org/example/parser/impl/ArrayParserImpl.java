package org.example.parser.impl;

import org.example.parser.ArrayParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ArrayParserImpl implements ArrayParser {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");

    @Override
    public List<Integer> parseLine(String line) {
        List<Integer> numbers = new ArrayList<>();

        if (line == null || line.trim().isEmpty()) {
            return numbers;
        }

        String[] parts = splitLine(line.trim());

        for (String part : parts) {
            String trimmedPart = part.trim();
            if (INTEGER_PATTERN.matcher(trimmedPart).matches()) {
                numbers.add(Integer.parseInt(trimmedPart));
            }
        }

        return numbers;
    }

    private String[] splitLine(String line) {
        if (line.contains(",")) {
            String[] parts = line.split(",");
            return trimParts(parts);
        }
        if (line.contains("-")) {
            String[] parts = line.split("-");
            return trimParts(parts);
        }
        if (line.contains(";")) {
            String[] parts = line.split(";");
            return trimParts(parts);
        }
        String[] parts = line.split("\\s+");
        return trimParts(parts);
    }

    private String[] trimParts(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }
}