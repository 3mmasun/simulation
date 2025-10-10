package org.simulation.domain.model;

import org.app.simulation.domain.model.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {
    @Test
    void testEastDirection() {
        Direction east = new Direction.EAST();
        assertEquals("N", east.antiClockwise().toString());
        assertEquals("S", east.clockwise().toString());
    }

    @Test
    void testNorthDirection() {
        Direction north = new Direction.NORTH();
        assertEquals("W", north.antiClockwise().toString());
        assertEquals("E", north.clockwise().toString());
    }

    @Test
    void TestSouthDirection() {
        Direction north = new Direction.SOUTH();
        assertEquals("E", north.antiClockwise().toString());
        assertEquals("W", north.clockwise().toString());
    }

    @Test
    void TestWestDirection() {
        Direction north = new Direction.WEST();
        assertEquals("S", north.antiClockwise().toString());
        assertEquals("N", north.clockwise().toString());
    }

}