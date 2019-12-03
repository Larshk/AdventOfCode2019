package adventofcode2019.day3;

import adventofcode2019.DailyTask;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static adventofcode2019.util.InputUtil.inputLines;

/**
 * Day 3: Crossed Wires
 * https://adventofcode.com/2019/day/3
 */
public class DayThree implements DailyTask {
    @Override
    public String solvePartOne(String input) {
        Set<Position> points = new HashSet<>();

        List<Set<Position>> wires = inputLines(input, this::mapPoints).collect(Collectors.toList());

        Set<Position> intersections = wires.get(0);
        intersections.retainAll(wires.get(1));
        intersections.remove(new Position(0, 0));

        int minDistance = Integer.MAX_VALUE;

        for (Position intersection : intersections) {
            minDistance = Math.min(minDistance, intersection.manhattanDistanceTo(0, 0));
        }

        return String.valueOf(minDistance);
    }

    private Set<Position> mapPoints(String s) {
        String[] instructions = s.split(",");
        Set<Position> positions = new HashSet<>();
        positions.add(new Position(0, 0));

        int x = 0;
        int y = 0;

        for (String instruction : instructions) {
            switch (instruction.charAt(0)) {
                case 'U':
                    for (int i = 0; i < Integer.parseInt(instruction.substring(1)); i++) {
                        y--;
                        positions.add(new Position(x, y));
                    }
                    break;
                case 'D':
                    for (int i = 0; i < Integer.parseInt(instruction.substring(1)); i++) {
                        y++;
                        positions.add(new Position(x, y));
                    }
                    break;
                case 'L':
                    for (int i = 0; i < Integer.parseInt(instruction.substring(1)); i++) {
                        x--;
                        positions.add(new Position(x, y));
                    }
                    break;
                case 'R':
                    for (int i = 0; i < Integer.parseInt(instruction.substring(1)); i++) {
                        x++;
                        positions.add(new Position(x, y));
                    }
                    break;
            }
        }

        return positions;
    }

    @Override
    public String solvePartTwo(String input) {
        throw new IllegalStateException("No solution found");
    }
}
