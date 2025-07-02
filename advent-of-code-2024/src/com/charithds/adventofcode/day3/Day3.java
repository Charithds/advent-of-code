package com.charithds.adventofcode.day3;

import com.charithds.adventofcode.TaskInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 implements TaskInt {
    private static final String DAY3_FILE = "resources/day3.dat";

    public String getInput() {
        try (FileReader fileReader = new FileReader(DAY3_FILE);
            BufferedReader reader = new BufferedReader(fileReader)){
            return reader.lines().collect(Collectors.joining("\n"));
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
//        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        String input = getInput();
        Matcher matcher = pattern.matcher(input);

        long sum = 0;
        while (matcher.find()) {
            sum += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
        }
        System.out.println("Answer is " + sum);
    }

}
