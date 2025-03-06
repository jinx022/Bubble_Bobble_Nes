package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EntityController;

/**
 * Implements movement behavior for Zen-type enemy.
 * Moves horizontally with random jumps and direction changes.
 */
public class ZenMovement implements MovementBehaviour {
    /** Direction of horizontal movement */
    private boolean movingRight = true;
    /** Total duration of current horizontal movement */
    private int horizontalMoveDuration = 0;
    /** Timer tracking remaining movement time */
    private int horizontalMoveTimer = 0;
    /** Minimum distance from screen edges */
    private static final int BORDER_PADDING = 8;
    /** Speed of horizontal movement */
    private static final int MOVE_SPEED = 1;
    /** Minimum duration for movement in one direction */
    private static final int MIN_MOVE_DURATION = 140;
    /** Maximum duration for movement in one direction */
    private static final int MAX_MOVE_DURATION = 200;
    /** Probability to perform a jump */
    private static final double JUMP_PROBABILITY = 0.1;
    /** Probability to change direction */
    private static final double DIRECTION_CHANGE_PROBABILITY = 0.3;

    /**
     * Moves the Zen enemy with random jumps and direction changes.
     * Controls horizontal movement timing and boundary collisions.
     *
     * @param controller The entity controller managing this enemy
     */
    @Override
    public void move(EntityController controller) {
        if (!controller.entity.isJumping() && Math.random() < JUMP_PROBABILITY) {
            controller.jump();
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
     * Checks Zen condition
     *
     * @return True if alive, false otherwise
     */
    @Override
    public boolean isAlive() {
        return false;
    }
}