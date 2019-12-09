package adventofcode2019.day9;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.is;

public class DayNineTest {
    private final DayNine task = new DayNine();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayNinePartOneExamples() {
        collector.checkThat(task.solvePartOne("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"), is("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"));
        collector.checkThat(task.solvePartOne("1102,34915192,34915192,7,4,7,99,0"), is("1219070632396864"));
        collector.checkThat(task.solvePartOne("104,1125899906842624,99"), is("1125899906842624"));
    }

    @Test
    public void testDayNinePartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day9/input.txt")), is("4261108180"));
    }

    @Test
    public void testDayNinePartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day9/input.txt")), is("77944"));
    }
}