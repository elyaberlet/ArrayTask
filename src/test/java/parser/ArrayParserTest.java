package parser;

import org.example.parser.ArrayParser;
import org.example.parser.impl.ArrayParserImpl;  // ← ДОБАВИТЬ ЭТОТ ИМПОРТ
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayParserTest {

    private ArrayParser parser;

    @BeforeEach
    void setUp() {
        parser = new ArrayParserImpl();  // ✅ Теперь работает
    }

    @Test
    void testParseLineValid() {
        List<Integer> result = parser.parseLine("1, 2, 3, 4, 5");
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    @Test
    void testParseLineWithInvalidNumbers() {
        List<Integer> result = parser.parseLine("1, 2, x3, 6..5, 77");
        assertEquals(List.of(1, 2, 77), result);
    }

    @Test
    void testParseLineEmpty() {
        List<Integer> result = parser.parseLine("");
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseLineWithDash() {
        List<Integer> result = parser.parseLine("11- 2 - 42- 100");
        assertEquals(List.of(11, 2, 42, 100), result);
    }
}