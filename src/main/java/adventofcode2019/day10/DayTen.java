package adventofcode2019.day10;

import adventofcode2019.DailyTask;
import adventofcode2019.util.EuclideanPoint;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;

/**
 * Day 10: Monitoring Station
 * https://adventofcode.com/2019/day/10
 */
public class DayTen implements DailyTask {
    @Override
    public String solvePartOne(String input) {
        Set<EuclideanPoint> EuclideanPoints = parseEuclideanPointField(input);

        int maxInLineOfSight = Integer.MIN_VALUE;
        EuclideanPoint bestEuclideanPoint = null;

        for (EuclideanPoint center : EuclideanPoints) {
            Multimap<Double, EuclideanPoint> headingToEuclideanPointMap = getHeadingToEuclideanPointMap(EuclideanPoints, center);
            if (headingToEuclideanPointMap.keySet().size() > maxInLineOfSight) {
                bestEuclideanPoint = center;
                maxInLineOfSight = headingToEuclideanPointMap.keySet().size();
            }
        }

        assert bestEuclideanPoint != null;

        // System.out.println("Best EuclideanPoint " + bestEuclideanPoint.x + "," + bestEuclideanPoint.y);

        return String.valueOf(maxInLineOfSight);
    }

    @Override
    public String solvePartTwo(String input) {
        // Best location for monitoring station was found in part one
        EuclideanPoint locationOfMonitoringStation = new EuclideanPoint(13,17);
        Set<EuclideanPoint> EuclideanPoints = parseEuclideanPointField(input);
        Multimap<Double, EuclideanPoint> headingToEuclideanPointMap = getHeadingToEuclideanPointMap(EuclideanPoints, locationOfMonitoringStation);

        List<Double> headingsSorted = new ArrayList<>(headingToEuclideanPointMap.keySet());
        headingsSorted.sort(Double::compare);

        int headingIndex = headingsSorted.indexOf(0.0);
        int EuclideanPointsZapped = 0;
        while (!EuclideanPoints.isEmpty()) {
            Double heading = headingsSorted.get(headingIndex++ % headingsSorted.size());
            if (headingToEuclideanPointMap.containsKey(heading)) {
                // Closest EuclideanPoint is first in the list
                EuclideanPoint target = headingToEuclideanPointMap.get(heading).iterator().next();

                // Zap it
                headingToEuclideanPointMap.remove(heading, target);
                EuclideanPointsZapped++;

                if (EuclideanPointsZapped == 200) {
                    return String.valueOf(target.x * 100 + target.y);
                }
            }
        }

        throw new IllegalStateException();
    }

    private Multimap<Double, EuclideanPoint> getHeadingToEuclideanPointMap(Set<EuclideanPoint> EuclideanPoints, EuclideanPoint center) {
        // Sort by distance from center to each EuclideanPoint
        List<EuclideanPoint> othersByDistance = new ArrayList<>(EuclideanPoints);
        othersByDistance.remove(center);
        othersByDistance.sort(Comparator.comparingInt(center::manhattanDistanceTo));

        Multimap<Double, EuclideanPoint> headingToEuclideanPointMap = ArrayListMultimap.create();
        for (EuclideanPoint other : othersByDistance) {
            double heading = toDegrees(atan2(other.x - center.x, -(other.y - center.y)));
            headingToEuclideanPointMap.put(heading, other);
        }

        return headingToEuclideanPointMap;
    }

    private Set<EuclideanPoint> parseEuclideanPointField(String input) {
        Set<EuclideanPoint> EuclideanPoints = new HashSet<>();

        try (Scanner scanner = new Scanner(input)) {
            int y = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int x = 0; x < line.length(); x++) {
                    if (line.charAt(x) == '#') {
                        EuclideanPoints.add(new EuclideanPoint(x, y));
                    }
                }
                y++;
            }
        }

        return EuclideanPoints;
    }
}
