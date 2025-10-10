package org.app.simulation.domain.model;

public sealed interface Command {
    record FORWARD() implements Command{
        @Override
        public String toString() {
            return "F";
        }
    }
    record LEFT() implements Command {
        @Override
        public String toString() {
            return "L";
        }
    }
    record RIGHT() implements Command {
        @Override
        public String toString() {
            return "R";
        }
    }
}
