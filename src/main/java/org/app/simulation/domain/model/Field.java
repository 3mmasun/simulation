package org.app.simulation.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Field {
    private Integer width;
    private Integer height;
    private ConcurrentMap<Coordinate, List<Car>> carLocations;

    public Field(Integer width, Integer height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than 0.");
        }
        this.width = width;
        this.height = height;
        carLocations = new ConcurrentHashMap<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                carLocations.put(Coordinate.of(x, y), new ArrayList<>());
            }
        }
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public List<Car> getCars(Coordinate coordinate) {
        return carLocations.get(coordinate).stream().toList();
    }

    public void placeCar(Car car) {
        carLocations.get(car.getCoordinate()).add(car);
    }

    public void move(Car car, Coordinate currentCoordinate, Coordinate newCoordinate) {
        carLocations.get(currentCoordinate).remove(car);
        carLocations.get(newCoordinate).add(car);
    }
}
