package fr.cytech.projetgenielogiciel.maze;

import java.io.Serializable;
import java.util.Arrays;

import javafx.scene.paint.Color;

/**
 * Class representing a maze.
 *
 * A maze is a 2D grid of cells, made out of cells.
 */
public class Maze implements Serializable {
    /**
     * The width of the maze.
     */
    private final Integer width;

    /**
     * The height of the maze.
     */
    private final Integer height;

    /**
     * The cells of the maze.
     */
    private final Cell[][] cells;

    /**
     * A record holding an (X, Y) position vector in the maze.
     */
    public record Position(Integer x, Integer y) implements Serializable {
    };

    /**
     * Adjacency list for the maze.
     *
     * This should be used with the methods of this class, as the adjacency list
     * does not provide any sense of directionality by itself.
     * It is nonetheless accessible read-only for debugging purposes, or if
     * an algorithm needs it and it is preferable to not rely on topology.
     */
    private final AdjacencyList adjacencyList = new AdjacencyList();

    /**
     * Constructor for a Maze, without specifying the cells.
     *
     * @param width  the width of the maze (number of cells)
     * @param height the height of the maze (number of cells)
     */
    public Maze(Integer width, Integer height) {
        this.width = width - 1;
        this.height = height - 1;
        this.cells = new Cell[width][height];

        for (Integer y = 0; y < height; y++) {
            for (Integer x = 0; x < width; x++) {
                cells[x][y] = new Cell();
            }
        }
    }

    /**
     * Constructor for a Maze, with the cells already created.
     *
     * @param cells the cells of the maze
     */
    public Maze(Cell[][] cells) {
        this.width = cells.length;
        this.height = cells[0].length;
        this.cells = cells;
    }

    /**
     * Checks if a cell is valid.
     * This first checks if its position is in the maze,
     * and then cehcks if the cell itself is valid.
     * 
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @return true if the cell is valid, false otherwise
     */
    public boolean isValidCell(Integer x, Integer y) {
        if (x < 0 || x > width || y < 0 || y > height) {
            return false;
        }
        if (cells[x][y] == null) {
            return false;
        }
        return cells[x][y].isValid();
    }

    /**
     * Connects two cells in the maze, bidirectionally.
     * Silently ignores the connection if the target cell is invalid.
     *
     * @param sourceX   the x coordinate of the source cell
     * @param sourceY   the y coordinate of the source cell
     * @param direction the direction to connect to
     */
    public void connect(Integer sourceX, Integer sourceY, Direction direction) {
        Integer targetX = sourceX + direction.getX();
        Integer targetY = sourceY + direction.getY();

        if (isValidCell(targetX, targetY)) {
            adjacencyList.addEdge(cells[sourceX][sourceY].getId(), cells[targetX][targetY].getId());
        }
    }

    /**
     * Connects two cells in the maze, unidirectionally.
     * Silently ignores the connection if the target cell is invalid.
     *
     * @param sourceX   the x coordinate of the source cell
     * @param sourceY   the y coordinate of the source cell
     * @param direction the direction to connect to
     */
    public void connectOneWay(Integer sourceX, Integer sourceY, Direction direction) {
        Integer targetX = sourceX + direction.getX();
        Integer targetY = sourceY + direction.getY();

        if (isValidCell(targetX, targetY)) {
            adjacencyList.addEdgeOneWay(cells[sourceX][sourceY].getId(), cells[targetX][targetY].getId());
        }
    }

    /**
     * Disconnects two cells in the maze, bidirectionally.
     * Silently ignores the disconnection if the target cell is invalid.
     *
     * @param sourceX   the x coordinate of the source cell
     * @param sourceY   the y coordinate of the source cell
     * @param direction the direction to disconnect from
     */
    public void disconnect(Integer sourceX, Integer sourceY, Direction direction) {
        Integer targetX = sourceX + direction.getX();
        Integer targetY = sourceY + direction.getY();

        if (isValidCell(targetX, targetY)) {
            adjacencyList.removeEdge(cells[sourceX][sourceY].getId(), cells[targetX][targetY].getId());
        }
    }

