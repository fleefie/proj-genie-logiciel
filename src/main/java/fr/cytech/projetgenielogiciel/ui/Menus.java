package fr.cytech.projetgenielogiciel.ui;
import fr.cytech.projetgenielogiciel.maze.Maze;
import fr.cytech.projetgenielogiciel.maze.builder.DfsBuilder;
import fr.cytech.projetgenielogiciel.maze.builder.IBuilder;
import fr.cytech.projetgenielogiciel.maze.solver.ISolver;
import fr.cytech.projetgenielogiciel.maze.solver.TremauxSolver;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
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
        primaryStage.setTitle("CYnapse");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Title
        Label title1 = new Label("CYnapse");
        title1.setStyle("-fx-font-size: 30px;");
        BorderPane.setAlignment(title1, Pos.TOP_CENTER);
        root.setTop(title1);

        //Center of scene
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(10));
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER);
        Button generator = new Button("Generator");
        Button loadMaze = new Button("Load maze");
        generator.setPrefWidth(100);
        loadMaze.setPrefWidth(100);
        generator.setPrefHeight(50);
        loadMaze.setPrefHeight(50);
        hbox1.getChildren().addAll(generator,loadMaze);
        root.setCenter(hbox1);

        //Bottom of scene
        Button about = new Button("About");
        BorderPane.setAlignment(about, Pos.BOTTOM_CENTER);
        root.setBottom(about);

        generator.setOnAction(e -> {
            Generator(primaryStage);
        });
        loadMaze.setOnAction(e -> {
            System.out.println("Load maze");
        });
        about.setOnAction(e -> {
            System.out.println("About");
        });


        //DEBUG
        Button debug1 = new Button("Show maze (debug)");
        hbox1.getChildren().add(debug1);
        debug1.setOnAction(e -> {
            showMaze(primaryStage);
        });

    }

    public void Generator(Stage primaryStage){
        //Panel initialization
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Generator");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Top of scene
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(10));
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.CENTER);
        //Length of maze
        TextField posX = new TextField();
        posX.setPromptText("X");
        posX.setPrefWidth(40);
        TextField posY = new TextField();
        posY.setPromptText("Y");
        posY.setPrefWidth(40);
        //Algorithm choice
        ComboBox<String> algorithmChoice = new ComboBox<>();
        algorithmChoice.getItems().addAll("DFS", "BFS");
        algorithmChoice.setPromptText("Algorithm");
        algorithmChoice.setPrefWidth(120);
        //Seed selection
        TextField seed = new TextField();
        seed.setPromptText("Seed");
        seed.setPrefWidth(70);
        //Add into hbox
        hbox1.getChildren().addAll(posX,posY,algorithmChoice,seed);
        //BorderPane.setAlignment(hbox1, Pos.TOP_CENTER);
        root.setTop(hbox1);

        //Center of scene
        VBox vbox1 = new VBox();
        vbox1.setPadding(new Insets(10));
        vbox1.setSpacing(10);
        vbox1.setAlignment(Pos.CENTER);
        //CheckBox list
        Label text1 = new Label("Resolution algorithm :");
        /*CheckBox check1 = new CheckBox("AStar Djikstra");
        CheckBox check2 = new CheckBox("AStar Euclidean");
        CheckBox check3 = new CheckBox("AStar Manhattan");
        CheckBox check4 = new CheckBox("Tremaux");
        vbox1.getChildren().addAll(text1,check1,check2,check3,check4);*/
        RadioButton radio1 = new RadioButton("AStar Djikstra");
        RadioButton radio2 = new RadioButton("AStar Euclidean");
        RadioButton radio3 = new RadioButton("AStar Manhattan");
        RadioButton radio4 = new RadioButton("Tremaux");
        ToggleGroup group = new ToggleGroup();
        radio1.setToggleGroup(group);
        radio2.setToggleGroup(group);
        radio3.setToggleGroup(group);
        radio4.setToggleGroup(group);
        vbox1.getChildren().addAll(text1,radio1,radio2,radio3,radio4);
        root.setCenter(vbox1);

        //Bottom of scene
        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(30);
        hbox2.setAlignment(Pos.CENTER);
        Button back = new Button("Back");
        Button loadState = new Button("Load state");
        Button begin = new Button("Begin");
        back.setPrefWidth(100);
        loadState.setPrefWidth(100);
        begin.setPrefWidth(100);
        back.setPrefHeight(50);
        loadState.setPrefHeight(50);
        begin.setPrefHeight(50);
        hbox2.getChildren().addAll(back,loadState,begin);
        root.setBottom(hbox2);

        back.setOnAction(e -> {
            start(primaryStage);
        });
        loadState.setOnAction(e -> {
            // !!!!!!!!
        });
        begin.setOnAction(e -> {
            // !!!!!!!
        });


    }
}
