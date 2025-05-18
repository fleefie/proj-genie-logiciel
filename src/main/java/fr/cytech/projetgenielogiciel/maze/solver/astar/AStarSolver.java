package fr.cytech.projetgenielogiciel.maze.solver.astar;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.solver.ISolver;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class representing an A* solver.
 *
 * The heuristic should be provided through inheritance
 */
public abstract class AStarSolver implements ISolver {

    /**
     * Reference to the maze that the solver is using.
     */
    @Getter
    @Setter
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
    protected Stack<Cell> cameFrom = new Stack<>();

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
     * The previously checked cell.
     */
    private Cell old = null;

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
    public AStarSolver(Maze maze, Cell start, Cell end, double pondG, double pondH) {
        this.current = start;
        this.start = start;
        this.end = end;
        this.maze = maze;
        this.solved = false;
        this.distanceFactor = pondG;
        this.heuristicFactor = pondH;
        this.gScore = new HashMap<>();
        this.fScore = new HashMap<>();
        this.openSet = new PriorityQueue<>(
                Comparator.comparingDouble(c -> fScore.getOrDefault(c.getId(), Double.MAX_VALUE)));

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
        if (solved) {
            return false;
        }
        if (!initialized) {
            gScore.put(start.getId(), 0.0);
            fScore.put(start.getId(), heuristic(start));
            openSet.add(start);
            initialized = true;
        }

        if (old != null) {
            old.setColor(Color.BLUE);
        }
        current.setColor(Color.BLUE);
        current = openSet.poll();
        current.setColor(Color.RED);
        System.out.println("actuel " + current);
        System.out.println("fScore " + fScore.get(current.getId()));
        System.out.println("gScore " + gScore.get(current.getId()));
        if (current.getId() == end.getId()) {
            solved = true;
            current.setColor(Color.GREEN);
            while (!cameFrom.isEmpty()) {
                Cell temp = cameFrom.pop();
                int voisin = 0;
                for (Integer neighborId : maze.getAdjacencyList().getNeighbors(temp.getId())) {
                    Cell neighbor = maze.findCellById(neighborId);
                    if (neighbor == null)
                        continue;
                    else if (cameFrom.contains(neighbor) || neighbor.getColor() == Color.GREEN) {
                        voisin++;
                    }
                }
                if (voisin == 2 || temp == start) {
                    temp.setColor(Color.GREEN);
                }
            }
            return true;
        }
        Boolean stuck = true;

        for (Integer neighborId : maze.getAdjacencyList().getNeighbors(current.getId())) {
            Cell neighbor = maze.findCellById(neighborId);
            System.out.println("voisin " + neighbor);
            if (neighbor == null)
                continue;

            if (neighbor.getColor() == Color.BLUE) {
                // Check if the path is estimated to be shorter
                Double fTemp = fScore.get(neighbor.getId());
                if (fTemp > fScore.get(current.getId())) {
                    Cell actual = cameFrom.pop();
                    while (!actual.equals(neighbor)) {
                        actual = cameFrom.pop();
                    }
                    cameFrom.add(current);
                }
            } else {
                stuck = false;

                Double tentativeGScore = gScore.getOrDefault(current.getId(), Double.MAX_VALUE);

                gScore.put(neighbor.getId(), tentativeGScore);
                Double tempF = ((tentativeGScore * distanceFactor) + (heuristic(neighbor) * heuristicFactor))
                        * 2;
                fScore.put(neighbor.getId(), tempF);

                openSet.add(neighbor);
                System.out.println(neighbor + " ajouté");
            }

        }

        if (stuck) {
            old = current;
        } else {
            cameFrom.add(current);
        }
        System.out.println("openset " + openSet);
        System.out.println("cameFrom " + cameFrom);
        System.out.println();
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
    public abstract Double heuristic(Cell c);

}
