package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.BubController;

import javafx.application.Platform;

/**
 * Manages power-ups for the main character.
 * Supports various power-up types with temporary and permanent effects.
 */
public class PowerUpHandler {
    /** Bub Controller */
    private final BubController bubController;
    /** Timer to handle power ups */
    private PowerUpTimer currentTimer;
    /** Save the default Bub controller */
    private MovementBehaviour originalMovement;
    /** Save the file name of the power up */
    private String powerUpType;
    /** Flag for the thunder bubble */
    private boolean thunderBubbleActive = false;
    /** Flag for the fire bubble */
    private boolean fireBubbleActive = false;
    /** Flag for movement power ups */
    private boolean bubbleMovement = false;
    /** Flag for bonus points */
    private boolean crystalActive = false;
    /** Flag for clock power up */
    private boolean clockActive = false;
    /** Clock effect duration */
    private static final int CLOCK_DURATION = 5000;
    /** Movement effect duration */
    private static final int SPEED_UP_DURATION = 5000;
    /** Points to add as bonus */
    private static final int CRYSTAL_BONUS = 10;

    /**
     * Constructor for the Power Up Handler
     * @param bubController Bub Controller
     */
    public PowerUpHandler(BubController bubController) {
        this.bubController = bubController;
        this.originalMovement = bubController.getMovementBehaviour();
    }

    /**
     * Applies the specified power-up effect.
     * Handles duration and cancellation of temporary effects.
     * @param powerUpType The type of power-up to apply
     */
    public void applyPowerUp(String powerUpType) {
        this.powerUpType = powerUpType;
        if (currentTimer != null) {
            currentTimer.cancel();
        }

        switch (powerUpType) {
            case "shoes":
                applySpeedUp();
                break;
            case "thunder_bubble":
                applyThunderBubble();
                break;
            case "fire_bubble":
                applyFireBubble();
                break;
            case "candy_blue", "candy_pink":
                applyBubbleMovement();
                break;
            case "life":
                bubController.powerLife();
                break;
            case "holy_water":
                bubController.powerInvincible();
                break;
            case "crystal":
                crystalActive = true;
                bubController.addScore(CRYSTAL_BONUS);
                startPowerUpTimer(() -> {
                    crystalActive = false;
                }, SPEED_UP_DURATION);
                break;
            case "clock":
                applyClock();
                break;
            case "dynamite":
                bubController.triggerDynamiteEffect();
                break;
            default:
                System.out.println("Power-up type not recognized: " + powerUpType);
        }
    }

    /**
     * Applies speed boost power-up effect.
     * Temporarily increases movement speed for a fixed duration.
     */
    private void applySpeedUp() {
        MovementBehaviour speedUpMovement = new SpeedUpMovement(bubController.getPressedKeys());
        bubController.setMovementBehaviour(speedUpMovement);
        startPowerUpTimer(()->{
            bubController.setMovementBehaviour(originalMovement);
        }, SPEED_UP_DURATION);
    }

    /**
     * Activates thunder bubble power-up.
     * Enhances bubble shooting capabilities temporarily.
     */
    private void applyThunderBubble() {
        thunderBubbleActive = true;
        startPowerUpTimer(()->{
            thunderBubbleActive = false;
        }, SPEED_UP_DURATION);
    }

    /**
     * Activates fire bubble power-up.
     * Enhances bubble shooting capabilities temporarily.
     */
    private void applyFireBubble(){
        fireBubbleActive = true;
        startPowerUpTimer(()->{
            fireBubbleActive = false;
        }, SPEED_UP_DURATION);
    }

    /**
     * Set the flag for the movement power up
     */
    private void applyBubbleMovement(){
        bubbleMovement = true;
    }

    /**
     * Return the value of the flag Crystal
     *
     * @return True if active, false otherwise
     */
    public boolean isCrystalActive() { return crystalActive; }

    /**
     * Start the timer of the power up
     *
     * @param onExpire The moment the time expire
     * @param duration The duration of the power up
     */
    private void startPowerUpTimer(Runnable onExpire, long duration) {
        currentTimer = new PowerUpTimer(onExpire, duration);
        currentTimer.start();
    }

    /**
     * Return the value of the flag Thunder
     *
     * @return True if active, false otherwise
     */
    public boolean isThunderBubbleActive() {
        return thunderBubbleActive;
    }

    /**
     * Return the value of the flag Fire
     *
     * @return True if active, false otherwise
     */
    public boolean isFireBubbleActive() {
        return fireBubbleActive;
    }

    /**
     * Return the value of the flag Movement
     *
     * @return True if active, false otherwise
     */
    public boolean isBubbleMovement() { return bubbleMovement; }

    /**
     * Activates clock power-up.
     * Freeze enemies movement capabilities temporarily.
     */
    private void applyClock() {
        clockActive = true;
        bubController.freezeAllEnemies();
        startPowerUpTimer(() -> {
            clockActive = false;
            bubController.unfreezeAllEnemies();
        }, CLOCK_DURATION);
    }

    /**
     * Gets the currently active power-up type
     *
     * @return String representing the current power-up type, or null if none active
     */
    public String getPowerUpType(){ return powerUpType; }

    /**
     * Inner class to handle power-up duration timing.
     * Manages expiration of temporary power-up effects.
     */
    private static class PowerUpTimer {
        /** The thread that manages the power-up timer */
        private Thread timerThread;
        /** Callback to be executed when the power-up expires */
        private final Runnable onExpire;
        /** Duration of the power-up effect in milliseconds */
        private final long duration;

        /**
         * Creates a new power-up timer with the specified callback and duration.
         *
         * @param onExpire The callback to execute when the power-up expires
         * @param duration The duration of the power-up in milliseconds
         */
        public PowerUpTimer(Runnable onExpire, long duration) {
            this.onExpire = onExpire;
            this.duration = duration;
        }

        /**
         * Starts the power-up timer.
         * Creates and starts a new thread that waits for the specified duration
         * before executing the expiration callback on the JavaFX Application Thread.
         */
        public void start() {
            timerThread = new Thread(() -> {
                try {
                    Thread.sleep(duration);
                    Platform.runLater(onExpire);
                } catch (InterruptedException e) {
                }
            });
            timerThread.start();
        }

        /**
         * Cancels the power-up timer if it's currently running.
         * This will prevent the expiration callback from being executed.
         */
        public void cancel() {
            if (timerThread != null && timerThread.isAlive()) {
                timerThread.interrupt();
            }
        }
    }
}