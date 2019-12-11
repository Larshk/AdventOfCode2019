package adventofcode2019.day10;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;

import static adventofcode2019.InputReader.getInput;
import static org.hamcrest.CoreMatchers.is;

public class DayTenTest {
    private final DayTen task = new DayTen();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testDayNinePartOneExamples() {
        collector.checkThat(task.solvePartOne(
                ".#..#\n" +
                ".....\n" +
                "#####\n" +
                "....#\n" +
                "...##\n"), is("8"));

        collector.checkThat(task.solvePartOne(
                "......#.#.\n" +
                "#..#.#....\n" +
                "..#######.\n" +
                ".#.#.###..\n" +
                ".#..#.....\n" +
                "..#....#.#\n" +
                "#..#....#.\n" +
                ".##.#..###\n" +
                "##...#..#.\n" +
                ".#....####\n"), is("33"));

        collector.checkThat(task.solvePartOne(
                "#.#...#.#.\n" +
                ".###....#.\n" +
                ".#....#...\n" +
                "##.#.#.#.#\n" +
                "....#.#.#.\n" +
                ".##..###.#\n" +
                "..#...##..\n" +
                "..##....##\n" +
                "......#...\n" +
                ".####.###.\n"), is("35"));

        collector.checkThat(task.solvePartOne(
                ".#..#..###\n" +
                "####.###.#\n" +
                "....###.#.\n" +
                "..###.##.#\n" +
                "##.##.#.#.\n" +
                "....###..#\n" +
                "..#.#..#.#\n" +
                "#..#.#.###\n" +
                ".##...##.#\n" +
                ".....#.#..\n"), is("41"));

        collector.checkThat(task.solvePartOne(
                ".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##\n"), is("210"));
    }

    @Test
    public void testDayNinePartOneSolution() throws IOException {
        collector.checkThat(task.solvePartOne(getInput("/day10/input.txt")), is("269"));
    }

    @Test
    public void testDayNinePartTwoSolution() throws IOException {
        collector.checkThat(task.solvePartTwo(getInput("/day10/input.txt")), is("612"));
    }
}