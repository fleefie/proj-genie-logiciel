package fr.cytech.projetgenielogiciel.maze.builder;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;

class DfsBuilderTest {
    @Test
    void testBuild() {
        // Test maze please ignore
        Maze maze = new Maze(5, 5);
        IBuilder builder = new DfsBuilder(maze, 0, 0, 42);
        for (Boolean step : builder)
            if (step == false)
                break;

        String testMaze = "{((0, 0), [NORTH]), ((1, 0), [EAST]), ((2, 0), [EAST, WEST]), ((3, 0), [NORTH, EAST, WEST]), ((4, 0), [NORTH, WEST]), ((0, 1), [EAST, SOUTH]), ((1, 1), [NORTH, WEST]), ((2, 1), [NORTH, EAST]), ((3, 1), [SOUTH, WEST]), ((4, 1), [NORTH, SOUTH]), ((0, 2), [NORTH, EAST]), ((1, 2), [SOUTH, WEST]), ((2, 2), [EAST, SOUTH]), ((3, 2), [NORTH, WEST]), ((4, 2), [NORTH, SOUTH]), ((0, 3), [NORTH, SOUTH]), ((1, 3), [NORTH, EAST]), ((2, 3), [EAST, WEST]), ((3, 3), [SOUTH, WEST]), ((4, 3), [NORTH, SOUTH]), ((0, 4), [EAST, SOUTH]), ((1, 4), [SOUTH, WEST]), ((2, 4), [EAST]), ((3, 4), [EAST, WEST]), ((4, 4), [SOUTH, WEST])}";
        assertTrue(maze.toString().equals(testMaze));
    }

    @Test
    void testIteration() {
        // Test maze please ignore
        Cell.setTotalCellCount(0);
        Maze maze1 = new Maze(5, 5);
        IBuilder builder1 = new DfsBuilder(maze1, 0, 0, 42);
        for (Boolean step : builder1)
            if (step == false)
                break;

        Cell.setTotalCellCount(0);
        Maze maze2 = new Maze(5, 5);
        IBuilder builder2 = new DfsBuilder(maze2, 0, 0, 42);
        builder2.build();

        Cell.setTotalCellCount(0);
        Maze maze3 = new Maze(5, 5);
        IBuilder builder3 = new DfsBuilder(maze3, 0, 0, 42);
        while (!builder3.isFinished())
            builder3.step();

        assertTrue(maze1.equals(maze2));
        assertTrue(maze2.equals(maze3));
    }
}
