package org.app.simulation.domain.model;

public record Coordinate(int x, int y) {
    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
}
