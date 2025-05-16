package fr.cytech.projetgenielogiciel.Solver;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;

import java.util.*;

public class AStarSolver implements ISolver {
    protected Maze laby;
    protected boolean solved;
    protected Map<Integer, Integer> gScore; // Distance from the start
    protected Map<Integer, Integer> fScore; // Total distance estimation (g + h)
    // protected Map<Integer, Integer> cameFrom; // To build the path
    protected PriorityQueue<Cell> openSet; // Cells who need to be checked
    protected Cell start;
    protected Cell end;

    /**
     * Creator of Solver class
     * 
     * @param lab take a maze that will be solved step by step
     *
     */
    public AStarSolver(Maze lab) {
        try {
            if (lab == null) {
                throw new IllegalArgumentException("labyrinthe null || case null");
            }
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
     * @param current actual position in the maze
     */
    public void step(Cell current) {
        current.isVisited();
        if (current.equals(end)) {
            solved = true;
            current.isInPath();
            return;
        }

        for (Integer neighborId : laby.getAdjacencyList().getNeighbors(current.getId())) {
            Cell neighbor = findCellById(neighborId);
            if (neighbor == null)
                continue;

            neighbor.setParentId(current.getId());

            // The distance between two adjacent cells is 1
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

    /**
     * Solve the maze by starting from s to f
     * 
     * @param s start of the maze
     * @param f end of the maze
     */
    public void solve(Cell s, Cell f) {
        this.start = s;
        this.end = f;
        this.solved = false;
        this.gScore.clear();
        this.fScore.clear();
        // this.cameFrom.clear();
        this.openSet.clear();

        // Initialization
        gScore.put(s.getId(), 0);
        fScore.put(s.getId(), (int) heuristic(s));
        openSet.add(s); // Add in queue

        while (!openSet.isEmpty() && !solved) {
            Cell current = openSet.poll();
            step(current);

        }

        // la on fait le chemin

        Cell current = f;
        while (!current.equals(s)) {
            current = current.getParentId();
            current.isInPath();
        }

    }

    /**
     *
     * @return true when it's finished and false if it's not
     */
    public boolean isFinished() {
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
