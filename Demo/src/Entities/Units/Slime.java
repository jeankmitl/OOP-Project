package Entities.Units;

import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bullet;
import Asset.VFX;
import DSystem.DWait;
import DSystem.DTimer;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Slime extends Unit {

    private DTimer costGenerationTimer;
    
    public Slime(int row, int col) {
        super(row, col, getUNIT_STATS());
        costGenerationTimer = new DTimer(15, e -> {
            if (isDead()) {
                costGenerationTimer.stop();
                return;
            }
            generateCost();
        });
        animationTimer = new DTimer(0.25, e -> updateFrame(0.25));
        costGenerationTimer.start();
        animationTimer.start();
    }
    
    public void stopGeneratingCost() {
        costGenerationTimer.stop();
    }

    // Add 50 cost every 15 seconds
    public void generateCost() {
        setStatus(ATK_STATUS);
        GamePanel.remainMana += 50;
        if (GamePanel.remainMana > GamePanel.MAX_MANA) {
            GamePanel.remainMana = GamePanel.MAX_MANA;
        }
        new DWait(1.5, (e) -> {
            setStatus(IDLE_STATUS);
            GamePanel.getVfxs().add(new VFX(getX(), getY() - 50, "get_mana_slime_vfx"));
        }).start();
    }

    @Override
    public void attack(List<Bullet> bullets) {}

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.SLIME_STATS;
    }
    
    
}
