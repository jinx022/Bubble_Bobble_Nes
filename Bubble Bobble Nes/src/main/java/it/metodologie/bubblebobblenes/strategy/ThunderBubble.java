package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EntityController;

/**
 * Handles Thunder Bubble movement in the game.
 * Bubbles move horizontally.
 */
public class ThunderBubble implements MovementBehaviour<EntityController> {
    /** Maximum horizontal distance to travel */
    private double horizontalDistance = 50;
    /** Total distance moved horizontally */
    private double movedDistance = 0;
    /** Horizontal movement speed */
    private double horizontalSpeed = 8;
    /** Flag for horizontal movement completion */
    private boolean isHorizontalMoveCompleted = false;
    /** Bubble state */
    private boolean isAlive = true;
    /** Start time of vertical movement */
    private long startTime;
    /** Maximum bubble lifetime in milliseconds */
    private static final long BUBBLE_LIFETIME = 3000;

    /**
     * Horizontal movement until reaching set distance
     */
    public void move(EntityController controller) {
        if (isAlive) {
            if (movedDistance < horizontalDistance) {
                if (controller.entity.isFacingRight()) {
                    controller.entity.setX(controller.entity.getX() + horizontalSpeed);
                } else {
                    controller.entity.setX(controller.entity.getX() - horizontalSpeed);
                }
                movedDistance += horizontalSpeed;
                if (controller.entity.getX() <= 8) {
                    controller.entity.setX(8);
                } else if (controller.entity.getX() + controller.entity.getWidth() >= 248) {
                    controller.entity.setX(248 - controller.entity.getWidth());
                }
            } else if (!isHorizontalMoveCompleted) {
                isHorizontalMoveCompleted = true;
                startTime = System.currentTimeMillis();
            }
            if (isHorizontalMoveCompleted) {
                if (System.currentTimeMillis() - startTime >= BUBBLE_LIFETIME) {
                    this.isAlive = false;
                }
            }
        }
    }

    /**
     * Checks Thunder Bubble condition
     *
     * @return True if alive, false otherwise
     */
    public boolean isAlive() {
        return isAlive;
    }
}