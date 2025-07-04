package com.charithds.adventofcode.day11;

import com.charithds.adventofcode.TaskInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 implements TaskInt {
    /*
    stones: straight line, numbered
    blink: changes the stone
        number changes
        or stone split in two

    blink -> each stone follows the rules
        - stone with 0 -> stone with 1
        - even number of digits -> two stones with left half digits, and right half digits
        - else a new stone with cur num * 2024
     */
    private final String DAY11_FILE;

    public Day11(String day8File) {
        if ("sample".equals(day8File)) {
            DAY11_FILE = "resources/day11-sample.dat";
        } else {
            DAY11_FILE = STR."resources/day11.dat";
        }
    }

    private Map<Long, Long> getInput() {
        try (var fileReader = new FileReader(DAY11_FILE);
             var reader = new BufferedReader(fileReader)) {
            return Arrays.stream(reader.readLine().split(" ")).map(Long::parseLong)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<Long, Long> applyRules(Map<Long, Long> array) {
        Map<Long, Long> newArray = new HashMap<>();
        Set<Map.Entry<Long, Long>> entries = new HashSet<>(array.entrySet());
        Iterator<Map.Entry<Long, Long>> iterator = entries.iterator();

        while (iterator.hasNext()) {
            Map.Entry<Long, Long> next = iterator.next();
            Long element = next.getKey();
            if (element == 0L) {
                if (newArray.containsKey(1L)) {
                    newArray.put(1L, newArray.get(1L) + next.getValue());
                } else {
                    newArray.putIfAbsent(1L, next.getValue());
                }
            } else if (String.valueOf(element).length()%2 == 0) {
                String elementSt = String.valueOf(element);
                Long left = Long.parseLong(elementSt.substring(0, elementSt.length() / 2 ));
                Long right = Long.parseLong(elementSt.substring(elementSt.length() / 2));

                newArray.putIfAbsent(left, 0L);
                newArray.putIfAbsent(right, 0L);

                newArray.put(left, newArray.get(left) + next.getValue());
                newArray.put(right, newArray.get(right) + next.getValue());

            } else {
                long l = element * 2024L;
                newArray.putIfAbsent(l, 0L);
                newArray.put(l, newArray.get(l) + next.getValue());
            }
        }
//
//        for (int i = 0; i < array.size(); i++) {
//            Long element = array.get(i);
//            if (element == 0) {
//
////                array.remove(i);
//                newArray.add(1L);
//                newArray.putIfAbsent(1L, 0);
//
//            } else if (String.valueOf(element).length()%2 == 0) {
//                String elementSt = String.valueOf(element);
//                String left = elementSt.substring(0, elementSt.length() / 2 );
//                String right = elementSt.substring(elementSt.length() / 2);
////                System.out.println("element/left/right "+ element + "/" +left + "/" + right);
////                array.remove(i);
//                newArray.add(Long.parseLong(left));
//                newArray.add(Long.parseLong(right));
//
//            } else {
////                array.remove(i);
//                newArray.add(element*2024L);
//            }
//        }
        return newArray;
    }

    @Override
    public void execute() {
        Map<Long, Long> input = getInput();
        for (int i = 0; i < 75; i++) {
            input = applyRules(input);
        }

        long sum = input.values().stream().mapToLong(Long::valueOf).sum();

        System.out.println(sum);

    }
}
