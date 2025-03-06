package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EntityController;

/**
 * Handles Fire Bubble movement in the game.
 * Fire Bubble move horizontally first, then vertically downward.
 */
public class FireBubble implements MovementBehaviour<EntityController> {
    /** Maximum horizontal distance to travel */
    private double horizontalDistance = 50;
    /** Total distance moved horizontally */
    private double movedDistance = 0;
    /** Horizontal movement speed */
    private double horizontalSpeed = 8;
    /** Vertical movement speed */
    private double verticalSpeed = 2;
    /** Flag for horizontal movement completion */
    private boolean isHorizontalMoveCompleted = false;
    /** Bubble state */
    private boolean isAlive = true;
    /** Start time of vertical movement */
    private long startTime;
    /** Maximum bubble lifetime in milliseconds */
    private static final long BUBBLE_LIFETIME = 10000;

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
                controller.entity.setY(controller.entity.getY() + verticalSpeed);
                if (controller.entity.getY() >= 248) {
                    controller.entity.setY(248);
                }

                if (System.currentTimeMillis() - startTime >= BUBBLE_LIFETIME) {
                    this.isAlive = false;
                }
            }
        }
    }

    /**
     * Checks Fire Bubble condition
     *
     * @return True if alive, false otherwise
     */
    public boolean isAlive() {
        return isAlive;
    }
}