    /**
     * Disconnects two cells in the maze, unidirectionally.
     * Silently ignores the disconnection if the target cell is invalid.
     *
     * @param sourceX   the x coordinate of the source cell
     * @param sourceY   the y coordinate of the source cell
     * @param direction the direction to disconnect from
     */
    public void disconnectOneWay(Integer sourceX, Integer sourceY, Direction direction) {
        Integer targetX = sourceX + direction.getX();
        Integer targetY = sourceY + direction.getY();

        if (isValidCell(targetX, targetY)) {
            adjacencyList.removeEdgeOneWay(cells[sourceX][sourceY].getId(), cells[targetX][targetY].getId());
        }
    }

    /**
     * Check if a cell has a connection in a given direction.
     *
     * @param x         the x coordinate of the cell
     * @param y         the y coordinate of the cell
     * @param direction the direction to check
     * @return true if the cell has a connection in the given direction, false
     *         otherwise
     */
    public boolean hasConnection(Integer x, Integer y, Direction direction) {
        Integer targetX = x + direction.getX();
        Integer targetY = y + direction.getY();

        if (isValidCell(targetX, targetY)) {
            return adjacencyList.hasEdge(cells[x][y].getId(), cells[targetX][targetY].getId());
        }
        return false;
    }

    /**
     * ToString implementation.
     * Formatted as a list of coordinates followed by their connections
     * {[((x, y), [NORTH, SOUTH, ... ]), ...]}
     *
     * @return the string representation of the maze
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Integer y = 0; y < width + 1; y++) {
            for (Integer x = 0; x < height + 1; x++) {
                sb.append("((").append(x).append(", ").append(y).append("), [");
                for (Direction direction : Direction.values()) {
                    if (hasConnection(x, y, direction)) {
                        sb.append(direction).append(", ");
                    }
                }
                if (sb.charAt(sb.length() - 2) == ',') {
                    sb.setLength(sb.length() - 2); // Remove trailing comma and space
                }
                sb.append("]), ");
            }
        }
        if (sb.charAt(sb.length() - 2) == ',') {
            sb.setLength(sb.length() - 2); // Remove trailing comma and space
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Creates a formatted display of the maze.
     * This display is a grid of cells, represented by their ID.
     * The grid will be aligned to the largest ID.
     * Connections are represented by '|' and '-'.
     * Connections are displayed between cells, and for the sake of simplicity
     * of implementation, are displayed in the leftmost position for vertical
     * connections, relative to the size of the ID.
     * Unidirectional connections are represented by arrows.
     * 
     * Example:
     * <code>
     * 004-005>006
     * |   v
     * 001 002-003
     * </code>
     *
     * @return the string representation of the maze, formated.
     */

