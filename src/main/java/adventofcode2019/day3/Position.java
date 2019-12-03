package adventofcode2019.day3;

import java.util.Objects;

import static java.lang.Math.abs;

class Position {
    private int x, y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int manhattanDistanceTo(int otherX, int otherY) {
        return abs(x - otherX) + abs(y - otherY);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}