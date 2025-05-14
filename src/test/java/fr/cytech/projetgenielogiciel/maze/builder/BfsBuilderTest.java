package fr.cytech.projetgenielogiciel.maze.builder;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;

class BfsBuilderTest {
    @Test
    void testBuild() {
        // Test maze please ignore
        Maze maze = new Maze(5, 5);
        IBuilder builder = new BfsBuilder(maze, 0, 0, 42);
        for (Boolean step : builder)
            if (step == false)
                break;

        String testMaze = "{((0, 0), [NORTH, EAST]), ((1, 0), [EAST, WEST]), ((2, 0), [EAST, WEST]), ((3, 0), [EAST, WEST]), ((4, 0), [WEST]), ((0, 1), [NORTH, EAST, SOUTH]), ((1, 1), [NORTH, EAST, WEST]), ((2, 1), [NORTH, EAST, WEST]), ((3, 1), [EAST, WEST]), ((4, 1), [WEST]), ((0, 2), [NORTH, SOUTH]), ((1, 2), [NORTH, SOUTH]), ((2, 2), [NORTH, EAST, SOUTH]), ((3, 2), [EAST, WEST]), ((4, 2), [WEST]), ((0, 3), [NORTH, SOUTH]), ((1, 3), [NORTH, SOUTH]), ((2, 3), [NORTH, EAST, SOUTH]), ((3, 3), [NORTH, EAST, WEST]), ((4, 3), [NORTH, WEST]), ((0, 4), [SOUTH]), ((1, 4), [SOUTH]), ((2, 4), [SOUTH]), ((3, 4), [SOUTH]), ((4, 4), [SOUTH])}";
        assertTrue(maze.toString().equals(testMaze));
    }

    @Test
    void testIteration() {
        // Test maze please ignore
        Cell.setTotalCellCount(0);
        Maze maze1 = new Maze(5, 5);
        IBuilder builder1 = new BfsBuilder(maze1, 0, 0, 42);
        for (Boolean step : builder1)
            if (step == false)
                break;

        Cell.setTotalCellCount(0);
        Maze maze2 = new Maze(5, 5);
        IBuilder builder2 = new BfsBuilder(maze2, 0, 0, 42);
        builder2.build();

        Cell.setTotalCellCount(0);
        Maze maze3 = new Maze(5, 5);
        IBuilder builder3 = new BfsBuilder(maze3, 0, 0, 42);
        while (!builder3.isFinished())
            builder3.step();

        assertTrue(maze1.equals(maze2));
        assertTrue(maze2.equals(maze3));
    }
}
