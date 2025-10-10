# About the submission

JDK/JRE 25 should be installed.

Running the project
```bash
# cd into the project directory
java -jar target/simulation-1.0-SNAPSHOT.jar
```

# Implementation notes
This note describes the thoughts behind the implementation.

## Initial implementation
Board
- Board should contain a list of cars
- Board can issue commands to cars
- Board should be able to receive the next coordinates of the cars by simulating a command, then check if there's collision

Car
- Car should be able to move
- Car should be able to turn
- Car should be able to output the next coordinates by simulating a command

## Implementation step 2: Responsibility of the domain objects

Simulation is not the responsibility of the field but the car.

For a car to drive the next coordinates after executing a command, it must be aware of the boundary of the field.

Thus, a reference to the field is needed in the Car instance. And simulation can only run when the field is set.

### Commands

LinkedList is a suitable data structure for commands as it allows the first element to be removed easily.

It also maintains the order of the commands.

## Implementation step 3: Simulation Service

The Simulation Service is responsible for orchestrating the simulation.
- Simulation Service should instruct the car to execute a command
- Simulation Service should instruct the field to move a car
- Simulation Service should instruct the cars if there is a collision
- Simulation Service should handle the output of the result of the simulation

## Final Implementation

Made a few changes:
- ArrayList is sufficient to hold the commands of a car. 
- ConcurrentMap was used to ensure thread safety when updating field with car locations.

## Future improvements
- A different simulator can be injected to the simulation service, which could implement different strategies such as collision detection before moving into a coordinate.
- Menu Service can be implemented using a double linked list, allowing user to go back to previous menu or go to the next menu.