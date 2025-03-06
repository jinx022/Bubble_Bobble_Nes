package it.metodologie.bubblebobblenes.builder;

import it.metodologie.bubblebobblenes.controller.EnemyController;
import it.metodologie.bubblebobblenes.model.Level;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Create and load all the enemies in every level
 */
public class EnemyLoader implements EntityFactory {
    /** List of enemies in every level */
    private List<EnemyController> enemiesList;
    /** Builder to create the enemies */
    private EntityDirector<EnemyController> director;

    /**
     * Initialize the list and the type of builder
     */
    public EnemyLoader(){
        this.enemiesList = new ArrayList<>();
        this.director = new EntityDirector<>(new EnemyBuilder());
    }

    /**
     * Actual method to create and load the enemies
     *
     * @param level Level number where to load the enemies
     * @param platforms Platforms of the actual level
     * @param scene Scene for the level
     * @param root Pane for the level
     */
    public void loadEntities(int level, List<Level> platforms, Scene scene, Pane root) {
        switch (level) {
            case 1:
                enemiesList.addAll(director.createMultipleEntities(3, 121, 30, 2, true, "zen", platforms, scene, root));
                break;
            case 2:
                enemiesList.addAll(director.createMultipleEntities(2, 80, 30, 40, false, "zen", platforms, scene, root));
                enemiesList.addAll(director.createMultipleEntities(2, 160, 30, 40, false, "zen", platforms, scene, root));
                break;
            case 3:
                enemiesList.addAll(director.createMultipleEntities(2, 60, 30, 50, false, "zen", platforms, scene, root));
                enemiesList.addAll(director.createMultipleEntities(2, 180, 30, 50, false, "zen", platforms, scene, root));
                break;
            case 4:
                enemiesList.addAll(director.createMultipleEntities(2, 60, 30, 50, false, "zen", platforms, scene, root));
                enemiesList.addAll(director.createMultipleEntities(2, 180, 30, 50, false, "zen", platforms, scene, root));
                enemiesList.addAll(director.createMultipleEntities(2, 60, 30, 50, false, "zen", platforms, scene, root));
                break;
            case 5:
                enemiesList.addAll(director.createMultipleEntities(2, 70, 40, 50, false, "zen", platforms, scene, root));
                enemiesList.addAll(director.createMultipleEntities(2, 180, 30, 50, false, "zen", platforms, scene, root));
                break;
            case 6:
                enemiesList.addAll(director.createMultipleEntities(2, 60, 30, 50, false, "zen", platforms, scene, root));
                enemiesList.addAll(director.createMultipleEntities(2, 180, 30, 50, false, "mighta", platforms, scene, root));
                break;
            case 7:
                enemiesList.addAll(director.createMultipleEntities(2, 180, 30, 50, false, "mighta", platforms, scene, root));
                enemiesList.addAll(director.createMultipleEntities(2, 180, 30, 50, false, "mighta", platforms, scene, root));
                break;
            case 8:
                enemiesList.addAll(director.createMultipleEntities(2, 60, 30, 50, false, "zen", platforms, scene, root));
                enemiesList.addAll(director.createMultipleEntities(2, 180, 30, 50, false, "zen", platforms, scene, root));
                enemiesList.addAll(director.createMultipleEntities(2, 180, 100, 50, false, "monsta", platforms, scene, root));
                break;
        }
    }

    /**
     * List of enemies added from the loader
     * @return List of enemies controller
     */
    public List<EnemyController> getEnemies(){ return enemiesList; }
}