package Entities.Enemies;

import Asset.ImgManager;
import DSystem.DTimer;
import DSystem.OTimer;
import Main.GamePanel;
import Entities.Units.Unit;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Sorcerer extends Enemy {
    private DTimer abilityTimer;
    
    public Sorcerer(double x, int row) {
        super(x, row, getENEMY_STATS());
        
        abilityTimer = new DTimer(20, e -> {
            if (!isDead()) {
                this.ability();
            }
        });
        abilityTimer.start();
    }

    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.SORCERER_STATS;
    }
    
    @Override
    public void ability() {
        GamePanel.reduceMana(20);
        System.out.println("Mana Steal!!!");
    }
}
