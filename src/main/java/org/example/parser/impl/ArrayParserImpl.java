package org.example.parser.impl;

import org.example.parser.ArrayParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayParserImpl implements ArrayParser {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");

    @Override
    public List<Integer> parseLine(String line) {
        List<Integer> numbers = new ArrayList<>();

        if (line == null || line.isBlank()) {
            return numbers;
        }

        Matcher matcher = INTEGER_PATTERN.matcher(line);

        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }

        return numbers;
    }
}