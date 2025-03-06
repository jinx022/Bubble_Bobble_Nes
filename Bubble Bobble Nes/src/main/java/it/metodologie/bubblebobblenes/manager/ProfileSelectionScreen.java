package it.metodologie.bubblebobblenes.manager;

import it.metodologie.bubblebobblenes.GameManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import it.metodologie.bubblebobblenes.JBubbleBobble;

import java.io.File;
import java.io.IOException;

/**
 * Screen to create and select a profile and start the game
 */
public class ProfileSelectionScreen {

    /**
     * Screen for all the action available to the user
     *
     * @param stage Main stage
     */
    public void show(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: black;");

        Label titleLabel = new Label("Select Profile");
        titleLabel.setStyle("-fx-text-fill: white;");
        grid.add(titleLabel, 0, 0, 2, 1);

        ComboBox<String> profilesComboBox = new ComboBox<>();
        profilesComboBox.setStyle("-fx-text-fill: black; -fx-background-color: white;");
        grid.add(profilesComboBox, 0, 1, 2, 1);

        // Carica i profili esistenti
        File profilesDir = new File("profiles");
        if (profilesDir.exists() && profilesDir.isDirectory()) {
            File[] profileFiles = profilesDir.listFiles((dir, name) -> name.endsWith(".properties"));
            if (profileFiles != null) {
                for (File file : profileFiles) {
                    String nickname = file.getName().replace(".properties", "");
                    profilesComboBox.getItems().add(nickname);
                }
            }
        }

        Button startButton = new Button("Play");
        Button createNewButton = new Button("Create New Profile");
        Button backButton = new Button("Back to Menu");

        grid.add(startButton, 0, 2);
        grid.add(createNewButton, 1, 2);
        grid.add(backButton, 0, 3, 2, 1);

        startButton.setOnAction(e -> {
            String selectedProfile = profilesComboBox.getValue();
            if (selectedProfile != null) {
                GameManager gameManager = new GameManager();
                try {
                    UserProfile profile = UserProfileManager.loadProfile(selectedProfile);
                    gameManager.start(stage, profile);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        createNewButton.setOnAction(e -> {
            UserProfileForm form = new UserProfileForm();
            try {
                form.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        backButton.setOnAction(e -> {
            try {
                JBubbleBobble menu = new JBubbleBobble();
                menu.mainMenu(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(grid, 256, 200);
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
}