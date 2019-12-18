package adventofcode2019.day13;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.is;

public class DayThirteenTest {
    private final DayThirteen task = new DayThirteen();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayThirteenPartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day13/input.txt")), is("326"));
    }

    @Test
    public void testDayThirteenPartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day13/input.txt")), is("15988"));
    }
}