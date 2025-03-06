package it.metodologie.bubblebobblenes.subject;

import it.metodologie.bubblebobblenes.observer.Observer;

/**
 * Interface for the classes that have to notify the observers
 */
public interface Subject {

    /**
     * Add an observer to the list
     *
     * @param o The view of the observer that need an update
     */
    void addObserver(Observer o);

    /**
     * Remove an observer from the list
     *
     * @param o Thew view of the observer who doesn't need anymore to be updated
     */
    void removeObserver(Observer o);

    /**
     * Notify all the observers of the changes necessary
     */
    void notifyObservers();
}