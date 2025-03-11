package Entities.Enemies;

import Asset.ImgManager;
import DSystem.DTimer;
import Main.GamePanel;
import Entities.Units.Unit;
import DSystem.DWait;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Ninja extends Enemy {
    
    public Ninja(double x, int row) {
        super(x, row, getENEMY_STATS());
    }
    
    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.NINJA_STATS;
    }
}
