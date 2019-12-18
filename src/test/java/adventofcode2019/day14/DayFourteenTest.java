package adventofcode2019.day14;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.is;

public class DayFourteenTest {
    private final DayFourteen task = new DayFourteen();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayFourteenPartOneExamples() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day14/inputExampleOne.txt")), is("31"));
        collector.checkThat(task.solvePartOne(getInput("/day14/inputExampleTwo.txt")), is("165"));
        collector.checkThat(task.solvePartOne(getInput("/day14/inputExampleThree.txt")), is("13312"));
        collector.checkThat(task.solvePartOne(getInput("/day14/inputExampleFour.txt")), is("180697"));
        collector.checkThat(task.solvePartOne(getInput("/day14/inputExampleFive.txt")), is("2210736"));
    }

    @Test
    public void testDayFourteenPartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day14/input.txt")), is("319014"));
    }

    @Test
    public void testDayFourteenPartTwoExamples() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day14/inputExampleThree.txt")), is("82892753"));
        collector.checkThat(task.solvePartTwo(getInput("/day14/inputExampleFour.txt")), is("5586022"));
        collector.checkThat(task.solvePartTwo(getInput("/day14/inputExampleFive.txt")), is("460664"));
    }

    @Test
    public void testDayFourteenPartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day14/input.txt")), is("4076490"));
    }
}