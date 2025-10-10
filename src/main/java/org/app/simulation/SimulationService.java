package org.app.simulation;

import org.app.simulation.domain.model.*;

import java.util.List;

public class SimulationService {
    private final SimpleSimulator simpleSimulator;

    public SimulationService(SimpleSimulator simpleSimulator) {
        this.simpleSimulator = simpleSimulator;
    }

    public void handleAddCar(String name, Integer x, Integer y, String direction, String commandInput) {
        Car car = createCar(name, x, y, direction);
        List<Command> commands = commandInput.chars()
            .mapToObj((c) -> (char) c)
            .map(this::mapCharToCommand)
            .toList();
        simpleSimulator.addCar(car, commands);
    }

    public void handleCreateNewField(String width, String height) {
        int widthInt = Integer.parseInt(width);
        int heightInt = Integer.parseInt(height);
        Field field = new Field(widthInt, heightInt);
        this.simpleSimulator.newField(field);
    }

    public void handleSimulationRun() {
        this.simpleSimulator.runSimulation();
    }

    private Car createCar(String name, Integer x, Integer y, String direction) {
        return new Car(name, new Coordinate(x, y), mapStringToDirection(direction));
    }

    public List<String> listCars() {
        return simpleSimulator.getCars();
    }

    public List<String> showResults() {
        return simpleSimulator.getResult();
    }

    public void handStartOver() {
        this.simpleSimulator.startOver();
    }

    private Command mapCharToCommand(char character) {
        return switch (character) {
            case 'F' -> new Command.FORWARD();
            case 'R' -> new Command.RIGHT();
            case 'L' -> new Command.LEFT();
            default -> throw new IllegalArgumentException("Command cannot be recognised.");
        };
    }

    private Direction mapStringToDirection(String character) {
        return switch (character) {
            case "N" -> new Direction.NORTH();
            case "S" -> new Direction.SOUTH();
            case "E" -> new Direction.EAST();
            case "W" -> new Direction.WEST();
            default ->
                throw new IllegalArgumentException("Only N, S, W, E (representing North, South, West, East) are " +
                                                       "allowed for direction.");
        };
    }
}
