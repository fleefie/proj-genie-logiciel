package fr.cytech.projetgenielogiciel.Solver;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.builder.DfsBuilder;


import java.util.*;

public class AStarSolver implements ISolver {
    protected Maze laby;
    protected boolean solved=false;
    protected Map<Integer, Integer> gScore; // Distance from the start
    protected Map<Integer, Integer> fScore; // Total distance estimation (g + h)
    protected PriorityQueue<Cell> openSet; // Cells who need to be checked
    private final Stack<Cell> cameFrom = new Stack<Cell>(); //To build the path
    protected Cell start;
    protected Cell end;
    protected Cell current;

    /**
     * Creator of Solver class
     * 
     * @param lab take a maze that will be solved step by step
     *
     */
    public AStarSolver(Maze lab, Cell start, Cell end) {
        try {
            if (lab == null) {
                throw new IllegalArgumentException("labyrinthe null || case null");
            }
            this.current = start;
            this.start = start;
            this.end = end;
            this.laby = lab;
            this.solved = false;
            this.gScore = new HashMap<>(); // Distance start and actual pos
            this.fScore = new HashMap<>(); // Distance gScore + heuristic
            // this.cameFrom = new HashMap<>();
            this.openSet = new PriorityQueue<>(
                    Comparator.comparingInt(c -> fScore.getOrDefault(c.getId(), Integer.MAX_VALUE)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     *
     * @return lab which is the maze
     */
    public Maze getLaby() {
        return this.laby;
    }

    /**
     *
     * @param lab which is the maze you want to change into
     */
    public void setLaby(Maze lab) {
        if (lab != null) {
            this.laby = lab;
        }
    }

    /**
     * Does one step from the cell c
     * 
     *
     */
    public Boolean step() {
        current.setColor(Colors.Blue);
        if (current.equals(end)) {
            solved = true;
            cameFrom.add(current);
            return true;
        }
        Boolean isntStuck = false;
        for (Integer neighborId : laby.getAdjacencyList().getNeighbors(current.getId())) {
            Cell neighbor = findCellById(neighborId);
            if (neighbor == null)
                continue;

            if(neighbor.getColor()!=Colors.White){
                // verification si le chemin est "plus court"
                Integer ftemp = fScore.getValue(neighbor.getId());
                if(ftemp > fScore.getValue(current.getId())){
                    Cell actual = cameFrom.pop();
                    while(!actual.equals(neighbor)){
                        actual = cameFrom.pop();
                    }
                    cameFrom.add(current);
                }
            }
            else{
                isntStuck = true;

                int tentativeGScore = gScore.getOrDefault(current.getId(), Integer.MAX_VALUE) + 1;

                if (tentativeGScore < gScore.getOrDefault(neighbor.getId(), Integer.MAX_VALUE)) {
                    current.setParentId(neighbor.getId());
                    // cameFrom.put(neighbor.getId(), current.getId());
                    gScore.put(neighbor.getId(), tentativeGScore);
                    fScore.put(neighbor.getId(), tentativeGScore + (int) heuristic(neighbor));

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }

        }
        if(!isntStuck){
            current = cameFrom.pop();
        }
        else{
            cameFrom.add(current);
        }
        return true;
    }

    /**
     * Solve the maze by starting from s to f
     */
    public Boolean solve() {
        this.solved = false;
        this.gScore.clear();
        this.fScore.clear();
        // this.cameFrom.clear();
        this.openSet.clear();
        if(current == end){ //To verif if we need to atleast do 1 step
            return false;
        }

        // Initialization
        gScore.put(start.getId(), 0);
        fScore.put(start.getId(), (int) heuristic(start));
        openSet.add(start); // Add in queue

        while (!openSet.isEmpty() && !solved) {
            Cell current = openSet.poll();
            step();

        }
        if(cameFrom.contains(end)){ //If we got a solution
            while (!cameFrom.isEmpty()) {
                Cell current = cameFrom.pop();
                current.setColor(Colors.Green);
            }
            return true;
        }
        else{ //We dont have a solution
            return false;
        }
    }

    //Setup les trucs de bases
    private class HeuristicIterator implements Iterator<Boolean> {
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
        return new HeuristicIterator();
    };

    /**
     *
     * @return true when it's finished and false if it's not
     */

    public Boolean isFinished() {
        return solved;
    }

    /**
     *
     * @param c the cell on which the heuristic is based
     * @return the Manhattan Distance
     */
    public float heuristic(Cell c) {
        // A bouger dans l'héritage, j'ai juste fait pour A*
        int[] startCoords = findCoordinates(start);
        int[] endCoords = findCoordinates(end);
        int[] currentCoords = findCoordinates(c);

        if (startCoords == null || endCoords == null || currentCoords == null) {
            return Float.MAX_VALUE;
        }

        // Manhattan Distance
        return Math.abs(currentCoords[0] - endCoords[0]) + Math.abs(currentCoords[1] - endCoords[1]);
    }

    /**
     *
     * @param id
     * @return the cell corresponding to the id
     */
    private Cell findCellById(int id) {
        for (int x = 0; x <= laby.getWidth(); x++) {
            for (int y = 0; y <= laby.getHeight(); y++) {
                if (laby.getCell(x, y) != null && laby.getCell(x, y).getId() == id) {
                    return laby.getCell(x, y);
                }
            }
        }
        return null;
    }

    /**
     *
     * @param cell
     * @return [x,y] the coordinates of the cell
     */
    private int[] findCoordinates(Cell cell) {
        if (cell == null)
            return null;

        for (int x = 0; x <= laby.getWidth(); x++) {
            for (int y = 0; y <= laby.getHeight(); y++) {
                if (laby.getCell(x, y) != null && laby.getCell(x, y).equals(cell)) {
                    return new int[] { x, y };
                }
            }
        }
        return null;
    }

}
