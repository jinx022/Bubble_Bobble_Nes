/**
 * Main Package for Bubble Bobble Game.
 * It contains the core classes and the access point to the application.
 * 
 * Game's structure is organized by many design pattern:
 * - Model-View-Controller for responsibility's separation
 * - Builder Pattern for the creation of the enemies and the power ups
 * - Strategy Pattern for the behaviours of the entities
 * - Observer Pattern for the updates of the views
 * - Singleton Pattern to manage the music
 * 
 * The package contains:
 * - {@link it.metodologie.bubblebobblenes.GameManager} - Initialize the mains objects of the application
 * - {@link it.metodologie.bubblebobblenes.GameScreenManager} - Screens for game state: win; continue and game over
 * - {@link it.metodologie.bubblebobblenes.JBubbleBobble} - Access point to the application and the main menu
 */
package it.metodologie.bubblebobblenes;