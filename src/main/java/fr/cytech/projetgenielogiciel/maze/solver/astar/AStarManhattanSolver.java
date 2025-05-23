package fr.cytech.projetgenielogiciel.maze.solver.astar;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.Maze.Position;

/**
 * A* implementation using the Manhattan distance as a heuristic.
 */
public class AStarManhattanSolver extends AStarSolver {
    /**
     * Constructor for the A* solver using the Manhattan distance heuristic.
     *
     * @param maze            The maze to solve.
     * @param start           The starting cell.
     * @param end             The ending cell.
     * @param distanceFactor  The distance factor for the heuristic.
     * @param heuristicFactor The heuristic factor for the heuristic.
     */
    public AStarManhattanSolver(Maze maze, Cell start, Cell end, double distanceFactor, double heuristicFactor) {
        super(maze, start, end, distanceFactor, heuristicFactor);
    }

    @Override
    protected Double heuristic(Cell c) {
        Position startCoords = this.maze.findCoordinates(start);
        Position endCoords = this.maze.findCoordinates(end);
        Position currentCoords = this.maze.findCoordinates(c);

        if (startCoords == null || endCoords == null || currentCoords == null) {
            return Double.MAX_VALUE;
        }

        return Double
                .valueOf(Math.abs(currentCoords.x() - endCoords.x()) + Math.abs(currentCoords.y() - endCoords.y()));
    }

}
