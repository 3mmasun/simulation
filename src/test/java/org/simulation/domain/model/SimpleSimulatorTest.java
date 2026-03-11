package org.simulation.domain.model;

import org.app.simulation.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimpleSimulatorTest {
    private final Field TEST_FIELD = new Field(10, 10);
    private SimpleSimulator simpleSimulator;

    @BeforeEach
    void shouldCreateField() {
        simpleSimulator = new SimpleSimulator();
        simpleSimulator.newField(TEST_FIELD);
    }

    @Test
    void shouldAddCarToSimulation() {
        Car car = new Car("A", Coordinate.of(1, 1), new Direction.NORTH());
        simpleSimulator.addCar(car, List.of(new Command.FORWARD()));
        assertThat(simpleSimulator.getCars()).containsExactly("A, (1,1) N, F");
    }

    @Test
    void shouldRunSimulationAndReturnResult() {
        Car carA = new Car("A", Coordinate.of(1, 1), new Direction.EAST());
        simpleSimulator.addCar(carA, List.of(new Command.FORWARD(), new Command.FORWARD()));
        simpleSimulator.runSimulation();
        List<String> result = simpleSimulator.getResult();
        assertThat(result).containsExactly("A, (3,1) E");
    }

    @Test
    void shouldRunSimulationAndReturnCrashedResult() {
        Car carA = new Car("A", Coordinate.of(1, 1), new Direction.EAST());
        Car carB = new Car("B", Coordinate.of(3, 1), new Direction.WEST());
        simpleSimulator.addCar(carA, List.of(new Command.FORWARD(), new Command.FORWARD()));
        simpleSimulator.addCar(carB, List.of(new Command.FORWARD()));
        simpleSimulator.runSimulation();
        List<String> result = simpleSimulator.getResult();
        assertThat(result).containsExactlyInAnyOrder("B, collides with A at (2,1) at step 1",
                                           "A, collides with B at (2,1) at step 1");
    }

    @Test
    void shouldRunSimulationWithoutCrashing() {
        Car carA = new Car("A", Coordinate.of(1, 1), new Direction.EAST());
        Car carB = new Car("B", Coordinate.of(2, 1), new Direction.WEST());
        simpleSimulator.addCar(carA, List.of(new Command.FORWARD()));
        simpleSimulator.addCar(carB, List.of(new Command.FORWARD()));
        simpleSimulator.runSimulation();
        List<String> cars = simpleSimulator.getResult();
        assertThat(cars).containsExactlyInAnyOrder("A, (2,1) E", "B, (1,1) W");
    }

    @Test
    void shouldReturnListOfCars() {
        Car carA = new Car("A", Coordinate.of(1, 1), new Direction.EAST());
        Car carB = new Car("B", Coordinate.of(3, 1), new Direction.WEST());
        simpleSimulator.addCar(carA, List.of(new Command.FORWARD(), new Command.FORWARD()));
        simpleSimulator.addCar(carB, List.of(new Command.FORWARD()));
        List<String> cars = simpleSimulator.getCars();
        assertThat(cars).containsExactlyInAnyOrder("A, (1,1) E, FF", "B, (3,1) W, F");
    }

    @Test
    void shouldThrowExceptionIfFieldIsNotCreated() {
        SimpleSimulator simpleSimulator = new SimpleSimulator();
        Car car = new Car("A", Coordinate.of(1, 1), new Direction.NORTH());
        List<Command> commandList = new ArrayList<>();
        assertThatThrownBy(()-> simpleSimulator.addCar(car, commandList))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Field is not created yet.");
    }

    @Test
    void shouldThrowExceptionIfCoordinateOutOfFieldBoundary() {
        Car car1 = new Car("A", Coordinate.of(11, 1), new Direction.NORTH());
        assertThatThrownBy(()-> simpleSimulator.addCar(car1, List.of())).isInstanceOf(IllegalArgumentException.class);
        Car car2 = new Car("A", Coordinate.of(10, 11), new Direction.NORTH());
        assertThatThrownBy(()-> simpleSimulator.addCar(car2, List.of())).isInstanceOf(IllegalArgumentException.class);
    }
}