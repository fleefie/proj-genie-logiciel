package fr.cytech.projetgenielogiciel.maze.solver;

import fr.cytech.projetgenielogiciel.maze.Cell;

import java.util.Iterator;

public interface ISolver extends Iterable<Boolean> {
    /**
     * Runs a single step of the building process.
     *
     * @return Whether a step could be processed
     */
    public Boolean step();


    /**
     * Returns an iterator of the building steps.
     *
     * @return An interator returning whether each step iteration
     *         was able to be processed
     */
    public Iterator<Boolean> iterator();

    /**
     * Returns whether the builder is done building or if there is
     * still work to be done.
     *
     * @return Whether the builder has finished building
     */
    public Boolean isFinished();

    //no need for toString & equals
    /** Does one step from the cell c
     * @param c actual position in the maze
     */
    public void step(Cell c);

    /** Solve the maze by starting from s to f
     * @param s start of the maze
     * @param f end of the maze
     */
    public void solve(Cell s,Cell f);


}
