package fr.cytech.projetgenielogiciel.maze.solver.astar;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.Maze.Position;

/**
 * A* implementation using the Manhattan distance as a heuristic.
 */
public class AStarManhattanSolver extends AStarSolver {
    public AStarManhattanSolver(Maze lab, Cell start, Cell end, double pondG, double pondH) {
        super(lab, start, end, pondG, pondH);
    }

    @Override
    public Double heuristic(Cell c) {
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
