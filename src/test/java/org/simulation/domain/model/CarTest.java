package org.simulation.domain.model;

import org.app.simulation.domain.model.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarTest {

    private static final Coordinate _1_1 = Coordinate.of(1, 1);
    private static final Coordinate _0_0 = Coordinate.of(0, 0);
    private static final Coordinate _9_9 = Coordinate.of(9, 9);
    public static final Field TEST_FIELD = new Field(10, 10);

    @Test
    void shouldExecuteSingleCommand() {
        Car aCar = new Car("A", _1_1, new Direction.EAST());
        aCar.executeCommand(new Command.FORWARD(), TEST_FIELD);
        assertThat(aCar.getCoordinate().toString()).isEqualTo("(2,1)");
        assertThat(aCar.getDirection().toString()).isEqualTo("E");

        aCar.executeCommand(new Command.LEFT(), TEST_FIELD);
        assertThat(aCar.getCoordinate().toString()).isEqualTo("(2,1)");
        assertThat(aCar.getDirection().toString()).isEqualTo("N");

        aCar.executeCommand(new Command.RIGHT(), TEST_FIELD);
        assertThat(aCar.getCoordinate().toString()).isEqualTo("(2,1)");
        assertThat(aCar.getDirection().toString()).isEqualTo("E");
    }

    @Test
    void shouldNotMoveBeyondFieldBoundary() {
        Car car1 = new Car("A", _0_0, new Direction.WEST());
        car1.executeCommand(new Command.FORWARD(), TEST_FIELD);
        assertThat(car1.getCoordinate().toString()).isEqualTo("(0,0)");
        assertThat(car1.getDirection().toString()).isEqualTo("W");

        Car car2 = new Car("A", _0_0, new Direction.SOUTH());
        car2.executeCommand(new Command.FORWARD(), TEST_FIELD);
        assertThat(car2.getCoordinate().toString()).isEqualTo("(0,0)");
        assertThat(car2.getDirection().toString()).isEqualTo("S");

        Car car3 = new Car("A", _9_9, new Direction.NORTH());
        car3.executeCommand(new Command.FORWARD(), TEST_FIELD);
        assertThat(car3.getCoordinate().toString()).isEqualTo("(9,9)");
        assertThat(car3.getDirection().toString()).isEqualTo("N");

        Car car4 = new Car("A", _9_9, new Direction.EAST());
        car4.executeCommand(new Command.FORWARD(), TEST_FIELD);
        assertThat(car4.getCoordinate().toString()).isEqualTo("(9,9)");
        assertThat(car4.getDirection().toString()).isEqualTo("E");
    }

    @Test
    void shouldNotMoveAfterCollision() {
        Car car = new Car("A", _1_1, new Direction.NORTH());
        car.executeCommand(new Command.FORWARD(), TEST_FIELD);
        car.crashed();
        assertThat(car.getCoordinate().toString()).isEqualTo("(1,2)");
        assertThat(car.getDirection().toString()).isEqualTo("N");
        assertThat(car.getStepCount()).isEqualTo(1);
    }

    @Test
    void shouldNotMoveWhenFieldIsNull() {
        Car car = new Car("A", _1_1, new Direction.NORTH());
        assertThatThrownBy(() -> car.executeCommand(new Command.FORWARD(), null))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Board is not set");
    }

}