package fr.cytech.projetgenielogiciel.maze;

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
