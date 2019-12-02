package adventofcode2019.day2;

import adventofcode2019.day1.DayOne;
import adventofcode2019.util.IntcodeProgram;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;

public class DayTwoTest {
    private final DayTwo task = new DayTwo();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayTwoPartOneExamples() {
        IntcodeProgram program = IntcodeProgram.parseIntcodeCode("1,9,10,3,2,3,11,0,99,30,40,50");
        program.run();
        assertArrayEquals(new int[] { 3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50 }, program.getMemoryCopy());

        program = IntcodeProgram.parseIntcodeCode("1,0,0,0,99");
        program.run();
        assertArrayEquals(new int[] { 2, 0, 0, 0, 99 }, program.getMemoryCopy());

        program = IntcodeProgram.parseIntcodeCode("2,3,0,3,99");
        program.run();
        assertArrayEquals(new int[] { 2, 3, 0, 6, 99 }, program.getMemoryCopy());

        program = IntcodeProgram.parseIntcodeCode("2,4,4,5,99,0");
        program.run();
        assertArrayEquals(new int[] { 2, 4, 4, 5, 99, 9801 }, program.getMemoryCopy());

        program = IntcodeProgram.parseIntcodeCode("1,1,1,4,99,5,6,0,99");
        program.run();
        assertArrayEquals(new int[] { 30, 1, 1, 4, 2, 5, 6, 0, 99 }, program.getMemoryCopy());
    }

    @Test
    public void testDayTwoPartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day2/input.txt")), is("7594646"));
    }

    @Test
    public void testDayTwoPartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day2/input.txt")), is("3376"));
    }
}
