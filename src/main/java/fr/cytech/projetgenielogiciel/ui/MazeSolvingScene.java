package fr.cytech.projetgenielogiciel.ui;

import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.solver.ISolver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class for the maze solving scene.
 */
public class MazeSolvingScene {
    /**
     * whether the scene is displaying an animation for the building.
     */
    private boolean isAnimating = false;

    /**
     * The count of building steps done so far.
     */
    private int stepCount = 0;

    /**
     * The animation timeline for generation.
     */
    private Timeline animation;

    /**
     * Constructor for the maze solving scene.
     *
     * @param stage  the stage to display the scene to
     * @param maze   the maze to solve
     * @param solver the solver to use
     */
    public MazeSolvingScene(Stage stage, Maze maze, ISolver solver) {
        // Always reset maze colors between scenes.
        maze.resetColors();

        // Scene setup
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setTitle("CYnapse - Maze resolution");
        stage.setScene(scene);
        stage.show();

        /*
         * TOP BUTTONS
         */

        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);

        // Container for the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        // The buttons in question:
        Button step = new Button("Step 0");
        Button finish = new Button("Finish");
        Button anim = new Button("Animate");
        Button load = new Button("Load solver state");
        Button save = new Button("Save solver state");

        // TODO: make this use local resources.
        // ImageView icon = new ImageView(
        // new
        // Image("https://icons.veryicon.com/png/o/miscellaneous/winsion/play-button-6.png"));
        // icon.setFitHeight(20);
        // icon.setFitWidth(20);
        // anim.setGraphic(icon);

        buttonBox.getChildren().addAll(step, finish, anim, load, save);
        topBox.getChildren().add(buttonBox);
        root.setTop(topBox);

        /*
         * CENTER MAZE VIEW
         */

        MazeView view = new MazeView(maze);
        HBox center = new HBox(view);

        center.setAlignment(Pos.CENTER);
        root.setCenter(center);

        /*
         * BOTTOM BUTTONS
         */

        // Slider for the animation delay
        Slider speedSlider = new Slider(10, 1000, 200);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(250);
        speedSlider.setMinorTickCount(4);
        speedSlider.setBlockIncrement(50);
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (animation != null) {
                startAnimation(newVal.doubleValue(), solver, step, view);
            }
        });

        // Container for the buttons
        HBox bottom = new HBox(30);
        bottom.setPadding(new Insets(10));
        bottom.setAlignment(Pos.CENTER);

        // The buttons or something idk)
        Button back = new Button("Back");
        Button endGen = new Button("End resolution");
        bottom.getChildren().addAll(back, speedSlider, endGen);
        root.setBottom(bottom);

        /*
         * BUTTON ACTIONSe
         */

        // Step ahead once, adding one to the counter
        step.setOnAction(e -> {
            if (solver.step())
                stepCount++;
            step.setText("Step " + stepCount);
            view.update();
        });

        // Instantly finish every possible step
        finish.setOnAction(e -> {
            for (Boolean stepStatus : solver)
                if (stepStatus) {
                    stepCount++;
                    step.setText("Step " + stepCount);
                }
            view.update();
        });

        // Toggle animation state, creating a new animation if necessary
        anim.setOnAction(e -> {
            if (!isAnimating) {
                if (animation == null)
                    startAnimation(200, solver, step, view);
                isAnimating = true;
                animation.play();
            } else {
                isAnimating = false;
                animation.stop();
            }
        });

        // Load a generator from a file
        load.setOnAction(e -> {
            /* TODO */
        });

        // Save a generator from a file
        save.setOnAction(e -> {
            /* TODO */
        });

        // Go back to the solver setup screen
        back.setOnAction(e -> new CreateSolverScene(stage, maze));

        // End generation and go to the display scene
        endGen.setOnAction(e -> new MazeDisplayScene(stage, maze));
    }

    /**
     * Method to create and update the solving animation.
     *
     * @param delayMs the delay, in ms, between steps
     * @param solver  the solver
     * @param step    the step button, to update the text
     * @param view    the display of the maze
     */
    private void startAnimation(double delayMs, ISolver solver, Button step, MazeView view) {
        if (animation != null)
            animation.stop();

        animation = new Timeline(new KeyFrame(Duration.millis(delayMs), e -> {
            if (!solver.step())
                animation.stop();

            stepCount++;
            step.setText("Step " + stepCount);
            view.update();
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        if (isAnimating)
            animation.play();
    }
}
