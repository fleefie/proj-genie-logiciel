package fr.cytech.projetgenielogiciel.maze.solver;
import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;

import java.util.Iterator;

public class TremauxSolver implements ISolver {
    protected Maze laby;
    protected boolean solved;


    /** Creator of Solver class
     * @param lab take a maze that will be solved step by step
     *
     */
    public TremauxSolver(Maze lab, Cell start, Cell end){
        try{
            if(lab == null){
                throw new IllegalArgumentException("labyrinthe null");
            }
            this.laby = lab;
            this.solved = false;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    public Maze getLaby(){
        return this.laby;
    }

    public void setLaby(Maze lab) {
        if (lab != null) {
            this.laby = lab;
        }
    }


    /** Does one step from the cell c
     * @param c actual position in the maze
     */
    public Boolean step(){
        return true;
    }

    /** Solve the maze by starting from s to f
     */
    public Boolean solve(){
        return true;
    }

    //Setup les trucs de bases
    private class TremauxIterator implements Iterator<Boolean> {
        @Override
        public boolean hasNext() {
            return !solved;
        }

        @Override
        public Boolean next() {
            if (solved) {
                return false;
            }
            return step();
        }
    }
    /**
     * Create an iterator for the solver.
     *
     * @return The iterator.
     */
    @Override
    public Iterator<Boolean> iterator() {
        return new TremauxIterator();
    };

    // ???
    public Boolean isFinished(){
        return false;
    }

    public float heuristic(Cell c){
        return 0;
    }


}
