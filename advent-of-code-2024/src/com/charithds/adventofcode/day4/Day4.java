package com.charithds.adventofcode.day4;

import com.charithds.adventofcode.TaskInt;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 implements TaskInt {
    private static final String DAY4_FILE = "resources/day4.dat";
    private static final String STRING_TO_SEARCH = "XMAS";
    private static final String STRING_TO_SEARCH_REVERSE = "SAMX";

    private List<String> getInput() {
        try {
            return Files.lines(Paths.get(DAY4_FILE)).collect(Collectors.toUnmodifiableList());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String horizontal(List<String> input, int row, int col) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int currentElement = col + i;
            if (currentElement >= input.get(row).length()) {
                break;
            }
            sb.append(input.get(row).charAt(currentElement));
        }
        return sb.toString();
    }

    private String vertical(List<String> input, int row, int col) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int currentElement = row + i;
            if (currentElement >= input.size()) {
                break;
            }
            sb.append(input.get(currentElement).charAt(col));
        }
        return sb.toString();
    }

    private String diagonal1(List<String> input, int row, int col) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int currentRow = row + i;
            int currentCol = col + i;
            if (currentRow >= input.size() || currentCol >= input.get(row).length()) {
                break;
            }
            sb.append(input.get(currentRow).charAt(currentCol));
        }
        return sb.toString();
    }
    /*
    0 1 2 3 4
    1 1 2 3 4
    2 1 2 3 4
    3 1 2 3 4
     */

    private String diagonal2(List<String> input, int row, int col) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int currentRow = row - i;
            int currentCol = col + i;
            if (currentRow < 0 || currentCol >= input.get(row).length()) {
                break;
            }
            sb.append(input.get(currentRow).charAt(currentCol));
        }
        return sb.toString();
    }

    private int check(List<String > input, int row, int col) {
        String horizontal = horizontal(input, row, col);
        String vertical = vertical(input, row, col);
        String diagonal1 = diagonal1(input, row, col);
        String diagonal2 = diagonal2(input, row, col);
        System.out.println("Checking at row: " + row + ", col: " + col);
        System.out.println("Horizontal: " + horizontal + ", Vertical: " + vertical +
                ", Diagonal1: " + diagonal1 + ", Diagonal2: " + diagonal2);

        int count = 0;
        if (STRING_TO_SEARCH.equals(horizontal)) {
            count++;
        }
        if (STRING_TO_SEARCH_REVERSE.equals(horizontal)) {
            count++;
        }
        if (STRING_TO_SEARCH.equals(vertical)) {
            count++;
        }
        if (STRING_TO_SEARCH_REVERSE.equals(vertical)) {
            count++;
        }
        if (STRING_TO_SEARCH.equals(diagonal1)) {
            count++;
        }
        if (STRING_TO_SEARCH_REVERSE.equals(diagonal1)) {
            count++;
        }
        if (STRING_TO_SEARCH.equals(diagonal2)) {
            count++;
        }
        if (STRING_TO_SEARCH_REVERSE.equals(diagonal2)) {
            count++;
        }
        return count;

    }


    @Override
    public void execute() {
        List<String> input = getInput();
//        System.out.println(input);
        if (input == null) {
            System.out.println("Failed to read input data.");
            return;
        }

        int count = 0;
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int row = 0; row < input.size(); row++) {
            for (int col = 0; col < input.get(row).length(); col++) {
                var currentChar = input.get(row).charAt(col);
                if (currentChar != 'X' && currentChar != 'S') {
                    grid[row][col] = '.';
                    continue;
                }
                int localCount = check(input, row, col);
                if (localCount > 0) {
                    grid[row][col] = currentChar;
                    count += localCount;
                } else {
                    grid[row][col] = '.';
                }
            }
        }

//        for (int i = 0; i < input.size(); i++) {
//            for (int j = 0; j < input.get(0).length(); j++) {
//                System.out.print(grid[i][j]);
//            }
//            System.out.println();
//        }

        System.out.println("Number of occurrences of 'XMAS' or 'SAMX': " + count);

    }
}
