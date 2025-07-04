package com.charithds.adventofcode.day9;

import com.charithds.adventofcode.TaskInt;

import java.util.*;

class Disk {

    private final int[] disk;
    private final LinkedList<List<Integer>> fileMap;
    private final LinkedList<List<Integer>> spaceMap;

    Disk(int[] disk, LinkedList<List<Integer>> fileMap, LinkedList<List<Integer>> spaceMap) {
        this.disk = disk;
        this.fileMap = fileMap;
        this.spaceMap = spaceMap;
    }

    public LinkedList<List<Integer>> getFileMap() {
        return fileMap;
    }

    public LinkedList<List<Integer>> getSpaceMap() {
        return spaceMap;
    }

    public int[] getDisk() {
        return disk;
    }
}

public class Day9Part2 implements TaskInt {
    private final String DAY8_FILE;

    public Day9Part2(String day8File) {
        if ("sample".equals(day8File)) {
            DAY8_FILE = "resources/day9-sample.dat";
        } else {
            DAY8_FILE = STR."resources/day9.dat";
        }
    }

    public String getInput() {
        try (var fileReader = new java.io.FileReader(DAY8_FILE);
             var reader = new java.io.BufferedReader(fileReader)) {
            return reader.readLine();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Disk getDisk(String input) {
        int sum = 0;
        for (int i = 0; i < input.length(); i++) {
            int blockSize = input.charAt(i) - 48;
            sum += blockSize;
        }

        int[] bytes = new int[sum];
        LinkedList<List<Integer>> fileMap = new LinkedList<>();
        LinkedList<List<Integer>> spaceMap = new LinkedList<>();

        int pos = 0;
        for (int i = 0; i < input.length(); i++) {
            int blockSize =  input.charAt(i) - 48;
            if (i%2 == 0) {
                // file
                fill(bytes, i/2, pos, blockSize);
                // fileID, starting pos, block sizee
                fileMap.add(List.of(i/2, pos, blockSize));
            } else {
                // space
                fill(bytes, -1, pos, blockSize);
                spaceMap.add(List.of(i/2, pos, blockSize));
            }
            pos += blockSize;
        }
        return new Disk(bytes, fileMap, spaceMap);
    }

    private void fill(int[] bytes, int value, int start, int blockSize) {
        for (int i = 0; i < blockSize; i++) {
            bytes[start + i] = value;
        }
    }

    private void defrag(Disk diskDetails) {
        // move the pointer 1 by one
        // if a matching section is found, add it and move the pointer by the block size
        int[] disk = diskDetails.getDisk();
        LinkedList<List<Integer>> fileMap = diskDetails.getFileMap();
        LinkedList<List<Integer>> spaceMap = diskDetails.getSpaceMap();


        for (int i = 0; i < spaceMap.size(); i++) {
            var pos = spaceMap.get(i).get(1);
            List<Integer> spaceDetails = spaceMap.get(i);
            int fileIterator = fileMap.size() - 1;
            List<Integer> fileDetails = fileMap.get(fileIterator);
            // fileID, starting pos, block sizee

            int spaceToFill = spaceDetails.get(2);
            while (fileDetails.get(1) > pos) {
                Integer fileBlockSize = fileDetails.get(2);
                if (spaceToFill >= fileBlockSize) {
                    fill(disk, fileDetails.get(0), pos, fileBlockSize);
                    fill(disk, -1, fileDetails.get(1), fileBlockSize);
                    fileMap.remove(fileIterator);
                    pos += fileBlockSize;
                    spaceToFill -= fileBlockSize;
                }
                if (spaceToFill <= 0) break;
                fileDetails = fileMap.get(--fileIterator);

            }
        }
    }

    private long calculateHash(int[] disk) {
        long sum = 0;
        for (int i = 0; i < disk.length; i++) {
            if (disk[i] == -1) {
                continue;
            }
            sum += (long) disk[i] * i;
        }
        return sum;
    }

    @Override
    public void execute() {
        String input = getInput();
//        System.out.println("Input: " + input);
        Disk disk = getDisk(input);
        defrag(disk);
        System.out.println("Disk is "+ Arrays.toString(disk.getDisk()));
        System.out.println("Checksum is "+ calculateHash(disk.getDisk()));


    }
}
