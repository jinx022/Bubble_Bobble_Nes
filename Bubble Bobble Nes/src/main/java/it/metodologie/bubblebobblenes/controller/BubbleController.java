package it.metodologie.bubblebobblenes.controller;

import it.metodologie.bubblebobblenes.GameManager;
import it.metodologie.bubblebobblenes.model.Bubble;
import it.metodologie.bubblebobblenes.model.Level;
import it.metodologie.bubblebobblenes.observer.Observer;
import it.metodologie.bubblebobblenes.strategy.*;
import it.metodologie.bubblebobblenes.subject.Subject;
import it.metodologie.bubblebobblenes.view.EntityView;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller for the Bubbles
 */
public class BubbleController extends EntityController implements Subject {
    /** Model of the Bubble */
    private Bubble bubble;
    /** View of the Bubble */
    private EntityView view;
    /** Rules movement for the bubbles */
    private MovementBehaviour movementBehaviour;
    /** Instance of the game loop */
    private GameManager gameManager;
    /** Pane of the game */
    private Pane root;
    /** Controller of an Enemy captured in a bubble */
    private EnemyController capturedEnemy = null;
    /** List of images for the View */
    private List<String> animationFrames = new ArrayList<>();
    /** List of observes, for this class the enemies */
    private List<Observer> observerList = new ArrayList<>();

    /**
     * Constructor for the controller
     *
     * @param bubble Model of the Bubble
     * @param view View of the Bubble
     * @param platforms Reference to the platforms
     * @param scene Main Scene
     * @param root Main Pane
     * @param gameManager Game loop manager
     */
    public BubbleController(Bubble bubble, EntityView view, List<Level> platforms, Scene scene, Pane root, GameManager gameManager) {
        super(bubble, view, platforms, scene);
        this.bubble = bubble;
        this.view = view;
        this.movementBehaviour = new BubbleMovement();
        this.root = root;
        this.gameManager = gameManager;
    }

    /**
     * Update the view of the bubbles
     */
    @Override
    public void update() {
        applyGravity();
        view.getImageView().setLayoutX(bubble.getX());
        view.getImageView().setLayoutY(bubble.getY());
    }

    /**
     * Set a new movement behaviour for the bubbles
     * @param newMovementBehaviour Movement behaviour to set
     */
    public void setMovementBehaviour(MovementBehaviour newMovementBehaviour) {
        this.movementBehaviour = newMovementBehaviour;
    }

    /**
     * Control if a bubble or an enemy are touching
     *
     * @param bubbleController Controller of Bubble type
     * @param enemyController Controller of Enemy type
     * @return True if they touched, False otherwise
     */
    public boolean enemiesCollision(BubbleController bubbleController, EnemyController enemyController) {
        if(capturedEnemy != null) {
            return false;
        }
        boolean collision = EntityController.checkCollision(bubbleController, enemyController);

        if (collision) {
            if (movementBehaviour instanceof FireBubble || movementBehaviour instanceof ThunderBubble) {
                handleInstantDefeat(enemyController);
            } else {
                capturedEnemy(enemyController);
            }
        }
        return collision;
    }

    /**
     * Kill an Enemy, without been captured, if collide with a Fire Bubble or a Thunder
     *
     * @param enemy Enemy Controller to be removed
     */
    private void handleInstantDefeat(EnemyController enemy) {
        enemy.setCaptured(true);
        gameManager.handleEnemyDefeat(enemy);
        root.getChildren().remove(enemy.getView().getImageView());
        gameManager.addEnemy(enemy);

        if (movementBehaviour instanceof FireBubble) {
            DefeatEffectHandler.playDefeatEffect(
                    enemy,
                    root,
                    DefeatEffectHandler.EffectType.FIRE,
                    null
            );
        }else if(movementBehaviour instanceof ThunderBubble){
            DefeatEffectHandler.playDefeatEffect(
                    enemy,
                    root,
                    DefeatEffectHandler.EffectType.THUNDER,
                    null
            );
        }
    }

