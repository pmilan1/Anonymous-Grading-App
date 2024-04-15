package com.example.anonymousgradingapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomNumberGenerator {
    private List<Integer> numbers;
    private int currentIndex;
    private Random random;

    public RandomNumberGenerator(int min, int max) {
        numbers = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        currentIndex = 0;
        random = new Random();
    }

    public int next() {
        if (currentIndex >= numbers.size()) {
            Collections.shuffle(numbers);
            currentIndex = 0;
        }
        return numbers.get(currentIndex++);
    }
}
