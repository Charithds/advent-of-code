package com.charithds.adventofcode.day2;

import com.charithds.adventofcode.TaskInt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 implements TaskInt {
    private static final String DAY2_FILE = "resources/day2.dat";

    public List<List<Integer>> getInput() {
        try {
            return Files.lines(Paths.get(DAY2_FILE))
                    .map(line -> Arrays.stream(line.split(" "))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList())
                    ).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSafe(List<Integer> list) {
        int direction = list.get(1) - list.get(0) > 0 ? 1 : -1;
        for (int i = 0; i < list.size() - 1; i++) {
            var diff = list.get(i + 1) - list.get(i);
            var diffWithDirection = diff * direction;
            if (diffWithDirection < 1 || diffWithDirection > 3) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute() {
        List<List<Integer>> input = getInput();
        if (input == null) {
            System.out.println("Failed to read input data.");
            return;
        }

        int safeCount = 0;
        for (List<Integer> list : input) {
            if (isSafe(list)) {
                safeCount++;
            }
        }

        System.out.println("Number of safe lists: " + safeCount);
    }

}
