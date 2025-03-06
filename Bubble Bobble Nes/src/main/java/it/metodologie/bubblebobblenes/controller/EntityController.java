package it.metodologie.bubblebobblenes.controller;

import it.metodologie.bubblebobblenes.model.Entity;
import it.metodologie.bubblebobblenes.model.Level;
import it.metodologie.bubblebobblenes.strategy.BubbleMovement;
import it.metodologie.bubblebobblenes.strategy.MovementBehaviour;
import it.metodologie.bubblebobblenes.view.EntityView;

import javafx.scene.Scene;
import java.util.List;

/**
 * Abstract class to define the connection between Model and View and
 * the general rules for every entity in the game
 */
public abstract class EntityController {
    /** Model of the entity */
    public Entity entity;
    /** View of the entity */
    public EntityView view;
    /** Rules to make move the entity */
    protected MovementBehaviour movementBehaviour;
    /** Platforms where the entities can move */
    public List<Level> platforms;
    /** Main Scene */
    public Scene scene;
    /** Entity in the air or not */
    private boolean isJumping = false;
    /** Entity is on a platform or not */
    private boolean canJump = true;
    /** Speed for the jump */
    private double ySpeed = 0;
    /** Horizontal speed */
    private double xSpeed = 3;
    /** Increase the speed when falling down */
    private final double gravity = 0.5;
    /** Force to jump */
    private final double jump = -6.5;
    /** Limit of the jump */
    private static final int MAX_FALL_SPEED = 10;
    /** Default value to evaluate the collision */
    private static final double COLLISION_TOLERANCE = 2.0;
    /** Scene width */
    private static final double WINDOW_WIDTH = 256;
    /** Scene height */
    private static final double WINDOW_HEIGHT = 200;
    /** Standard thickness of every horizontal platforms */
    private static final double PLATFORM_THICKNESS = 8;
    /** Where the real ground starts */
    private static final double GROUND_Y = WINDOW_HEIGHT - PLATFORM_THICKNESS; // 192

    /**
     * Constructor for the controller
     *
     * @param entity Model of the entity
     * @param view View of the enity
     * @param platforms Reference to the platforms
     * @param scene Main Scene
     */
    public EntityController(Entity entity, EntityView view, List<Level> platforms, Scene scene) {
        this.entity = entity;
        this.view = view;
        this.platforms = platforms;
        this.scene = scene;
        this.movementBehaviour = new BubbleMovement();
    }

    /**
     * Movement Behaviour of the Entity
     *
     * @return The type of the movement in use
     */
    public MovementBehaviour getMovementBehaviour() {
        return movementBehaviour;
    }

    /**
     * Update variables like the position and the condition of the entity
     */
    public void update() {
        movementBehaviour.move(this);
        view.getImageView().setLayoutX(entity.getX());
        view.getImageView().setLayoutY(entity.getY());
        applyGravity();
        boolean onPlatform = checkPlatformCollision();
        if(onPlatform) {
            isJumping = false;
            canJump = true;
        } else{
            isJumping = true;
        }
    }

    /**
     * Set when the entity is jumping and its force
     */
    public void jump() {
        if(!isJumping && canJump) {
            isJumping = true;
            canJump = false;
            ySpeed = jump;
        }
    }

    /**
     * Move the entity to the left
     */
    public void moveLeft() {
        entity.setFacing(false);
        if(entity.getX() - xSpeed > 0 && !checkVerticalPlatformCollision(entity.getX() - xSpeed, entity.getY())) {
            entity.setX(entity.getX() - xSpeed);
            view.flipHorizontally(false);
            view.nextFrame();
        }
    }

    /**
     * Move the entity to the right
     */
    public void moveRight() {
        entity.setFacing(true);
        if(entity.getX() + entity.getWidth() < WINDOW_WIDTH && !checkVerticalPlatformCollision(entity.getX() + xSpeed, entity.getY())) {
            entity.setX(entity.getX() + xSpeed);
            view.flipHorizontally(true);
            view.nextFrame();
        }
    }

    /**
     * Set the entity to fall when not on a platform
     */
    public void applyGravity() {
        if (isJumping) {
            ySpeed += gravity;

            if (ySpeed > MAX_FALL_SPEED) {
                ySpeed = MAX_FALL_SPEED;
            }

            entity.setY(entity.getY() + ySpeed);

            if (entity.getY() + entity.getHeight() > GROUND_Y) {
                if (isOnSolidGround()) {
                    entity.setY(GROUND_Y - entity.getHeight());
                    isJumping = false;
                    canJump = true;
                    ySpeed = 0;
                } else {
                    resetToTop();
                }
            }
        }

        if (entity.getY() < 8) {
            ySpeed = 0;
        }
    }

    /**
     * Control if two entities are touching
     *
     * @param controller1 One of the entities
     * @param controller2 The second one
     * @return
     */
    public static boolean checkCollision(EntityController controller1, EntityController controller2) {
        var bounds1 = controller1.view.getImageView().getBoundsInParent();
        var bounds2 = controller2.view.getImageView().getBoundsInParent();
        return bounds1.intersects(bounds2);
    }

