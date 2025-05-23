package fr.cytech.projetgenielogiciel.ui;

import fr.cytech.projetgenielogiciel.Serialiseur;
import fr.cytech.projetgenielogiciel.maze.Maze;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

/**
 * Scene to display the maze view and some buttons.
 */
public class MazeDisplayScene {
    /**
     * Constructor for the maze display scene.
     *
     * @param stage the stage to display to
     * @param maze  the maze to be displayed
     */
    public MazeDisplayScene(Stage stage, Maze maze) {
        // Reset maze colors first.
        maze.resetColors();

        // Scene setup
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setTitle("CYnapse - Maze viewer");
        stage.setScene(scene);
        stage.show();

        // The maze view itself
        MazeView view = new MazeView(maze);
        HBox center = new HBox(view);
        center.setAlignment(Pos.CENTER);
        root.setCenter(center);

        // The bottom controls
        HBox bottom = new HBox(30);
        bottom.setPadding(new Insets(10));
        bottom.setAlignment(Pos.CENTER);

        // "We have buttons at home"
        Button menu = new Button("Menu");
        Button load = new Button("Load");
        Button save = new Button("Save");
        Button solve = new Button("Solve");

        bottom.getChildren().addAll(menu, save, solve);
        root.setBottom(bottom);

        /*
         * BUTTON ACTIONSe
         */

        // Go to main menu
        menu.setOnAction(e -> new ApplicationMain().start(stage));

        // Load a maze
        load.setOnAction(e -> {
            /* TODO */
        });

        // Save the maze
        save.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load maze from file...");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.ser")
            );
            fileChooser.setSelectedExtensionFilter(fileChooser.getExtensionFilters().get(0));
            String path = fileChooser.showSaveDialog(stage).getAbsolutePath();
            try {
                Serialiseur.serialiser(maze, path);
                JOptionPane.showMessageDialog(null, "Fichier sauvegardé : " + path);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        });

        // Go to the solver scene with this maze
        solve.setOnAction(e -> new CreateSolverScene(stage, maze));
    }
}
