package com.charithds.adventofcode.day6;

import com.charithds.adventofcode.TaskInt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6Part2 implements TaskInt {
    private final String DAY6_FILE;

    public Day6Part2(String day6File) {
        if ("sample".equals(day6File)) {
            DAY6_FILE = "resources/day6-sample.dat";
        } else {
            DAY6_FILE = STR."resources/day6.dat";
        }
        StringTemplate.Processor<String, RuntimeException> STX = StringTemplate::interpolate;
    }

    public Character[][] getInput() {
        try (var fileReader = new java.io.FileReader(DAY6_FILE);
             var reader = new java.io.BufferedReader(fileReader)) {

            Character[][] map = reader.lines()
                    .map(line -> line.chars()
                            .mapToObj(c -> (char) c)
                            .toArray(Character[]::new)
                    ).toArray(Character[][]::new);
            return map;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void execute() {
        Character[][] input = getInput();
        Set<List<Integer>> obstables = new HashSet<>();
        List<Integer> guardPos = null;
        int guardDirection = 0; // 0: up, 1: right, 2: down, 3: left

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == '#') {
                    obstables.add(List.of(i, j));
                } else if (input[i][j] == 'v') {
                    guardPos = List.of(i, j);
                    guardDirection = 2; // down
                } else if (input[i][j] == '>') {
                    guardPos = List.of(i, j);
                    guardDirection = 1; // right
                } else if (input[i][j] == '<') {
                    guardPos = List.of(i, j);
                    guardDirection = 3; // left
                } else if (input[i][j] == '^') {
                    guardPos = List.of(i, j);
                    guardDirection = 0; // up
                }
            }
        }

        int numberOfLoops = 0;
        List<List<Integer>> path = new ArrayList<>(getPath(guardPos, guardDirection, copyInput(input), obstables));
        for (int i = 0; i < path.size(); i++) {
            List<Integer> pos = path.get(i);
            int x = pos.get(0), y = pos.get(1);
            if (guardPos.get(0) == x && guardPos.get(1) == y) {
                continue;
            }
            ArrayList<List<Integer>> newObstacles = new ArrayList<>(obstables);
            newObstacles.add(List.of(x, y));
            numberOfLoops += checkLoops(guardPos, guardDirection, copyInput(input), new HashSet<>(newObstacles));
        }

        System.out.println("Number of loops: " + numberOfLoops);


    }

    private static int checkLoops(List<Integer> guardPos, int guardDirection, Character[][] input, Set<List<Integer>> obstables) {
        int i = guardPos.get(0), j = guardPos.get(1);
        input[i][j] = (char) (65 + guardDirection);
        while (true) {
            int nextI = i, nextJ = j;
            switch (guardDirection) {
                case 0 -> nextI--;
                case 1 -> nextJ++;
                case 2 -> nextI++;
                case 3 -> nextJ--;
            }
            if (nextI >= input.length || nextI < 0 ||
                    nextJ >= input[i].length || nextJ < 0) {
                break;
            }

            if (obstables.contains(List.of(nextI, nextJ))) {
                // Obstacle encountered, change direction
                guardDirection = (guardDirection + 1) % 4;
            } else {
                i = nextI;
                j = nextJ;
                char directionChar = (char) (65 + guardDirection); // Convert direction to character (A, B, C, D)
                if (input[i][j] == directionChar) {
                    // a loop is comepleted.
                    return 1;
                } else {
                    input[i][j] = directionChar;
                }
            }
        }
        return 0;
    }

    private static Set<List<Integer>> getPath(List<Integer> guardPos, int guardDirection, Character[][] input, Set<List<Integer>> obstables) {
        int i = guardPos.get(0), j = guardPos.get(1);
        input[i][j] = 'O'; // Update guard position
        Set<List<Integer>> path = new HashSet<>();
        while (true) {
            int nextI = i, nextJ = j;
            switch (guardDirection) {
                case 0 -> nextI--;
                case 1 -> nextJ++;
                case 2 -> nextI++;
                case 3 -> nextJ--;
            }
            if (nextI >= input.length || nextI < 0 ||
                    nextJ >= input[i].length || nextJ < 0) {
                break;
            }

            if (obstables.contains(List.of(nextI, nextJ))) {
                // Obstacle encountered, change direction
                guardDirection = (guardDirection + 1) % 4;
            } else {
                i = nextI;
                j = nextJ;
                if (input[i][j] != 'O') {
                    input[i][j] = 'O'; // Update guard position
                    path.add(List.of(i, j));
                }
            }
        }
        return path;
    }

    private Character[][] copyInput(Character[][] input) {
        Character[][] copy = new Character[input.length][];
        for (int i = 0; i < input.length; i++) {
            copy[i] = input[i].clone();
        }
        return copy;
    }
}
