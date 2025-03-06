package it.metodologie.bubblebobblenes.builder;

import it.metodologie.bubblebobblenes.controller.PowerUpController;
import it.metodologie.bubblebobblenes.model.PowerUp;
import it.metodologie.bubblebobblenes.view.EntityView;

/**
 * Create an entity of the type Power up
 */
public class PowerUpBuilder extends GenericBuilder<PowerUpController> {
    /**
     * Create Model, View and Controller for a Power Up
     *
     * @return Power Up type controller
     */
    @Override
    public PowerUpController build(){
        PowerUp powerUp = new PowerUp(x, y, width, height);
        EntityView powerUpView = new EntityView(type);
        return new PowerUpController(powerUp, powerUpView, platforms, scene, root);
    }
}