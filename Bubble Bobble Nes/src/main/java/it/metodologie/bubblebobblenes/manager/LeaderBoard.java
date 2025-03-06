package it.metodologie.bubblebobblenes.manager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import it.metodologie.bubblebobblenes.JBubbleBobble;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * A screen to show the first 10 best players who played the game
 */
public class LeaderBoard {

    /**
     * Max number of players to show
     */
    private static final int MAX_DISPLAYED_SCORES = 10;

    /**
     * Show the ranking
     *
     * @param stage Main stage
     */
    public void show(Stage stage) {
        List<UserProfile> topPlayers = loadAllProfiles();

        // Ordina i giocatori per punteggio più alto
        topPlayers.sort((p1, p2) -> Integer.compare(p2.getHighScore(), p1.getHighScore()));

        // Crea l'interfaccia
        Label titleLabel = new Label("Top Players");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setStyle("-fx-text-fill: white;");

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 5, 10, 5));
        grid.setStyle("-fx-background-color: black;");

        // Aggiungi intestazioni
        Label rankHeader = new Label("#");
        Label playerHeader = new Label("Player");
        Label scoreHeader = new Label("High Score");
        Label gamesHeader = new Label("Wins");

        Label[] headers = {rankHeader, playerHeader, scoreHeader, gamesHeader};
        for (Label header : headers) {
            header.setStyle("-fx-text-fill: white;");
            header.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            //header.setAlignment(Pos.CENTER);
        }

        grid.add(titleLabel, 0, 0, 4, 1);
        grid.add(rankHeader, 0, 1);
        grid.add(playerHeader, 1, 1);
        grid.add(scoreHeader, 2, 1);
        grid.add(gamesHeader, 3, 1);

        // Aggiungi i giocatori alla griglia
        int row = 2;
        for (int i = 0; i < Math.min(topPlayers.size(), MAX_DISPLAYED_SCORES); i++) {
            UserProfile player = topPlayers.get(i);

            Label rankLabel = new Label("#" + (i + 1));
            Label nameLabel = new Label(player.getNickname());
            Label scoreLabel = new Label(String.valueOf(player.getHighScore()));
            Label winsLabel = new Label(String.valueOf(player.getGamesWon()));

            Label[] labels = {rankLabel, nameLabel, scoreLabel, winsLabel};
            for (Label label : labels) {
                label.setStyle("-fx-text-fill: white;");
                label.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
                //label.setAlignment(Pos.CENTER);
            }

            grid.add(rankLabel, 0, row);
            grid.add(nameLabel, 1, row);
            grid.add(scoreLabel, 2, row);
            grid.add(winsLabel, 3, row);

            row++;
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

        grid.add(backButton, 0, row, 4, 1);

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: black; -fx-background: black;");

        scrollPane.getStylesheets().add(
                getClass().getResource("/scrollbar-style.css").toExternalForm()
        );

        Scene scene = new Scene(scrollPane, 256, 200);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                try {
                    JBubbleBobble mainMenu = new JBubbleBobble();
                    mainMenu.mainMenu(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load all the profiles created until this moment
     *
     * @return Return a List of UserProfile
     */
    private List<UserProfile> loadAllProfiles() {
        List<UserProfile> profiles = new ArrayList<>();
        File propertiesDir = new File("profiles");
        File[] propertyFiles = propertiesDir.listFiles((dir, name) -> name.endsWith(".properties"));

        if (propertyFiles != null) {
            for (File file : propertyFiles) {
                Properties properties = new Properties();
                try (FileInputStream fis = new FileInputStream(file)) {
                    properties.load(fis);

                    String nickname = properties.getProperty("nickname", "Unknown");
                    String avatarPath = properties.getProperty("avatarPath", "no_avatar");
                    int gamesPlayed = Integer.parseInt(properties.getProperty("gamesPlayed", "0"));
                    int gamesWon = Integer.parseInt(properties.getProperty("gamesWon", "0"));
                    int gamesLost = Integer.parseInt(properties.getProperty("gamesLost", "0"));
                    int currentLevel = Integer.parseInt(properties.getProperty("currentLevel", "1"));
                    int currentScore = Integer.parseInt(properties.getProperty("currentScore", "0"));
                    int highScore = Integer.parseInt(properties.getProperty("highScore", "0"));

                    if (!nickname.equals("no_profile_created")) {
                        profiles.add(new UserProfile(nickname, avatarPath, gamesPlayed,
                                gamesWon, gamesLost, currentLevel,
                                currentScore, highScore));
                    }
                } catch (IOException e) {
                    System.err.println("Error loading profile from " + file.getName() + ": " + e.getMessage());
                }
            }
        }
        return profiles;
    }
}