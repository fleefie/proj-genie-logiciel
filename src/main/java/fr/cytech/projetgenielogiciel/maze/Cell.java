package fr.cytech.projetgenielogiciel.maze;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Class representing a cell in a maze.
 */
@ToString
@NoArgsConstructor
public class Cell implements Serializable {
    /**
     * The number of cells created.
     */
    @Getter
    @Setter
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
			case UNPROCESSED -> {yield Color.WHITE;}
			case QUEUED -> {yield Color.YELLOW;}
			case PROCESSED -> {yield Color.BLUE;}
			case IN_PATH -> {yield Color.GREEN;}
			case CURRENT -> {yield Color.RED;}
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
    @Getter
    private final Integer id = totalCellCount++;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

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
	

	private enum State implements Serializable {
		UNPROCESSED,
		PROCESSED,
		CURRENT,
		IN_PATH,
		QUEUED;
	}
}
