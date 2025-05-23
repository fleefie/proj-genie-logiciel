package fr.cytech.projetgenielogiciel.maze.builder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Direction;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.Maze.Position;
import javafx.scene.paint.Color;
import lombok.Getter;

/**
 * Maze builder using Eller's set theory based algorithm.
 *
 * Source:
 * https://cantwell-tom.medium.com/ellers-maze-algorithm-in-javascript-2e5742c1a4cd
 *
 *
 * Step 1: Initialize the first row.
 * Step 2: For each column in that row, if it
 * doesn't have a cell, create one and add it to its own set. Each cell in the
 * row should be part of a set at this point.
 * Step 3: Going from left to right,
 * if the current cell and the next cell are in different sets, randomly decide
 * to connect to the next cell. If they connect, merge the cells' sets
 * together.
 * Step 4: Initialize the next row. For each set in the current row,
 * connect downward for at least one cell per set, adding that cell to the set
 * it connected from.
 * Step 5: Move to the next row and repeat steps 2 through 4.
 * Step 6: When on the last row, repeat step 3 but remove the randomness,
 * always connecting to the next cell if it's in a different set. Don't make any
 * downward connections.
 */
public class EllerBuilder implements IBuilder, Serializable {

    private Boolean finished;
    @Getter
    private final Maze maze;
    private final Random rand;

    public EllerBuilder(Maze maze, Integer startx, Integer starty, Integer seed) {
        this.maze = maze;
        this.maze.getCell(startx, starty).setColor(Color.RED);
        this.rand = new Random(seed);

        this.finished = false;
    }

    @Override
    public Boolean step() {
        return null;
        // TODO: Actually make this lol
    }

    @Override
    public Boolean isFinished() {
        return finished;
    }

    @Override
    public Iterator<Boolean> iterator() {
        return new EllerIterator();
    }

    @Override
    public Boolean build() {
        Boolean ret = false;
        for (Boolean stepStatus : this)
            ret = stepStatus;
        return ret;
    }

    private class EllerIterator implements Iterator<Boolean> {
        @Override
        public boolean hasNext() {
            return !finished;
        }

        @Override
        public Boolean next() {
            return step();
        }
    }
}
