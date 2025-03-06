package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EntityController;

/**
 * Implements movement behavior for blue candy power-up.
 * This candy increases the bubble's movement speed.
 */
public class CandyBlue extends BubbleMovement{
    /** Multiplier applied to the bubble's speed */
    private final double SPEED_MULTIPLIER = 1.5;

    /**
     * Moves the bubble with increased speed.
     * Multiplies the base horizontal speed by a fixed multiplier before executing movement.
     *
     * @param controller The entity controller managing this bubble
     */
    public void move(EntityController controller){
        super.horizontalSpeed *= SPEED_MULTIPLIER;
        super.move(controller);
    }
}