    /*
     * HACK: This has been GPT'd because I was too lazy to do it myself.
     * We probably should reimplement it to make it more readable.
     */
    public String toFormatedString() {
        // Find the maximum ID length for proper alignment
        int maxLength = 0;
        for (int x = 0; x <= width; x++) {
            for (int y = 0; y <= height; y++) {
                int idLength = cells[x][y].getId().toString().length();
                if (idLength > maxLength) {
                    maxLength = idLength;
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        // Process the maze row by row, starting from the top
        for (int y = height; y >= 0; y--) {
            // First build the row with cells and horizontal connections
            StringBuilder cellRow = new StringBuilder();
            for (int x = 0; x <= width; x++) {
                // Pad the cell ID to align with maxLength
                String id = String.format("%0" + maxLength + "d", cells[x][y].getId());
                cellRow.append(id);

                // Check for horizontal connection to the next cell
                if (x < width) {
                    boolean eastConn = hasConnection(x, y, Direction.LEFT);
                    boolean westConn = hasConnection(x + 1, y, Direction.RIGHT);

                    if (eastConn && westConn) {
                        cellRow.append('-');
                    } else if (eastConn) {
                        cellRow.append('>');
                    } else if (westConn) {
                        cellRow.append('<');
                    } else {
                        cellRow.append(' ');
                    }
                }
            }
            sb.append(cellRow).append('\n');

            // Then build the row with vertical connections, but only if not the last row
            if (y > 0) {
                StringBuilder connRow = new StringBuilder();
                for (int x = 0; x <= width; x++) {
                    // Check for vertical connection to the cell below
                    boolean southConn = hasConnection(x, y, Direction.DOWN);
                    boolean northConn = hasConnection(x, y - 1, Direction.UP);

                    if (southConn && northConn) {
                        connRow.append('|');
                    } else if (southConn) {
                        connRow.append('v');
                    } else if (northConn) {
                        connRow.append('^');
                    } else {
                        connRow.append(' ');
                    }

                    // Add spacing to align with the cells above
                    connRow.append(" ".repeat(maxLength));
                }
                sb.append(connRow).append('\n');
            }
        }

        return sb.toString();
    }

    /**
     * Get a cell from the maze.
     *
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @return the cell at the given coordinates
     * @apiNote This will later return an Optional<Cell> to avoid null checks.
     */
    public Cell getCell(Integer x, Integer y) {
        if (isValidCell(x, y)) {
            return cells[x][y];
        }
        return null;
    }

    /**
     * Reset the color of every cell in the maze.
     */
    public void resetColors() {
        for (Cell[] cells : this.cells) {
            for (Cell cell : cells) {
                cell.setColor(Color.WHITE);
            }
        }
    }

    /**
     * Finds the coordinates of a cell.
     *
     * @param cell the cell in the maze
     * @return [x,y] the coordinates of the cell
     */
    public Position findCoordinates(Cell cell) {
        if (cell == null)
            return null;

        for (int x = 0; x <= this.getWidth(); x++) {
            for (int y = 0; y <= this.getHeight(); y++) {
                if (this.getCell(x, y) != null && this.getCell(x, y).equals(cell)) {
                    return new Position(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Find a cell in the maze by its ID.
     *
     * @param id the id of the cell
     * @return the cell corresponding to the id
     */
    public Cell findCellById(Integer id) {
        for (int x = 0; x <= this.getWidth(); x++) {
            for (int y = 0; y <= this.getHeight(); y++) {
                if (this.getCell(x, y) != null && this.getCell(x, y).getId() == id) {
                    return this.getCell(x, y);
                }
            }
        }
        return null;
    }

    /**
     * HashCode implementation for the maze.
     *
     * @return the hashcode of the maze
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((width == null) ? 0 : width.hashCode());
        result = prime * result + ((height == null) ? 0 : height.hashCode());
        result = prime * result + Arrays.deepHashCode(cells);
        result = prime * result + ((adjacencyList == null) ? 0 : adjacencyList.hashCode());
        return result;
    }

    /**
     * Equals implementation for the Maze.
     *
     * @param obj the object to compare to
     * @return true if the objects are equivalent, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Maze other = (Maze) obj;
        if (width == null) {
            if (other.width != null)
                return false;
        } else if (!width.equals(other.width))
            return false;
        if (height == null) {
            if (other.height != null)
                return false;
        } else if (!height.equals(other.height))
            return false;
        if (!Arrays.deepEquals(cells, other.cells))
            return false;
        if (adjacencyList == null) {
            if (other.adjacencyList != null)
                return false;
        } else if (!adjacencyList.equals(other.adjacencyList))
            return false;
        return true;
    }

    /**
     * Get the width of the maze.
     *
     * @return the width of the maze
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Get the height of the maze.
     *
     * @return the height of the maze
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Get the cells of the maze.
     *
     * @return the cells of the maze
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Get the adjacency list of the maze.
     *
     * @return the adjacency list of the maze
     */
    public AdjacencyList getAdjacencyList() {
        return adjacencyList;
    }
}
