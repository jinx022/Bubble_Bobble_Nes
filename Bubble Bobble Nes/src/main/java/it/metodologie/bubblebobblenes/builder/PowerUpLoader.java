package it.metodologie.bubblebobblenes.builder;

import it.metodologie.bubblebobblenes.controller.PowerUpController;
import it.metodologie.bubblebobblenes.model.Level;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Create and load all the power ups in every level
 */
public class PowerUpLoader implements EntityFactory {
    /** List of power ups in every level */
    private List<PowerUpController> powerUpList;
    /** Builder to create the power ups */
    private EntityDirector<PowerUpController> director;

    /**
     * Initialize the list and the type of builder
     */
    public PowerUpLoader() {
        this.powerUpList = new ArrayList<>();
        this.director = new EntityDirector<>(new PowerUpBuilder());
    }

    /**
     * Actual method to create and load the power ups
     *
     * @param level Level number where to load the power ups
     * @param platforms Platforms of the actual level
     * @param scene Scene for the level
     * @param root Pane for the level
     */
    public void loadEntities(int level, List<Level> platforms, Scene scene, Pane root) {
        switch (level) {
            case 1:
                powerUpList.addAll(director.createMultipleEntities(1, 90, 180, 0, true, "shoes", platforms, scene, root));
                break;
            case 2:
                powerUpList.addAll(director.createMultipleEntities(1, 90, 180, 0, true, "candy_pink", platforms, scene, root));
                break;
            case 3:
                powerUpList.addAll(director.createMultipleEntities(1, 30, 180, 0, true, "candy_blue", platforms, scene, root));
                break;
            case 4:
                powerUpList.addAll(director.createMultipleEntities(1, 30, 180, 0, true, "life", platforms, scene, root));
                break;
            case 5:
                powerUpList.addAll(director.createMultipleEntities(1, 30, 180, 0, true, "thunder_bubble", platforms, scene, root));
                powerUpList.addAll(director.createMultipleEntities(1, 190, 180, 0, true, "clock", platforms, scene, root));
                break;
            case 6:
                powerUpList.addAll(director.createMultipleEntities(1, 30, 180, 0, true, "dynamite", platforms, scene, root));
                break;
            case 7:
                powerUpList.addAll(director.createMultipleEntities(1, 30, 180, 0, true, "crystal", platforms, scene, root));
                powerUpList.addAll(director.createMultipleEntities(1, 190, 180, 0, true, "life", platforms, scene, root));
                break;
            case 8:
                powerUpList.addAll(director.createMultipleEntities(1, 30, 180, 0, true, "fire_bubble", platforms, scene, root));
                powerUpList.addAll(director.createMultipleEntities(1, 190, 180, 0, true, "holy_water", platforms, scene, root));
                break;
        }
    }

    /**
     * List of power ups added from the loader
     * @return List of power ups controller
     */
    public List<PowerUpController> getPowerUpList() {
        return powerUpList;
    }
}