package fr.cytech.projetgenielogiciel.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Main class for the application, also representing the main menu.
 */
public class ApplicationMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set the scene...
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("CYnapse - Main menu");
        primaryStage.setScene(scene);
        primaryStage.show();

        Label title = new Label("CYnapse");
        title.setStyle("-fx-font-size: 30px;");
        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        root.setTop(title);

        // Main menu buttons go here.
        HBox centerButtons = new HBox(10);
        centerButtons.setPadding(new Insets(10));
        centerButtons.setAlignment(Pos.CENTER);
        Button generator = new Button("Generate");
        Button loadMaze = new Button("Load Maze");
        Button loadBuilder = new Button("Load Builder state");
        Button loadSolver = new Button("Load Solver state");

        centerButtons.getChildren().addAll(generator, loadMaze, loadBuilder, loadSolver);
        root.setCenter(centerButtons);

        Button about = new Button("About");
        BorderPane.setAlignment(about, Pos.BOTTOM_CENTER);
        root.setBottom(about);

        // Main menu button actions go here.
        generator.setOnAction(e -> new CreateGeneratorScene(primaryStage));
        loadMaze.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load maze from file...");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            fileChooser.setSelectedExtensionFilter(fileChooser.getExtensionFilters().get(0));
            String path = fileChooser.showOpenDialog(primaryStage).getAbsolutePath();
        });
        loadBuilder.setOnAction(e -> {
            /* TODO */
        });
        loadSolver.setOnAction(e -> {
            /* TODO */
        });
        about.setOnAction(e -> new AboutScene(primaryStage));
    }
}
