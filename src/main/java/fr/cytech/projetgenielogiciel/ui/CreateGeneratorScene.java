package fr.cytech.projetgenielogiciel.ui;

import fr.cytech.projetgenielogiciel.Serializer;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.builder.*;
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

import java.io.IOException;

public class CreateGeneratorScene {

    public CreateGeneratorScene(Stage stage) {
        // Scene setup
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setTitle("CYnapse - Maze generation setup");
        stage.setScene(scene);
        stage.show();

        // Top algorithm choser
        HBox top = new HBox(10);
        top.setAlignment(Pos.TOP_LEFT);

        ComboBox<String> algorithmChoice = new ComboBox<>();
        algorithmChoice.getItems().addAll("DFS", "BFS","Imperfect DFS", "Imperfect BFS");
        algorithmChoice.setPromptText("Select Algorithm");

        // Load button
        Button load = new Button("Load builder state");

        top.getChildren().addAll(new Label("Builder Algorithm: "), algorithmChoice, load);
        root.setTop(top);

        // Middle display for the parameters
        VBox paramBox = new VBox(10);
        paramBox.setAlignment(Pos.TOP_LEFT);

        root.setCenter(paramBox);

        /*
         * BUILDER VARIABLES
         */

        // Maze dimmensions
        TextField mazeWidth = new TextField();
        mazeWidth.setPromptText("Maze Width [Integer]");
        TextField mazeHeight = new TextField();
        mazeHeight.setPromptText("Maze Height [Integer]");

        // Starting position
        TextField startX = new TextField();
        startX.setPromptText("Start X position [Integer]");
        TextField startY = new TextField();
        startY.setPromptText("Start Y position [Integer]");

        // Random seed
        TextField seed = new TextField();
        seed.setPromptText("Seed [Integer]");

        //percent of walls changed
        TextField percentOfWalls = new TextField();
        percentOfWalls.setPromptText("Number between 1 and 100 [Integer]");

        /*
         * ALGORITHM PARAMS DISPLAY
         */

        algorithmChoice.setOnAction(e -> {
            paramBox.getChildren().clear();
            String selected = algorithmChoice.getValue();

            switch (selected) {
                case "DFS", "BFS":
                    paramBox.getChildren().addAll(
                            mazeWidth,
                            mazeHeight,
                            startX,
                            startY,
                            seed);
                    break;
                case "Imperfect DFS", "Imperfect BFS" :
                    paramBox.getChildren().addAll(
                            mazeWidth,
                            mazeHeight,
                            startX,
                            startY,
                            seed,
                            percentOfWalls);
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
        back.setOnAction(e -> new ApplicationMain().start(stage));

        // Load state
        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load builder from file...");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.ser"));
            fileChooser.setSelectedExtensionFilter(fileChooser.getExtensionFilters().get(0));
            try {

                String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
                IBuilder builder = Serializer.deserialize(path, IBuilder.class);

                new MazeGenerationScene(stage, builder.getMaze(), builder);
            } catch (IOException | ClassNotFoundException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Failed to load builder");
                errorAlert.setContentText(ex.getMessage());
                errorAlert.showAndWait();
            } catch (NullPointerException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Failed to load builder");
                errorAlert.setContentText("No file selected.");
                errorAlert.showAndWait();
            }
        });

        // Create the builder
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
                Integer w = Integer.parseInt(mazeWidth.getText());
                if (w <= 0)
                    throw new IllegalArgumentException();

                Integer h = Integer.parseInt(mazeHeight.getText());
                if (h <= 0)
                    throw new IllegalArgumentException();

                Integer sx = Integer.parseInt(startX.getText());
                if (sx < 0 || sx >= w)
                    throw new IllegalArgumentException();

                Integer sy = Integer.parseInt(startY.getText());
                if (sy < 0 || sy >= h)
                    throw new IllegalArgumentException();

                Integer s = Integer.parseInt(seed.getText());

                Integer p = Integer.parseInt(percentOfWalls.getText());

                IBuilder builder = null;
                Maze maze = new Maze(w, h);
                switch (selected) {
                    case "DFS":
                        builder = new DfsBuilder(maze, sx, sy, s);
                        break;
                    case "BFS":
                        builder = new BfsBuilder(maze, sx, sy, s);
                        break;
                    case "Imperfect BFS":
                        builder = new ImperfectBfsBuilder(maze, sx, sy, s, p);
                        break;
                    case "Imperfect DFS":
                        builder = new ImperfectDfsBuilder(maze, sx, sy, s, p);
                        break;
                }

                // Finally send the builder and the maze to the building scene.
                new MazeGenerationScene(stage, maze, builder);

            } catch (Exception ex) {
                showError("Please fill every field with valid data.");
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
