package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.BubController;
import it.metodologie.bubblebobblenes.controller.EntityController;
import it.metodologie.bubblebobblenes.singleton.AudioManager;

import javafx.scene.input.KeyCode;
import java.util.Set;

/**
 * Handles movement for the player Bub.
 * Responds to keyboard inputs for movement and jumping.
 */
public class BubMovement implements MovementBehaviour{
    /** Audio effects manager */
    private AudioManager audioManager;
    /** Set of currently pressed keys */
    protected final Set<KeyCode> pressedKeys;
    /** Flag to prevent multiple jumps */
    private boolean jumpKeyPressed = false;

    /**
     * Constructor for the Movement
     *
     * @param pressedKeys Reference from the keyboard
     */
    public BubMovement(Set<KeyCode> pressedKeys) {
        this.pressedKeys = pressedKeys;
        audioManager = AudioManager.getInstance();
    }

    /**
     * Handles character movement based on player input.
     * Includes jump handling, lateral movement, and power-up bonus points.
     *
     * @param controller The controller of the Bub
     */
    @Override
    public void move(EntityController controller) {
        if (pressedKeys.contains(KeyCode.UP) && !jumpKeyPressed) {
            audioManager.playSoundEffect("jump");
            controller.jump();
            jumpKeyPressed = true;
            if (controller instanceof BubController bubController) {
                if (bubController.getPowerUpHandler().isCrystalActive()) {
                    bubController.addScore(10);
                }
            }
        }
        if (!pressedKeys.contains(KeyCode.UP)) {
            jumpKeyPressed = false;
        }

        if (pressedKeys.contains(KeyCode.LEFT)) {
            controller.moveLeft();
        }
        if (pressedKeys.contains(KeyCode.RIGHT)) {
            controller.moveRight();
        }
    }

    /**
     * Checks Bub condition
     *
     * @return True if alive, false otherwise
     */
    @Override
    public boolean isAlive() {
        return false;
    }
}