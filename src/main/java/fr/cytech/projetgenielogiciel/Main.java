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
import fr.cytech.projetgenielogiciel.ui.Menus;
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
public class Main {

    static Menus app = new Menus();

    /**
     * Main method to run the project.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        app.main(args);
    }
}
