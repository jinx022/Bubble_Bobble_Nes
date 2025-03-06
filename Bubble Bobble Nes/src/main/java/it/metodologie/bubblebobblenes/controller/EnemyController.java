package it.metodologie.bubblebobblenes.controller;

import it.metodologie.bubblebobblenes.model.Enemy;
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
 * Controller of the Enemy
 */
public class EnemyController extends EntityController implements Subject{
    /** Model of the Enemy */
    private Enemy enemy;
    /** View of the Enemy */
    private EntityView view;
    /** Movement of the Enemy */
    private MovementBehaviour movementBehaviour;
    /** Main Pane */
    private Pane root;
    /** List of images for the View */
    private List<String> animationFrames = new ArrayList<>();
    /** List of observer, for this class the Bub */
    private List<Observer> observerList = new ArrayList<>();
    /** Checks if an Enemy has been captured */
    private boolean isCaptured = false;
    /** Checks if an Enemy can move or not */
    private boolean movementEnabled = true;

    /**
     * Constructor for the controller
     *
     * @param enemy Model of the Enemy
     * @param view View of the Enemy
     * @param platforms Reference to the platforms
     * @param scene Main Scene
     * @param root Main Pane
     */
    public EnemyController(Enemy enemy, EntityView view, List<Level> platforms, Scene scene, Pane root) {
        super(enemy, view, platforms, scene);
        this.enemy = enemy;
        this.view = view;
        String animationFrame = view.getCurrentFrame();
        if (animationFrame.startsWith("mighta")) {
            this.movementBehaviour = new MightaMovement();
        } else if (animationFrame.startsWith("monsta")) {
            this.movementBehaviour = new MonstaMovement();
        } else {
            this.movementBehaviour = new ZenMovement();
        }
        this.root = root;
    }

    /**
     * Manage all the situation in which an Enemy can be;
     * if it's free, captured and even for special type
     */
    public void update() {
        if(movementEnabled && !isCaptured()){
            movementBehaviour.move(this);
            if(!(movementBehaviour instanceof MonstaMovement)){
                applyGravity();
                checkPlatformCollision();
            }
        } else if(movementBehaviour instanceof BubbleMovement) {
            movementBehaviour.move(this);
        }
        view.getImageView().setLayoutX(enemy.getX());
        view.getImageView().setLayoutY(enemy.getY());

    }

    /**
     * Set if an Enemy can move or not
     *
     * @param enabled True if they can move, false otherwise
     */
    public void setMovementEnabled(boolean enabled) { this.movementEnabled = enabled; }

    public boolean enemiesCollision(BubController bubController, EnemyController enemyController) {
        boolean collision = EntityController.checkCollision(bubController, enemyController);
        if(collision){
            if(isCaptured)
                capturedEnemyCollision();
            else
                bubCollision(bubController);
        }
        return collision;
    }

    /**
     * Handle the collision with the Bub
     *
     * @param bub Controller of the Bub
     */
    public void bubCollision(BubController bub){
        bub.hit();
        bub.setCanMove(false);
        addObserver(bub.getView());
        root.getChildren().remove(bub.getView().getImageView());
        setImageView("bub_death_0", "bub_death_1", "bub_death_2", "bub_death_3");
        root.getChildren().add(bub.getView().getImageView());

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                    Platform.runLater(() -> {
                        root.getChildren().remove(bub.getView().getImageView());
                        setImageView("bub_0", "bub_1");
                        bub.entity.setX(5);
                        bub.entity.setY(170);
                        bub.setCanMove(true);
                        root.getChildren().add(bub.getView().getImageView());
                    });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Set the new images to show when the Bub get killed or respawn
     *
     * @param imageNames Name of the images saved
     */
    public void setImageView(String... imageNames) {
        animationFrames.clear();
        animationFrames.addAll(Arrays.asList(imageNames));
        notifyObservers();
    }

    /**
     * Handle an Enemy who collided with a Bubble
     */
    public void capturedEnemyCollision(){
        Platform.runLater(() -> {
            root.getChildren().remove(view.getImageView());
            isCaptured = false;
        });
    }

    /**
     * View object of the Enemy
     *
     * @return The View of the Enemy
     */
    public EntityView getView() {
        return this.view;
    }

    /**
     * Set if an Enemy has been captured
     *
     * @param captured True if its captured, false otherwise
     */
    public void setCaptured(boolean captured) {
        this.isCaptured = captured;
    }

    /**
     * Checks if an Enemy is free to move or not
     *
     * @return True if its captured, false otherwise
     */
    public boolean isCaptured() {
        return isCaptured;
    }

    /**
     * Set the movement behaviour for different types of enemies
     * or restore the original one
     *
     * @param newMovementBehaviour The movement behaviour to use
     */
    public void setMovementBehaviour(MovementBehaviour newMovementBehaviour) {
        this.movementBehaviour = newMovementBehaviour;
    }

    /**
     *  Checks which movement behaviour is set for the enemies
     *
     * @return The specific movement behaviour
     */
    public MovementBehaviour getMovementBehaviour() {
        return movementBehaviour;
    }

    /**
     * Add the Bub to the observer list
     *
     * @param o Controller of the Bub
     */
    @Override
    public void addObserver(Observer o) {
        observerList.add(o);
    }

    /**
     * Remove the Bub from the observer list
     *
     * @param o Controller of the Bube
     */
    @Override
    public void removeObserver(Observer o) {
        observerList.remove(o);
    }

    /**
     * Notify the Bub of its view change
     */
    @Override
    public void notifyObservers() {
        observerList.forEach(o -> o.update(animationFrames.toArray(new String[0])));
    }
}