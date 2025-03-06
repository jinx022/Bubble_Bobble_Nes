package it.metodologie.bubblebobblenes.builder;

import it.metodologie.bubblebobblenes.model.Level;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Generic builder for different entities, allowing method chaining
 *
 * @param <T> Generic for the entities to create
 */
public abstract class GenericBuilder<T> implements Builder<T> {
    /** X coordinate on the scene */
    protected double x;
    /** Y coordinate on the scene */
    protected double y;
    /** Default width of the view */
    protected double width = 16;
    /** Default height of the view */
    protected double height = 16;
    /** Type of entity to create */
    protected String type;
    /** Scene inherited */
    protected Scene scene;
    /** Pane inherited */
    protected Pane root;
    /** Platforms of the level */
    protected List<Level> platforms;

    /**
     * Choose where the entity has to be
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return This builder instance
     */
    @Override
    public Builder<T> position(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set the size of the view
     *
     * @param width Width of the entity view
     * @param height Height of the entity view
     * @return This builder instance
     */
    @Override
    public Builder<T> size(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Choose the specific entity to create
     *
     * @param type Name of the entity to create
     * @return This builder instance
     */
    @Override
    public Builder<T> type(String type) {
        this.type = type;
        return this;
    }

    /**
     * Scene and Pane for the entities
     *
     * @param scene Scene inherited
     * @param root Pane inherited
     * @return This builder instance
     */
    @Override
    public Builder<T> scene(Scene scene, Pane root) {
        this.scene = scene;
        this.root = root;
        return this;
    }

    /**
     * The Platforms where the entities will spawn
     *
     * @param platforms List of platforms
     * @return This builder instance
     */
    @Override
    public Builder<T> platforms(List<Level> platforms) {
        this.platforms = platforms;
        return this;
    }
}