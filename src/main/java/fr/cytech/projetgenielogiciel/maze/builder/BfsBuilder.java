package fr.cytech.projetgenielogiciel.maze.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Deque;
import java.util.Random;
import java.util.Set;

import fr.cytech.projetgenielogiciel.maze.Direction;
import fr.cytech.projetgenielogiciel.maze.Maze;

/**
 * Implements a perfect maze builder using a BFS algorithm.
 */
public class BfsBuilder implements IBuilder {
    /**
     * Record for holding a cell's position.
     */
    record Position(Integer x, Integer y) {
    };

    /**
     * Holds the set of cells that were visited.
     */
    private final Set<Position> visited = new HashSet<Position>();

    /**
     * Whether the builder is finished building.
     */
    private Boolean finished = false;

    /**
     * The current cell of the builder, in case it needs to be paused.
     */
    private Position currentCell;

    /**
     * The queue of cells.
     */
    private final Deque<Position> positionQueue = new LinkedList<Position>();

    /**
     * Random generator.
     */
    private final Random rand;

    /**
     * Reference to the maze that the builder is working on
     */
    private final Maze maze;

    /**
     * Constructor.
     * 
     * @param maze   Reference to the maze being worked on.
     * @param startx starting X position.
     * @param starty starting Y position.
     * @param seed   seed for the RNG
     */
    public BfsBuilder(Maze maze, Integer startx, Integer starty, Integer seed) {
        this.maze = maze;
        this.currentCell = new Position(startx, starty);
        this.rand = new Random(seed);
        // Needed here due to how the step() function has to work
        this.positionQueue.offerFirst(currentCell);
        this.visited.add(currentCell);
    }

    /**
     * Create an iterator for the builder.
     *
     * @return The iterator.
     */
    @Override
    public Iterator<Boolean> iterator() {
        return new BfsIterator();
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
     * Iterator implementation for the BFS. Required to avoid self-referencing.
     */
    private class BfsIterator implements Iterator<Boolean> {
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
     * Executes a step of the BFS.
     * A step is considered over once we found a new cell to visit.
     *
     * @return whether a step could be executed.
     */
    /**
     * Executes a step of the BFS.
     * A step is considered over once we process the next cell in the queue.
     *
     * @return whether a step could be executed.
     */
    @Override
    public Boolean step() {

        currentCell = positionQueue.pollFirst();

        // Check all valid neighbors and add them to the queue
        List<Direction> directions = new ArrayList<>(Arrays.asList(
                Direction.NORTH,
                Direction.SOUTH,
                Direction.EAST,
                Direction.WEST));
        Collections.shuffle(directions, rand);

        for (Direction direction : directions) {
            Position target = new Position(
                    currentCell.x() + direction.getX(),
                    currentCell.y() + direction.getY());

            if (isValidTarget(target)) {
                // Connect the maze and enqueue the target cell
                maze.connect(currentCell.x(), currentCell.y(), direction);
                positionQueue.offerLast(target);
                visited.add(target);
            }
        }

        // If the queue is empty, we're done here
        if (positionQueue.isEmpty()) {
            this.finished = true;
            return false;
        }

        return true;
    }

    /**
     * Checks if a cell is a valid target for the BFS.
     * 
     * @param target the position of the target cell.
     */
    private Boolean isValidTarget(Position target) {
        return (target.x() >= 0 && target.x() <= maze.getWidth()
                && target.y() >= 0 && target.y() <= maze.getHeight()
                && !visited.contains(target));
    }
}
