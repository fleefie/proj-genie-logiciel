package fr.cytech.projetgenielogiciel.maze;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void testCellIdIncrement() {
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();

        assertEquals(cell1.getId() + 1, cell2.getId());
    }

    @Test
    void testCellValidity() {
        Cell cell = new Cell();
        assertTrue(cell.isValid());
    }
}
