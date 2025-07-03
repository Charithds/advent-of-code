package com.charithds.adventofcode.day6;

import com.charithds.adventofcode.TaskInt;

import java.util.*;

public class Day6 implements TaskInt {
    private final String DAY6_FILE;

    public Day6(String day6File) {
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

        int i = guardPos.get(0), j = guardPos.get(1);
        int steps = 1;
        input[i][j] = 'O'; // Update guard position


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
                    steps++;
                }
            }
        }
        System.out.println("Guard moved " + steps + " steps before hitting the boundary.");

        for (int k = 0; k < input.length; k++) {
            for (int l = 0; l < input[k].length; l++) {
                System.out.print(input[k][l]);
            }
            System.out.println();
        }


    }
}
