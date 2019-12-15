package adventofcode2019.day13;

import adventofcode2019.DailyTask;
import adventofcode2019.util.IntcodeComputer;

import static adventofcode2019.util.IntcodeComputer.END_OF_OUTPUT;

/**
 * Day 13: Care Package
 * https://adventofcode.com/2019/day/13
 */
public class DayThirteen implements DailyTask {
    private static final long TILE_BLOCK = 2L;
    private static final long TILE_HORIZONTAL_PADDLE = 3L;
    private static final long TILE_BALL = 4L;

    @Override
    public String solvePartOne(String input) {
        IntcodeComputer robot = IntcodeComputer.parseIntcodeCode(input);

        try {
            robot.start();

            int index = 0;
            int blockTiles = 0;

            while (true) {
                long output = robot.read();
                if (output == END_OF_OUTPUT) {
                    break;
                }
                if (index % 3 == 2 && output == TILE_BLOCK) {
                    blockTiles++;
                }
                index++;
            }

            return String.valueOf(blockTiles);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Intcode computation was interrupted", e);
        }
    }

    @Override
    public String solvePartTwo(String input) {
        IntcodeComputer robot = IntcodeComputer.parseIntcodeCode(input);

        robot.setMemory(0, 2L);

        try {
            robot.start();

            long playerScore = 0;

            long paddleX = 0L;
            long ballX;

            while (true) {
                long x = robot.read();
                if (x == END_OF_OUTPUT) {
                    break;
                }
                long y = robot.read();
                long val = robot.read();

                if (x == -1L && y == 0L) {
                    playerScore = val;
                } else {
                    if (val == TILE_BALL) {
                        ballX = x;

                        if (paddleX < ballX) {
                            robot.write(1L);
                        } else if (paddleX > ballX) {
                            robot.write(-1L);
                        } else {
                            robot.write(0L);
                        }
                    } else if (val == TILE_HORIZONTAL_PADDLE) {
                        paddleX = x;
                    }
                }
            }

            return String.valueOf(playerScore);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Intcode computation was interrupted", e);
        }
    }
}
