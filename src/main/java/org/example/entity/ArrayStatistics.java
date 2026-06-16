package org.example.entity;

public record ArrayStatistics(
        int sum,
        double average,
        int min,
        int max,
        int size
) {}