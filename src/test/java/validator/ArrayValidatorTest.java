package validator;

import org.example.validator.ArrayValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayValidatorTest {

    private ArrayValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ArrayValidator();
    }

    @Test
    void testIsValidLineTrue() {
        assertTrue(validator.isValidLine("1, 2, 3, 4, 5"));
    }

    @Test
    void testIsValidLineFalse() {
        assertFalse(validator.isValidLine("1, 2, x3, 4"));
    }

    @Test
    void testIsValidLineEmpty() {
        assertFalse(validator.isValidLine(""));
    }
}