package fr.cytech.projetgenielogiciel.maze.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.cytech.projetgenielogiciel.maze.Direction;
import fr.cytech.projetgenielogiciel.maze.Maze;
import javafx.scene.paint.Color;

/**
 * Implements an imperfect maze builder using a DFS algorithm as a base.
 */
public class ImperfectDfsBuilder extends DfsBuilder {
    /**
     * Number of walls that will be change randomly.
     */
    private Integer numberOfChangedWall;

    /**
     * List of the 4 different possible directions.
     */
    private final List<Direction> directions = new ArrayList<>(Arrays.asList(
            Direction.NORTH,
            Direction.SOUTH,
            Direction.EAST,
            Direction.WEST));

    /**
     * Constructor.
     *
     * @param maze          Reference to the maze being worked on.
     * @param startx        starting X position.
     * @param starty        starting Y position.
     * @param seed          seed for the RNG
     * @param percentOfWall percent of walls that will be changed
     */
    public ImperfectDfsBuilder (Maze maze, Integer startx, Integer starty, Integer seed, Integer percentOfWall) {
        super(maze, startx, starty, seed);
        this.numberOfChangedWall = (startx-1)*(starty-1)*percentOfWall/100;
    }

    /**
     * Executes a step of the DFS.
     * A step is considered over once we found a new cell to visit.
     * Or changes an internal wall of the maze.
     * A step is considered over once a connection between 2 cells has been changed.
     *
     * @return whether a step could be executed.
     */
    @Override
    public Boolean step (){
        if (super.step()){
            return true;
        }
        else {
            if (this.numberOfChangedWall>0) {
                int x = getRand().nextInt(this.maze.getWidth());
                int y = getRand().nextInt(this.maze.getHeight());
                Position target;
                int indexDirection;
                do {
                    indexDirection = getRand().nextInt(4);
                    target = new Position(
                            x + directions.get(indexDirection).getX(),
                            y + directions.get(indexDirection).getY());
                } while (!isValidTarget(target));
                this.maze.getCell(target.x(), target.y()).setColor(Color.RED);
                if (this.maze.hasConnection(x, y, directions.get(indexDirection))){
                    this.maze.disconnect(x, y, directions.get(indexDirection));
                } else {
                    this.maze.connect(x, y, directions.get(indexDirection));
                }
                --this.numberOfChangedWall;
                this.maze.getCell(target.x(), target.y()).setColor(Color.GREEN);
                return true;
            }
            else {
                return false;
            }
        }
    }

    /**
     * Whether the building process is finished.
     *
     * @return if the building process is finished
     */
    @Override
    public Boolean isFinished(){
        return ((this.numberOfChangedWall == 0) && getFinished());
    }

}
