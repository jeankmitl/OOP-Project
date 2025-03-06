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

        
        
        
        
        super.setTotal_Frame_Idle(5);
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
        this.currentFrame = 0;
        this.Status = "ATK";
        GamePanel.remainMana += 50;
        if (GamePanel.remainMana > GamePanel.MAX_MANA) {
            GamePanel.remainMana = GamePanel.MAX_MANA;
        }
        new DWait(1.5, (e) -> {
            this.currentFrame = 0;
            this.Status ="idle";
            GamePanel.getVfxs().add(new VFX(getX(), getY() - 50, "get_mana_slime_vfx"));
        }).start();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }

    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        return false;
    }

    @Override
    public void attack(List<Bullet> bullets) {}

    @Override
    public BufferedImage getBufferedImage() {
        return super.getBufferedImage();
    }
    
    @Override
    public void updateFrame(double x) {
        super.updateFrame(x);
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.SLIME_STATS;
    }
    
    
}
