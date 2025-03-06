package it.metodologie.bubblebobblenes.builder;

import it.metodologie.bubblebobblenes.model.Level;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Interface shows the steps to all the builders to create an entity
 *
 * @param <T> Generic for the entities to create
 */
public interface Builder<T> {
    /** Coordinates where the entities spawn */
    Builder<T> position(double x, double y);
    /** Size of the entities view */
    Builder<T> size(double width, double height);
    /** Name of the entity to create */
    Builder<T> type(String type);
    /** Scene and Pane where to insert entities */
    Builder<T> scene(Scene scene, Pane root);
    /** List of platforms from a level */
    Builder<T> platforms(List<Level> platforms);
    /** Create the entity */
    T build();
}