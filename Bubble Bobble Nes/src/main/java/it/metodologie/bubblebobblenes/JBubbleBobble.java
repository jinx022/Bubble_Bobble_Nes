package it.metodologie.bubblebobblenes;

import it.metodologie.bubblebobblenes.manager.*;
import it.metodologie.bubblebobblenes.singleton.AudioManager;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Bubble Bobble Game
 *
 * Implementation in Java and JavaFX of the arcade game from NES system.
 * The player controls the dragon Bub who can fire bubbles to capture the enemies
 * and move forward the levels.
 *
 * @author Marco Eduardo Picozzi
 * @version 0.1
 */
public class JBubbleBobble extends Application {
    /** Start up image */
    private ImageView imageView;
    /** Store the info saved */
    private UserProfile loadedProfile;
    /** Show imageView if the application is launched */
    private static boolean isFirstStart = true;

    /**
     * Launch the main menu after a short animation that plays
     * on the launch of the application
     *
     * @param stage Top level JavaFX container for the main menu
     * @throws IOException
     */
    public void mainMenu(Stage stage) throws IOException {
        Image introImage = new Image(JBubbleBobble.class.getResourceAsStream("/images/title.png"));
        imageView = new ImageView(introImage);
        imageView.setFitWidth(256);
        imageView.setFitHeight(218);

        Button startButton = new Button("Start Game");
        Button createProfile = new Button("Create a Profile");
        Button showLeaderBoard = new Button("Leader Board");
        Button loadStatistics = new Button("Show Statistics");
        Button exitButton = new Button("Exit");
        VBox menuButtons = new VBox(10, startButton, createProfile, showLeaderBoard, loadStatistics, exitButton);
        menuButtons.setAlignment(Pos.CENTER);
        menuButtons.setVisible(false);

        EventHandler<ActionEvent> buttonHandler = event -> {
            Button sourceButton = (Button) event.getSource();
            Stage gameStage = (Stage) sourceButton.getScene().getWindow();

            switch (sourceButton.getText()) {
                case "Start Game":
                    if (UserProfileManager.loadProfile().getNickname().equals("no_profile_created")) {
                        UserProfileForm userProfileForm = new UserProfileForm();
                        try {
                            userProfileForm.start(gameStage);
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        ProfileSelectionScreen profileSelection = new ProfileSelectionScreen();
                        profileSelection.show(gameStage);
                    }
                    break;
                case "Create a Profile":
                    UserProfileForm userProfileForm = new UserProfileForm();
                    try {
                        userProfileForm.start(gameStage);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Leader Board":
                    LeaderBoard leaderboard = new LeaderBoard();
                    leaderboard.show(gameStage);
                    break;
                case "Show Statistics":
                    StatisticsScreen statisticsScreen = new StatisticsScreen();
                    statisticsScreen.show(gameStage);
                    break;
                case "Exit":
                    gameStage.close();
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected button text: " + sourceButton.getText());
            }
        };

        startButton.setOnAction(buttonHandler);
        createProfile.setOnAction(buttonHandler);
        showLeaderBoard.setOnAction(buttonHandler);
        loadStatistics.setOnAction(buttonHandler);
        exitButton.setOnAction(buttonHandler);

        StackPane root = new StackPane();
        root.getChildren().addAll(imageView, menuButtons);
        root.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(root, 256, 218);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                try {
                    mainMenu(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        if (isFirstStart) {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), imageView);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), imageView);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                imageView.setVisible(false);
                menuButtons.setVisible(true);
            });

            fadeIn.setOnFinished(e -> fadeOut.play());
            fadeIn.play();

            isFirstStart = false;
        } else {
            imageView.setVisible(false);
            menuButtons.setVisible(true);
        }

        stage.setScene(scene);
        stage.setTitle("Bubble Bobble Nes - Java & JavaFX");
        stage.show();
    }

    /**
     * @param stage The primary stage for this application
     * @throws IOException If an error occur during the loading of the audio source or the necessary param
     */
    @Override
    public void start(Stage stage) throws IOException {
        AudioManager.getInstance().playMusic("menu", false);
        mainMenu(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}