package fr.cytech.projetgenielogiciel.maze;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class representing a cell in a maze.
 */
@ToString
@NoArgsConstructor
public class Cell {
    /**
     * The number of cells created.
     */
    @Getter
    @Setter
    static private Integer totalCellCount = 0;

    /**
     * The color of the cell, used for various display needs
     */
    @Getter
    @Setter
    private Color color = Color.WHITE;

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
}
