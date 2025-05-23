package fr.cytech.projetgenielogiciel.maze.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import fr.cytech.projetgenielogiciel.maze.Direction;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.Maze.Position;
import javafx.scene.paint.Color;
import lombok.Getter;

/**
 * Implements a perfect maze builder using a DFS algorithm.
 */
public class DfsBuilder implements IBuilder {

    /**
     * Holds the set of cells that were visited.
     */
    private final Set<Position> visited = new HashSet<Position>();

    /**
     * Whether the builder is finished building.
     */
    @Getter
    protected Boolean finished = false;

    /**
     * The current cell of the builder, in case it needs to be paused.
     */
    private Position currentCell;

    /**
     * The stack of cells.
     * We're writing the DFS procedurally and not recursively,
     * so we need this.
     */
    private final Stack<Position> positionStack = new Stack<Position>();

    /**
     * Random generator.
     */
    @Getter
    private final Random rand;

    /**
     * Reference to the maze that the builder is working on
     */
    @Getter
    protected final Maze maze;

    /**
     * Constructor.
     * 
     * @param maze   Reference to the maze being worked on.
     * @param startx starting X position.
     * @param starty starting Y position.
     * @param seed   seed for the RNG
     */
    public DfsBuilder(Maze maze, Integer startx, Integer starty, Integer seed) {
        this.maze = maze;
        this.currentCell = new Position(startx, starty);
        this.maze.getCell(currentCell.x(), currentCell.y()).setColor(Color.RED);
        this.rand = new Random(seed);
        this.positionStack.push(currentCell);
    }

    /**
     * Create an iterator for the builder.
     *
     * @return The iterator.
     */
    @Override
    public Iterator<Boolean> iterator() {
        return new DfsIterator();
    };

    /**
     * Runs as many building steps as possible.
     *
     * @return whether the build process could execute a step.
     */
    @Override
    public Boolean build() {
        Boolean ret = false;
        for (Boolean stepStatus : this)
            ret = stepStatus;
        return ret;
    }

    /**
     * Whether the building process is finished.
     *
     * @return if the building process is finished
     */
    @Override
    public Boolean isFinished() {
        return finished;
    }

    /**
     * INTERNAL USE ONLY!
     * Iterator implementation for the DFS. Required to avoid self-referencing.
     */
    private class DfsIterator implements Iterator<Boolean> {
        @Override
        public boolean hasNext() {
            return !finished;
        }

        @Override
        public Boolean next() {
            if (finished) {
                return false;
            }
            return step();
        }
    }

    /**
     * Executes a step of the DFS.
     * A step is considered over once we found a new cell to visit.
     *
     * @return whether a step could be executed.
     */
    @Override
    public Boolean step() {
        visited.add(currentCell);

        // If the current cell has valid neighbors, try moving to one of them
        if (hasValidTargets(currentCell)) {
            List<Direction> directions = new ArrayList<Direction>(Arrays.asList(
                    Direction.NORTH,
                    Direction.SOUTH,
                    Direction.EAST,
                    Direction.WEST));
            Collections.shuffle(directions, rand); // inefficient, but clean

            for (Direction direction : directions) {
                Position target = new Position(
                        currentCell.x() + direction.getX(),
                        currentCell.y() + direction.getY());

                if (isValidTarget(target)) {
                    // Connect the maze and move to the target cell
                    this.maze.connect(currentCell.x(), currentCell.y(), direction);
                    this.maze.getCell(currentCell.x(), currentCell.y()).setColor(Color.BLUE);
                    this.positionStack.push(target);
                    this.currentCell = target;
                    this.maze.getCell(currentCell.x(), currentCell.y()).setColor(Color.RED);
                    return true;
                }
            }
        }

        // If no valid neighbors, backtrack
        if (!positionStack.isEmpty()) {
            positionStack.pop();
        }
        if (!positionStack.isEmpty()) {
            this.maze.getCell(currentCell.x(), currentCell.y()).setColor(Color.BLUE);
            currentCell = positionStack.peek();
            this.maze.getCell(currentCell.x(), currentCell.y()).setColor(Color.RED);
            return true;
        } else {
            this.finished = true; // We should be done here?
            return false;
        }
    }

    /**
     * Checks if a cell is a valid target for the DFS.
     * 
     * @param target the position of the target cell.
     */
    protected Boolean isValidTarget(Position target) {
        return (target.x() >= 0 && target.x() <= maze.getWidth()
                && target.y() >= 0 && target.y() <= maze.getHeight()
                && !visited.contains(target));
    }

    /**
     * Checks if the position has any valid targets.
     *
     * @param cell the position of the cell to check.
     */
    private Boolean hasValidTargets(Position cell) {
        return (isValidTarget(new Position(cell.x() + 1, cell.y()))
                || isValidTarget(new Position(cell.x() - 1, cell.y()))
                || isValidTarget(new Position(cell.x(), cell.y() + 1))
                || isValidTarget(new Position(cell.x(), cell.y() - 1)));
    }
}
