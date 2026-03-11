package org.app.simulation.domain.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleSimulator implements Simulator{
    private Field field;
    private Map<Car, List<Command>> carCommands;
    private Integer totalSteps;

    public SimpleSimulator() {
        this.carCommands = new HashMap<>();
        this.totalSteps = 0;
    }

    public void addCar(Car car, List<Command> commands) {
        if (this.field == null) {
            throw new IllegalStateException("Field is not created yet.");
        }
        validateCarPosition(car.getCoordinate());
        this.carCommands.put(car, commands);
        this.field.placeCar(car);
        updateTotalSteps(commands);
    }

    public void newField(Field field) {
        this.field = field;
    }

    @Override
    public void runSimulation() {
        IntStream.range(0, this.totalSteps).forEach(step -> {
            Set<Car> cars = carCommands.keySet();
            for (Car car : cars) {
                int totalStep = carCommands.get(car).size();
                if (step >= totalStep) {continue;}
                Coordinate currentCoordinate = car.getCoordinate();
                Command nextCommand = carCommands.get(car).get(step);
                Coordinate newCoordinate = car.executeCommand(nextCommand, field);
                field.move(car, currentCoordinate, newCoordinate);
            }
            for (Car car : cars) {
                List<Car> carsAtLocation = field.getCars(car.getCoordinate());
                if (carsAtLocation.size() > 1) {
                    for (Car c : carsAtLocation) {
                        c.crashed();
                    }
                }
            }
        });
    }

    public void startOver() {
        this.carCommands.clear();
    }

    public List<String> getResult() {
        return carCommands.keySet().stream().map(this::result).toList();
    }

    public List<String> getCars() {
        return carCommands.keySet().stream()
            .map(this::formatCar)
            .toList();
    }

    public String formatCar(Car car) {
        String commandStrings = carCommands.get(car).stream()
            .map(Command::toString)
            .reduce(String::concat)
            .get();
        return String.format("%s, %s %s, %s",
                             car.getName(), car.getCoordinate().toString(), car.getDirection().toString(), commandStrings);
    }

    private void updateTotalSteps(List<Command> commands) {
        if (commands.size() > this.totalSteps) {
            this.totalSteps = commands.size();
        }
    }

    private void validateCarPosition(Coordinate coordinate) {
        if (coordinate.x() < 0 || coordinate.y() < 0
            || coordinate.x() >= this.field.getWidth()
            || coordinate.y() >= this.field.getHeight()) {
            throw new IllegalArgumentException("Invalid car position.");
        }
    }

    private String result(Car car) {
        if (car.getState() == State.NORMAL)
            return String.format("%s, %s %s",
                                 car.getName(), car.getCoordinate().toString(), car.getDirection().toString());
        else {
            List<Car> crashedCars = this.field.getCars(car.getCoordinate());
            String otherCarNames = crashedCars.stream()
                .map(Car::getName)
                .filter(name -> !car.getName().equals(name))
                .collect(Collectors.joining(", "));
            return String.format("%s, collides with %s at %s at step %d",
                                 car.getName(), otherCarNames, car.getCoordinate().toString(), car.getStepCount());
        }
    }

}
