package it.metodologie.bubblebobblenes.manager;

import it.metodologie.bubblebobblenes.JBubbleBobble;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Show all the infos of the player
 */
public class StatisticsScreen {
    /**
     * Using the layouts offered in JavaFX, display all the data saved and updated of the player
     *
     * @param stage Main Stage
     */
    public void show(Stage stage) {
        UserProfile profile = UserProfileManager.loadProfile();

        Label titleLabel = new Label("Player Statistics");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleLabel.setStyle("-fx-text-fill: white;");

        Image avatarImage = new Image(getClass().getResourceAsStream(profile.getAvatarPath()));
        if (avatarImage.isError()) {
            System.err.println("Error loading image from path: " + profile.getAvatarPath());
        }
        ImageView avatarImageView = new ImageView(avatarImage);
        avatarImageView.setFitHeight(16);
        avatarImageView.setFitWidth(16);

        Label nicknameLabel = new Label("Nickname: " + profile.getNickname());
        Label currentLevelLabel = new Label("Current Level: " + profile.getCurrentLevel());
        Label currentScoreLabel = new Label("Current Score: " + profile.getCurrentScore());
        Label highScoreLabel = new Label("High Score: " + profile.getHighScore());
        Label gamesPlayedLabel = new Label("Games Played: " + profile.getGamesPlayed());
        Label gamesWonLabel = new Label("Games Won: " + profile.getGamesWon());
        Label gamesLostLabel = new Label("Games Lost: " + profile.getGamesLost());

        Label[] labels = {nicknameLabel, currentLevelLabel, currentScoreLabel,
                highScoreLabel, gamesPlayedLabel, gamesWonLabel, gamesLostLabel};

        for (Label label : labels) {
            label.setStyle("-fx-text-fill: white;");
            label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        }

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {
            try {
                JBubbleBobble menu = new JBubbleBobble();
                menu.mainMenu(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: black;");

        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(avatarImageView, 0, 1);
        grid.add(nicknameLabel, 0, 2);
        grid.add(currentLevelLabel, 0, 3);
        grid.add(currentScoreLabel, 0, 4);
        grid.add(highScoreLabel, 0, 5);
        grid.add(gamesPlayedLabel, 0, 6);
        grid.add(gamesWonLabel, 0, 7);
        grid.add(gamesLostLabel, 0, 8);
        grid.add(backButton, 0, 9, 2, 1);

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: black; -fx-background: black;");

        scrollPane.getStylesheets().add(
                getClass().getResource("/scrollbar-style.css").toExternalForm()
        );

        Scene scene = new Scene(scrollPane, 256, 200);
        stage.setScene(scene);
        stage.show();
    }
}