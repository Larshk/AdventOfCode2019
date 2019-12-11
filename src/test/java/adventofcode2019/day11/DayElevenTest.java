package adventofcode2019.day11;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.is;

public class DayElevenTest {
    private final DayEleven task = new DayEleven();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayNinePartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day11/input.txt")), is("2172"));
    }

    @Test
    public void testDayNinePartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day11/input.txt")), is(
                "...##.####.#....####.####..##..#..#.###....\n" +
                "....#.#....#....#....#....#..#.#..#.#..#...\n" +
                "....#.###..#....###..###..#....####.#..#...\n" +
                "....#.#....#....#....#....#.##.#..#.###....\n" +
                ".#..#.#....#....#....#....#..#.#..#.#......\n" +
                "..##..####.####.####.#.....###.#..#.#......\n"));
    }
}