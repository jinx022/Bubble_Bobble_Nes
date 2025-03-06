package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EntityController;
import it.metodologie.bubblebobblenes.model.Level;

/**
 * Implements movement for Monsta-type enemy.
 * Moves both horizontally and vertically, bouncing off obstacles.
 */
public class MonstaMovement implements MovementBehaviour {
    /** Current horizontal movement direction */
    private boolean movingRight = true;
    /** Current vertical movement direction */
    private boolean movingUp = true;
    /** Timestamp of last direction change */
    private long lastDirectionChange = System.currentTimeMillis();
    /** Minimum distance from screen edges */
    private static final int BORDER_PADDING = 8;
    /** Movement speed in all directions */
    private static final double MOVEMENT_SPEED = 0.5;
    /** Probability of random direction change */
    private static final double DIRECTION_CHANGE_PROBABILITY = 0.02;
    /** Minimum time between direction changes */
    private static final long DIRECTION_CHANGE_INTERVAL = 2000;

    /**
     * Handles Monsta enemy movement:
     * - Smooth movement in all directions
     * - Bouncing off platforms and screen edges
     * - Occasional direction changes
     */
    @Override
    public void move(EntityController controller) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDirectionChange > DIRECTION_CHANGE_INTERVAL) {
            if (Math.random() < DIRECTION_CHANGE_PROBABILITY) {
                movingRight = !movingRight;
                movingUp = !movingUp;
                lastDirectionChange = currentTime;
            }
        }

        double nextX = controller.entity.getX() + (movingRight ? MOVEMENT_SPEED : -MOVEMENT_SPEED);
        double nextY = controller.entity.getY() + (movingUp ? -MOVEMENT_SPEED : MOVEMENT_SPEED);

        if (nextX <= BORDER_PADDING || nextX >= 256 - controller.entity.getWidth() - BORDER_PADDING) {
            movingRight = !movingRight;
            nextX = controller.entity.getX();
        }
        if (nextY <= BORDER_PADDING || nextY >= 200 - controller.entity.getHeight() - BORDER_PADDING) {
            movingUp = !movingUp;
            nextY = controller.entity.getY();
        }

        boolean canMove = true;
        for (var platform : controller.platforms) {
            if (checkPlatformCollision(controller, nextX, nextY, platform)) {
                canMove = false;
                if (isHorizontalCollision(controller, nextX, controller.entity.getY(), platform)) {
                    movingRight = !movingRight;
                }
                if (isVerticalCollision(controller, controller.entity.getX(), nextY, platform)) {
                    movingUp = !movingUp;
                }
                break;
            }
        }

        if (canMove) {
            controller.entity.setX(nextX);
            controller.entity.setY(nextY);
            if (movingRight) {
                controller.view.flipHorizontally(true);
            } else {
                controller.view.flipHorizontally(false);
            }
        }
        controller.view.nextFrame();
    }

    /**
     * Checks for collision between the entity and a platform at the next position.
     *
     * @param controller The entity controller
     * @param nextX Next X position to check
     * @param nextY Next Y position to check
     * @param platform The platform to check against
     * @return true if collision detected, false otherwise
     */
    private boolean checkPlatformCollision(EntityController controller, double nextX, double nextY, Level platform) {
        return nextX < platform.getX() + platform.getWidth() &&
                nextX + controller.entity.getWidth() > platform.getX() &&
                nextY < platform.getY() + platform.getHeight() &&
                nextY + controller.entity.getHeight() > platform.getY();
    }

    /**
     * Checks for horizontal collision with a platform.
     *
     * @param controller The entity controller
     * @param nextX Next X position to check
     * @param currentY Current Y position
     * @param platform The platform to check against
     * @return true if horizontal collision detected, false otherwise
     */
    private boolean isHorizontalCollision(EntityController controller, double nextX, double currentY, Level platform) {
        return nextX < platform.getX() + platform.getWidth() &&
                nextX + controller.entity.getWidth() > platform.getX() &&
                currentY < platform.getY() + platform.getHeight() &&
                currentY + controller.entity.getHeight() > platform.getY();
    }

    /**
     * Checks for vertical collision with a platform.
     *
     * @param controller The entity controller
     * @param currentX Current X position
     * @param nextY Next Y position to check
     * @param platform The platform to check against
     * @return true if vertical collision detected, false otherwise
     */
    private boolean isVerticalCollision(EntityController controller, double currentX, double nextY, Level platform) {
        return currentX < platform.getX() + platform.getWidth() &&
                currentX + controller.entity.getWidth() > platform.getX() &&
                nextY < platform.getY() + platform.getHeight() &&
                nextY + controller.entity.getHeight() > platform.getY();
    }

    /**
     * Checks Monsta condition
     *
     * @return True if alive, false otherwise
     */
    @Override
    public boolean isAlive() {
        return false;
    }
}