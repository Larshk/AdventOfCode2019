package adventofcode2019.day11;

import adventofcode2019.DailyTask;
import adventofcode2019.util.EuclideanPoint;
import adventofcode2019.util.IntcodeComputer;

import java.util.HashMap;
import java.util.Map;

import static adventofcode2019.util.IntcodeComputer.END_OF_OUTPUT;

/**
 * Day 11: Space Police
 * https://adventofcode.com/2019/day/11
 */
public class DayEleven implements DailyTask {
    private enum Direction {
        UP, RIGHT, DOWN, LEFT;

        private Direction toLeft() {
            return values()[(ordinal() - 1 + values().length) % values().length];
        }

        private Direction toRight() {
            return values()[(ordinal() + 1) % values().length];
        }
    }

    private static class PanelGrid {
        private Map<EuclideanPoint, Long> panelToColorMap = new HashMap<>();
        private EuclideanPoint robotPosition = new EuclideanPoint(0, 0);
        private Direction robotDirection = Direction.UP;

        private long readColor() {
            return panelToColorMap.getOrDefault(robotPosition, 0L);
        }

        private void paint(long color) {
            panelToColorMap.put(robotPosition, color);
        }

        public void turn(long directionToTurn) {
            if (directionToTurn == 0L) {
                robotDirection = robotDirection.toLeft();
            } else if (directionToTurn == 1L) {
                robotDirection = robotDirection.toRight();
            }
        }

        public void advance() {
            switch(robotDirection) {
                case UP:
                    robotPosition = new EuclideanPoint(robotPosition.x, robotPosition.y - 1);
                    break;
                case RIGHT:
                    robotPosition = new EuclideanPoint(robotPosition.x + 1, robotPosition.y);
                    break;
                case DOWN:
                    robotPosition = new EuclideanPoint(robotPosition.x, robotPosition.y + 1);
                    break;
                case LEFT:
                    robotPosition = new EuclideanPoint(robotPosition.x - 1, robotPosition.y);
                    break;
            }
        }

        public String render() {
            int minX = panelToColorMap.keySet().stream()
                    .map(p -> p.x)
                    .reduce(Integer::min)
                    .orElseThrow(IllegalStateException::new);
            int maxX = panelToColorMap.keySet().stream()
                    .map(p -> p.x)
                    .reduce(Integer::max)
                    .orElseThrow(IllegalStateException::new);
            int minY = panelToColorMap.keySet().stream()
                    .map(p -> p.y)
                    .reduce(Integer::min)
                    .orElseThrow(IllegalStateException::new);
            int maxY = panelToColorMap.keySet().stream()
                    .map(p -> p.y)
                    .reduce(Integer::max)
                    .orElseThrow(IllegalStateException::new);

            StringBuilder sb = new StringBuilder();

            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    sb.append(panelToColorMap.getOrDefault(new EuclideanPoint(x, y), 0L) == 0 ? "." : "#");
                }
                sb.append("\n");
            }

            return sb.toString();
        }
    }

    @Override
    public String solvePartOne(String input) {
        PanelGrid panels = new PanelGrid();

        IntcodeComputer robot = IntcodeComputer.parseIntcodeCode(input);

        try {
            robot.start();

            while (!robot.hasTerminated()) {
                robot.write(panels.readColor());
                long colorToPaint = robot.read();
                if (colorToPaint == END_OF_OUTPUT) {
                    break;
                }
                long directionToTurn = robot.read();

                panels.paint(colorToPaint);
                panels.turn(directionToTurn);
                panels.advance();

                // System.out.println("Painted");
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("Intcode computation was interrupted", e);
        }

        return String.valueOf(panels.panelToColorMap.keySet().size());
    }

    @Override
    public String solvePartTwo(String input) {
        PanelGrid panels = new PanelGrid();
        panels.paint(1L);

        IntcodeComputer robot = IntcodeComputer.parseIntcodeCode(input);

        try {
            robot.start();

            while (!robot.hasTerminated()) {
                robot.write(panels.readColor());
                long colorToPaint = robot.read();
                if (colorToPaint == END_OF_OUTPUT) {
                    break;
                }
                long directionToTurn = robot.read();

                panels.paint(colorToPaint);
                panels.turn(directionToTurn);
                panels.advance();

                // System.out.println("Painted");
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("Intcode computation was interrupted", e);
        }

        return panels.render();
    }
}
