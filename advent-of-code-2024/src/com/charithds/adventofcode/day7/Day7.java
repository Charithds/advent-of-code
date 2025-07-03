package com.charithds.adventofcode.day7;

import com.charithds.adventofcode.TaskInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

class TestData {
    private final Long key;
    private final List<Long> numbers;

    TestData(Long key, List<Long> numbers) {
        this.key = key;
        this.numbers = numbers;
    }

    public List<Long> getNumbers() {
        return numbers;
    }

    public Long getKey() {
        return key;
    }
}
public class Day7 implements TaskInt {
    private final String DAY7_FILE;

    public Day7(String day6File) {
        if ("sample".equals(day6File)) {
            DAY7_FILE = "resources/day7-sample.dat";
        } else {
            DAY7_FILE = STR."resources/day7.dat";
        }
    }

    public List<TestData> getInput() {
        try (var fileReader = new FileReader(DAY7_FILE);
             var reader = new BufferedReader(fileReader)) {

            var map = reader.lines()
                    .map(line -> {
                        String[] split = line.split(":");
                        Long key = Long.parseLong(split[0].trim());
                        List<Long> numbers = Arrays.stream(split[1].trim().split(" ")).map(Long::parseLong).toList();
                        return new TestData(key, numbers);
                    }).toList();
            System.out.println("Input data loaded successfully." + map.size() + " entries found.");
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void execute() {
        List<TestData> input = getInput();

        long total = 0;
        int testCases = 0;
        for (int k = 0; k < input.size(); k++) {
            testCases++;
            var entry = input.get(k);
            Long key = entry.getKey();
            List<Long> numbers = entry.getNumbers();

            long combinations = (long) Math.pow(2, numbers.size()-1);
            for (long i = 0; i < combinations; i++) {
                String binaryString = String.format("%"+(numbers.size()-1)+"s", Long.toBinaryString(i)).replace(" ", "0");
//                System.out.println("Binary string for combination " + i + ": " + binaryString);
                long start = numbers.get(0);
                for (int j = 0; j < binaryString.length(); j++) {
                    if (binaryString.charAt(j) == '1') {
                        start += numbers.get(j + 1);
                    } else {
                        start *= numbers.get(j + 1);
                    }
                }
                if (start == key) {
                    total += key;

//                    System.out.println("Found a valid combination for key: " + key + " with total: " + total);
                    break; // Found a valid combination, no need to check further
                }
            }
        }
        System.out.println("Total number of keys: " + testCases);
        System.out.println("Total of all keys with valid combinations: " + total);
    }
}
