package org.app.simulation.domain.model;

import java.util.*;

public class Car {
    private UUID id;
    private String name;
    private State state = State.NORMAL;
    private Coordinate coordinate;
    private Direction direction;
    private final Integer SPEED = 1;
    private Integer stepCount = 0;

    public Car(String name, Coordinate coordinate, Direction direction) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.coordinate = coordinate;
        this.direction = direction;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Direction getDirection() {
        return direction;
    }

    public State getState() {
        return state;
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public String getName() {
        return name;
    }

    public Coordinate executeCommand(Command command, Field field) {
        if (field == null) {
            throw new IllegalStateException("Board is not set");
        }
        if (this.state == State.CRASHED) {
            return this.coordinate;
        }
        switch (command) {
            case Command.FORWARD() -> this.coordinate = this.forward(field);
            case Command.RIGHT() -> this.direction = this.direction.clockwise();
            case Command.LEFT() -> this.direction = this.direction.antiClockwise();
            default -> {
            }
        }
        stepCount++;
        return this.coordinate;
    }

    public void crashed() {
        this.state = State.CRASHED;
    }

    private Coordinate forward(Field field) {
        return switch (this.direction){
            case Direction.WEST() -> moveWest();
            case Direction.EAST() -> moveEast(field.getWidth());
            case Direction.NORTH() -> moveNorth(field.getHeight());
            case Direction.SOUTH() -> moveSouth();
        };
    }

    private Coordinate moveEast(Integer width) {
        if (this.coordinate.x() + SPEED > width - 1) {
            return this.coordinate;
        }
        return new Coordinate(this.coordinate.x() + SPEED, this.coordinate.y());
    }

    private Coordinate moveNorth(Integer height) {
        if (this.coordinate.y() + SPEED > height - 1) {
            return this.coordinate;
        }
        return new Coordinate(this.coordinate.x(), this.coordinate.y() + SPEED);
    }

    private Coordinate moveSouth() {
        if (this.coordinate.y() - SPEED < 0) {
            return this.coordinate;
        }
        return new Coordinate(this.coordinate.x(), this.coordinate.y() - SPEED);
    }

    private Coordinate moveWest() {
        if (this.coordinate.x() - SPEED < 0) {
            return this.coordinate;
        }
        return new Coordinate(this.coordinate.x() - SPEED, this.coordinate.y());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Car car)) return false;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
