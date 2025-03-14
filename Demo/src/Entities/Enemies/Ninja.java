package Entities.Enemies;

import Asset.ImgManager;
import DSystem.DTimer;
import Main.GamePanel;
import Entities.Units.Unit;
import DSystem.DWait;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Ninja extends Enemy {

    public boolean dashable = true;
    
    public Ninja(double x, int row) {
        super(x, row, getENEMY_STATS());
    }
    
    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.NINJA_STATS;
    }

    @Override
    public void ability() {
        if (dashable) {
            this.x -= 189;
            dashable = false;
        }
    }
    
    @Override
    public Enemy createNew(int x, int y) {
        return new Ninja(x, y);
    }
}
