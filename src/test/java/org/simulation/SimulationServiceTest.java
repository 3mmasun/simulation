package org.simulation;

import org.app.simulation.SimulationService;
import org.app.simulation.domain.model.SimpleSimulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SimulationServiceTest {
    private SimulationService simulationService;

    @BeforeEach
    void setUp() {
        simulationService = new SimulationService(new SimpleSimulator());
    }

    @Test
    void shouldInitializeBoardAndPlaceACar() {
        simulationService.handleCreateNewField("10", "10");
        simulationService.handleAddCar("A", 1, 1, "N", "FFRFFL");
    }

    @Test
    void shouldThrowExceptionIfDirectionIsNotRecognised() {
        simulationService.handleCreateNewField("10", "10");
        assertThatThrownBy(() -> simulationService.handleAddCar("A", 1, 1, "X", "FFRFFL"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Only N, S, W, E (representing North, South, West, East) are allowed for direction.");
    }

    @Test
    void shouldHandleSimulationRunAndPrintResult() {
        simulationService.handleCreateNewField("10", "10");
        simulationService.handleAddCar("A", 1, 1, "N", "FFRFFL");
        simulationService.handleAddCar("B", 4, 3, "S", "FFRFFL");
        simulationService.handleSimulationRun();
        List<String> carList = simulationService.listCars();
        assertThat(carList).containsExactlyInAnyOrder("A, (3,3) N, FFRFFL", "B, (2,1) S, FFRFFL");
        List<String> resultList = simulationService.showResults();
        assertThat(resultList).containsExactlyInAnyOrder("A, (3,3) N", "B, (2,1) S");
    }

    @Test
    void shouldClearCarListWhenHandStartOver() {
        simulationService.handleCreateNewField("10", "10");
        simulationService.handleAddCar("A", 1, 1, "N", "FFRFFL");
        simulationService.handStartOver();
        assertTrue(simulationService.listCars().isEmpty());
    }
}