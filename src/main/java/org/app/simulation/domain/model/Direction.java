package org.app.simulation.domain.model;

public sealed interface Direction {
    Direction clockwise();

    Direction antiClockwise();

    record NORTH() implements Direction {
        @Override
        public Direction clockwise() {
            return new EAST();
        }

        @Override
        public Direction antiClockwise() {
            return new WEST();
        }

        @Override
        public String toString() {
            return "N";
        }
    }

    record SOUTH() implements Direction {
        @Override
        public Direction clockwise() {
            return new WEST();
        }

        @Override
        public Direction antiClockwise() {
            return new EAST();
        }
        @Override
        public String toString() {
            return "S";
        }
    }

    record EAST() implements Direction {
        @Override
        public Direction clockwise() {
            return new SOUTH();
        }

        @Override
        public Direction antiClockwise() {
            return new NORTH();
        }

        @Override
        public String toString() {
            return "E";
        }
    }

    record WEST() implements Direction {
        @Override
        public Direction clockwise() {
            return new NORTH();
        }

        @Override
        public Direction antiClockwise() {
            return new SOUTH();
        }

        @Override
        public String toString() {
            return "W";
        }
    }

}
