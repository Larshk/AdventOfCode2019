package adventofcode2019.day6;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.is;

public class DaySixTest {
    private final DaySix task = new DaySix();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDaySixPartOneExample() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day6/inputExamplePartOne.txt")), is("42"));
    }

    @Test
    public void testDaySixPartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day6/input.txt")), is("344238"));
    }

    @Test
    public void testDaySixPartTwoExample() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day6/inputExamplePartTwo.txt")), is("4"));
    }

    @Test
    public void testDaySixPartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day6/input.txt")), is("436"));
    }
}