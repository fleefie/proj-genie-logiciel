package fr.cytech.projetgenielogiciel.maze;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListTest {

    @Test
    void testAddEdge() {
        AdjacencyList adjacencyList = new AdjacencyList();
        adjacencyList.addEdge(1, 2);

        assertTrue(adjacencyList.hasEdge(1, 2));
        assertTrue(adjacencyList.hasEdge(2, 1));
    }

    @Test
    void testRemoveEdge() {
        AdjacencyList adjacencyList = new AdjacencyList();
        adjacencyList.addEdge(1, 2);
        adjacencyList.removeEdge(1, 2);

        assertFalse(adjacencyList.hasEdge(1, 2));
        assertFalse(adjacencyList.hasEdge(2, 1));
    }
}
