package it.metodologie.bubblebobblenes.factory;

import it.metodologie.bubblebobblenes.model.Entity;
import it.metodologie.bubblebobblenes.model.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Create and load the platforms of every level
 */
public class LevelsLoader implements LevelFactory {
    /** List of entities of Level type */
    private List<Level> platforms;

    /**
     * Constructor that initialize the list
     */
    public LevelsLoader() {
        this.platforms = new ArrayList<>();
    }

    /**
     * Load int the list all the platforms necessary for a level game
     * @param level Number value of the level to load
     */
    @Override
    public void loadLevel(int level) {
        switch (level){
            case 1:
                this.platforms.add(new Level(0, 0, 256, 8)); // top

                this.platforms.add(new Level(8, 74, 24, 8));
                this.platforms.add(new Level(60, 74, 136, 8));
                this.platforms.add(new Level(224, 74, 24, 8));

                this.platforms.add(new Level(8, 114, 24, 8));
                this.platforms.add(new Level(60, 114, 136, 8));
                this.platforms.add(new Level(224, 114, 24, 8));

                this.platforms.add(new Level(8, 154, 24, 8));
                this.platforms.add(new Level(60, 154, 136, 8));
                this.platforms.add(new Level(224, 154, 24, 8));

                this.platforms.add(new Level(0, 192, 256, 8)); // ground

                this.platforms.add(new Level(0, 0, 8, 200)); // vertical lines
                this.platforms.add(new Level(248, 0, 8, 200));
                break;
            case 2:
                platforms.clear();
                this.platforms.add(new Level(0, 0, 256, 8)); // top

                this.platforms.add(new Level(70, 40, 116, 8));

                this.platforms.add(new Level(55, 80, 48, 8));
                this.platforms.add(new Level(148, 80, 48, 8));

                this.platforms.add(new Level(33, 120, 190, 8));

                this.platforms.add(new Level(24, 150, 50, 8));
                this.platforms.add(new Level(98, 150, 50, 8));
                this.platforms.add(new Level(172, 150, 50, 8));

                this.platforms.add(new Level(0, 192, 256, 8)); // ground

                this.platforms.add(new Level(0, 0, 8, 200)); // vertical lines
                this.platforms.add(new Level(248, 0, 8, 200));
                break;
            case 3:
                platforms.clear();
                this.platforms.add(new Level(0, 0, 71, 8)); // top
                this.platforms.add(new Level(104, 0, 48, 8));
                this.platforms.add(new Level(185, 0, 71, 8));

                this.platforms.add(new Level(24, 54, 8, 74));
                this.platforms.add(new Level(32, 54, 58, 8));
                this.platforms.add(new Level(166, 54, 58, 8));
                this.platforms.add(new Level(32, 84, 68, 8));
                this.platforms.add(new Level(156, 84, 68, 8));
                this.platforms.add(new Level(32, 120, 78, 8));
                this.platforms.add(new Level(146, 120, 78, 8));
                this.platforms.add(new Level(216, 54, 8, 74));

                this.platforms.add(new Level(8, 150, 56, 8));
                this.platforms.add(new Level(88, 150, 20, 8));
                this.platforms.add(new Level(148, 150, 20, 8));
                this.platforms.add(new Level(192, 150, 56, 8));

                this.platforms.add(new Level(0, 192, 71, 8)); // ground
                this.platforms.add(new Level(104, 192, 48, 8));
                this.platforms.add(new Level(185, 192, 71, 8));

                this.platforms.add(new Level(0, 0, 8, 200)); // vertical lines
                this.platforms.add(new Level(248, 0, 8, 200));
                break;
            case 4:
                platforms.clear();
                this.platforms.add(new Level(0, 0, 71, 8)); // top
                this.platforms.add(new Level(104, 0, 48, 8));
                this.platforms.add(new Level(185, 0, 71, 8));
                this.platforms.add(new Level(71, 0, 8, 23));
                this.platforms.add(new Level(185, 0, 8, 23));
                this.platforms.add(new Level(71, 23, 23, 8));
                this.platforms.add(new Level(170, 23, 23, 8));

                this.platforms.add(new Level(25, 57, 71, 8));
                this.platforms.add(new Level(25, 57, 8, 31));
                this.platforms.add(new Level(25, 80, 31, 8));
                this.platforms.add(new Level(96, 57, 8, 60));

                this.platforms.add(new Level(152, 57, 71, 8));
                this.platforms.add(new Level(215, 57, 8, 31));
                this.platforms.add(new Level(192, 80, 31, 8));
                this.platforms.add(new Level(144, 57, 8, 60));

                this.platforms.add(new Level(25, 120, 31, 8));
                this.platforms.add(new Level(25, 120, 8, 47));
                this.platforms.add(new Level(25, 167, 53, 8));

                this.platforms.add(new Level(192, 120, 31, 8));
                this.platforms.add(new Level(215, 120, 8, 47));
                this.platforms.add(new Level(170, 167, 53, 8));

                this.platforms.add(new Level(119, 167, 15, 8));

                this.platforms.add(new Level(0, 192, 71, 8)); // ground
                this.platforms.add(new Level(104, 192, 48, 8));
                this.platforms.add(new Level(185, 192, 71, 8));

                this.platforms.add(new Level(0, 0, 8, 200)); // vertical lines
                this.platforms.add(new Level(248, 0, 8, 200));
                break;
            case 5:
                platforms.clear();
                this.platforms.add(new Level(0, 0, 71, 8)); // top
                this.platforms.add(new Level(104, 0, 48, 8));
                this.platforms.add(new Level(185, 0, 71, 8));

                this.platforms.add(new Level(25, 32, 8, 16));
                this.platforms.add(new Level(40, 40, 177, 8));
                this.platforms.add(new Level(210, 32, 8, 16));

                this.platforms.add(new Level(25, 80, 180, 8));
                this.platforms.add(new Level(210, 72, 8, 16));

                this.platforms.add(new Level(25, 112, 8, 16));
                this.platforms.add(new Level(35, 120, 180, 8));

                this.platforms.add(new Level(25, 167, 80, 8));
                this.platforms.add(new Level(142, 167, 80, 8));

                this.platforms.add(new Level(0, 192, 71, 8)); // ground
                this.platforms.add(new Level(104, 192, 48, 8));
                this.platforms.add(new Level(185, 192, 71, 8));

                this.platforms.add(new Level(0, 0, 8, 200)); // vertical lines
                this.platforms.add(new Level(248, 0, 8, 200));
                break;
            case 6:
                platforms.clear();
                this.platforms.add(new Level(0, 0, 71, 8)); // top
                this.platforms.add(new Level(104, 0, 48, 8));
                this.platforms.add(new Level(185, 0, 71, 8));

                this.platforms.add(new Level(43, 40, 205, 8));

                this.platforms.add(new Level(8, 80, 205, 8));

                this.platforms.add(new Level(43, 120, 205, 8));

                this.platforms.add(new Level(25, 167, 80, 8));
                this.platforms.add(new Level(142, 167, 80, 8));

                this.platforms.add(new Level(0, 192, 71, 8)); // ground
                this.platforms.add(new Level(104, 192, 48, 8));
                this.platforms.add(new Level(185, 192, 71, 8));

                this.platforms.add(new Level(0, 0, 8, 200)); // vertical lines
                this.platforms.add(new Level(248, 0, 8, 200));
                break;
            case 7:
                platforms.clear();
                this.platforms.add(new Level(0, 0, 71, 8)); // top
                this.platforms.add(new Level(104, 0, 48, 8));
                this.platforms.add(new Level(185, 0, 71, 8));

                this.platforms.add(new Level(8, 40, 80, 8));
                this.platforms.add(new Level(168, 40, 80, 8));

                this.platforms.add(new Level(8, 80, 90, 8));
                this.platforms.add(new Level(158, 80, 90, 8));

                this.platforms.add(new Level(8, 120, 100, 8));
                this.platforms.add(new Level(148, 120, 100, 8));

                this.platforms.add(new Level(25, 167, 80, 8));
                this.platforms.add(new Level(142, 167, 80, 8));

                this.platforms.add(new Level(0, 192, 71, 8)); // ground
                this.platforms.add(new Level(104, 192, 48, 8));
                this.platforms.add(new Level(185, 192, 71, 8));

                this.platforms.add(new Level(0, 0, 8, 200)); // vertical lines
                this.platforms.add(new Level(248, 0, 8, 200));
                break;
            case 8:
                platforms.clear();
                this.platforms.add(new Level(0, 0, 256, 8)); // top

                this.platforms.add(new Level(33, 30, 8, 74));

                this.platforms.add(new Level(50, 40, 18, 8));
                this.platforms.add(new Level(50, 80, 18, 8));

                this.platforms.add(new Level(180, 40, 18, 8));
                this.platforms.add(new Level(180, 80, 18, 8));

                this.platforms.add(new Level(207, 30, 8, 74));

                this.platforms.add(new Level(50, 120, 148, 8));

                this.platforms.add(new Level(8, 167, 20, 8));
                this.platforms.add(new Level(50, 167, 18, 8));
                this.platforms.add(new Level(86, 167, 76, 8));
                this.platforms.add(new Level(180, 167, 18, 8));
                this.platforms.add(new Level(228, 167, 20, 8));

                this.platforms.add(new Level(0, 192, 256, 8)); // ground

                this.platforms.add(new Level(0, 0, 8, 200)); // vertical lines
                this.platforms.add(new Level(248, 0, 8, 200));
                break;
        }
    }

    /**
     * The list saved by the load method
     *
     * @return The complete level structure
     */
    public List<Level> getLevelList() { return platforms; }
}