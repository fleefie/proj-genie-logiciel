package fr.cytech.projetgenielogiciel.maze.solver.astar;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;

/**
 * A* implementation using Djikstra's heuristic.
 */
public class AStarDjikstraSolver extends AStarSolver {
    /**
     * Constructor for the A* solver using Djikstra's heuristic.
     *
     * @param maze  The maze to solve.
     * @param start The starting cell.
     * @param end   The ending cell.
     */
    public AStarDjikstraSolver(Maze maze, Cell start, Cell end) {
        super(maze, start, end, 0, 0);
    }

    @Override
    protected Double heuristic(Cell c) {
        return 0.0;
    }
}
