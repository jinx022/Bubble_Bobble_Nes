package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EntityController;

/**
 * Implements movement behavior for pink candy power-up.
 * This candy increases the bubble's travel distance.
 */
public class CandyPink extends BubbleMovement{
    /** Tracks if the distance extension has been applied */
    private boolean hasExtendedDistance = false;
    /** Multiplier applied to the bubble's travel distance */
    private final double RANGE_EXTENSION = 1.5;

    /**
     * Moves the bubble with extended range.
     * Extends the horizontal travel distance on first execution, then maintains normal movement.
     *
     * @param controller The entity controller managing this bubble
     */
    public void move(EntityController controller){
        if(!hasExtendedDistance){
            super.horizontalDistance *= RANGE_EXTENSION;
            hasExtendedDistance = true;
        }
        super.move(controller);
    }
}