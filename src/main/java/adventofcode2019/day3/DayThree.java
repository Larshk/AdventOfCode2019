package adventofcode2019.day3;

import adventofcode2019.DailyTask;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static adventofcode2019.util.InputUtil.inputLines;

/**
 * Day 3: Crossed Wires
 * https://adventofcode.com/2019/day/3
 * <p>
 * Not the nicest solution - not much time today!
 */
public class DayThree implements DailyTask {
    @Override
    public String solvePartOne(String input) {
        return solve(input, (intersections, wires) -> intersections.stream()
                .map(i -> i.manhattanDistanceTo(0, 0))
                .reduce(Integer::min)
                .orElseThrow(IllegalStateException::new));
    }

    @Override
    public String solvePartTwo(String input) {
        return solve(input, (intersections, wires) -> intersections.stream()
                .map(i -> wires.get(0).get(i).getStep() + wires.get(1).get(i).getStep())
                .reduce(Integer::min)
                .orElseThrow(IllegalStateException::new));
    }

    private String solve(String input, BiFunction<Set<Position>, List<Map<Position, Position>>, Integer> minimumFunction) {
        List<Map<Position, Position>> wires = inputLines(input, this::mapPoints).collect(Collectors.toList());

        Set<Position> intersections = new HashSet<>(wires.get(0).keySet());
        intersections.retainAll(wires.get(1).keySet());
        intersections.remove(new Position(0, 0, 0));

        return String.valueOf(minimumFunction.apply(intersections, wires));
    }

    private Map<Position, Position> mapPoints(String s) {
        String[] instructions = s.split(",");

        // Must use a Map because Java's Set doesn't support get() needed for part 2
        Map<Position, Position> positions = new HashMap<>();

        Position centerPosition = new Position(0, 0, 0);
        positions.put(centerPosition, centerPosition);

        int x = 0;
        int y = 0;
        int step = 0;

        for (String instruction : instructions) {
            char direction = instruction.charAt(0);

            for (int i = 0; i < Integer.parseInt(instruction.substring(1)); i++) {
                switch (direction) {
                    case 'U':
                        y--;
                        break;
                    case 'D':
                        y++;
                        break;
                    case 'L':
                        x--;
                        break;
                    case 'R':
                        x++;
                        break;
                }

                step++;

                Position position = new Position(x, y, step);
                positions.put(position, position);
            }
        }

        return positions;
    }
}
