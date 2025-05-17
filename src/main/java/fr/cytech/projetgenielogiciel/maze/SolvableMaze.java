package fr.cytech.projetgenielogiciel.maze;

import java.util.Iterator;

import fr.cytech.projetgenielogiciel.maze.builder.IBuilder;
import fr.cytech.projetgenielogiciel.maze.solver.ISolver;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class representing a Maze along a building and solving strategy.
 */
@AllArgsConstructor
public class SolvableMaze {
    /**
     * The maze to be built and solved.
     */
    @Getter
    private final Maze maze;

    /**
     * The builder strategy to build the maze.
     */
    private final IBuilder builder;

    /**
     * The solver strategy to solve the maze.
     */
    private final ISolver solver;

    /**
     * Whether the maze is being solved or built.
     * Only used to reset the colors for now.
     */
    private Boolean isBeingBuilt = false;

    /**
     * Builds the maze using the builder strategy.
     *
     * @return true if a building step could be performed, false otherwise.
     */
    public Boolean build() {
        if (this.isBeingBuilt == false) {
            this.maze.resetColors();
            this.isBeingBuilt = true;
        }
        return builder.build();
    }

    /**
     * Solves the maze using the solver strategy.
     *
     * @return true if a solving step could be performed, false otherwise.
     */
    public Boolean solve() {
        if (this.isBeingBuilt == true) {
            this.maze.resetColors();
            this.isBeingBuilt = false;
        }
        return solver.solve();
    }

    /**
     * Builds the maze using the builder strategy for a single step.
     *
     * @return true if a building step could be performed, false otherwise.
     */
    public Boolean BuildStep() {
        if (this.isBeingBuilt == false) {
            this.maze.resetColors();
            this.isBeingBuilt = true;
        }
        return builder.step();
    }

    /**
     * Solves the maze using the solver strategy for a single step.
     *
     * @return true if a solving step could be performed, false otherwise.
     */
    public Boolean SolveStep() {
        if (this.isBeingBuilt == true) {
            this.maze.resetColors();
            this.isBeingBuilt = false;
        }
        return solver.step();
    }

    /**
     * Returns an iterator for the builder strategy.
     *
     * @return an iterator for the builder strategy.
     */
    public Iterator<Boolean> builderIterator() {
        return builder.iterator();
    }

    /**
     * Returns an iterator for the solver strategy.
     *
     * @return an iterator for the solver strategy.
     */
    public Iterator<Boolean> solverIterator() {
        return solver.iterator();
    }

    /**
     * Returns the current step of the builder strategy.
     *
     * @return the current step of the builder strategy.
     */
    public Boolean isBuilderFinished() {

        return builder.isFinished();
    }

    /**
     * Returns the current step of the solver strategy.
     *
     * @return the current step of the solver strategy.
     */
    public Boolean isSolverFinished() {
        return solver.isFinished();
    }

    /**
     * Reset the maze's colors.
     */
    public void resetColors() {
        maze.resetColors();
    }
}
