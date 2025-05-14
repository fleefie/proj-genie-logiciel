package fr.cytech.projetgenielogiciel.maze.builder;

import java.util.Iterator;

/**
 * Interface defining a maze builder.
 * A maze builder is not a constructor, rather,
 * it generates a valid maze, depending on the implementation.
 * See: DfsBuilder and DfsBuilderImperfect.
 *
 * @author fleefie
 */
public interface IBuilder extends Iterable<Boolean> {
    /**
     * Runs a single step of the building process.
     *
     * @return Whether a step could be processed
     */
    public Boolean step();

    /**
     * Runs the entire rest of the building process.
     *
     * @return Whether the maze could be fully generated.
     */
    public Boolean build();

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
}
