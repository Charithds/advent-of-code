package com.charithds.adventofcode.day8;

import com.charithds.adventofcode.TaskInt;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day8Part2 implements TaskInt {
    private final String DAY8_FILE;

    public Day8Part2(String day8File) {
        if ("sample".equals(day8File)) {
            DAY8_FILE = "resources/day8-sample.dat";
        } else {
            DAY8_FILE = STR."resources/day8.dat";
        }
    }

    public Character[][] getInput() {
        try (var fileReader = new java.io.FileReader(DAY8_FILE);
             var reader = new java.io.BufferedReader(fileReader)) {

            Character[][] map = reader.lines()
                    .map(line -> line.chars()
                            .mapToObj(c -> (char) c)
                            .map(c -> c == '#' ? '.' : c)
                            .toArray(Character[]::new)
                    ).toArray(Character[][]::new);
            return map;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<List<Integer>> getAntinodesPositions(List<Integer> pos1, List<Integer> pos2, int limX, int limY) {
        List<List<Integer>> antinodes = new ArrayList<>();

        int minX = Math.min(pos1.get(0), pos2.get(0));
        int maxX = Math.max(pos1.get(0), pos2.get(0));
        int minY = Math.min(pos1.get(1), pos2.get(1));
        int maxY = Math.max(pos1.get(1), pos2.get(1));

        var dx = Math.abs(pos1.get(0) - pos2.get(0));
        var dy = Math.abs(pos1.get(1) - pos2.get(1));

        var gcd = euclidgcd(dx, dy);
        dx = dx / gcd;
        dy = dy / gcd;
        System.out.println("dx: " + dx + ", dy: " + dy + ", gcd: " + gcd);

        List<Integer> antonode1 = new ArrayList<>(), antonode2 = new ArrayList<>();
        if (pos1.get(0) == minX) {
            antonode1.add(minX - dx);
            antonode2.add(maxX + dx);
        } else {
            antonode1.add(maxX + dx);
            antonode2.add(minX - dx);
        }

        if (pos1.get(1) == minY) {
            antonode1.add(minY - dy);
            antonode2.add(maxY + dy);
        } else {
            antonode1.add(maxY + dy);
            antonode2.add(minY - dy);
        }

        var allNodes = new HashSet<List<Integer>>();
        allNodes.add(pos1);
        allNodes.add(pos2);

        extract(pos1, limX, limY, antonode1, allNodes, dx, dy);
        extract(pos2, limX, limY, antonode2, allNodes, dx, dy);
        return new ArrayList<>(allNodes);
    }

    private static void extract(List<Integer> pos1, int limX, int limY, List<Integer> antonode1,
                                Set<List<Integer>> allNodes, int dx, int dy) {
        var node1 = antonode1;
        while (true) {
            if (node1.get(0) < 0 || node1.get(1) < 0 || node1.get(0) >= limX || node1.get(1) >= limY) {
                break;
            }
            allNodes.add(node1);
            var x = node1.get(0) > pos1.get(0) ? node1.get(0) + dx : node1.get(0) - dx;
            var y = node1.get(1) > pos1.get(1) ? node1.get(1) + dy : node1.get(1) - dy;
            node1 = List.of(x, y);
        }

        var node2 = antonode1;
        while (true) {
            if (node2.get(0) < 0 || node2.get(1) < 0 || node2.get(0) >= limX || node2.get(1) >= limY) {
                break;
            }
            allNodes.add(node2);
            var x = node2.get(0) > pos1.get(0) ? node2.get(0) + dx : node2.get(0) - dx;
            var y = node2.get(1) > pos1.get(1) ? node2.get(1) + dy : node2.get(1) - dy;
            node2 = List.of(x, y);
        }
    }

    private int euclidgcd(int a, int b){
        if(b==0)
            return a;
        int a_rem = a % b;
        return euclidgcd(b, a_rem);
    }

    private int lcm(int a, int b) {
        int gcd=euclidgcd(a, b);
        return (a/gcd*b);
    }

    @Override
    public void execute() {
        Character[][] input = getInput();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                System.out.print(input[i][j]);
            }
            System.out.println();
        }

        Map<Character, List<List<Integer>>> map = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                Character c = input[i][j];
                if (c.equals('.') || c.equals('#')) continue;
                map.putIfAbsent(c, new ArrayList<>());
                map.get(c).add(List.of(i, j));
            }
        }

        System.out.println(map);

        List<List<Integer>> antinodes = new ArrayList<>();
        for (Map.Entry<Character, List<List<Integer>>> entry : map.entrySet()) {
            Character key = entry.getKey();
            List<List<Integer>> positions = entry.getValue();

            Set<List<Integer>> uniqueAntinodes = new HashSet<>();
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i+1; j < positions.size(); j++) {
//                    System.out.println("Processing positions: " + positions.get(i) + " and " + positions.get(j));
                    List<List<Integer>> antinodesPositions = getAntinodesPositions(positions.get(i), positions.get(j), input.length, input[0].length);
//                    System.out.println("Antinodes positions: " + antinodesPositions);
                    uniqueAntinodes.addAll(antinodesPositions);
                }
            }
            antinodes.addAll(uniqueAntinodes);
        }
        var filteredAntiNodes =  antinodes.stream()
                .filter(a -> a.get(0) >= 0 && a.get(1) >= 0)
                .filter(a -> a.get(0) < input.length && a.get(1) < input[0].length)
                .collect(Collectors.toSet());

//        System.out.println(input.length + " x " + input[0].length);
//        System.out.println(antinodes);

        filteredAntiNodes.forEach(a -> {
            input[a.get(0)][a.get(1)] = '#';
        });

        Map<Long, Long> collect = filteredAntiNodes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().collect(Collectors.groupingBy(c -> c.getValue(), Collectors.counting()));
        System.out.println(collect);

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                System.out.print(input[i][j]);
            }
            System.out.println();
        }


//        System.out.println("Antinodes: " + filteredAntiNodes);
        System.out.println("Antinodes count: " + filteredAntiNodes.size());

    }
}
