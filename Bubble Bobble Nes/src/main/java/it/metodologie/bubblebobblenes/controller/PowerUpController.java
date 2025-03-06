package it.metodologie.bubblebobblenes.controller;

import it.metodologie.bubblebobblenes.model.Level;
import it.metodologie.bubblebobblenes.model.PowerUp;
import it.metodologie.bubblebobblenes.view.EntityView;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Controller for the entity Power Up
 */
public class PowerUpController extends EntityController{
    /** Save the name of the power up */
    private String frame;
    /** Model of the Power Up */
    private PowerUp powerUp;
    /** View of the Power Up */
    private EntityView view;
    /** Main Pane */
    private Pane root;

    /**
     * Constructor of the Controller
     *
     * @param powerUp Model of the Power Up
     * @param view View of the Power Up
     * @param platforms Reference to the platforms
     * @param scene Main Scene
     * @param root Main Pane
     */
    public PowerUpController(PowerUp powerUp, EntityView view, List<Level> platforms, Scene scene, Pane root) {
        super(powerUp, view, platforms, scene);
        this.powerUp = powerUp;
        this.view = view;
        this.root = root;
        this.frame = view.getCurrentFrame();
    }

    /**
     * Update, if necessary, the coordinates in the scene
     */
    public void update(){
        view.getImageView().setLayoutX(entity.getX());
        view.getImageView().setLayoutY(entity.getY());
    }

    /**
     * View object of the Power Up
     * @return The View of the Power Up
     */
    public EntityView getView() {
        return this.view;
    }

    /**
     * Save the name/type of the Power Up spawned
     *
     * @return The file name saved in the /resources/images/
     */
    public String getFrame(){ return frame; }

    /**
     * Control if the Bub takes a Power Up
     *
     * @param bubController Controller of the Bub
     * @param powerUpController Controller of the Power Up
     * @return
     */
    public boolean powerCollision(BubController bubController, PowerUpController powerUpController) {
        return EntityController.checkCollision(bubController, powerUpController);
    }
}