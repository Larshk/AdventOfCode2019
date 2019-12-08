package adventofcode2019.day8;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.is;

public class DayEightTest {
    private final DayEight task = new DayEight();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayEightPartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day8/input.txt")), is("2806"));
    }

    @Test
    public void testDayEightPartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day8/input.txt")), is(
                "#### ###    ##  ##  ###  \n" +
                "   # #  #    # #  # #  # \n" +
                "  #  ###     # #  # ###  \n" +
                " #   #  #    # #### #  # \n" +
                "#    #  # #  # #  # #  # \n" +
                "#### ###   ##  #  # ###  \n"));
    }
}