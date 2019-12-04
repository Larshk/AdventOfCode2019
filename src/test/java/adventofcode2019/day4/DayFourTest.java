package adventofcode2019.day4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.is;

public class DayFourTest {
    private final DayFour task = new DayFour();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayFourPartOneSolution() {
        collector.checkThat(task.solvePartOne("171309-643603"), is("1625"));
    }

    @Test
    public void testDayFourPartTwoSolution() {
        collector.checkThat(task.solvePartTwo("171309-643603"), is("1111"));
    }
}
