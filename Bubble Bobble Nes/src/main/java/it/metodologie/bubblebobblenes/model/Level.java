package it.metodologie.bubblebobblenes.model;

/**
 * Model for the Platforms
 */
public class Level extends Entity{

    /**
     * Create a new instance extending the abstract class
     *
     * @param x Value of the x coordinate
     * @param y Value of the y coordinate
     * @param width Value of the platform width
     * @param height Value of the platform height
     */
    public Level(double x, double y, double width, double height){
        super(x, y, width, height);
    }
}