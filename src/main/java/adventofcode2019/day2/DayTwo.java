package adventofcode2019.day2;

import adventofcode2019.DailyTask;
import adventofcode2019.util.IntcodeProgram;

/**
 * Day 2: 1202 Program Alarm
 * https://adventofcode.com/2019/day/2
 */
public class DayTwo implements DailyTask {
    @Override
    public String solvePartOne(String input) {
        IntcodeProgram program = IntcodeProgram.parseIntcodeCode(input);

        program.setNoun(12);
        program.setVerb(2);
        program.run();

        return String.valueOf(program.peek(0));
    }

    @Override
    public String solvePartTwo(String input) {
        int target = 19690720;

        IntcodeProgram program = IntcodeProgram.parseIntcodeCode(input);

        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                program.reset();
                program.setNoun(noun);
                program.setVerb(verb);
                program.run();
                if (program.peek(0) == target) {
                    return String.valueOf(noun * 100 + verb);
                }
            }
        }

        throw new IllegalStateException("No solution found");
    }
}
