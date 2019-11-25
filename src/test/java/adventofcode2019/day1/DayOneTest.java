package adventofcode2019.day1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.is;

public class DayOneTest {
    private final DayOne task = new DayOne();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayOnePartOneExamples() {
        collector.checkThat(task.solvePartOne("input"), is("input"));
    }

    @Test
    public void testDayOnePartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day1/input.txt")), is("input"));
    }

    @Test
    public void testDayOnePartTwoExamples() {
        collector.checkThat(task.solvePartTwo("input"), is("input"));
    }

    @Test
    public void testDayOnePartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day1/input.txt")), is("input"));
    }
}
