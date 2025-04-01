/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.VFX;
import DSystem.DWait;
import Entities.Bullets.Bullet;
import Entities.Bullets.ExplosionBullet;
import Entities.Enemies.Enemy;
import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.DEAD_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.UnitChargeShootable;
import Main.GamePanel;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author anawi
 */
public class Deathlocked extends Unit implements UnitChargeShootable {
    private boolean cooldown = false;
    
    public Deathlocked(int row, int col) {
        super(row, col, getUNIT_STATS());
        attackWait.fill();
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.DEATHLOCKED_STATS;
    }
    
    
    @Override
    public BufferedImage getBufferedImage() {
        if (this.status.equals("idle")){
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
        else if(this.status.equals("ATK")){
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
        else{
            return actiondead.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
    }
    
    public void updateFrame() {
        if (status.equals("idle") || status.equals(DEAD_STATUS)){
            currentFrame = (currentFrame + 1) % total_Frame_Idle;
        } else {
            currentFrame = (currentFrame + 1) % total_Frame_ATK;
        }
    }
    
    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        for (Enemy enemy : enermies) {
            if (enemy.getRow() == this.getRow() && enemy.getX() > this.getX() && enemy.getX() + GamePanel.GRID_OFFSET_X < 1050) {
                setStatus(IDLE_STATUS);
                return true;
            }
        }
        setStatus(DEAD_STATUS, false);
        return false;
    }

    @Override
    public void shoot(List<Bullet> bullets) {
        setStatus(ATK_STATUS);
        new DWait(1.8, (e) -> {
            Enemy enemyMostHealth = null;
            for (Enemy enemy: GamePanel.getEnemies()) {
                if (enemy.getRow() == getRow() && 
                        (enemyMostHealth == null || enemy.getHealth() > enemyMostHealth.getHealth())) {
                    enemyMostHealth = enemy;
                }
            }
            if (enemyMostHealth != null) {
                bullets.add(new ExplosionBullet(enemyMostHealth.getX(), enemyMostHealth.getY(), atk, 
                        enemyMostHealth.getRow(), enemyMostHealth.getX() / GamePanel.CELL_WIDTH));
            }
            setStatus(IDLE_STATUS);
        }).start();
    }
}
