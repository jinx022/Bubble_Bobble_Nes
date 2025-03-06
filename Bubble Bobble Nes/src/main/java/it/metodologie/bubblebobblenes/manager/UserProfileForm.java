package it.metodologie.bubblebobblenes.manager;

import it.metodologie.bubblebobblenes.JBubbleBobble;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

/**
 * Form to create a new player profile and statistics
 */
public class UserProfileForm {
    /**
     * Launch the form and fill the necessary infos
     *
     * @param primaryStage Main Stage
     * @throws UnsupportedEncodingException If UTF-8 encoding is not supported when decoding the avatars path
     */
    public void start(Stage primaryStage) throws UnsupportedEncodingException {
        primaryStage.setTitle("User Profile Form");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label nicknameLabel = new Label("Nickname:");
        nicknameLabel.setStyle("-fx-text-fill: grey;");
        grid.add(nicknameLabel, 0, 1);
        TextField nicknameField = new TextField();
        grid.add(nicknameField, 1, 1);

        Label avatarLabel = new Label("Avatar:");
        avatarLabel.setStyle("-fx-text-fill: grey;");
        grid.add(avatarLabel, 0, 2);
        ComboBox<String> avatarComboBox = new ComboBox<>();
        avatarComboBox.setStyle("-fx-text-fill: black; -fx-background-color: white;");
        grid.add(avatarComboBox, 1, 2);

        try{
        String path = URLDecoder.decode(Objects.requireNonNull(getClass().getResource("/images/avatars/")).getFile(), "UTF-8");
        File imagesDir = new File(path);
        if (imagesDir.isDirectory()) {
            for (File file : Objects.requireNonNull(imagesDir.listFiles())) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    avatarComboBox.getItems().add(file.getName());
                }
            }
        }
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        Button submitButton = new Button("Create User");
        grid.add(submitButton, 1, 3);

        submitButton.setOnAction(event -> {
            String nickname = nicknameField.getText();
            if (UserProfileManager.profileExists(nickname)) {
                Label errorLabel = new Label("Nickname already used!");
                errorLabel.setStyle("-fx-text-fill: red;");
                grid.add(errorLabel, 1, 5);
                return;
            }
            String avatarPath = "/images/avatars/" + avatarComboBox.getValue();
            UserProfile userProfile = new UserProfile(nickname, avatarPath);
            UserProfileManager.saveProfile(userProfile);
        });

        Button backButton = new Button("Back to Menu");
        grid.add(backButton, 1, 4);
        backButton.setOnAction(e -> {
            try {
                JBubbleBobble menu = new JBubbleBobble();
                menu.mainMenu(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(grid, 256, 200);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                try {
                    JBubbleBobble mainMenu = new JBubbleBobble();
                    mainMenu.mainMenu(primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        grid.setStyle("-fx-background-color: black;");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}