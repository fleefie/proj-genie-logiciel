package fr.cytech.projetgenielogiciel.maze.solver;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;


import java.util.*;

public class AStarSolver implements ISolver {
    @Getter
    @Setter
    protected Maze laby;
    protected boolean solved=false;
    protected Map<Integer, Integer> gScore; // Distance from the start
    protected Map<Integer, Integer> fScore; // Total distance estimation (g + h)
    protected PriorityQueue<Cell> openSet; // Cells who need to be checked
    private Stack<Cell> cameFrom = new Stack<>(); //To build the path
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
            this.openSet = new PriorityQueue<>(
                    Comparator.comparingInt(c -> fScore.getOrDefault(c.getId(), Integer.MAX_VALUE)));

            this.solved = false;
            this.gScore.clear();
            this.fScore.clear();
            // this.cameFrom.clear();
            this.openSet.clear();

            // Initialization
            gScore.put(start.getId(), 0);
            fScore.put(start.getId(), (int) heuristic(start));
            openSet.add(start); // Add in queue
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }



    /**
     * Does one step from the cell c
     * 
     *
     */
    public Boolean step() {
        current = openSet.poll();
        current.setColor(Color.RED);
        System.out.println("actuel "+current);
        System.out.println("fScore "+fScore.get(current.getId()));
        System.out.println("gScore "+gScore.get(current.getId()));
        if(current.getId()==end.getId()){
            System.out.println("FINISHED");
            solved = true;
            cameFrom.add(current);
            return true;
        }
        Boolean isntStuck = false;

        for (Integer neighborId : laby.getAdjacencyList().getNeighbors(current.getId())) {
            Cell neighbor = findCellById(neighborId);
            System.out.println("voisin "+neighbor);
            if (neighbor == null)
                continue;

            if(neighbor.getColor()==Color.BLUE){
                // verification si le chemin est "plus court"
                Integer fTemp = fScore.get(neighbor.getId());
                if(fTemp > fScore.get(current.getId())){
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

                gScore.put(neighbor.getId(), tentativeGScore);
                fScore.put(neighbor.getId(), tentativeGScore + heuristic(neighbor));

                openSet.add(neighbor);
                System.out.println(neighbor+" ajouté");


            }

        }
        current.setColor(Color.BLUE);
        if(!isntStuck){
            current = cameFrom.pop();
        }
        else{
            cameFrom.add(current);
        }
        System.out.println("openset "+openSet);
        System.out.println("cameFrom "+cameFrom);
        System.out.println();
        return true;
    }

    /**
     * Solve the maze by starting from s to f
     */
    public Boolean solve() {
        if(current == end){ //To verif if we need to atleast do 1 step
            return false;
        }

        while (!openSet.isEmpty() && !solved) {
            current = openSet.poll();
            step();

        }
        if(solved){ //If we got a solution
            while (!cameFrom.isEmpty()) {
                Cell current = cameFrom.pop();
                current.setColor(Color.GREEN);
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
    }

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
    public int heuristic(Cell c) {
        // A bouger dans l'héritage, j'ai juste fait pour A*
        int[] startCoords = findCoordinates(start);
        int[] endCoords = findCoordinates(end);
        int[] currentCoords = findCoordinates(c);

        if (startCoords == null || endCoords == null || currentCoords == null) {
            return Integer.MAX_VALUE;
        }

        // Manhattan Distance
        System.out.println(currentCoords[0]+" "+currentCoords[1]);
        System.out.println(endCoords[0]+" "+endCoords[1]);

        return Math.abs(currentCoords[0] - endCoords[0]) + Math.abs(currentCoords[1] - endCoords[1]);
    }

    /**
     *
     * @param id the id of the cell
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
     * @param cell the cell in the maze
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
