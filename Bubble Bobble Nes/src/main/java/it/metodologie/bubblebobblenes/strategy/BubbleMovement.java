package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EntityController;

/**
 * Handles bubble movement in the game.
 * Bubbles move horizontally first, then vertically upward.
 */
public class BubbleMovement implements MovementBehaviour<EntityController>{
    /** Total distance moved horizontally */
    private double movedDistance = 0;
    /** Vertical movement speed */
    private double verticalSpeed = 1.5;
    /** Maximum horizontal distance to travel */
    protected double horizontalDistance = 50;
    /** Horizontal movement speed */
    protected double horizontalSpeed = 8;
    /** Flag for horizontal movement completion */
    private boolean isHorizontalMoveCompleted = false;
    /** Bubble state */
    private boolean isAlive = true;
    /** Start time of vertical movement */
    private long startTime;
    /** Maximum bubble lifetime in milliseconds */
    public static final long BUBBLE_LIFETIME = 10000;

    /**
     * Handles bubble movement in two phases:
     * 1. Horizontal movement until reaching set distance
     * 2. Vertical movement until disappearance or collision
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
                } else if (controller.entity.getX() + controller.entity.getWidth()>= 248) {
                    controller.entity.setX(248 - controller.entity.getWidth());
                }
            } else if (!isHorizontalMoveCompleted) {
                isHorizontalMoveCompleted = true;
                startTime = System.currentTimeMillis();
            }
            if (isHorizontalMoveCompleted) {
                controller.entity.setY(controller.entity.getY() - verticalSpeed);
                if (controller.entity.getY() <= 8) {
                    controller.entity.setY(8);
                }

                if (System.currentTimeMillis() - startTime >= BUBBLE_LIFETIME) {
                    this.isAlive = false;
                }
            }
        }
    }

    /**
     * Checks bubble condition
     *
     * @return True if alive, false otherwise
     */
    public boolean isAlive() {
        return isAlive;
    }
}