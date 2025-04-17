package fr.cytech.projetgenielogiciel.maze;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    @Test
    void testMazeConnections() {
        Maze maze = new Maze(5, 5);
        maze.connect(1, 0, Direction.NORTH);

        assertTrue(maze.hasConnection(1, 0, Direction.NORTH));
        assertTrue(maze.hasConnection(1, 1, Direction.SOUTH));
        assertFalse(maze.hasConnection(1, 0, Direction.SOUTH));
    }

    @Test
    void testToString() {
        Maze maze = new Maze(2, 2);
        maze.connect(0, 0, Direction.NORTH);

        String expected = String.format("{((0, 0), [NORTH]), ((1, 0), []), ((0, 1), [SOUTH]), ((1, 1), [])}");

        assertEquals(expected, maze.toString());
    }

    @Test
    void testToFormattedString() {
        Maze maze = new Maze(2, 2);
        maze.connect(0, 0, Direction.NORTH);

        String id00 = String.format("%02d", maze.getCell(0, 0).getId());
        String id10 = String.format("%02d", maze.getCell(1, 0).getId());
        String id01 = String.format("%02d", maze.getCell(0, 1).getId());
        String id11 = String.format("%02d", maze.getCell(1, 1).getId());

        // This feels too specific.
        // TODO: Generalize this test to work with any test maze

        String actual = maze.toFormatedString().trim();

        String normalizedActual = actual.replaceAll("\\s+", " ").replaceAll(" \n", "\n").replaceAll("\n ", "\n");
        String normalizedExpected = String.format("%s %s | %s %s", id01, id11, id00, id10);
        assertEquals(normalizedExpected.trim(), normalizedActual.trim());

    }
}
