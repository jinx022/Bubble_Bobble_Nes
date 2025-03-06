package it.metodologie.bubblebobblenes.model;

/**
 * Model for the Bub
 */
public class Bub extends Entity  {
    /** Number of available lifes for the player */
    private int lifes = 4;
    /** Timestamp of the last hit taken, used to manage invulnerability period */
    private long lastHitTime;
    /** Time in which the Bub doesn't lose a life */
    private static final long INVULNERABILITY_DURATION = 5000;
    /** Flag to know is the Bub is vulnerable or not */
    private boolean isInvulnerable = false;

    /**
     * Create a new instance extending the abstract class
     *
     * @param x Value of the x coordinate
     * @param y Value of the y coordinate
     * @param width Value of the image width
     * @param height Value of the image height
     */
    public Bub(double x, double y, double width, double height){
        super(x, y, width, height);
    }

    /**
     * Add or remove a life to the Bub
     *
     * @param life Value of life to add or to remove
     */
    public void setLifes(int life){
        if(!isInvulnerable){
            this.lifes -= life;
            isInvulnerable = true;
            lastHitTime = System.currentTimeMillis();
        }
    }

    /**
     * Checks if the Bub has any remaining lifes
     *
     * @return True if it has lifes, false otherwise
     */
    public boolean haveLife(){
        return this.lifes > 0;
    }

    /**
     * Checks the lifes the Bub has
     *
     * @return Value of the lifes available
     */
    public int getLifes(){ return this.lifes; }

    /**
     * The Bub doesn't lose a life
     */
    public void getInvincible(){
        isInvulnerable = true;
        lastHitTime = System.currentTimeMillis();
    }

    /**
     * Checks if the Bub is invincible or not
     */
    public void updateInvulnerability() {
        if (isInvulnerable && System.currentTimeMillis() - lastHitTime > INVULNERABILITY_DURATION) {
            isInvulnerable = false;
        }
    }
}