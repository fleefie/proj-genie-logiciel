package fr.cytech.projetgenielogiciel.maze.solver;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

public class TremauxSolver implements ISolver, Serializable {
    @Setter
    @Getter
    protected Maze maze;
    protected boolean solved = false;
    protected Cell start;
    protected Cell end;
    protected Cell current;
    protected Map<Integer, Integer> marks; // Map to track how many times each cell has been visited
    protected Stack<Cell> path;

    /**
     * Creator of Solver class
     * 
     * @param lab take a maze that will be solved step by step
     *
     */
    public TremauxSolver(Maze lab, Cell start, Cell end) {
        try {
            if (lab == null) {
                throw new IllegalArgumentException("labyrinthe null");
            }
            this.maze = lab;
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
     * Does one step from the cell c
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
     * Solve the maze by starting from s to f
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

    // Setup les trucs de bases
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
}
