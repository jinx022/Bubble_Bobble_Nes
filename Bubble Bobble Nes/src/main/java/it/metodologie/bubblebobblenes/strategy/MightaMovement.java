package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EntityController;

/**
 * Implements movement for Mighta-type enemy.
 * Enemy moves horizontally and falls under gravity effect.
 */
public class MightaMovement implements MovementBehaviour {
    /** Current horizontal movement direction */
    private boolean movingRight = true;
    /** Total duration of current horizontal movement */
    private int horizontalMoveDuration = 0;
    /** Timer tracking remaining movement time */
    private int horizontalMoveTimer = 0;
    /** Vertical speed affected by gravity */
    private double ySpeed = 0;
    /** Speed of horizontal movement */
    private static final int MOVE_SPEED = 1;
    /** Minimum duration for movement in one direction */
    private static final int MIN_MOVE_DURATION = 120;
    /** Maximum duration for movement in one direction */
    private static final int MAX_MOVE_DURATION = 180;
    /** Minimum distance from screen edges */
    private static final int BORDER_PADDING = 8;
    /** Maximum falling speed */
    private static final int MAX_FALL_SPEED = 10;
    /** Gravity acceleration value */
    private static final double GRAVITY = 0.5;
    /** Probability to change direction during movement */
    private static final double DIRECTION_CHANGE_PROBABILITY = 0.2;

    /**
     * Handles Mighta enemy movement:
     * - Horizontal movement with random direction changes
     * - Vertical falling with gravity
     * - Platform collision handling
     */
    @Override
    public void move(EntityController controller) {
        ySpeed += GRAVITY;

        if (ySpeed > MAX_FALL_SPEED) {
            ySpeed = MAX_FALL_SPEED;
        }

        controller.entity.setY(controller.entity.getY() + ySpeed);

        if (controller.checkPlatformCollision()) {
            ySpeed = 0;
        }

        if (controller.entity.getY() + controller.entity.getHeight() > 200) {
            controller.entity.setY(8);
            ySpeed = 0;
        }

        if (horizontalMoveTimer <= 0) {
            horizontalMoveDuration = MIN_MOVE_DURATION + (int)(Math.random() * (MAX_MOVE_DURATION - MIN_MOVE_DURATION));
            horizontalMoveTimer = horizontalMoveDuration;

            if (Math.random() < DIRECTION_CHANGE_PROBABILITY) {
                movingRight = !movingRight;
            }
        }

        if (horizontalMoveTimer > 0) {
            for (int i = 0; i < MOVE_SPEED; i++) {
                if (movingRight) {
                    if (controller.entity.getX() >= 256 - controller.entity.getWidth() - BORDER_PADDING) {
                        movingRight = false;
                        break;
                    }
                    controller.entity.setX(controller.entity.getX() + 1);
                    controller.view.flipHorizontally(true);
                    controller.view.nextFrame();
                } else {
                    if (controller.entity.getX() <= BORDER_PADDING) {
                        movingRight = true;
                        break;
                    }
                    controller.entity.setX(controller.entity.getX() - 1);
                    controller.view.flipHorizontally(false);
                    controller.view.nextFrame();
                }
            }
            horizontalMoveTimer--;
        }
    }

    /**
     * Checks Mighta condition
     *
     * @return True if alive, false otherwise
     */
    @Override
    public boolean isAlive() {
        return false;
    }
}