package adventofcode2019.day10;

import adventofcode2019.DailyTask;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

import static java.lang.Math.*;

/**
 * Day 10: Monitoring Station
 * https://adventofcode.com/2019/day/10
 */
public class DayTen implements DailyTask {
    private static class Asteroid {
        private int x;
        private int y;

        private Asteroid(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private int manhattanDistanceTo(Asteroid other) {
            return abs(x - other.x) + abs(y - other.y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Asteroid asteroid = (Asteroid) o;
            return x == asteroid.x &&
                    y == asteroid.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    @Override
    public String solvePartOne(String input) {
        Set<Asteroid> asteroids = parseAsteroidField(input);

        int maxInLineOfSight = Integer.MIN_VALUE;
        Asteroid bestAsteroid = null;

        for (Asteroid center : asteroids) {
            Multimap<Double, Asteroid> headingToAsteroidMap = getHeadingToAsteroidMap(asteroids, center);
            if (headingToAsteroidMap.keySet().size() > maxInLineOfSight) {
                bestAsteroid = center;
                maxInLineOfSight = headingToAsteroidMap.keySet().size();
            }
        }

        assert bestAsteroid != null;

        // System.out.println("Best asteroid " + bestAsteroid.x + "," + bestAsteroid.y);

        return String.valueOf(maxInLineOfSight);
    }

    @Override
    public String solvePartTwo(String input) {
        // Best location for monitoring station was found in part one
        Asteroid locationOfMonitoringStation = new Asteroid(13,17);
        Set<Asteroid> asteroids = parseAsteroidField(input);
        Multimap<Double, Asteroid> headingToAsteroidMap = getHeadingToAsteroidMap(asteroids, locationOfMonitoringStation);

        List<Double> headingsSorted = new ArrayList<>(headingToAsteroidMap.keySet());
        headingsSorted.sort(Double::compare);

        int headingIndex = headingsSorted.indexOf(0.0);
        int asteroidsZapped = 0;
        while (!asteroids.isEmpty()) {
            Double heading = headingsSorted.get(headingIndex++ % headingsSorted.size());
            if (headingToAsteroidMap.containsKey(heading)) {
                // Closest asteroid is first in the list
                Asteroid target = headingToAsteroidMap.get(heading).iterator().next();

                // Zap it
                headingToAsteroidMap.remove(heading, target);
                asteroidsZapped++;

                if (asteroidsZapped == 200) {
                    return String.valueOf(target.x * 100 + target.y);
                }
            }
        }

        throw new IllegalStateException();
    }

    private Multimap<Double, Asteroid> getHeadingToAsteroidMap(Set<Asteroid> asteroids, Asteroid center) {
        // Sort by distance from center to each asteroid
        List<Asteroid> othersByDistance = new ArrayList<>(asteroids);
        othersByDistance.remove(center);
        othersByDistance.sort(Comparator.comparingInt(center::manhattanDistanceTo));

        Multimap<Double, Asteroid> headingToAsteroidMap = ArrayListMultimap.create();
        for (Asteroid other : othersByDistance) {
            double heading = toDegrees(atan2(other.x - center.x, -(other.y - center.y)));
            headingToAsteroidMap.put(heading, other);
        }

        return headingToAsteroidMap;
    }

    private Set<Asteroid> parseAsteroidField(String input) {
        Set<Asteroid> asteroids = new HashSet<>();

        try (Scanner scanner = new Scanner(input)) {
            int y = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int x = 0; x < line.length(); x++) {
                    if (line.charAt(x) == '#') {
                        asteroids.add(new Asteroid(x, y));
                    }
                }
                y++;
            }
        }

        return asteroids;
    }
}
