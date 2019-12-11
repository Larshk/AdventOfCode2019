package adventofcode2019.util;

import java.util.Objects;

import static java.lang.Math.abs;

public class EuclideanPoint {
    public int x;
    public int y;

    public EuclideanPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int manhattanDistanceTo(EuclideanPoint other) {
        return abs(x - other.x) + abs(y - other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EuclideanPoint euclideanPoint = (EuclideanPoint) o;
        return x == euclideanPoint.x &&
               y == euclideanPoint.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
