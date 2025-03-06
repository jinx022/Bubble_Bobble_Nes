package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EntityController;

/**
 * Interface that defines movement behavior for game entities.
 * Uses Strategy pattern to manage different types of movement.
 */
public interface MovementBehaviour<T extends EntityController> {

    /**
     * Handles the entity's movement.
     * @param controller The controller of the entity to move
     */
    void move(T controller);

    /**
     * Checks if the entity is still active/alive.
     * @return true if the entity is still active, false otherwise
     */
    boolean isAlive();
}