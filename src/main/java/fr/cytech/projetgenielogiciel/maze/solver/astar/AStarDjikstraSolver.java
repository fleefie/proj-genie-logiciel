package fr.cytech.projetgenielogiciel.maze.solver.astar;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;

import java.io.Serializable;

/**
 * A* implementation using Djikstra's heuristic.
 */
public class AStarDjikstraSolver extends AStarSolver implements Serializable {
    public AStarDjikstraSolver(Maze lab, Cell start, Cell end) {
        super(lab, start, end, 0, 0);
    }

    @Override
    public Double heuristic(Cell c) {
        return 0.0;
    }
}
