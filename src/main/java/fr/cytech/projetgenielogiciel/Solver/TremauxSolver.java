package fr.cytech.projetgenielogiciel.Solver;
import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;

public class TremauxSolver implements ISolver {
    protected Maze laby;
    protected boolean solved;


    /** Creator of Solver class
     * @param lab take a maze that will be solved step by step
     *
     */
    public TremauxSolver(Maze lab){
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
    public void step(Cell c){

    }

    /** Solve the maze by starting from s to f
     * @param s start of the maze
     * @param f end of the maze
     */
    public void solve(Cell s,Cell f){

    }

    // ???
    public boolean isFinished(){
        return false;
    }

    public float heuristic(Cell c){
        return 0;
    }


}
