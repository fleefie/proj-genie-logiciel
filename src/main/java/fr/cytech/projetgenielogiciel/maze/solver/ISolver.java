package fr.cytech.projetgenielogiciel.maze.solver;

import fr.cytech.projetgenielogiciel.maze.Cell;

import java.util.Iterator;

public interface ISolver extends Iterable<Boolean> {
    /**
     * Runs a single step of the solving process.
     *
     * @return Whether a step could be processed
     */
    public Boolean step();

    /**
     * Runs the entire rest of the solving process.
     *
     * @return Whether the maze could be fully solved.
     */
    public Boolean solve();

    /**
     * Returns an iterator of the solve steps.
     *
     * @return An interator returning whether each step iteration
     *         was able to be processed
     */
    public Iterator<Boolean> iterator();

    /**
     * Returns whether the solver is done solving or if there is
     * still work to be done.
     *
     * @return Whether the solver has finished building
     */
    public Boolean isFinished();
}
