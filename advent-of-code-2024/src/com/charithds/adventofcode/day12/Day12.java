package com.charithds.adventofcode.day12;

import com.charithds.adventofcode.TaskInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 implements TaskInt {
    private final String DAY12_FILE;

    public Day12(String day8File) {
        if ("sample".equals(day8File)) {
            DAY12_FILE = "resources/day12-sample.dat";
        } else {
            DAY12_FILE = STR."resources/day12.dat";
        }
    }

    private void getStatistic(Character[][] map, Set<List<Integer>> visited, List<Integer> currentPosition, Integer[] stats) {
        if (visited.contains(currentPosition)) return;
        visited.add(currentPosition);
        stats[1] = stats[1]+1;
        Integer x = currentPosition.get(0), y = currentPosition.get(1);

        var currentCharacter = map[x][y];

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (Math.abs(i) == Math.abs(j)) {
                    continue;
                }

                int x1 = currentPosition.get(0) + i, y1 = currentPosition.get(1) + j;
                if (x1 < 0 || y1 < 0 || x1 >= map.length || y1 >= map[0].length
                        || map[x1][y1] != currentCharacter) {
                    // out of range
                    stats[0] = stats[0] + 1;
                    continue;
                }
                var nextPosition = List.of(x1, y1);
                getStatistic(map, visited, nextPosition, stats);

            }
        }
    }

    private Character[][] getInput() {
        try (var fileReader = new FileReader(DAY12_FILE);
             var reader = new BufferedReader(fileReader)) {
            return reader.lines()
                    .map(line -> line.chars().mapToObj(c -> (char) c).toArray(Character[]::new))
                    .toArray(Character[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void execute() {
//        Character[][] map = new Character[][]{
//                {'A','A','A','A'},
//                {'B','B','C','D'},
//                {'B','B','C','C'},
//                {'E','E','E','C'}
//        };

        Character[][] map = getInput();
        HashSet<List<Integer>> visited = new HashSet<>();

        long sum = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                var currentPos = List.of(i, j);
                if (visited.contains(currentPos)) continue;
                Integer[] stats = new Integer[]{0, 0};
                getStatistic(map, visited, currentPos, stats);
//                System.out.println(STR."Char is \{String.valueOf(map[i][j])} \{stats[0]} * \{stats[1]}");
                sum += (long) stats[0] * stats[1];
            }
        }


        System.out.println(sum);

    }
}
