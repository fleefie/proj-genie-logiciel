package fr.cytech.projetgenielogiciel.maze.solver.astar;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.solver.ISolver;
import javafx.scene.paint.Color;

/**
 * Abstract class representing an A* solver.
 *
 * The heuristic should be provided through inheritance
 */
public abstract class AStarSolver implements ISolver {

    /**
     * Reference to the maze that the solver is using.
     */
    protected Maze maze;

    /**
     * Whether the maze is solved or not.
     */
    protected boolean solved = false;

    /**
     * Map representing the distance from the start for each cell.
     */
    protected Map<Integer, Double> gScore;

    /**
     * Map representing the estimated total distance g + h for each cell.
     */
    protected Map<Integer, Double> fScore;

    /**
     * A priority queue of the cells that need to be processed.
     */
    protected PriorityQueue<Cell> openSet;

    /**
     * The stack representing the final path.
     */
    protected Map<Integer, Integer> cameFrom;

    /**
     * The starting cell.
     */
    protected Cell start;

    /**
     * The target cell.
     */
    protected Cell end;

    /**
     * The current cell.
     */
    protected Cell current;

    /**
     * The factor for the distance from the start.
     */
    protected double distanceFactor;

    /**
     * The factor for the heuristic.
     */
    protected double heuristicFactor;

    /**
     * Whether the solver was initialized.
     */
    private Boolean initialized = false;

    /**
     * Abstract constructor.
     * 
     * @param maze  The maze to be solved
     * @param start the starting cell
     * @param end   the ending cell
     * @pondG the factor for the distance from the start
     * @pondH the factor for the estimated distance from the end (Heuristic-based)
     */
    public AStarSolver(Maze maze, Cell start, Cell end, double distanceFactor, double heuristicFactor) {
        this.current = start;
        this.start = start;
        this.end = end;
        this.maze = maze;
        this.solved = false;
        this.distanceFactor = distanceFactor;
        this.heuristicFactor = heuristicFactor;
        this.gScore = new HashMap<>();
        this.fScore = new HashMap<>();
        this.openSet = new PriorityQueue<>(
                Comparator.comparingDouble(c -> fScore.getOrDefault(c.getId(), Double.MAX_VALUE)));
        this.cameFrom = new HashMap<>();
        this.solved = false;
        this.gScore.clear();
        this.fScore.clear();
        this.openSet.clear();
    }

    /**
     * Executes one step of the solving algorithm.
     *
     * @return Whether a step could be performed.
     */
    public Boolean step() {
        // No need to do anything in this case
        if (solved)
            return false;

        // Quick initialization check!
        if (!initialized) {
            gScore.put(start.getId(), 0.0);
            fScore.put(start.getId(), heuristic(start));
            openSet.add(start);
            initialized = true;
        }

        // If the open set is empty, we're done and the maze is not solvable.
        if (openSet.isEmpty()) {
            solved = true;
            return false;
        }

        // Remove the previous path highlight
        for (Cell[] row : maze.getCells()) {
            for (Cell cell : row) {
                if (cell.getColor() == Color.GREEN && cell != start && cell != end) {
                    cell.setColor(Color.WHITE);
                }
            }
        }

        current = openSet.poll();

        // Make every processed cell blue
        for (Cell[] row : maze.getCells()) {
            for (Cell cell : row) {
                if (cell != start && cell != end &&
                        gScore.containsKey(cell.getId()) &&
                        !openSet.contains(cell) &&
                        cell != current &&
                        cell.getColor() != Color.GREEN) {
                    cell.setColor(Color.BLUE);
                }
            }
        }

        // Make cells that need to be processed yellow
        for (Cell cell : openSet) {
            /*
             * Pretty ugly but honestly that's the easiest way to do it without adding a
             * visited set,
             * which would itself make it much more complex.
             */
            if (cell != start && cell != end && cell != current && cell.getColor() != Color.GREEN) {
                cell.setColor(Color.YELLOW);
            }
        }

        // Make current cell red
        current.setColor(Color.RED);

        /*
         * Check whether we've finished early to avoid needless checking.
         * If solved, highlight the final full path in green
         */
        if (current.getId() == end.getId()) {
            Integer pathId = end.getId();
            while (pathId != null) {
                Cell pathCell = maze.findCellById(pathId);
                if (pathCell != null)
                    pathCell.setColor(Color.GREEN);
                pathId = cameFrom.get(pathId);
            }
            solved = true;
            return true;
        }

        // If we're not done, check for potential neighbors
        for (Integer neighborId : maze.getAdjacencyList().getNeighbors(current.getId())) {
            Cell neighbor = maze.findCellById(neighborId);
            if (neighbor == null)
                continue;

            Double tryGScore = gScore.getOrDefault(current.getId(), Double.MAX_VALUE) + 1;
            if (tryGScore < gScore.getOrDefault(neighborId, Double.MAX_VALUE)) {
                cameFrom.put(neighborId, current.getId());
                gScore.put(neighborId, tryGScore);
                Double tryFScore = (tryGScore * distanceFactor) + (heuristic(neighbor) * heuristicFactor);
                fScore.put(neighborId, tryFScore);
                if (!openSet.contains(neighbor))
                    openSet.add(neighbor);
            }
        }

        // Highlight the current temporary path.
        Integer pathId = current.getId();
        while (pathId != null) {
            Cell pathCell = maze.findCellById(pathId);
            if (pathCell != null && pathCell != end && pathCell != current) {
                pathCell.setColor(Color.GREEN);
            }
            pathId = cameFrom.get(pathId);
        }

        return true;
    }

    /**
     * Executes the entire rest of the solving process.
     *
     * @return whether a step could be performed
     */
    public Boolean solve() {
        if (!initialized) {
            gScore.put(start.getId(), 0.0);
            fScore.put(start.getId(), heuristic(start));
            openSet.add(start);
            initialized = true;
        }

        if (current == end) {
            return false;
        }

        while (!openSet.isEmpty() && !solved) {
            step();
        }

        if (solved) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Iterator over the solving process.
     */
    private class HeuristicIterator implements Iterator<Boolean> {
        @Override
        public boolean hasNext() {
            return !solved;
        }

        @Override
        public Boolean next() {
            if (solved) {
                return false;
            }
            return step();
        }
    }

    /**
     * Create an iterator for the solver.
     *
     * @return The iterator.
     */
    @Override
    public Iterator<Boolean> iterator() {
        return new HeuristicIterator();
    }

    /**
     * Whether the algorithm is done working.
     *
     * @return true when it's finished and false if it's not
     */
    public Boolean isFinished() {
        return solved;
    }

    /**
     * Compute the weight of a path given it's position in the maze.
     *
     * @param c the cell on which the heuristic is based
     * @return the computed weight.
     */
    protected abstract Double heuristic(Cell c);

    /**
     * Read accessor for the maze.
     */
    public Maze getMaze() {
        return maze;
    }
}
