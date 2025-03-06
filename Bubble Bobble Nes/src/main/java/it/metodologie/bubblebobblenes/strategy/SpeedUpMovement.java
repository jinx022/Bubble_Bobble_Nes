package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EntityController;

import javafx.scene.input.KeyCode;

import java.util.Set;

/**
 * Implements accelerated movement for the main character.
 * Activated by "shoes" power-up, increases movement speed.
 */
public class SpeedUpMovement extends BubMovement {
    /** Flag to prevent multiple jumps */
    private boolean jumpKeyPressed = false;
    /** Constant to accelerate movement */
    private static final double SPEED_MULTIPLIER = 1.5;

    /**
     * Constructor
     * @param pressedKeys Reference from the keyboard
     */
    public SpeedUpMovement(Set<KeyCode> pressedKeys) {
        super(pressedKeys);
    }

    /**
     * Handles accelerated movement:
     * - Maintains same control logic as base movement
     * - Applies speed multiplier to movement
     */
    @Override
    public void move(EntityController controller) {
        if (pressedKeys.contains(KeyCode.UP) && !jumpKeyPressed) {
            controller.jump();
            jumpKeyPressed = true;
        }
        if (!pressedKeys.contains(KeyCode.UP)) {
            jumpKeyPressed = false;
        }

        if (pressedKeys.contains(KeyCode.LEFT)) {
            int steps = (int) SPEED_MULTIPLIER;
            double remainder = SPEED_MULTIPLIER - steps;

            for (int i = 0; i < steps; i++) {
                controller.moveLeft();
            }
            if (Math.random() < remainder) {
                controller.moveLeft();
            }
        }
        if (pressedKeys.contains(KeyCode.RIGHT)) {
            int steps = (int) SPEED_MULTIPLIER;
            double remainder = SPEED_MULTIPLIER - steps;

            for (int i = 0; i < steps; i++) {
                controller.moveRight();
            }
            if (Math.random() < remainder) {
                controller.moveRight();
            }
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