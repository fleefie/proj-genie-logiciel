package fr.cytech.projetgenielogiciel.ui;

import java.io.IOException;

import fr.cytech.projetgenielogiciel.Serializer;
import fr.cytech.projetgenielogiciel.maze.Cell;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.solver.ISolver;
import fr.cytech.projetgenielogiciel.maze.solver.TremauxSolver;
import fr.cytech.projetgenielogiciel.maze.solver.astar.AStarDjikstraSolver;
import fr.cytech.projetgenielogiciel.maze.solver.astar.AStarEuclideanSolver;
import fr.cytech.projetgenielogiciel.maze.solver.astar.AStarManhattanSolver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Scene representing the solver setup scene.
 */
public class CreateSolverScene {
    /**
     * Constructor for the solver scene.
     *
     * @param stage the stage to display the scene on
     * @param maze  the maze to work with
     */
    public CreateSolverScene(Stage stage, Maze maze) {
        // Always reset maze colors between scenes.
        maze.resetColors();

        // Scene setup
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setTitle("CYnapse - Maze resolution setup");
        stage.setScene(scene);
        stage.show();

        // Top algorithm choser
        HBox top = new HBox(10);
        top.setAlignment(Pos.TOP_LEFT);

        ComboBox<String> algorithmChoice = new ComboBox<>();
        algorithmChoice.getItems().addAll("Tremaux", "Dijkstra", "A* Euclidean", "A* Manhattan");
        algorithmChoice.setPromptText("Select Algorithm");

        // Load button
        Button load = new Button("Load solver state");

        top.getChildren().addAll(new Label("Solver Algorithm: "), algorithmChoice, load);
        root.setTop(top);

        // Middle display for the parameters
        VBox paramBox = new VBox(10);
        paramBox.setAlignment(Pos.TOP_LEFT);

        root.setCenter(paramBox);

        /*
         * Yes, this is ugly. No, I do not care.
         *
         * It should be trivial to add in new solver types thanks to this structure.
         */

        /*
         * SOLVER VARIABLES
         */

        // Starting position
        TextField startX = new TextField();
        startX.setPromptText("Start X position [Integer]");
        TextField startY = new TextField();
        startY.setPromptText("Start Y position [Integer]");

        // End position
        TextField endX = new TextField();
        endX.setPromptText("End X position [Integer]");
        TextField endY = new TextField();
        endY.setPromptText("End Y position [Integer]");

        // A* distance weight
        TextField astar_distanceFactor = new TextField();
        astar_distanceFactor.setPromptText("Distance Weight [Decimal]");

        // A* heuristic weight
        TextField astar_heuristicFactor = new TextField();
        astar_heuristicFactor.setPromptText("Heuristic Weight [Decimal]");

        /*
         * ALGORITHM PARAMS DISPLAY
         */

        algorithmChoice.setOnAction(e -> {
            paramBox.getChildren().clear();
            String selected = algorithmChoice.getValue();

            switch (selected) {
                case "A* Euclidean":
                    paramBox.getChildren().addAll(
                            startX,
                            startY,
                            endX,
                            endY,
                            astar_distanceFactor,
                            astar_heuristicFactor);
                    break;
                case "A* Manhattan":
                    paramBox.getChildren().addAll(
                            startX,
                            startY,
                            endX,
                            endY,
                            astar_distanceFactor,
                            astar_heuristicFactor);
                    break;
                case "Dijkstra":
                    paramBox.getChildren().addAll(
                            startX,
                            startY,
                            endX,
                            endY);
                    break;
                case "Tremaux":
                    paramBox.getChildren().addAll(
                            startX,
                            startY,
                            endX,
                            endY);
                    break;
            }
        });

        // Finally, some display adjustments.
        HBox bottom = new HBox(30);
        bottom.setPadding(new Insets(10));
        bottom.setAlignment(Pos.CENTER);

        // The other bottom buttons here. Maybe I should move them?
        Button back = new Button("Back");
        Button begin = new Button("Begin");

        bottom.getChildren().addAll(back, begin);
        root.setBottom(bottom);

        /*
         * BUTTON ACTIONS
         */

        // Main menu
        back.setOnAction(e -> new CreateGeneratorScene(stage));

        // Load state
        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load builder from file...");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.ser"));
            fileChooser.setSelectedExtensionFilter(fileChooser.getExtensionFilters().get(0));
            try {

                String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                ISolver solver = Serializer.deserialize(path, ISolver.class);

                new MazeSolvingScene(stage, solver.getMaze(), solver);
            } catch (IOException | ClassNotFoundException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Failed to load solver state.");
                errorAlert.setContentText(ex.getMessage());
                errorAlert.showAndWait();
            } catch (NullPointerException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Failed to load solver state.");
                errorAlert.setContentText("No file selected.");
                errorAlert.showAndWait();
            }
        });

        // Create the solver
        begin.setOnAction(e -> {
            String selected = algorithmChoice.getValue();
            if (selected == null) {
                showError("Select an algorithm.");
                return;
            }

            /*
             * ALGORITHM PARAMETERS VERIFICATION
             */
            try {

                Integer sx = Integer.parseInt(startX.getText());
                if (!(0 <= sx && sx <= maze.getWidth()))
                    throw new IllegalArgumentException();

                Integer sy = Integer.parseInt(startY.getText());
                if (!(0 <= sy && sy <= maze.getHeight()))
                    throw new IllegalArgumentException();

                Integer ex = Integer.parseInt(endX.getText());
                if (!(0 <= ex && ex <= maze.getWidth()))
                    throw new IllegalArgumentException();

                Integer ey = Integer.parseInt(endY.getText());
                if (!(0 <= ey && ey <= maze.getHeight()))
                    throw new IllegalArgumentException();

                Double dw = 0.0d;
                Double dh = 0.0d;

                // It should now be safe to do this?
                Cell start = maze.getCell(sx, sy);
                Cell end = maze.getCell(ex, ey);

                // Null safety be damned, this should always work.
                ISolver solver = null;

                switch (selected) {
                    case "Tremaux":
                        solver = new TremauxSolver(maze, start, end);
                        break;
                    case "Dijkstra":
                        solver = new AStarDjikstraSolver(maze, start, end);
                        break;
                    case "A* Euclidean":
                        dw = Double.parseDouble(astar_distanceFactor.getText());
                        if (dw < 0)
                            throw new IllegalArgumentException();

                        dh = Double.parseDouble(astar_heuristicFactor.getText());
                        if (dh < 0)
                            throw new IllegalArgumentException();

                        solver = new AStarEuclideanSolver(maze, start, end, dw, dh);
                        break;
                    case "A* Manhattan":
                        dw = Double.parseDouble(astar_distanceFactor.getText());
                        if (dw < 0)
                            throw new IllegalArgumentException();

                        dh = Double.parseDouble(astar_heuristicFactor.getText());
                        if (dh < 0)
                            throw new IllegalArgumentException();

                        solver = new AStarManhattanSolver(maze, start, end, dw, dh);
                        break;
                }

                // Finally send the solver and the maze to the solving scene.
                new MazeSolvingScene(stage, maze, solver);

            } catch (Exception ex) {
                showError("Please input valid parameters.");
            }
        });
    }

    /**
     * Displays a simple error message box.
     *
     * @param msg the message to display.
     */
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
