package fr.cytech.projetgenielogiciel.maze.builder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fr.cytech.projetgenielogiciel.maze.Direction;
import fr.cytech.projetgenielogiciel.maze.Maze;
import javafx.scene.paint.Color;

/**
 * Class to build an imperfect maze using a DFS as a base.
 */
public class ImperfectDfsBuilder extends DfsBuilder {

    /**
     * Probability of opening a wall during the imperfect phase.
     */
    private final Double wallOpenProbability;

    /**
     * Phase of the algorithm:
     * 0 = DFS
     * 1 = Imperfect
     */
    private Integer phase = 0;

    /**
     * Current X position in the maze for the imperfect phase.
     */
    private Integer currentX = 0;

    /**
     * Current Y position in the maze for the imperfect phase.
     */
    private Integer currentY = 0;

    /**
     * Random number generator for the imperfect phase.
     */
    private final Random rand;

    /**
     * Constructor for the ImperfectDfsBuilder.
     *
     * @param maze                The maze to build.
     * @param startx              The starting x position.
     * @param starty              The starting y position.
     * @param seed                The seed for the random number generator.
     * @param wallOpenProbability The probability of opening a wall during the
     *                            imperfect phase.
     */
    public ImperfectDfsBuilder(Maze maze, Integer startx, Integer starty, Integer seed, Integer wallOpenProbability) {
        super(maze, startx, starty, seed);
        this.wallOpenProbability = wallOpenProbability / 100.0;
        this.rand = new Random(seed);
    }

    @Override
    public Boolean step() {
        // Phase 0: DFS
        if (phase == 0) {
            if (super.step())
                return true;
            finished = false;
            phase = 1;
            currentX = 0;
            currentY = 0;
            maze.resetColors();
            maze.getCell(currentX, currentY).setColor(Color.RED);
        }

        // Phase 1: Imperfection phase
        if (super.finished == false) {
            if (rand.nextDouble() < wallOpenProbability) {
                List<Direction> dirs = Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
                Collections.shuffle(dirs, rand);
                Direction d = dirs.get(0);
                Integer nx = currentX + d.getX();
                Integer ny = currentY + d.getY();
                if (nx >= 0 && nx <= maze.getWidth() && ny >= 0 && ny <= maze.getHeight()) {
                    if (!maze.hasConnection(currentX, currentY, d)) {
                        maze.connect(currentX, currentY, d);
                    }
                }
            }

            maze.getCell(currentX, currentY).setColor(Color.BLUE);

            currentX++;
            if (currentX > maze.getWidth()) {
                currentX = 0;
                currentY++;
                if (currentY > maze.getHeight()) {
                    super.finished = true;
                    return true;
                }
            }

            maze.getCell(currentX, currentY).setColor(Color.RED);

            super.finished = false;
            return true;
        }

        return false;
    }
}
