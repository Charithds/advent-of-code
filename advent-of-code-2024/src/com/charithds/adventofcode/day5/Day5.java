package com.charithds.adventofcode.day5;

import com.charithds.adventofcode.TaskInt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class InputData {
    private final List<Integer[]> pairs;
    private final List<List<Integer>> numbers;

    InputData(List<Integer[]> pairs, List<List<Integer>> numbers) {
        this.pairs = pairs;
        this.numbers = numbers;
    }

    public List<List<Integer>> getNumbers() {
        return numbers;
    }

    public List<Integer[]> getPairs() {
        return pairs;
    }
}

public class Day5 implements TaskInt {
//    private static final String DAY5_FILE = "resources/day5.dat";
    private static final String DAY5_FILE = "resources/day5.dat";

    private InputData getInput() {
        try (
                var lines = Files.lines(Paths.get(DAY5_FILE))
        ) {
            InputData inputData = new InputData(new ArrayList<>(), new ArrayList<>());
            lines.forEach(line -> {

                if (line.contains("|")) {
                    inputData.getPairs().add(
                            Arrays.stream(line.split("\\|")).map(Integer::parseInt).toArray(Integer[]::new));
                } else if (line.contains(",")) {
                    inputData.getNumbers().add(Arrays.stream(line.split(","))
                            .map(String::trim)
                            .map(Integer::parseInt)
                            .toList());
                }
            });
            return inputData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void execute() {
        InputData input = getInput();
        Set<List<Integer>> correct = input.getPairs().stream().map(a -> List.of(a[0], a[1]))
                .collect(Collectors.toSet());

        long sum = 0;
        for (List<Integer> testcase : input.getNumbers()) {
            boolean valid = isValid(testcase, correct);
            if (valid) {
                sum += testcase.get((testcase.size()+1)/2 -1);
            }
        }

        System.out.println("Answer is " + sum);


    }

    private boolean isValid(List<Integer> numbers, Set<List<Integer>> correct) {
        for (int i = 0; i < numbers.size() - 1; i++) {
            for (int j = i+1; j < numbers.size(); j++) {
                if (!correct.contains(List.of(numbers.get(i), numbers.get(j)))) {
                    return false;
                }
            }
        }
        return true;
    }
}
