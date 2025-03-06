package it.metodologie.bubblebobblenes;

import it.metodologie.bubblebobblenes.manager.UserProfile;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Different output screen are shown according to the game result
 */
public class GameScreenManager {
    /** Screen width size */
    private static final int WINDOW_WIDTH = 256;
    /** Screen height size */
    private static final int WINDOW_HEIGHT = 200;

    /**
     * Show a Victory screen at the end of the game
     *
     * @param stage Stage of the application
     */
    public static void showVictoryScreen(Stage stage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setMaxWidth(WINDOW_WIDTH);
        layout.setMaxHeight(WINDOW_HEIGHT);

        Label congratsLabel = new Label("CONGRATULATIONS!");
        congratsLabel.setStyle("-fx-text-fill: yellow; -fx-font-size: 16px;");

        Label victoryLabel = new Label("YOU WON!");
        victoryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        Button menuButton = new Button("Main Menu");
        menuButton.setOnAction(e -> {
            try {
                JBubbleBobble menu = new JBubbleBobble();
                menu.mainMenu(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(congratsLabel, victoryLabel, menuButton);
        root.getChildren().add(layout);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), layout);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        stage.setScene(scene);
    }

    /**
     * Show a continue screen when a level is completed
     *
     * @param stage Stage of the application
     * @param currentLevel Value of the level completed
     * @param gameManager Game loop handler
     */
    public static void showContinueScreen(Stage stage, int currentLevel, GameManager gameManager) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setMaxWidth(WINDOW_WIDTH);
        layout.setMaxHeight(WINDOW_HEIGHT);

        Label levelLabel = new Label("LEVEL " + currentLevel + " COMPLETE!");
        levelLabel.setStyle("-fx-text-fill: yellow; -fx-font-size: 14px;");

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> gameManager.startNextLevel());

        layout.getChildren().addAll(levelLabel, continueButton);
        root.getChildren().add(layout);

        stage.setScene(scene);
    }

    /**
     * Show a game over screen when the player loses all his lifes
     *
     * @param stage Stage of the application
     */
    public static void showGameOverScreen(Stage stage, UserProfile profile) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setMaxWidth(WINDOW_WIDTH);
        layout.setMaxHeight(WINDOW_HEIGHT);

        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button retryButton = new Button("Retry");
        retryButton.setOnAction(e -> {
            try {
                GameManager newGame = new GameManager();
                newGame.start(stage, profile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Button menuButton = new Button("Main Menu");
        menuButton.setOnAction(e -> {
            try {
                JBubbleBobble menu = new JBubbleBobble();
                menu.mainMenu(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(retryButton, menuButton);

        layout.getChildren().addAll(gameOverLabel, buttons);
        root.getChildren().add(layout);

        Timeline blink = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> gameOverLabel.setVisible(false)),
                new KeyFrame(Duration.seconds(1.0), e -> gameOverLabel.setVisible(true))
        );
        blink.setCycleCount(5);
        blink.play();

        stage.setScene(scene);
    }
}