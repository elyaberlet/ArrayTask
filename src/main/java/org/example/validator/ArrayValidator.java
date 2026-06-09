package org.example.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ArrayValidator {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");

    public List<String> validateLine(String line) {
        List<String> errors = new ArrayList<>();

        if (line == null) {
            errors.add("Line is null");
            return errors;
        }

        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            errors.add("Line is empty");
            return errors;
        }

        String[] parts = trimmedLine.split("[,\\-;\\s]+");

        for (String part : parts) {
            if (part.isEmpty()) continue;
            if (!INTEGER_PATTERN.matcher(part).matches()) {
                errors.add("Invalid integer format: " + part);
            }
        }

        return errors;
    }

    public boolean isValidLine(String line) {
        return validateLine(line).isEmpty();
    }
}