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
    /**
     * Up direction.
     */
    UP,
    /**
     * Left direction.
     */
    LEFT,
    /**
     * Down direction.
     */
    DOWN,
    /**
     * Right direction.
     */
    RIGHT;

    /**
     * Get the opposite direction.
     *
     * @return the opposite direction
     */
    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

    /**
     * Get the x component of the 2D vector representing the direction.
     */
    public int getX() {
        return switch (this) {
            case UP -> 0;
            case DOWN -> 0;
            case LEFT -> 1;
            case RIGHT -> -1;
        };
    }

    /**
     * Get the y component of the 2D vector representing the direction.
     */
    public int getY() {
        return switch (this) {
            case UP -> 1;
            case DOWN -> -1;
            case LEFT -> 0;
            case RIGHT -> 0;
        };
    }

    /**
     * Create a direction from a vector.
     */
    public static Direction fromVector(Integer x, Integer y) {
        if (x == y)
            throw new IllegalArgumentException("A direction may not be diagonal or null");

        if (x != -1 && x != 1 && x != 0 && y != 1 && y != -1 && y != 0)
            throw new IllegalArgumentException("A direction may only contain unit vectors");

        if (x == 1)
            return Direction.LEFT;
        if (x == -1)
            return Direction.RIGHT;
        if (y == 1)
            return Direction.UP;
        if (y == -1)
            return Direction.DOWN;

        throw new IllegalArgumentException("Bad direction vector arguments");
    }
}
