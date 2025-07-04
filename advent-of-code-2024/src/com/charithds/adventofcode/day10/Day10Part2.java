package com.charithds.adventofcode.day10;

import com.charithds.adventofcode.TaskInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10Part2 implements TaskInt {
    /*
        The topographic map indicates the height at each position using a scale from 0 (lowest) to 9 (highest).
        For example:

        0123
        1234
        8765
        9876

        you determine that a good hiking trail is
            as long as possible and has
            an
                even, gradual, uphill slope.
            hiking trail is any path that starts at height 0, ends at height 9,
            and always increases by a height of exactly 1 at each step.
        Hiking trails never include diagonal steps - only up, down, left, or right (from the perspective of the map)
     */

    /*
        A trailhead is any position that starts one or more hiking trails - here, these positions will always have height 0.
        trailhead's score is the number of 9-height positions reachable from that trailhead via a hiking trail.
        In the above example, the single trailhead in the top left corner has a score of 1 because it can reach a single 9 (the one in the bottom left).
        .s are impossible to travel

     */

    private final String DAY10_FILE;

    public Day10Part2(String day10File) {
        if ("sample".equals(day10File)) {
            DAY10_FILE = "resources/day10-sample.dat";
        } else {
            DAY10_FILE = STR."resources/day10.dat";
        }
    }

    private HikeMap getInput() {
        try (var fileReader = new FileReader(DAY10_FILE);
             var reader = new BufferedReader(fileReader)) {
            Integer[][] array = reader.lines()
                    .map(line -> line.chars().boxed().map(c -> c == '.' ? -1 : c - 48).toArray(Integer[]::new))
                    .toArray(Integer[][]::new);

            var startingPos = new ArrayList<List<Integer>>();
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    if (array[i][j] == 0) {
                        startingPos.add(List.of(i, j));
                    }
                }
            }
            return new HikeMap(array, startingPos);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private long findTrailCount(List<Integer> startingPos, Integer[][] map, int limX, int limY, List<Integer> parent,
                                Set<List<Integer>> nines) {

        Integer x = startingPos.get(0);
        Integer y = startingPos.get(1);
        if (x > limX || y > limY || x < 0 || y < 0) return 0;
        if (parent != null && map[x][y] - map[parent.get(0)][parent.get(1)] != 1) return 0;
        if (map[x][y] == -1) return 0;
        if (map[x][y] == 9) {
            nines.add(List.of(x, y));
            return 1;
        }
        // up 1, down 2, left 3, right 4
//        System.out.println("x : " + x + " y: "+ y);
        long sum = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (parent != null && i == parent.get(0) && j == parent.get(1)) {
                    continue;
                }
                if (Math.abs(i) == Math.abs(j)) {
                    continue;
                }
                sum += findTrailCount(List.of(x + i, y + j), map, limX, limY, List.of(x, y), nines);
            }
        }
        return sum;
    }

    private void printMap(Integer[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void execute() {
        // seems like flooding can work
        HikeMap input = getInput();
//        printMap(input.getMap());


        int limX = input.getMap().length - 1;
        int limY = input.getMap()[0].length - 1;
        long sum = 0;
        for (List<Integer> startingPosition : input.getStartingPositions()) {
            Set<List<Integer>> nines = new HashSet<>();
            sum += findTrailCount(startingPosition, input.getMap(), limX, limY, null, nines);
//            sum += nines.size();
        }

        System.out.println("Trail count is "+ sum);


    }
}
