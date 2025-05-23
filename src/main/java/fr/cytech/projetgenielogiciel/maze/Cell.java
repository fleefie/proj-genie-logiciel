package fr.cytech.projetgenielogiciel.maze;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Class representing a cell in a maze.
 */
public class Cell implements Serializable {
    /**
     * The number of cells created.
     */
    static private Integer totalCellCount = 0;

    /**
     * The color of the cell, used for various display needs.
     */
    private State state = State.UNPROCESSED;

    /**
     * Get the current color as a JavaFX Color
     */
    public Color getColor() {
        return switch (state) {
            case UNPROCESSED -> {
                yield Color.WHITE;
            }
            case QUEUED -> {
                yield Color.YELLOW;
            }
            case PROCESSED -> {
                yield Color.BLUE;
            }
            case IN_PATH -> {
                yield Color.GREEN;
            }
            case CURRENT -> {
                yield Color.RED;
            }
        };
    }

    /**
     * Set the color using a JavaFX Color
     */
    public void setColor(Color color) {
        if (color.equals(Color.BLUE))
            state = State.PROCESSED;
        else if (color.equals(Color.RED))
            state = State.CURRENT;
        else if (color.equals(Color.WHITE))
            state = State.UNPROCESSED;
        else if (color.equals(Color.YELLOW))
            state = State.QUEUED;
        else if (color.equals(Color.GREEN))
            state = State.IN_PATH;
        else
            state = State.UNPROCESSED;
    }

    /**
     * The ID of the cell.
     */
    private final Integer id = totalCellCount++;

    /**
     * HashCode method for the Cell class.
     *
     * @return the hash code of the cell
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * Equals method for the Cell class.
     *
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    /**
     * Checks if this cell is valid.
     * Always returns true for now.
     * 
     * @return true if the cell is valid, false otherwise
     */
    public Boolean isValid() {
        return true;
    }

    /**
     * Private enum representing the state of the cell.
     */
    private enum State {
        /**
         * The cell is unprocessed.
         */
        UNPROCESSED,
        /**
         * The cell is processed.
         */
        PROCESSED,
        /**
         * The cell is currently being processed.
         */
        CURRENT,
        /**
         * The cell is part of the path.
         */
        IN_PATH,
        /**
         * The cell is queued for processing.
         */
        QUEUED;
    }

    /**
     * toString method for the Cell class.
     *
     * @return a string representation of the cell
     */
    @Override
    public String toString() {
        return "Cell [state=" + state + ", id=" + id + "]";
    }

    /**
     * Gets the ID of the cell.
     *
     * @return the ID of the cell
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the total number of cells created.
     *
     * @return the total number of cells
     */
    public static Integer getTotalCellCount() {
        return totalCellCount;
    }

    /**
     * Sets the total number of cells created.
     * Please only use this method if you know what you're doing.
     *
     * @param totalCellCount the new total number of cells
     */
    public static void setTotalCellCount(Integer totalCellCount) {
        Cell.totalCellCount = totalCellCount;
    }
}
