package org.example.reader;

import org.example.entity.IntegerArray;
import org.example.exception.ArrayValidationException;
import java.util.List;

public interface ArrayFileReader {

    List<IntegerArray> readAllArrays(String filePath) throws ArrayValidationException;
}