package adventofcode2019.day12;

import adventofcode2019.DailyTask;

import java.math.BigInteger;
import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * Day 12: The N-Body Problem
 * https://adventofcode.com/2019/day/12
 *
 * The trick to understanding part II is that the axes are independent
 * and we can find the cycle period by finding the least common multiple
 * of the axes cycle periods.
 */
public class DayTwelve implements DailyTask {
    private final Moon io = new Moon(-16, 15, -9);
    private final Moon europa = new Moon(-14, 5, 4);
    private final Moon ganymede = new Moon(2, 0, 6);
    private final Moon callisto = new Moon(-3, 18, 9);

    public static class Moon {
        private long[] originalPos;
        private long[] pos;
        private long[] vel;

        public Moon(long x, long y, long z) {
            this.originalPos = new long[] { x, y, z };
            this.pos = new long[] { x, y, z };
            this.vel = new long[] { 0, 0, 0 };
        }

        private void applyGravity(Moon other) {
            for (int i = 0; i < 3; i++) {
                if (pos[i] != other.pos[i]) {
                    if (pos[i] < other.pos[i]) {
                        vel[i]++;
                        other.vel[i]--;
                    } else {
                        vel[i]--;
                        other.vel[i]++;
                    }
                }
            }
        }

        private void applyVelocity() {
            for (int i = 0; i < 3; i++) {
                pos[i] += vel[i];
            }
        }

        private long totalEnergy() {
            return (abs(pos[0]) + abs(pos[1]) + abs(pos[2])) * (abs(vel[0]) + abs(vel[1]) + abs(vel[2]));
        }
     }

    @Override
    public String solvePartOne(String input) {
        return solvePartOne(new Moon[] {io, europa, ganymede, callisto}, 1000);
    }

    @Override
    public String solvePartTwo(String input) {
        return solvePartTwo(new Moon[] {io, europa, ganymede, callisto});
    }

    String solvePartOne(Moon[] moons, long steps) {
        for (long step = 0; step < steps; step++) {
            performStep(moons);
        }

        return String.valueOf(Arrays.stream(moons)
                .map(Moon::totalEnergy)
                .reduce(Long::sum)
                .orElseThrow(IllegalStateException::new));
    }

    private void performStep(Moon[] moons) {
        moons[0].applyGravity(moons[1]);
        moons[0].applyGravity(moons[2]);
        moons[0].applyGravity(moons[3]);
        moons[1].applyGravity(moons[2]);
        moons[1].applyGravity(moons[3]);
        moons[2].applyGravity(moons[3]);

        for (Moon moon : moons) {
            moon.applyVelocity();
        }
    }

    String solvePartTwo(Moon[] moons) {
        long steps = 0;
        long[] cycleSteps = new long[3];

        while (cycleSteps[0] == 0 || cycleSteps[1] == 0 || cycleSteps[2] == 0) {
            steps++;
            performStep(moons);
            for (int axis = 0; axis < cycleSteps.length; axis++) {
                if (cycleSteps[axis] == 0 && allMoonsAreBackToStartOnAxis(moons, axis)) {
                    cycleSteps[axis] = steps + 1;
                }
            }
        }

        return String.valueOf(lcm(cycleSteps[0], lcm(cycleSteps[1], cycleSteps[2])));
    }

    private static boolean allMoonsAreBackToStartOnAxis(Moon[] moons, int axis) {
        for (Moon moon : moons) {
            if (moon.pos[axis] != moon.originalPos[axis]) {
                return false;
            }
        }
        return true;
    }

    private static long lcm(long long1, long long2) {
        BigInteger number1 = BigInteger.valueOf(long1);
        BigInteger number2 = BigInteger.valueOf(long2);
        BigInteger gcd = number1.gcd(number2);
        BigInteger absProduct = number1.multiply(number2).abs();
        return absProduct.divide(gcd).longValue();
    }
}
