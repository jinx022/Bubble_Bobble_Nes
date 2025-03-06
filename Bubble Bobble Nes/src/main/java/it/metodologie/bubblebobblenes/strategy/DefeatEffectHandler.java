package it.metodologie.bubblebobblenes.strategy;

import it.metodologie.bubblebobblenes.controller.EnemyController;
import it.metodologie.bubblebobblenes.view.EntityView;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

/**
 * Handles visual effects when an enemy is defeated.
 * Supports different effect types: FIRE, EXPLOSION, THUNDER.
 */
public class DefeatEffectHandler {

    /**
     * Effect type
     */
    public enum EffectType {
        /** Fire effects */
        FIRE,
        /** Dynamite effects */
        EXPLOSION,
        /** Thunder effects */
        THUNDER
    }

    /** Standard effect duration */
    private static final int EFFECT_DURATION = 3000;
    /** Specific duration for explosions */
    private static final int EXPLOSION_DURATION = 500;

    /**
     * Plays specified defeat effect for the enemy.
     * Handles animation and automatic effect removal.
     */
    public static void playDefeatEffect(EnemyController enemy, Pane root, EffectType effectType, Runnable onComplete) {
        String[] frames;
        int duration;

        switch (effectType) {
            case FIRE:
                frames = new String[]{"fire_effects_0", "fire_effects_1", "fire_effects_2"};
                duration = EFFECT_DURATION;
                break;
            case EXPLOSION:
                frames = new String[]{"dynamite_effects_0", "dynamite_effects_1", "dynamite_effects_2", "dynamite_effects_3"};
                duration = EXPLOSION_DURATION;
                break;
            case THUNDER:
                frames = new String[]{"thunder_effects_0", "thunder_effects_1", "thunder_effects_2"};
                duration = EFFECT_DURATION;
                break;
            default:
                throw new IllegalArgumentException("Unknown effect type: " + effectType);
        }

        EntityView effectView = new EntityView(frames);
        double enemyX = enemy.getView().getImageView().getLayoutX();
        double enemyY = enemy.getView().getImageView().getLayoutY();
        effectView.getImageView().setLayoutX(enemyX);
        effectView.getImageView().setLayoutY(enemyY);

        Platform.runLater(() -> {
            root.getChildren().add(effectView.getImageView());
            effectView.startAnimation();
        });

        new Thread(() -> {
            try {
                Thread.sleep(duration);
                Platform.runLater(() -> {
                    effectView.stopAnimation();
                    root.getChildren().remove(effectView.getImageView());
                    if (onComplete != null) {
                        onComplete.run();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}