package it.metodologie.bubblebobblenes.builder;

import it.metodologie.bubblebobblenes.controller.EnemyController;
import it.metodologie.bubblebobblenes.model.Enemy;
import it.metodologie.bubblebobblenes.view.EntityView;

/**
 * Create an entity of the type Enemy, no matter what kind of enemy is
 */
public class EnemyBuilder extends GenericBuilder<EnemyController>{

    /**
     * Create the Model, View and Controller for an Enemy
     *
     * @return Enemy type controller
     */
    @Override
    public EnemyController build() {
        Enemy enemy = new Enemy(x, y, width, height);
        EntityView enemyView = new EntityView(type + "_0", type + "_1");
        return new EnemyController(enemy, enemyView, platforms, scene, root);
    }
}