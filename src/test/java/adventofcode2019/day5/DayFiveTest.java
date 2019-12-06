package adventofcode2019.day5;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.is;

public class DayFiveTest {
    private final DayFive task = new DayFive();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayFivePartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day5/input.txt")), is("9025675"));
    }

    @Test
    public void testDayFivePartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day5/input.txt")), is("11981754"));
    }
}