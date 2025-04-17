package fr.cytech.projetgenielogiciel.maze;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Class representing a cell in a maze.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor
public class Cell {
    /**
     * The number of cells created.
     */
    @Getter
    static private Integer totalCellCount = 0;

    /**
     * The ID of the cell.
     */
    @Getter
    @EqualsAndHashCode.Include
    private final Integer id = totalCellCount++;

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
