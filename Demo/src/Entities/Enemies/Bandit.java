package Entities.Enemies;

import Asset.ImgManager;
import DSystem.DTimer;
import Main.GamePanel;
import Entities.Units.Unit;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bandit extends Enemy {

    public Bandit(double x, int row) {
        super(x, row, getENEMY_STATS());
    }

    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.BANDIT_STATS;
    }

    @Override
    public Enemy createNew(int x, int y) {
        return new Bandit(x, y);
    }
}
