package org.example.reader;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import org.example.factory.IntegerArrayFactory;
import org.example.parser.ArrayParser;
import org.example.reader.impl.ArrayFileReaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArrayFileReaderTest {

    @Mock
    private ArrayParser parser;

    @Mock
    private IntegerArrayFactory factory;

    @Mock
    private IntegerArray mockArray;

    private ArrayFileReader reader;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        reader = new ArrayFileReaderImpl(parser, factory);
    }

    @Test
    void testReadAllArraysSuccess() throws Exception {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "1,2,3\n4,5,6");
        when(parser.parseLine("1,2,3")).thenReturn(List.of(1, 2, 3));
        when(parser.parseLine("4,5,6")).thenReturn(List.of(4, 5, 6));
        when(factory.createArray(any())).thenReturn(mockArray);

        List<IntegerArray> result = reader.readAllArrays(file.toString());

        assertAll("Read arrays",
                () -> assertEquals(2, result.size()),
                () -> verify(parser, times(2)).parseLine(any()),
                () -> verify(factory, times(2)).createArray(any())
        );
    }

    @Test
    void testReadAllArraysWithEmptyLines() throws Exception {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "1,2,3\n\n4,5,6\n   ");
        when(parser.parseLine("1,2,3")).thenReturn(List.of(1, 2, 3));
        when(parser.parseLine("4,5,6")).thenReturn(List.of(4, 5, 6));
        when(factory.createArray(any())).thenReturn(mockArray);

        List<IntegerArray> result = reader.readAllArrays(file.toString());

        assertEquals(2, result.size());
        verify(parser, times(2)).parseLine(any());
    }

    @Test
    void testReadAllArraysWithInvalidLines() throws Exception {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "1,2,3\nabc,def\n4,5,6");
        when(parser.parseLine("1,2,3")).thenReturn(List.of(1, 2, 3));
        when(parser.parseLine("abc,def")).thenReturn(List.of());
        when(parser.parseLine("4,5,6")).thenReturn(List.of(4, 5, 6));
        when(factory.createArray(any())).thenReturn(mockArray);

        List<IntegerArray> result = reader.readAllArrays(file.toString());

        assertEquals(2, result.size());
        verify(factory, times(2)).createArray(any());
    }

    @Test
    void testReadAllArraysWithEmptyFile() throws Exception {
        Path file = tempDir.resolve("empty.txt");
        Files.writeString(file, "");

        List<IntegerArray> result = reader.readAllArrays(file.toString());

        assertTrue(result.isEmpty());
        verifyNoInteractions(parser);
        verifyNoInteractions(factory);
    }

    @Test
    void testReadAllArraysWithNonExistentFile() {
        assertThrows(ArrayValidationException.class,
                () -> reader.readAllArrays("non_existent_file.txt"));
    }

    @Test
    void testReadAllArraysWithNullPath() {
        assertThrows(ArrayValidationException.class,
                () -> reader.readAllArrays(null));
    }

    @Test
    void testReadAllArraysWithEmptyPath() {
        assertThrows(ArrayValidationException.class,
                () -> reader.readAllArrays(""));
    }
}