    /**
     * Handle the Enemy that has been captured
     *
     * @param enemy Controller of the Enemy captured
     */
    public void capturedEnemy(EnemyController enemy) {
        addObserver(enemy.getView());
        this.capturedEnemy = enemy;
        root.getChildren().remove(enemy.getView().getImageView());
        enemy.setCaptured(true);
        String enemyType = enemy.getView().getCurrentFrame().split("_")[0];

        MovementBehaviour newMovement;
        String[] capturedFrames;

        switch (enemyType) {
            case "mighta":
                newMovement = new BubbleMovement();
                capturedFrames = new String[]{"mighta_captured_0", "mighta_captured_1", "mighta_captured_2"};
                break;
            case "monsta":
                newMovement = new BubbleMovement();
                capturedFrames = new String[]{"monsta_captured_0", "monsta_captured_1", "monsta_captured_2"};
                break;
            default: // "zen" o altri
                newMovement = new BubbleMovement();
                capturedFrames = new String[]{"zen_captured_0", "zen_captured_1", "zen_captured_2"};
                break;
        }
        enemy.setMovementBehaviour(newMovement);
        setImageView(capturedFrames);
        root.getChildren().add(enemy.getView().getImageView());

        new Thread(() -> {
            try {
                Thread.sleep(BubbleMovement.BUBBLE_LIFETIME);
                if (enemy.isCaptured()) {
                    Platform.runLater(() -> {
                        enemy.setCaptured(false);
                        MovementBehaviour originalMovement;
                        String[] originalFrames;

                        switch (enemyType) {
                            case "mighta":
                                originalMovement = new MightaMovement();
                                originalFrames = new String[]{"mighta_0", "mighta_1"};
                                break;
                            case "monsta":
                                originalMovement = new MonstaMovement();
                                originalFrames = new String[]{"monsta_0", "monsta_1"};
                                break;
                            default: // "zen" o altri
                                originalMovement = new ZenMovement();
                                originalFrames = new String[]{"zen_0", "zen_1"};
                                break;
                        }
                        enemy.setMovementBehaviour(originalMovement);
                        root.getChildren().remove(enemy.getView().getImageView());
                        setImageView(originalFrames);
                        root.getChildren().add(enemy.getView().getImageView());
                        removeObserver(enemy.getView());
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Notify the View with the new frames to display
     *
     * @param imageNames Images to display as new animation
     */
    public void setImageView(String... imageNames) {
        animationFrames.clear();
        animationFrames.addAll(Arrays.asList(imageNames));
        notifyObservers();
    }

    /**
     * Follow the behaviour of the movement set
     */
    @Override
    public void applyGravity() {
        movementBehaviour.move(this);
    }

    /**
     * Except the top platforms, the bubbles move forward any other platforms inside the scene
     *
     * @return Never true
     */
    @Override
    public boolean checkPlatformCollision(){
        return false;
    }

    /**
     * View Object for the bubbles
     *
     * @return The View of the bubbles
     */
    public EntityView getView() {
        return this.view;
    }

    /**
     * Follow the timer set in the movement behaviour to know if a Bubble is still on the scene or not
     *
     * @return True if the time is not elapsed, false otherwise
     */
    public boolean isAlive(){
        return movementBehaviour.isAlive();
    }

    /**
     * Add an entity to the observer list to know how their view change
     *
     * @param o Observer who changed its view
     */
    @Override
    public void addObserver(Observer o) {
        observerList.add(o);
    }

    /**
     * Remove an entity from the observer list as its no more necessary to receive updated for the view
     *
     * @param o Observer who doesn't need to be notified
     */
    @Override
    public void removeObserver(Observer o) {
        observerList.remove(o);
    }

    /**
     * Notify the observers of the changes to apply
     */
    @Override
    public void notifyObservers() {
        observerList.forEach(o -> o.update(animationFrames.toArray(new String[0])));
    }
}