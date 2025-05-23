package fr.cytech.projetgenielogiciel.maze.solver;

import java.io.Serializable;
import java.util.Iterator;

import fr.cytech.projetgenielogiciel.maze.Maze;

public interface ISolver extends Iterable<Boolean>, Serializable {
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

    /**
     * Gets the maze associated to the solver.
     */
    public Maze getMaze();
}
