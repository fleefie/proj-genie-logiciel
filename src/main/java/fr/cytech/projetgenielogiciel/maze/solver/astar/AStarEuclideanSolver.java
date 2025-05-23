package fr.cytech.projetgenielogiciel.maze.solver.astar;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.Maze.Position;

import java.io.Serializable;

/**
 * A* implementation using the Euclidean distance as a heuristic.
 */
public class AStarEuclideanSolver extends AStarSolver implements Serializable {
    public AStarEuclideanSolver(Maze lab, Cell start, Cell end, double distanceFactor, double heuristicFactor) {
        super(lab, start, end, distanceFactor, heuristicFactor);
    }

    @Override
    public Double heuristic(Cell c) {
        Position startCoords = this.maze.findCoordinates(start);
        Position endCoords = this.maze.findCoordinates(end);
        Position currentCoords = this.maze.findCoordinates(c);

        if (startCoords == null || endCoords == null || currentCoords == null) {
            return Double.MAX_VALUE;
        }

        return Math
                .sqrt(Math.pow(currentCoords.x() - endCoords.x(), 2) + Math.pow(currentCoords.y() - endCoords.y(), 2));
    }

}
