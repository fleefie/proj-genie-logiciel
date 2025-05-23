package fr.cytech.projetgenielogiciel.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * About scene, for all of the stuff we wanted to share.
 */
public class AboutScene {
    public AboutScene(Stage stage) {
        // Set the scene...
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setTitle("CYnapse - About");
        stage.setScene(scene);
        stage.show();

        Label title = new Label("CYnapse");
        title.setStyle("-fx-font-size: 30px;");
        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        root.setTop(title);

        Label paragraph = new Label(
                "CYnapse is a visualization tool for maze generation and solving algorithms, " +
                        "designed to help understand complex algorithms through visuals. Built with JavaFX, " +
                        "it allows you (yes, you!) to generate and solve mazes with different algorithms, observe their behavior, "
                        +
                        "learning, debugging, and experimentation in algorithmic pathfinding and graph traversal." +
                        "\n\n" +
                        "CYnapse is made out of three distinct modules. Mazes, builders and solvers. " +
                        "All three may be loaded or saved independently, including the current state of " +
                        "any solver or builder. Though, a builder or a solver also holds a maze. " +
                        "To get started, simply generate a new maze. You may then solve it. " +
                        "At any point, click on a cell to edit it's walls, including during an " +
                        "ongoing process!" +
                        "\n\n" +
                        "In general, the following colors are used through the various algorithms:" +
                        "\n - Blue refers to a cell that has been fully processed." +
                        "\n - Red refers to the current position of the algorithm." +
                        "\n - Green refers to the currently chosen path (for solving)." +
                        "\n - Yellow refers to a cell that is queued for processing." +
                        "\n\n" +
                        "CYnapse was built with Java 21, Maven, JavaFX, and a little bit of Lombok to spice up our lives."
                        +
                        "\n\n" +
                        "CYnapse is an assignment by the teachers of CY Tech. " +
                        "This specific implementation of it is licensed under the GNU General Public License 3. " +
                        "The original assignment is ARR.");

        paragraph.setWrapText(true);
        paragraph.setMaxWidth(stage.getWidth());
        paragraph.setStyle("-fx-font-size: 14px;");
        paragraph.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(paragraph);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color:transparent;");
        root.setCenter(scrollPane);

        // Bottom button
        Button back = new Button("Back");
        back.setOnAction(e -> new ApplicationMain().start(stage));

        HBox bottom = new HBox(back);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(10));
        root.setBottom(bottom);
    }
}
