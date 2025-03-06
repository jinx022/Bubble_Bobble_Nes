package it.metodologie.bubblebobblenes;

import it.metodologie.bubblebobblenes.builder.EnemyLoader;
import it.metodologie.bubblebobblenes.builder.PowerUpLoader;
import it.metodologie.bubblebobblenes.controller.BubController;
import it.metodologie.bubblebobblenes.controller.BubbleController;
import it.metodologie.bubblebobblenes.controller.EnemyController;
import it.metodologie.bubblebobblenes.controller.PowerUpController;
import it.metodologie.bubblebobblenes.factory.LevelsLoader;
import it.metodologie.bubblebobblenes.manager.UserProfile;
import it.metodologie.bubblebobblenes.manager.UserProfileManager;
import it.metodologie.bubblebobblenes.model.Bub;
import it.metodologie.bubblebobblenes.singleton.AudioManager;
import it.metodologie.bubblebobblenes.view.EntityView;
import it.metodologie.bubblebobblenes.view.LevelView;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Initialize the main objects of the application and helps them with the collisions.
 * Save the statistic of the player
 */
public class GameManager {
    /** Controller of the Bub */
    private BubController controller;
    /** Model of the Bub */
    private Bub bub;
    /** View object */
    private EntityView view;
    /** List of Bubbles Controllers */
    public List<BubbleController> bubblesList = new ArrayList<>();
    /** List of Enemies Controllers */
    public List<EnemyController> enemiesList = new ArrayList<>();
    /** List of Enemies Controllers to be removed */
    public List<EnemyController> toRemoveEnemies = new ArrayList<>();
    /** List of Power Ups Controllers */
    private List<PowerUpController> powerUpControllers = new ArrayList<>();
    /** List of Power Ups Controllers used */
    private List<PowerUpController> activePowerUps = new ArrayList<>();
    /** Player's Profile */
    private UserProfile currentProfile;
    /** Main layer */
    private Stage gameStage;
    /** Game layer */
    public Pane root;
    /** Label to display info */
    private Label label;
    /** Object to handle game loop */
    private AnimationTimer timer;
    /** Object for the music and sounds effects */
    private AudioManager audioManager;
    /** Last level */
    private static final int MAX_LEVEL = 8;
    /** Points for every enemy defeated */
    private static final int ENEMY_KILL_SCORE = 5;
    /** Points for every Power Up grabbed */
    private static final int POWERUP_SCORE = 3;
    /** Points for every level completed */
    private static final int LEVEL_COMPLETE_SCORE = 10;
    /** Points for every level completed without loosing life */
    private static final int PERFECT_LEVEL_SCORE = 15;
    /** Default score for new player */
    private int currentScore = 0;
    /** Actual level to play */
    private int currentLevel;
    /** Counter for enemies killed in a level */
    private int enemiesKilledCount = 0;
    /** Show that next level is loading */
    private boolean isChangingLevel = false;
    /** Show a new level */
    private boolean isLevelInitialized = false;

    /**
     * Load all the necessary objects and information to start a game
     *
     * @param stage Primary Stage of the application
     * @param profile Infos about the user who play the game
     * @throws IOException If an error occur during the loading of the audio source or the necessary params
     */
    public void start(Stage stage, UserProfile profile) throws IOException {
        audioManager = AudioManager.getInstance();
        audioManager.playMusic("game", false);
        this.gameStage = stage;
        this.currentProfile = profile;
        this.currentLevel = profile.getCurrentLevel();
        this.currentScore = profile.getCurrentScore();
        this.bub = new Bub(50, 80, 16, 16);
        this.view = new EntityView("bub_0", "bub_1");
        loadLevel(currentLevel);
    }

    /**
     * Load the current level to play
     *
     * @param level Value of the level to load
     * @throws IOException If an error occur during the loading of the resources or the necessary param
     */
    private void loadLevel(int level) throws IOException {
        root = new Pane();
        root.setStyle("-fx-background-color: black;");

        bub.setX(50);
        bub.setY(80);

        LevelsLoader loader = new LevelsLoader();
        loader.loadLevel(level);
        LevelView levelView = new LevelView();
        levelView.setPlatforms(loader.getLevelList());

        Pane levelPane = levelView.drawLevelObjects();
        root.getChildren().add(levelPane);
        root.getChildren().add(view.getImageView());

        Scene scene = new Scene(root, 256, 218);

        controller = new BubController(bub, view, loader.getLevelList(), scene, root, this);

        EnemyLoader enemyLoader = new EnemyLoader();
        enemyLoader.loadEntities(level, loader.getLevelList(), scene, root);
        this.enemiesList = enemyLoader.getEnemies();
        for (EnemyController e : enemiesList) {
            root.getChildren().add(e.getView().getImageView());
        }

        PowerUpLoader powerUpLoader = new PowerUpLoader();
        powerUpLoader.loadEntities(level, loader.getLevelList(), scene, root);
        this.powerUpControllers = new ArrayList<>(powerUpLoader.getPowerUpList());
        this.activePowerUps.clear();

        isLevelInitialized = true;
        isChangingLevel = false;
        enemiesKilledCount = 0;

        gameStage.setScene(scene);
        gameStage.centerOnScreen();
        gameStage.show();

        startGameLoop();
    }

