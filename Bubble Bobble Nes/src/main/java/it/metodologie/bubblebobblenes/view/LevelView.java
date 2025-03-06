package it.metodologie.bubblebobblenes.view;

import it.metodologie.bubblebobblenes.model.Level;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * View for the platforms
 */
public class LevelView {
    /** List of all the platforms */
    private List<Level> platforms;

    /**
     * Inject the platform in the list
     *
     * @param platforms List of platforms
     */
    public void setPlatforms(List<Level> platforms) {
        this.platforms = platforms;
    }

    /**
     * Show the platforms, creating Rectangle to display
     *
     * @return The level structure in which to play
     */
    public Pane drawLevelObjects(){
        Pane levelPane = new Pane();
        for(Level platform : platforms){
            Rectangle rectangle = new Rectangle(
                    platform.getX(),
                    platform.getY(),
                    platform.getWidth(),
                    platform.getHeight()
            );
            rectangle.setFill(Color.BLUE);
            rectangle.setStrokeWidth(0);
            levelPane.getChildren().add(rectangle);
        }
        return levelPane;
    }
}