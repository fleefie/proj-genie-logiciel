package fr.cytech.projetgenielogiciel.Solver;

import fr.cytech.projetgenielogiciel.maze.Cell;

interface ISolver {

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

    // ???
    public boolean isFinished(); // Return if it is solved or not (litteraly getSolved)

}
