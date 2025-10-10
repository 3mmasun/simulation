package org.app;

import org.app.menu.MenuService;
import org.app.simulation.SimulationService;
import org.app.simulation.domain.model.SimpleSimulator;

import java.util.Scanner;

public class SimulationRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final MenuService menuService = new MenuService(scanner);
    private final SimulationService simulationService = new SimulationService(new SimpleSimulator());
    private boolean isRunning = true;

    public void run() {
        menuService.welcome();
        menuService.handleCreateNewBoard(simulationService);

        while (isRunning) {
            menuService.displayMainMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    menuService.handleAddCar(simulationService);
                    break;
                case "2":
                    menuService.handleSimulation(simulationService);
                    String anotherChoice = scanner.nextLine();
                    switch (anotherChoice) {
                        case "1":
                            simulationService.handStartOver(); // assume start over means resue the same field with a
                            // new list of cars
                            break;
                        case "2":
                            isRunning = false;
                            break;
                    }
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close();
    }
}
