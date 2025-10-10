package org.simulation.domain.model;

import org.app.simulation.domain.model.Car;
import org.app.simulation.domain.model.Coordinate;
import org.app.simulation.domain.model.Direction;
import org.app.simulation.domain.model.Field;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FieldTest {
    @Test
    void shouldPlaceCarOnBoard() {
        Field field = new Field(10, 10);
        Car car = new Car("A", Coordinate.of(2, 3), new Direction.NORTH());
        field.placeCar(car);
        List<Car> actualCars = field.getCars(Coordinate.of(2, 3));
        assertThat(actualCars).containsExactly(car);
    }
}