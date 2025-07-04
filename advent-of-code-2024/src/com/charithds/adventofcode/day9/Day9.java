package com.charithds.adventofcode.day9;

import com.charithds.adventofcode.TaskInt;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day9 implements TaskInt {
    private final String DAY8_FILE;

    public Day9(String day8File) {
        if ("sample".equals(day8File)) {
            DAY8_FILE = "resources/day9-sample.dat";
        } else {
            DAY8_FILE = STR."resources/day9.dat";
        }
    }

    public String getInput() {
        try (var fileReader = new java.io.FileReader(DAY8_FILE);
             var reader = new java.io.BufferedReader(fileReader)) {
            return reader.readLine();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void fill(int[] bytes, int value, int start, int blockSize) {
        for (int i = 0; i < blockSize; i++) {
            bytes[start + i] = value;
        }
    }

    private int[] convertToBytes(String input) {
        int sum = 0;
        for (int i = 0; i < input.length(); i++) {
            int blockSize = input.charAt(i) - 48;
//            System.out.println("Block size for " + i + ": " + blockSize + " : " + input.charAt(i));
            sum += blockSize;
        }

        int[] bytes = new int[sum];
        int pos = 0;
        for (int i = 0; i < input.length(); i++) {
            int blockSize =  input.charAt(i) - 48;
            if (i%2 == 0) {
                // file
                fill(bytes, i/2, pos, blockSize);
            } else {
                // space
                fill(bytes, -1, pos, blockSize);
            }
            pos += blockSize;
        }
        return bytes;

    }

    private void defrag(int[] disk) {
        int endPointer = disk.length - 1;
        for (int i = 0; i < disk.length; i++) {
            if (disk[i] != -1) {
                continue;
            }
            while (endPointer > i) {
                if (disk[endPointer] == -1) {
                    endPointer--;
                } else {
                    break;
                }
            }
            if (endPointer <= i) {
                break;
            }
            if (disk[i] == -1 && disk[endPointer] != -1) {
                // swap
                int temp = disk[i];
                disk[i] = disk[endPointer];
                disk[endPointer] = temp;
            }
        }
    }

    private long calculateHash(int[] disk) {
        long sum = 0;
        for (int i = 0; i < disk.length; i++) {
            if (disk[i] == -1) {
                break;
            }
            sum += (long) disk[i] * i;
        }
        return sum;
    }
    @Override
    public void execute() {
        String input = getInput();
//        System.out.println("Input: " + input);
        int[] disk = convertToBytes(input);
//        System.out.println("Disk: " + Arrays.toString(disk));
        defrag(disk);
        System.out.println(Arrays.toString(disk));
        var sum = calculateHash(disk);
        System.out.println("Sum: " + sum);

    }
}
