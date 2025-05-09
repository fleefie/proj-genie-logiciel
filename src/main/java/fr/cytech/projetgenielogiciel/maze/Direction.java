package fr.cytech.projetgenielogiciel.maze;

/**
 * Enum representing the four cardinal directions.
 *
 * Directions are represented with the following 2D vectors:
 * - NORTH: (0, 1)
 * - EAST: (-1, 0)
 * - SOUTH: (0, -1)
 * - WEST: (1, 0)
 * Make sure to adhere to this standard when using this enum to avoid
 * inverting vectors.
 */
// It made more sense to me to use this basis :-)
public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    /**
     * Get the opposite direction.
     *
     * @return the opposite direction
     */
    public Direction opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }

    /**
     * Get the x component of the 2D vector representing the direction.
     */
    public int getX() {
        return switch (this) {
            case NORTH -> 0;
            case SOUTH -> 0;
            case EAST -> 1;
            case WEST -> -1;
        };
    }

    /**
     * Get the y component of the 2D vector representing the direction.
     */
    public int getY() {
        return switch (this) {
            case NORTH -> 1;
            case SOUTH -> -1;
            case EAST -> 0;
            case WEST -> 0;
        };
    }
}
