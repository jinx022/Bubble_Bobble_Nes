package it.metodologie.bubblebobblenes.controller;

import it.metodologie.bubblebobblenes.GameManager;
import it.metodologie.bubblebobblenes.model.Bub;
import it.metodologie.bubblebobblenes.model.Bubble;
import it.metodologie.bubblebobblenes.model.Level;
import it.metodologie.bubblebobblenes.singleton.AudioManager;
import it.metodologie.bubblebobblenes.strategy.*;
import it.metodologie.bubblebobblenes.view.EntityView;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * Controller of the Bub - player
 */
public class BubController extends EntityController {
    /** Model of the Bub */
    private Bub bub;
    /** View of the Bub */
    private EntityView view;
    /** Reference to know how to apply a Power Up */
    private PowerUpHandler powerUpHandler;
    /** Keyboard command to move the Bub */
    private Set<KeyCode> pressedKeys = new HashSet<>();
    /** List of Bubbles */
    private List<BubbleController> bubbleControllers = new ArrayList<>();
    /** Main Pane */
    private Pane root;
    /** Game Manager to help handle all the connections */
    private GameManager gameManager;
    /** Object for the sounds effects */
    private AudioManager audioManager;
    /** Checks if the Bub is respawned or not */
    private boolean canMove = true;

    /**
     * Constructor of the Bub
     *
     * @param bub Model of the Bub
     * @param view View of the Bub
     * @param platforms Reference to the Platforms
     * @param scene Main Scene
     * @param root Main Pane
     * @param gameManager Handler of the game loop
     */
    public BubController(Bub bub, EntityView view, List<Level> platforms, Scene scene, Pane root, GameManager gameManager) {
        super(bub, view, platforms, scene);
        this.bub = bub;
        this.view = view;
        this.pressedKeys = new HashSet<>();
        this.movementBehaviour = new BubMovement(pressedKeys);
        this.root = root;
        this.gameManager = gameManager;
        this.powerUpHandler = new PowerUpHandler(this);
        this.audioManager = AudioManager.getInstance();

        Platform.runLater(() -> {
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE){
                    gameManager.stopGame();
                    return;
                }
                pressedKeys.add(event.getCode());
                if (event.getCode() == KeyCode.SPACE) {
                    shootBubble();
                }
            });

            scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
        });
    }

    /**
     * Shoot a Bubble for all the types available
     */
    private void shootBubble() {
        audioManager.playSoundEffect("bubble");
        if (powerUpHandler.isThunderBubbleActive()) {
            createThunderBubble();
        } else if (powerUpHandler.isFireBubbleActive()) {
            createFireBubble();
        } else {
            createBubble();
        }
    }

    /**
     * Create a special bubble of type Thunder
     *
     * @return A Thunder Bubble
     */
    private BubbleController createThunderBubble() {
        Bubble bubble = new Bubble((Bub) entity, entity.getX(), entity.getY(), 16, 16);
        EntityView bubbleView = new EntityView("thunder");
        BubbleController bubbleController = new BubbleController(bubble, bubbleView, platforms, scene, root, gameManager);
        bubbleController.setMovementBehaviour(new ThunderBubble());
        root.getChildren().add(bubbleView.getImageView());
        bubbleControllers.add(bubbleController);
        return bubbleController;
    }

    /**
     * Create a special bubble of type Fire
     *
     * @return A Fire Bubble
     */
    private BubbleController createFireBubble() {
        Bubble bubble = new Bubble((Bub) entity, entity.getX(), entity.getY(), 16, 16);
        EntityView bubbleView = new EntityView("bubble_fire_0", "bubble_fire_1", "bubble_fire_2");
        BubbleController bubbleController = new BubbleController(bubble, bubbleView, platforms, scene, root, gameManager);
        bubbleController.setMovementBehaviour(new FireBubble());
        root.getChildren().add(bubbleView.getImageView());
        bubbleControllers.add(bubbleController);
        return bubbleController;
    }

    /**
     * Create a default Bubble
     *
     * @return Normal Bubble without Power Ups
     */
    public BubbleController createBubble() {
        Bubble bubble = new Bubble((Bub) entity, entity.getX(), entity.getY(), 16, 16);
        EntityView bubbleView = new EntityView("bubble_0", "bubble_1");
        BubbleController bubbleController = new BubbleController(bubble, bubbleView, platforms, scene, root, gameManager);
        if(powerUpHandler.isBubbleMovement()){
            if(powerUpHandler.getPowerUpType().equals("candy_blue")){
                bubbleController.setMovementBehaviour(new CandyBlue());
            }else if(powerUpHandler.getPowerUpType().equals("candy_pink")){
                bubbleController.setMovementBehaviour(new CandyPink());
            }
        }
        root.getChildren().add(bubbleView.getImageView());
        bubbleControllers.add(bubbleController);
        return bubbleController;
    }

    /**
     * Update the view when the Bub is alive or when it gets killed
     */
    @Override
    public void update() {
        if(canMove){
            super.update();
            bub.updateInvulnerability();
        }
    }

    /**
     * Lost one life if Bub collide with any enemies
     */
    public void hit(){
        bub.setLifes(1);
    }

    /**
     * Add one more life if the Bub takes the Power Up of type Life
     */
    public void powerLife(){
        bub.setLifes(-1);
    }

    /**
     * The Bub doesn't lose life if it takes the Power Up of type Invincible
     */
    public void powerInvincible(){
        bub.getInvincible();
    }

    /**
     * Add bonus points to the general score
     *
     * @param points Points to add to the score
     */
    public void addScore(int points) {
        gameManager.addScore(points);
    }

    /**
     * Helps the managmente of some power ups
     *
     * @return The handler of the Power Ups
     */
    public PowerUpHandler getPowerUpHandler(){
        return powerUpHandler;
    }

    /**
     * Freeze all the enemies still alive in the position where they are
     * when the power up Clock is activated
     */
    public void freezeAllEnemies() {
        if (gameManager != null && gameManager.enemiesList != null) {
            for (EnemyController enemy : gameManager.enemiesList) {
                enemy.setMovementEnabled(false);
            }
        }
    }

    /**
     * Unfreeze all the enemies alive after few seconds, if not killed in the meanwhile
     */
    public void unfreezeAllEnemies() {
        if (gameManager != null && gameManager.enemiesList != null) {
            for (EnemyController enemy : gameManager.enemiesList) {
                enemy.setMovementEnabled(true);
            }
        }
    }

    /**
     * Play a special effects and remove all the enemies alive when the power up Dynamite is activated
     */
    public void triggerDynamiteEffect() {
        if (gameManager != null && gameManager.enemiesList != null) {
            List<EnemyController> enemies = new ArrayList<>(gameManager.enemiesList);
            for (EnemyController enemy : enemies) {
                enemy.setCaptured(true);

                DefeatEffectHandler.playDefeatEffect(
                        enemy,
                        root,
                        DefeatEffectHandler.EffectType.EXPLOSION,
                        () -> {
                            gameManager.handleEnemyDefeat(enemy);
                            Platform.runLater(() -> {
                                gameManager.enemiesList.remove(enemy);
                                root.getChildren().remove(enemy.getView().getImageView());
                            });
                        }
                );
            }
        }
    }

    /**
     * Set a new movement behaviour, in particular boost the default movement with power ups
     *
     * @param newMovementBehaviour New Movement Behaviour for the Bub
     */
    public void setMovementBehaviour(MovementBehaviour newMovementBehaviour) {
        this.movementBehaviour = newMovementBehaviour;
    }

    /**
     * The current movement behaviour of the Bub
     *
     * @return The Movement Behaviour in use
     */
    public MovementBehaviour getMovementBehaviour() {
        return this.movementBehaviour;
    }

    /**
     * To control the Bub with the keyboard
     *
     * @return The key pressed
     */
    public Set<KeyCode> getPressedKeys() {
        return this.pressedKeys;
    }

    /**
     * Handle the Power Ups
     *
     * @param powerUpType Name of the Power Up to apply
     */
    public void handlePowerUp(String powerUpType) {
        powerUpHandler.applyPowerUp(powerUpType);
    }

    /**
     * To use when the Bub gets killed
     *
     * @param canMove False if the Bub gets killed, true otherwise
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * View object of the Bub
     *
     * @return The View of the Bub
     */
    public EntityView getView() {
        return this.view;
    }

    /**
     * List of the bubble shot by the Bub
     *
     * @return List of the bubbles still alive
     */
    public List<BubbleController> getBubbleControllers() {
        return bubbleControllers;
    }
}