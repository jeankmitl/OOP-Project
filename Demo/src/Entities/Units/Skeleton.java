package Entities.Units;

import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bone;
import Entities.Bullets.Bullet;
import Asset.Audio;
import DSystem.DTimer;
import Asset.AudioName;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Skeleton extends Unit {

    private DTimer attackTimer;
    
    public Skeleton(int row, int col) {
        super(row, col, getUNIT_STATS());

        attackTimer = new DTimer(getUNIT_STATS().getAtkSpeed(), e -> {
            if (isEnemyInfront(GamePanel.getEnemies())) {
                if (isDead()) {
                    attackTimer.stop();
                    return;
                }
                attack(GamePanel.getBullets());
                Audio.play(AudioName.FIRE_TINY);
            }
        });

        attackTimer.start();
        animationTimer = new DTimer(0.25, e -> updateFrame(0.25)); //change
        animationTimer.start();
    }

    public void stopAttacking() {
        attackTimer.stop();
    }

    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        for (Enemy enermy : enermies) {
            if (enermy.getRow() == this.getRow() && enermy.getX() > this.getX()) {
                setStatus(ATK_STATUS);
                return true;
            }
        }
        setStatus(IDLE_STATUS);
        return false;
    }

    @Override
    public void attack(List<Bullet> bullets) {
        bullets.add(new Bone(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }

    @Override
    public BufferedImage getBufferedImage() {
        return super.getBufferedImage();
    }

    @Override
    public void updateFrame(double x) {
        super.updateFrame(x);
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.SKELETON_STATS;
    }

}
