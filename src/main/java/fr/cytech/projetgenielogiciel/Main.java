package fr.cytech.projetgenielogiciel;

import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.builder.BfsBuilder;
import fr.cytech.projetgenielogiciel.maze.builder.DfsBuilder;
import fr.cytech.projetgenielogiciel.maze.builder.IBuilder;
import fr.cytech.projetgenielogiciel.maze.solver.ISolver;
import fr.cytech.projetgenielogiciel.maze.solver.TremauxSolver;
import fr.cytech.projetgenielogiciel.maze.solver.astar.AStarDjikstraSolver;
import fr.cytech.projetgenielogiciel.maze.solver.astar.AStarEuclideanSolver;
import fr.cytech.projetgenielogiciel.maze.solver.astar.AStarManhattanSolver;
import fr.cytech.projetgenielogiciel.ui.MazeView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.NoArgsConstructor;

/**
 * Main class for the project.
 */
@NoArgsConstructor
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20;");
        Maze maze = new Maze(10, 10);
        MazeView mazeView = new MazeView(maze);
        root.setCenter(mazeView);

        // Add controls for maze generation
        HBox controls = new HBox(10);
        // ComboBox<String> algorithmChoice = new ComboBox<>();
        // algorithmChoice.getItems().addAll("DFS", "BFS");
        // algorithmChoice.setValue("DFS");

        Button generateButton = new Button("Step ahead");
        IBuilder builder = new DfsBuilder(maze, 0, 0, 8453); //253, 1967
        builder.build();
        maze.resetColors();

        //ISolver solver = new AStarManhattanSolver(maze, maze.getCell(0, 0),
        //maze.getCell(maze.getWidth(), maze.getHeight()), 0.5, 0.5);
        ISolver solver = new TremauxSolver(maze, maze.getCell(0, 0),
                maze.getCell(maze.getWidth(), maze.getHeight()));

        generateButton.setOnAction(e -> {
            //solver.solve();
            solver.step();
            mazeView.update();
        });

        controls.getChildren().addAll(/* algorithmChoice, */ generateButton);
        root.setBottom(controls);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("GUHHHH");
        primaryStage.setScene(scene);
        primaryStage.show();

        /*
        Timeline solverTimeline = new Timeline();
        solverTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> {
            boolean keepGoing = solver.step();
            mazeView.update();
            if (!keepGoing) {
                solverTimeline.stop(); // <-- just use your variable directly
            }
        }));
        solverTimeline.setCycleCount(Timeline.INDEFINITE);
        solverTimeline.play();
        */
    }

    /**
     * Main method to run the project.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
