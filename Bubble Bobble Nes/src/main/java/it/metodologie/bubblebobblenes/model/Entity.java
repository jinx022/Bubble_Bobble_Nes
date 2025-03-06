package it.metodologie.bubblebobblenes.model;

/**
 * Abstract class to define the Model of every Entity in the game
 */
public abstract class Entity{
    /** Spawning coordinates */
    private double x, y;
    /** Dimensions for the View */
    private double width, height;
    /** Checks if an Entity is jumping or not */
    private boolean isJumping;
    /** To determinate in which side the Entity is facing */
    private boolean isFacingRight = false;

    /**
     * Constructor for every Entity
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param width Width size
     * @param height Height size
     */
    public Entity(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Set the x coordinate
     *
     * @param x Value for the x coordinate
     */
    public void setX(double x){ this.x = x; }

    /**
     * Set the y coordinate
     *
     * @param y Value for the y coordinate
     */
    public void setY(double y){ this.y = y; }

    /**
     * Return the x coordinate
     *
     * @return Value of the x coordinate
     */
    public double getX(){ return x; }

    /**
     * Return the y coordinate
     *
     * @return Value of the y coordinate
     */
    public double getY(){ return y; }

    /**
     * Return the width of the image
     *
     * @return Value of the image width
     */
    public double getWidth(){ return width; }

    /**
     * Return the height of the image
     *
     * @return Value of the image height
     */
    public double getHeight(){ return height; }

    /**
     * Checks if an Entity is jumping or not
     *
     * @return True is jumping, false otherwise
     */
    public boolean isJumping(){ return isJumping; }

    /**
     * Set the facing of the Entity
     *
     * @param facingRight True if it's facing right, false otherwise
     */
    public void setFacing(boolean facingRight){ this.isFacingRight = facingRight; }

    /**
     * Checks in which direction an Entity is facing
     *
     * @return True it's facing right, false otherwise
     */
    public boolean isFacingRight(){ return isFacingRight; }
}