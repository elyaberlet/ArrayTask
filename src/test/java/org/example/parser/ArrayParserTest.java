package org.example.parser;

import org.example.parser.impl.ArrayParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayParserTest {

    private ArrayParser parser;

    @BeforeEach
    void setUp() {
        parser = new ArrayParserImpl();
    }

    @Test
    void testParseLineWithCommas() {
        String line = "1, 2, 3, 4, 5";
        List<Integer> result = parser.parseLine(line);

        assertAll("Parse with commas",
                () -> assertEquals(5, result.size()),
                () -> assertEquals(1, result.get(0)),
                () -> assertEquals(2, result.get(1)),
                () -> assertEquals(3, result.get(2)),
                () -> assertEquals(4, result.get(3)),
                () -> assertEquals(5, result.get(4))
        );
    }

    @Test
    void testParseLineWithSpaces() {
        String line = "10 20 30 40";
        List<Integer> result = parser.parseLine(line);

        assertAll("Parse with spaces",
                () -> assertEquals(4, result.size()),
                () -> assertEquals(10, result.get(0)),
                () -> assertEquals(20, result.get(1)),
                () -> assertEquals(30, result.get(2)),
                () -> assertEquals(40, result.get(3))
        );
    }

    @Test
    void testParseLineWithMixedDelimiters() {
        String line = "1, 2; 3 4-5";
        List<Integer> result = parser.parseLine(line);

        assertAll("Parse with mixed delimiters",
                () -> assertEquals(5, result.size()),
                () -> assertEquals(1, result.get(0)),
                () -> assertEquals(2, result.get(1)),
                () -> assertEquals(3, result.get(2)),
                () -> assertEquals(4, result.get(3)),
                () -> assertEquals(-5, result.get(4))
        );
    }

    @Test
    void testParseLineWithNegativeNumbers() {
        String line = "-1, 2, -3, 4, -5";
        List<Integer> result = parser.parseLine(line);

        assertAll("Parse with negative numbers",
                () -> assertEquals(5, result.size()),
                () -> assertEquals(-1, result.get(0)),
                () -> assertEquals(2, result.get(1)),
                () -> assertEquals(-3, result.get(2)),
                () -> assertEquals(4, result.get(3)),
                () -> assertEquals(-5, result.get(4))
        );
    }

    @Test
    void testParseLineWithEmptyString() {
        String line = "";
        List<Integer> result = parser.parseLine(line);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseLineWithBlankString() {
        String line = "   ";
        List<Integer> result = parser.parseLine(line);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseLineWithNull() {
        String line = null;
        List<Integer> result = parser.parseLine(line);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseLineWithNoNumbers() {
        String line = "abc, def, xyz";
        List<Integer> result = parser.parseLine(line);
        assertTrue(result.isEmpty());
    }
}