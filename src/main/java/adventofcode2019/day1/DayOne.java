package adventofcode2019.day1;

import adventofcode2019.DailyTask;

import java.util.function.Function;

import static adventofcode2019.util.InputUtil.inputLines;
import static java.lang.Math.max;

/**
 * Day 1: The Tyranny of the Rocket Equation
 * https://adventofcode.com/2019/day/1
 */
public class DayOne implements DailyTask {
    @Override
    public String solvePartOne(String input) {
        return solve(input, DayOne::fuelForMass);
    }

    @Override
    public String solvePartTwo(String input) {
        return solve(input, DayOne::fuelForMassAndFuel);
    }

    private String solve(String input, Function<Long, Long> fuelFunction) {
        return inputLines(input, Long::valueOf)
                .map(fuelFunction)
                .reduce(Long::sum)
                .map(String::valueOf)
                .orElseThrow(IllegalStateException::new);
    }

    private static long fuelForMass(long mass) {
        return mass / 3 - 2;
    }

    private static long fuelForMassAndFuel(long mass) {
        long fuel = fuelForMass(mass);
        if (fuel >= 0) {
            fuel += fuelForMassAndFuel(fuel);
        }
        return max(0, fuel);
    }
}
