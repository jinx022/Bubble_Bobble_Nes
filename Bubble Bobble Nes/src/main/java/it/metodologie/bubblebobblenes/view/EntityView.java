package it.metodologie.bubblebobblenes.view;

import it.metodologie.bubblebobblenes.observer.Observer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * View for the entities
 */
public class EntityView implements Observer {
    /** ImageView component used to display the entity sprite */
    private ImageView imageView;
    /** Timeline controlling the animation sequence */
    private Timeline animationTimeline;
    /** List of animation frames as Images */
    private List<Image> animationFrames;
    /** List of frame names for reference */
    private List<String> frameNames;
    /** Total number of animation frames */
    private int count;
    /** Index of the current animation frame */
    private int currentFrame = 0;
    /** Width of the entity sprite frame */
    public double frameWidth;
    /** Height of the entity sprite frame */
    public double frameHeight;

    /**
     * Constructor for the View
     *
     * @param imageNames List of images for an Entity
     */
    public EntityView(String... imageNames){
        this.animationFrames = new ArrayList<>();
        this.frameNames = new ArrayList<>();
        for(String imageName : imageNames){
            Image image = new Image(EntityView.class.getResourceAsStream("/images/" + imageName + ".png"));
            animationFrames.add(image);
            frameNames.add(imageName);
            count++;
        }
        imageView = new ImageView(animationFrames.get(currentFrame));
        this.frameWidth = imageView.getFitWidth();
        this.frameHeight = imageView.getFitHeight();
        imageView.setFitWidth(frameWidth);
        imageView.setFitHeight(frameHeight);

        initializeAnimationTimeline();
    }

    /**
     * Update the View
     *
     * @param imageNames Name of the images to update the View
     */
    @Override
    public void update(String... imageNames) {
        this.animationFrames.clear();
        this.frameNames.clear();
        for(String imageName : imageNames){
            Image image = new Image(EntityView.class.getResourceAsStream("/images/" + imageName + ".png"));
            this.animationFrames.add(image);
            frameNames.add(imageName);
        }
        currentFrame = 0;
        if (!animationFrames.isEmpty()) {
            imageView.setImage(animationFrames.get(currentFrame));
        }
        this.frameWidth = imageView.getFitWidth();
        this.frameHeight = imageView.getFitHeight();
        imageView.setFitWidth(frameWidth);
        imageView.setFitHeight(frameHeight);
    }

    /**
     * Returns the ImageView component used for rendering
     *
     * @return The ImageView containing the entity sprite
     */
    public ImageView getImageView() { return imageView; }

    /**
     * Flips the entity sprite horizontally based on direction
     *
     * @param flip true to flip left, false to flip right
     */
    public void flipHorizontally(boolean flip) {
        imageView.setScaleX(flip ? -1 : 1);
    }

    /**
     * Starts playing the animation sequence
     */
    public void startAnimation() {
        animationTimeline.play();
    }

    /**
     * Stops the animation sequence
     */
    public void stopAnimation() {
        animationTimeline.stop();
    }

    /**
     * Advances to the next frame in the animation sequence
     * Cycles back to first frame after reaching the end
     */
    public void nextFrame() {
        currentFrame = (currentFrame + 1) % animationFrames.size();
        imageView.setImage(animationFrames.get(currentFrame));
    }

    /**
     * Gets the name of the current animation frame
     *
     * @return Name of the current frame, or empty string if no frames exist
     */
    public String getCurrentFrame() {
        if (frameNames.isEmpty()) {
            return "";
        }
        return frameNames.get(currentFrame);
    }

    /**
     * Initializes the animation timeline with specified frame duration
     * and starts the animation loop
     */
    public void initializeAnimationTimeline() {
        animationTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> nextFrame()));
        animationTimeline.setCycleCount(Timeline.INDEFINITE);
        animationTimeline.play();
    }
}