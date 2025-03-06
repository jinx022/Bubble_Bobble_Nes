package it.metodologie.bubblebobblenes.builder;

import it.metodologie.bubblebobblenes.model.Level;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Create multiple entities of enemies or power ups
 * and add them to a list
 *
 * @param <T> Generic for the entities to create
 */
public class EntityDirector<T> {

    /** The builder of the entities */
    private GenericBuilder<T> builder;

    /**
     * Constructor for the builder
     *
     * @param builder A generic builder
     */
    public EntityDirector(GenericBuilder<T> builder) {
        this.builder = builder;
    }

    /**
     * Create multiple entities
     *
     * @param count Number of entities to create
     * @param startX X coordinate where the entities spawn
     * @param startY Y coordinate where the entities spawn
     * @param spacing Variable to make the entities not spawning in the same location
     * @param vertical Choose if spacing has to be used or not
     * @param entityType Type of entity to create
     * @param platforms Platforms of the level
     * @param scene Scene of the level
     * @param root Pane of the level
     * @return List of entities
     */
    public List<T> createMultipleEntities(int count, double startX, double startY,
                                          double spacing, boolean vertical, String entityType,
                                          List<Level> platforms, Scene scene, Pane root) {
        List<T> entities = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            double x = vertical ? startX : startX + (spacing * i);
            double y = vertical ? startY + (spacing * i) : startY;

            T entity = builder
                    .position(x, y)
                    .type(entityType)
                    .scene(scene, root)
                    .platforms(platforms)
                    .build();
            entities.add(entity);
        }

        return entities;
    }
}