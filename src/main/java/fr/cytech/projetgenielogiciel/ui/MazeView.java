package fr.cytech.projetgenielogiciel.ui;

import java.util.HashMap;
import java.util.Map;

import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Direction;
import fr.cytech.projetgenielogiciel.maze.Maze;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * JavaFX component to visualize a Maze.
 * Written with 3 AM Energy
 */
public class MazeView extends Pane {
    /**
     * Reference to the maze that the View is displaying.
     */
    private Maze maze;
    /**
     * The cell views based on their coordinates.
     */
    private final CellView[][] cellViewMap;

    private final Color wallColor = Color.BLACK;
    private double cellSize;

    /**
     * Creates a new MazeView component.
     * 
     * @param maze the maze to visualize
     */
    public MazeView(Maze maze) {
        this.maze = maze;
        this.cellViewMap = new CellView[maze.getWidth() + 1][maze.getHeight() + 1];
        this.widthProperty().addListener((obs, oldVal, newVal) -> resize());
        this.heightProperty().addListener((obs, oldVal, newVal) -> resize());
        initializeMazeView();
    }

    /**
     * Initializes the MazeView with CellViews based on the maze.
     */
    private void initializeMazeView() {
        getChildren().clear();

        for (int y = 0; y <= maze.getHeight(); y++) {
            for (int x = 0; x <= maze.getWidth(); x++) {
                Cell cell = maze.getCell(x, y);
                if (cell != null) {
                    cellViewMap[x][y] = new CellView(x, y);
                    getChildren().add(cellViewMap[x][y]);
                }
            }
        }

        resize();
    }

    /**
     * Updates the MazeView.
     */
    public void update() {
        for (CellView[] cellViews : this.cellViewMap) {
            for (CellView cellView : cellViews) {
                cellView.updateColor();
                cellView.updateWalls();
            }
        }
    }

    /**
     * Resizes all cells to fit the current pane size.
     */
    private void resize() {
        double width = getWidth();
        double height = getHeight();

        if (width <= 0 || height <= 0 || maze == null)
            return;

        cellSize = Math.min(
                width / (maze.getWidth() + 1),
                height / (maze.getHeight() + 1));

        for (CellView[] cellViews : cellViewMap)
            for (CellView cellView : cellViews)
                cellView.resize();
    }

    /**
     * Represents a single cell in the maze visualization.
     */
    private class CellView extends Region {
        /**
         * X position of the cell
         */
        private final int x;
        /**
         * Y position of the cell
         */
        private final int y;
        /**
         * The background rectangle
         */
        private final Rectangle background;
        /**
         * Map of directions to the corresponding wall.
         * A hashmap feels overkill, but you never know when you will
         * use 67-dimensional spaces.
         */
        private final Map<Direction, Line> walls = new HashMap<>();

        /**
         * Constructor for the CellView
         *
         * @param x X position of the cell
         * @param y Y position of the cell
         */
        public CellView(int x, int y) {
            this.x = x;
            this.y = y;

            background = new Rectangle();
            background.setStroke(Color.TRANSPARENT);
            updateColor();

            getChildren().add(background);

            for (Direction dir : Direction.values()) {
                Line wall = new Line();
                wall.setStroke(wallColor);
                wall.setStrokeWidth(2);
                walls.put(dir, wall);
                getChildren().add(wall);
            }

            // This is what shows the menu btw
            setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    showWallToggleMenu(event.getScreenX(), event.getScreenY());
                }
            });
        }

        /**
         * Function to show the wall toggle menu
         * 
         * @param screenX the X position of the menu (?)
         * @param screenY the Y position of the menu (???)
         */
        private void showWallToggleMenu(double screenX, double screenY) {
            ContextMenu contextMenu = new ContextMenu();

            for (Direction dir : Direction.values()) {
                MenuItem item = new MenuItem(dir.toString());
                boolean hasConn = maze.hasConnection(x, y, dir);
                item.setText((hasConn ? "Add " : "Remove ") + dir + " wall");
                item.setOnAction(e -> toggleWall(dir));
                contextMenu.getItems().add(item);
            }

            contextMenu.show(this, screenX, screenY);
        }

        /**
         * Function to easily toggle a wall based on a
         * given direction.
         * 
         * @param dir The direction to toggle
         */
        private void toggleWall(Direction dir) {
            // Don't forget to also update the other cell!
            int adjX = x + dir.getX();
            int adjY = y + dir.getY();

            // I forgot to add this check before. Couldn't figure out why it crashed lol.
            if (adjX >= 0 && adjX <= maze.getWidth() && adjY >= 0 && adjY <= maze.getHeight()
                    && maze.getCell(adjX, adjY) != null) {
                if (maze.hasConnection(x, y, dir)) {
                    maze.disconnect(x, y, dir);
                } else {
                    maze.connect(x, y, dir);
                }

                // Make sure to update adjacent cell's view too. Would be STUPID not to, right?
                CellView adjacentCellView = cellViewMap[adjX][adjY];
                if (adjacentCellView != null) {
                    adjacentCellView.updateWalls();
                }
            }
            updateWalls();
        }

        /**
         * Updates the color of the cell.
         */
        private void updateColor() {
            // This feels REALLY wrong but it seems fine..?
            // Inner classes are such a mess.
            Color color = maze.getCell(x, y).getColor();
            background.setFill(color);
        }

        /**
         * Resizes the cell to the right size
         */
        private void resize() {
            setLayoutX(x * cellSize);
            setLayoutY((maze.getHeight() - y) * cellSize);

            background.setWidth(cellSize);
            background.setHeight(cellSize);

            updateWalls();
        }

        /**
         * Updates the graphical representation of every wall of the cell
         */
        private void updateWalls() {
            /*
             * The fact that there is no way to make this any more succint
             * fills me with intense rage.
             */
            walls.get(Direction.NORTH).setStartX(0);
            walls.get(Direction.NORTH).setStartY(0);
            walls.get(Direction.NORTH).setEndX(cellSize);
            walls.get(Direction.NORTH).setEndY(0);
            walls.get(Direction.NORTH).setVisible(!maze.hasConnection(x, y, Direction.NORTH));

            walls.get(Direction.SOUTH).setStartX(0);
            walls.get(Direction.SOUTH).setStartY(cellSize);
            walls.get(Direction.SOUTH).setEndX(cellSize);
            walls.get(Direction.SOUTH).setEndY(cellSize);
            walls.get(Direction.SOUTH).setVisible(!maze.hasConnection(x, y, Direction.SOUTH));

            walls.get(Direction.EAST).setStartX(cellSize);
            walls.get(Direction.EAST).setStartY(0);
            walls.get(Direction.EAST).setEndX(cellSize);
            walls.get(Direction.EAST).setEndY(cellSize);
            walls.get(Direction.EAST).setVisible(!maze.hasConnection(x, y, Direction.EAST));

            walls.get(Direction.WEST).setStartX(0);
            walls.get(Direction.WEST).setStartY(0);
            walls.get(Direction.WEST).setEndX(0);
            walls.get(Direction.WEST).setEndY(cellSize);
            walls.get(Direction.WEST).setVisible(!maze.hasConnection(x, y, Direction.WEST));
        }
    }
}
