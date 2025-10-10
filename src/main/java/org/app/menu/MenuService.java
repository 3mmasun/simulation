package org.app.menu;

import org.app.simulation.SimulationService;

import java.util.List;
import java.util.Scanner;

public class MenuService {
    private final Scanner scanner;
    private final String fieldCreationRegex = "^\\d+ \\d+$";
    private final String carCreationRegex = "^\\d+ \\d+ [EWNS]$";
    private final String commandRegex = "^[FLR]+$";

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void welcome() {
        System.out.println("Welcome to Auto Driving Car Simulation!");
    }

    public void handleCreateNewBoard(SimulationService simulationService) {
        boolean boardCreated = false;
        System.out.println("Please enter the width and height of the simulation field in x y format:");
        while (!boardCreated) {
            try {
                String input = scanner.nextLine();
                if (!input.matches(fieldCreationRegex)) {
                    System.err.println("Invalid input. Please enter the width and height of the simulation field in x" +
                                          " y format");
                }
                String[] inputs = input.split(" ");
                simulationService.handleCreateNewField(inputs[0], inputs[1]);
                boardCreated = true;
            } catch (Exception e) {
                System.err.println("Invalid input. Please enter the width and height of the simulation field in x y " +
                                       "format");
            }
        }

    }

    public void handleAddCar(SimulationService simulationService) {
        boolean carCreated = false;
        while (!carCreated) {
            try {
                String name = getCarName();
                String positionAndDirectionInput = getPositionAndDirectionInput();
                String commands = getCommandInput();

                String[] positionAndDirection = positionAndDirectionInput.split(" ");
                Integer x = Integer.parseInt(positionAndDirection[0]);
                Integer y = Integer.parseInt(positionAndDirection[1]);
                String direction = positionAndDirection[2].toUpperCase();
                simulationService.handleAddCar(name, x, y, direction, commands);

                carCreated = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        listCards(simulationService.listCars());
    }

    public void handleSimulation(SimulationService simulationService) {
        listCards(simulationService.listCars());
        simulationService.handleSimulationRun();
        showResults(simulationService.showResults());
        displaySimulationMenu();
    }

    public void displayMainMenu() {
        System.out.println("""
                               
                               Please choose from the following options:
                               [1] Add a car to field
                               [2] Run simulation""");
    }

    private String getCommandInput() {
        System.out.println("Please enter the commands for car A:");
        String commands = scanner.nextLine();
        if (!commands.matches(commandRegex)) {
            throw new IllegalArgumentException("Command not recognised.");
        }
        return commands;
    }

    private String getPositionAndDirectionInput() {
        System.out.println("Please enter initial position of car A in x y Direction format:");
        String positionAndDirectionInput = scanner.nextLine();
        if (!positionAndDirectionInput.matches(carCreationRegex)) {
            throw new IllegalArgumentException("Invalid input. Please enter initial position of car A in x y Direction format");
        }
        return positionAndDirectionInput;
    }

    private String getCarName() {
        System.out.println("Please enter the name of the car:");
        String name = scanner.nextLine();
        if (name.isBlank()) {
            throw new IllegalArgumentException("Invalid input. Please enter the name of the car");
        }
        return name;
    }

    private void displaySimulationMenu() {
        System.out.println("""
                               
                               Please choose from the following options:
                               [1] Start over
                               [2] Exit""");
    }

    private void listCards(List<String> inputs) {
        System.out.println("Your current list of cars are:");
        for (String result : inputs) {
            System.out.println("- " + result);
        }
    }

    private void showResults(List<String> inputs) {
        System.out.println("After simulation, the result is:");
        for (String result : inputs) {
            System.out.println("- " + result);
        }
    }
}
