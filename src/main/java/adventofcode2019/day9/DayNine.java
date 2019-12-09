package adventofcode2019.day9;

import adventofcode2019.DailyTask;
import adventofcode2019.util.IntcodeComputer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Collectors;

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
        BlockingQueue<Long> inputQueue = new LinkedTransferQueue<>();
        BlockingQueue<Long> outputQueue = new LinkedTransferQueue<>();
        IntcodeComputer computer = IntcodeComputer.parseIntcodeCode(
                inputProgram,
                inputQueue,
                outputQueue);

        computer.start();

        inputQueue.offer(inputData);

        try {
            computer.join();
            return outputQueue.stream().map(String::valueOf).collect(Collectors.joining(","));
        } catch (InterruptedException e) {
            throw new IllegalStateException("Interrupted", e);
        }
    }

}
