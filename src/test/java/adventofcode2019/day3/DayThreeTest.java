package adventofcode2019.day3;

import adventofcode2019.day2.DayTwo;
import adventofcode2019.util.IntcodeProgram;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;

public class DayThreeTest {
    private final DayThree task = new DayThree();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayThreePartOneExamples() {
        collector.checkThat(task.solvePartOne(
                "R75,D30,R83,U83,L12,D49,R71,U7,L72\n" +
                        "U62,R66,U55,R34,D71,R55,D58,R83"), is("159"));
        collector.checkThat(task.solvePartOne(
                "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" +
                        "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"), is("135"));
    }

    @Test
    @Ignore
    public void testDayThreePartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day3/input.txt")), is("1337"));
    }

    @Test
    @Ignore
    public void testDayThreePartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day3/input.txt")), is("1337"));
    }
}
