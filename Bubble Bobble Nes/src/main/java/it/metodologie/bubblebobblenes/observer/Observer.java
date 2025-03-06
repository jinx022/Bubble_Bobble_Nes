package it.metodologie.bubblebobblenes.observer;

/**
 * Interface for the classes that need to be updated when necessary
 */
public interface Observer {

    /**
     * Update the View
     *
     * @param imageNames Name of the images to update the View
     */
    void update(String... imageNames);
}