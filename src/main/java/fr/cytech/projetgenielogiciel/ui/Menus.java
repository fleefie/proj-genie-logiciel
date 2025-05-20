package fr.cytech.projetgenielogiciel.ui;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.builder.DfsBuilder;
import fr.cytech.projetgenielogiciel.maze.builder.IBuilder;
import fr.cytech.projetgenielogiciel.maze.solver.ISolver;
import fr.cytech.projetgenielogiciel.maze.solver.TremauxSolver;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Menus extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //@Override
    public void showMaze(Stage primaryStage) {

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
    @Override
    public void start(Stage primaryStage){

        //Panel initialization
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("GUHHHH");
        primaryStage.setScene(scene);
        primaryStage.show();

        //
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(10));
        hbox1.setSpacing(10);
        Button bouton1 = new Button("Show maze");
        Button bouton2 = new Button("Test 2");
        Button bouton3 = new Button("Test 3");
        hbox1.getChildren().addAll(bouton1,bouton2,bouton3);

        bouton1.setOnAction(e -> {
            showMaze(primaryStage);
        });

        root.setCenter(hbox1);

    }
}
