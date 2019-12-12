package adventofcode2019.day12;

import adventofcode2019.day12.DayTwelve.Moon;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.is;

public class DayTwelveTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayTwelvePartOneExamples() {
        Moon io = new Moon(-1, 0, 2);
        Moon europa = new Moon(2, -10, -7);
        Moon ganymede = new Moon(4, -8, 8);
        Moon callisto = new Moon(3, 5, -1);

        collector.checkThat(new DayTwelve().solvePartOne(new Moon[] { io, europa, ganymede, callisto }, 10), is("179"));

        io = new Moon(-8, -10, 0);
        europa = new Moon(5, 5, 10);
        ganymede = new Moon(2, -7, 3);
        callisto = new Moon(9, -8, -3);

        collector.checkThat(new DayTwelve().solvePartOne(new Moon[] { io, europa, ganymede, callisto }, 100), is("1940"));
    }

    @Test
    public void testDayTwelvePartOneSolution() {
        collector.checkThat(new DayTwelve().solvePartOne(""), is("10664"));
    }

    @Test
    public void testDayTwelvePartTwoSolution() {
        collector.checkThat(new DayTwelve().solvePartTwo(""), is("303459551979256"));
    }
}