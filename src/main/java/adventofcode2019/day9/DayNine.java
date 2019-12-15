package adventofcode2019.day9;

import adventofcode2019.DailyTask;
import adventofcode2019.util.IntcodeComputer;

import java.util.stream.Collectors;

import static adventofcode2019.util.IntcodeComputer.END_OF_OUTPUT;

/**
 * Day 9: Sensor Boost
 * https://adventofcode.com/2019/day/9
 */
public class DayNine implements DailyTask {
    @Override
    public String solvePartOne(String input) {
        return solve(input, 1L);
    }

    @Override
    public String solvePartTwo(String input) {
        return solve(input, 2L);
    }

    private String solve(String inputProgram, long inputData) {
        IntcodeComputer computer = IntcodeComputer.parseIntcodeCode(inputProgram);

        computer.start();
        computer.write(inputData);

        try {
            computer.join();
            return computer.stream()
                    .filter(l -> l != END_OF_OUTPUT)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
        } catch (InterruptedException e) {
            throw new IllegalStateException("Interrupted", e);
        }
    }
}