    /**
     * Start a loop that leave the game on until the player losts all his lifes
     */
    private void startGameLoop() {
        this.label = new Label("lifes " + String.valueOf(bub.getLifes()));
        label.setTextFill(Color.RED);
        label.setLayoutX(0);
        label.setLayoutY(200);
        root.getChildren().addAll(label);
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (controller.entity instanceof Bub bub && !bub.haveLife()) {
                    timer.stop();
                    audioManager.playMusic("game_over", false);
                    if(currentProfile != null){
                        currentProfile.setGamesLost(1);
                        UserProfileManager.saveProfile(currentProfile);
                    }
                    Platform.runLater(() -> GameScreenManager.showGameOverScreen(gameStage, currentProfile));
                    return;
                }

                updateGameState();

                if (isLevelInitialized && enemiesList.isEmpty() && !isChangingLevel) {
                    handleLevelCompletion();
                }
            }
        };
        timer.start();
    }

    /**
     * Update all the entities inside the loop game
     */
    private void updateGameState() {
        this.label.setText("lifes " +  String.valueOf(bub.getLifes()));
        List<BubbleController> toRemove = new ArrayList<>();
        this.toRemoveEnemies = new ArrayList<>();
        List<PowerUpController> toRemovePowerUps = new ArrayList<>();

        controller.update();
        bubblesList.addAll(controller.getBubbleControllers());
        controller.getBubbleControllers().clear();

        updatePowerUps(toRemovePowerUps);

        updateBubbles(toRemove);

        updateEnemies(toRemoveEnemies);

        cleanupEntities(toRemove, toRemoveEnemies, toRemovePowerUps);
    }

    /**
     * Update the power ups that can be consumed
     *
     * @param toRemovePowerUps List of power ups to remove
     */
    private void updatePowerUps(List<PowerUpController> toRemovePowerUps) {
        powerUpControllers.stream()
                .filter(p -> p.getView().getImageView().getParent() != null)
                .forEach(p -> {
                    p.update();
                    if (p.powerCollision(controller, p)) {
                        handlePowerUpCollection(p);
                        toRemovePowerUps.add(p);
                    }
                });
    }

    /**
     * Update the bubbles and their collisions with the enemies
     *
     * @param toRemove List of bubbles to remove
     */
    private void updateBubbles(List<BubbleController> toRemove) {
        bubblesList.forEach(b -> {
            b.update();
            enemiesList.stream()
                    .filter(enemy -> b.enemiesCollision(b, enemy) || !b.isAlive())
                    .findFirst()
                    .ifPresent(enemy -> toRemove.add(b));
        });
    }

    /**
     * Update the enemies and theri collision with the Bub
     *
     * @param toRemoveEnemies List of enemies to be removed
     */
    private void updateEnemies(List<EnemyController> toRemoveEnemies) {
        enemiesList.forEach(e -> {
            e.update();
            if (e.enemiesCollision(controller, e) && e.isCaptured()) {
                toRemoveEnemies.add(e);
                handleEnemyDefeat(e);
            }
        });
    }

    /**
     * Add an Enemy who is defeated instantly
     *
     * @param enemyController Controller of an Entity of type Enemy
     */
    public void addEnemy(EnemyController enemyController){
        this.toRemoveEnemies.add(enemyController);
    }

    /**
     * Update kill count and player's score
     *
     * @param enemy Controller of an Entity of type Enemy
     */
    public void handleEnemyDefeat(EnemyController enemy) {
        audioManager.playSoundEffect("enemy_defeat");
        enemiesKilledCount++;
        currentScore += ENEMY_KILL_SCORE;
        if(currentProfile != null) {
            currentProfile.setCurrentScore(currentScore);
            if(currentScore > currentProfile.getHighScore()) {
                currentProfile.setHighScore(currentScore);
            }
        }
        spawnPowerUps();
    }

    /**
     * Add a power up to the scene
     */
    private void spawnPowerUps() {
        if (enemiesKilledCount > 0 && enemiesKilledCount <= powerUpControllers.size()) {
            PowerUpController powerUp = powerUpControllers.get(enemiesKilledCount - 1);
            if (!root.getChildren().contains(powerUp.getView().getImageView())) {
                root.getChildren().add(powerUp.getView().getImageView());
                activePowerUps.add(powerUp);
            }
        }
    }

    /**
     * Give to the player the ability to use a power up or take advantage from it
     *
     * @param powerUp Controller of an Entity of type Power Up
     */
    public void handlePowerUpCollection(PowerUpController powerUp) {
        audioManager.playSoundEffect("powerup");
        currentScore += POWERUP_SCORE;
        if(currentProfile != null) {
            currentProfile.setCurrentScore(currentScore);
            if(currentScore > currentProfile.getHighScore()) {
                currentProfile.setHighScore(currentScore);
            }
        }
        controller.handlePowerUp(powerUp.getFrame());
    }

    /**
     * Add extra points in specific situations
     *
     * @param points Value to sum to the general score
     */
    public void addScore(int points) {
        currentScore += points;
        if(currentProfile != null) {
            currentProfile.setCurrentScore(currentScore);
            if(currentScore > currentProfile.getHighScore()) {
                currentProfile.setHighScore(currentScore);
            }
        }
    }

    /**
     * Remove from the scene the entities that are no more
     *
     * @param toRemove List of bubbles to be removed
     * @param toRemoveEnemies List of enemies to be removed
     * @param toRemovePowerUps List of power ups to be removed
     */
    private void cleanupEntities(List<BubbleController> toRemove,
                                 List<EnemyController> toRemoveEnemies,
                                 List<PowerUpController> toRemovePowerUps) {
        for (PowerUpController powerUp : toRemovePowerUps) {
            root.getChildren().remove(powerUp.getView().getImageView());
            activePowerUps.remove(powerUp);
        }
        for (BubbleController controller : toRemove) {
            root.getChildren().remove(controller.getView().getImageView());
            bubblesList.remove(controller);
        }
        enemiesList.removeAll(toRemoveEnemies);
    }

    /**
     * Control and handle the game state at the end of every level
     */
    private void handleLevelCompletion() {
        isChangingLevel = true;
        isLevelInitialized = false;
        timer.stop();

        if(controller.entity instanceof Bub bub && bub.haveLife()) {
            currentScore += PERFECT_LEVEL_SCORE;
        } else {
            currentScore += LEVEL_COMPLETE_SCORE;
        }

        if(currentProfile != null) {
            currentProfile.setCurrentScore(currentScore);
            if(currentScore > currentProfile.getHighScore()) {
                currentProfile.setHighScore(currentScore);
            }
        }

        Platform.runLater(() -> {
            if (currentLevel >= MAX_LEVEL) {
                audioManager.playMusic("victory", false);
                currentProfile.setGamesWon(1);
                currentProfile.setCurrentLevel(1);
                UserProfileManager.saveProfile(currentProfile);
                GameScreenManager.showVictoryScreen(gameStage);
            } else {
                GameScreenManager.showContinueScreen(gameStage, currentLevel, this);
            }
        });
    }

    /**
     * Remove all the entities, except for the Bub, at the end of the level
     */
    private void cleanupCurrentLevel() {
        if (timer != null) {
            timer.stop();
        }
        bubblesList.clear();
        enemiesList.clear();
        enemiesKilledCount = 0;
        powerUpControllers.clear();
        isLevelInitialized = false;
        if (root != null) {
            root.getChildren().clear();
        }
    }

    /**
     * Load the next level after the previous was completed
     */
    public void startNextLevel() {
        try {
            currentLevel++;
            if (currentProfile != null) {
                currentProfile.setCurrentLevel(currentLevel);
                UserProfileManager.saveProfile(currentProfile);
            }
            cleanupCurrentLevel();
            loadLevel(currentLevel);
        } catch (IOException e) {
            e.printStackTrace();
            stopGame();
        }
    }

    /**
     * Return to the main menu and stop the game loop
     */
    public void stopGame() {
        if(timer != null){
            timer.stop();
        }
        audioManager.stopMusic();
        if(currentProfile != null){
            currentProfile.setCurrentLevel(currentLevel);
            currentProfile.setCurrentScore(currentScore);
            if(currentScore > currentProfile.getHighScore()) {
                currentProfile.setHighScore(currentScore);
            }
            UserProfileManager.saveProfile(currentProfile);
        }
        try {
            JBubbleBobble menu = new JBubbleBobble();
            menu.mainMenu(gameStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}