package adventofcode2019.day5;

import adventofcode2019.DailyTask;
import adventofcode2019.util.IntcodeProgram;

/**
 * Day 5: Sunny with a Chance of Asteroids
 * https://adventofcode.com/2019/day/5
 */
public class DayFive implements DailyTask {
    @Override
    public String solvePartOne(String input) {
        return solve(input, new int[] { 1 });
    }

    @Override
    public String solvePartTwo(String input) {
        return solve(input, new int[] { 5 });
    }

    private String solve(String input, int[] programInput) {
        IntcodeProgram program = IntcodeProgram.parseIntcodeCode(input);
        int[] output = program.run(programInput);
        return String.valueOf(output[output.length - 1]);
    }
}