    /**
     * Control if an entity is on a platform, no matter if it's the ground or another one
     *
     * @return Entity is on a platform or on the ground
     */
    public boolean checkPlatformCollision() {
        if (entity.getY() + entity.getHeight() > GROUND_Y) {
            if (isOnSolidGround()) {
                entity.setY(GROUND_Y - entity.getHeight());
                ySpeed = 0;
                isJumping = false;
                canJump = true;
                return true;
            }
        }

        boolean collision = platforms.stream().anyMatch(this::checkSpecificPlatformCollision);

        if (!collision && !isJumping) {
            isJumping = true;
        }

        return collision;
    }

    /**
     * Control for the platform that are not the ground
     *
     * @param platform List of platforms in the level
     * @return Entity is on a platform
     */
    private boolean checkSpecificPlatformCollision(Level platform) {
        double entityBottom = entity.getY() + entity.getHeight();
        double entityTop = entity.getY();

        if (ySpeed >= 0 &&
                entity.getX() + entity.getWidth() - COLLISION_TOLERANCE > platform.getX() &&
                entity.getX() + COLLISION_TOLERANCE < platform.getX() + platform.getWidth() &&
                Math.abs(entityBottom - platform.getY()) <= PLATFORM_THICKNESS + COLLISION_TOLERANCE &&
                entityTop < platform.getY() - COLLISION_TOLERANCE) {
            entity.setY(platform.getY() - entity.getHeight());
            ySpeed = 0;
            isJumping = false;
            canJump = true;
            return true;
        }
        return false;
    }

    /**
     * Control for the vertical platforms
     *
     * @param currentX X coordinate of the entity
     * @param currentY Y coordinate of the entity
     * @return Entity find a vertical platform or not
     */
    private boolean checkVerticalPlatformCollision(double currentX, double currentY) {
        return platforms.stream()
                .filter(platform -> platform.getHeight() > platform.getWidth())
                .anyMatch(platform -> {
                    double platformLeft = platform.getX();
                    double platformRight = platformLeft + platform.getWidth();
                    double platformTop = platform.getY();
                    double platformBottom = platformTop + platform.getHeight();

                    double entityRight = currentX + entity.getWidth();
                    double entityBottom = currentY + entity.getHeight();

                    // in the range of a vertical platform
                    if (!(entityBottom > platformTop && currentY < platformBottom)) {
                        return false;
                    }

                    // from left to right
                    if (entity.getX() < platformLeft && entityRight > platformLeft) {
                        return !(currentY + entity.getHeight() <= platformTop + PLATFORM_THICKNESS ||
                                currentY >= platformBottom - PLATFORM_THICKNESS);
                    }

                    // from right to left
                    if (entity.getX() > platformLeft && currentX < platformRight) {
                        return !(currentY + entity.getHeight() <= platformTop + PLATFORM_THICKNESS ||
                                currentY >= platformBottom - PLATFORM_THICKNESS);
                    }

                    return false;
                });
    }

    /**
     * Control for the platform that are the grounds
     *
     * @return Entity is on the ground
     */
    private boolean isOnSolidGround() {
        double entityBottom = entity.getY() + entity.getHeight();
        if (Math.abs(entityBottom - GROUND_Y) <= COLLISION_TOLERANCE) {
            for (Level platform : platforms) {
                if (Math.abs(platform.getY() - GROUND_Y) < 1) {
                    if (entity.getX() + entity.getWidth() > platform.getX() &&
                            entity.getX() < platform.getX() + platform.getWidth()) {
                        return true;
                    }
                }
            }
        }
        return platforms.stream().anyMatch(this::isOnSpecificPlatform);
    }

    /**
     * The entity is considered on the platform if:
     * - Its horizontal bounds overlap with the platform (considering tolerance)
     * - Its bottom edge is within the platform thickness range
     *
     * @param platform Platforms of the level
     * @return True if the entity is on the platform, False otherwise
     */
    private boolean isOnSpecificPlatform(Level platform) {
        double entityBottom = entity.getY() + entity.getHeight();

        boolean horizontalOverlap =
                entity.getX() + entity.getWidth() - COLLISION_TOLERANCE > platform.getX() &&
                        entity.getX() + COLLISION_TOLERANCE < platform.getX() + platform.getWidth();

        boolean atPlatformHeight = Math.abs(entityBottom - platform.getY()) <= PLATFORM_THICKNESS + COLLISION_TOLERANCE;

        return horizontalOverlap && atPlatformHeight;
    }

    /**
     * Entities that fall in the holes on the ground rejoin from the top of the scene,
     * considering the thickness of the horizontal platform
     */
    private void resetToTop() {
        if(entity.getY() > WINDOW_HEIGHT) {
            entity.setY(8);
            ySpeed = 0;
            isJumping = false;
            canJump = false;
        }
    }
}