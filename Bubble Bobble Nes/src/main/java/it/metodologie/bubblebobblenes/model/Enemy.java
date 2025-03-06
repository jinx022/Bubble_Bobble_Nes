package it.metodologie.bubblebobblenes.model;

/**
 * Model for the Enemy
 */
public class Enemy extends Entity {

    /**
     * Create a new instance extending the abstract class
     *
     * @param x Value of the x coordinate
     * @param y Value of the y coordinate
     * @param width Value of the image width
     * @param height Value of the image height
     */
    public Enemy(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}