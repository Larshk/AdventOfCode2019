package adventofcode2019.day7;

import adventofcode2019.DailyTask;
import adventofcode2019.util.IntcodeComputer;
import adventofcode2019.util.IntcodeProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

import static org.paukov.combinatorics3.Generator.permutation;

/**
 * Day 7: Amplification Circuit
 * https://adventofcode.com/2019/day/7
 */
public class DaySeven implements DailyTask {
    private List<IntcodeProgram> programs;

    @Override
    public String solvePartOne(String amplifierControlSoftware) {
        programs = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            programs.add(IntcodeProgram.parseIntcodeCode(amplifierControlSoftware));
        }

        return String.valueOf(
                permutation(0, 1, 2, 3, 4)
                    .simple()
                    .stream()
                    .map(this::calculateThrusterSignal)
                    .reduce(Integer::max)
                    .orElseThrow(IllegalStateException::new)
        );
    }

    @Override
    public String solvePartTwo(String amplifierControlSoftware) {
        return String.valueOf(
            permutation(5, 6, 7, 8, 9)
                    .simple()
                    .stream()
                    .map(permutation -> {
                        try {
                            return calculateThrusterSignalWithFeedbackLoop(
                                    amplifierControlSoftware,
                                    permutation);
                        } catch (InterruptedException e) {
                            throw new IllegalStateException("Interrupted", e);
                        }
                    })
                    .reduce(Long::max)
                    .orElseThrow(IllegalStateException::new)
        );
    }

    private int calculateThrusterSignal(List<Integer> phaseSettings) {
        programs.forEach(IntcodeProgram::reset);

        int output = 0;
        for (int i = 0; i < 5; i++) {
            output = programs.get(i).run(new int[] { phaseSettings.get(i), output })[0];
        }

        return output;
    }

    private long calculateThrusterSignalWithFeedbackLoop(
            String amplifierControlSoftware,
            List<Integer> phaseSettings) throws InterruptedException {
        List<BlockingQueue<Long>> ioQueues = new ArrayList<>();
        List<IntcodeComputer> computers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ioQueues.add(new LinkedTransferQueue<>());
        }

        for (int i = 0; i < 5; i++) {
            BlockingQueue<Long> input = ioQueues.get(i);
            BlockingQueue<Long> output = ioQueues.get(i + 1 > 4 ? 0 : i + 1);
            computers.add(IntcodeComputer.parseIntcodeCode(
                    amplifierControlSoftware,
                    input,
                    output));
        }

        // System.out.println("Phase settings " + phaseSettings);

        // Provide each amplifier its phase setting at its first input instruction
        for (int i = 0; i < 5; i++) {
            ioQueues.get(i).offer((long) phaseSettings.get(i));
        }

        // To start the process, a 0 signal is sent to amplifier A's input exactly once
        ioQueues.get(0).offer(0L);

        // Start the computers
        computers.forEach(Thread::start);

        for (IntcodeComputer computer : computers) {
            computer.join();
        }

        return computers.get(4).getLastOutput();
    }
}
