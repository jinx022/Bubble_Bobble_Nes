package it.metodologie.bubblebobblenes.builder;

import it.metodologie.bubblebobblenes.model.Level;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Interface for the enemies loading
 */
public interface EntityFactory {
    /**
     * Load the enemies
     *
     * @param level Level number
     * @param platforms Platforms of the level
     * @param scene Scene of the level
     * @param root Pane of the level
     */
    void loadEntities(int level, List<Level> platforms, Scene scene, Pane root);
}