package fr.cytech.projetgenielogiciel.maze.solver;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Class to solve a maze using the Tremaux algorithm.
 */
public class TremauxSolver implements ISolver {
    /**
     * The maze to be solved.
     */
    protected Maze maze;

    /**
     * Whether the maze is solved or not.
     */
    protected boolean solved = false;

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
     * The map representing the number of times each cell has been visited.
     */
    protected Map<Integer, Integer> marks;

    /**
     * The currently kept path.
     */
    protected Stack<Cell> path;

    /**
     * Constructor for the Tremaux solver.
     *
     * @param maze  The maze to be solved.
     * @param start The starting cell.
     * @param end   The target cell.
     */
    public TremauxSolver(Maze maze, Cell start, Cell end) {
        try {
            if (maze == null) {
                throw new IllegalArgumentException("Maze cannot be null");
            }
            this.maze = maze;
            this.solved = false;
            this.start = start;
            this.end = end;
            this.current = start;
            this.marks = new HashMap<>();
            this.path = new Stack<>();

            // Initialize marks for all cells
            for (int x = 0; x <= maze.getWidth(); x++) {
                for (int y = 0; y <= maze.getHeight(); y++) {
                    Cell cell = maze.getCell(x, y);
                    if (cell != null) {
                        marks.put(cell.getId(), 0);
                    }
                }
            }
            // Mark the starting cell once
            marks.put(start.getId(), 1);
            path.push(start);
            start.setColor(Color.BLUE);
        }

        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Execute a single step of the Tremaux algorithm.
     *
     * @return true if the step was successful, false if nothing was done
     */
    public Boolean step() {
        if (solved) {
            return false;
        }

        if (current.getId() == end.getId()) {
            solved = true;
            // the end has been reached so put the path in green
            for (Cell cell : path) {
                cell.setColor(Color.GREEN);
            }
            end.setColor(Color.GREEN);
            return true;
        }

        Cell nextCell = getNextCell();

        if (nextCell == null) { // if there is a "cul de sac", go back to the previous cell
            if (path.isEmpty()) { // if no solution
                solved = true;
            } else {
                current.setColor(Color.BLUE);
                current = path.pop();
                current.setColor(Color.RED);
            }
        } else {
            // go to the next cell
            current.setColor(Color.GREEN);
            path.push(current);
            current = nextCell;
            marks.put(current.getId(), marks.get(current.getId()) + 1);
            current.setColor(Color.RED);
        }

        return true;
    }

    /**
     * Execute the entire rest of the solving process.
     *
     * @return true if a step was successful, false if nothing was done
     */
    public Boolean solve() {
        if (current == end) {
            return false;
        }
        Boolean ret = true; // need ret to detect problems in the solution
        while (!solved && ret) {
            ret = step();
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
    private class TremauxIterator implements Iterator<Boolean> {
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
        return new TremauxIterator();
    }

    // ???
    public Boolean isFinished() {
        return solved;
    }

    /**
     * Search the next cell, firstly with mark=0 then secondly with mark=1
     * 
     * @return the next cell
     */
    private Cell getNextCell() {
        List<Integer> neighbors = maze.getAdjacencyList().getNeighbors(current.getId());
        Cell nextCell = null;

        // Search unvisited cell (with mark=0)
        for (Integer neighborId : neighbors) {
            Cell neighbor = maze.findCellById(neighborId);
            if (marks.get(neighbor.getId()) == 0) {
                nextCell = neighbor;
                break;
            }
        }

        // Search cells which has benn visited only one time (with mark=1)
        if (nextCell == null) { // if there is no cells with mark=0
            for (Integer neighborId : neighbors) {
                Cell neighbor = maze.findCellById(neighborId);
                if (marks.get(neighbor.getId()) == 1 && !neighbor.equals(path.peek())) {
                    nextCell = neighbor;
                    break;
                }
            }
        }

        return nextCell;
    }

    /**
     * Read accessor for the maze.
     */
    @Override
    public Maze getMaze() {
        return maze;
    }
}
