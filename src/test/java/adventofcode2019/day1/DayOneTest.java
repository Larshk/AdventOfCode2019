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
        collector.checkThat(task.solvePartOne("12"), is("2"));
        collector.checkThat(task.solvePartOne("14"), is("2"));
        collector.checkThat(task.solvePartOne("1969"), is("654"));
        collector.checkThat(task.solvePartOne("100756"), is("33583"));
        collector.checkThat(task.solvePartOne("12\n14\n1969\n100756"), is("34241"));
    }

    @Test
    public void testDayOnePartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day1/input.txt")), is("3297909"));
    }

    @Test
    public void testDayOnePartTwoExamples() {
        collector.checkThat(task.solvePartTwo("14"), is("2"));
        collector.checkThat(task.solvePartTwo("1969"), is("966"));
        collector.checkThat(task.solvePartTwo("100756"), is("50346"));
    }

    @Test
    public void testDayOnePartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day1/input.txt")), is("4943994"));
    }
}
