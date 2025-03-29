/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.Audio;
import Asset.AudioName;
import Entities.Bullets.Bone;
import Entities.Bullets.BoneViolet;
import Entities.Bullets.Bullet;
import Entities.Enemies.Enemy;
import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.UnitChargeShootable;
import Main.GamePanel;
import java.util.List;

/**
 *
 * @author anawi
 */
public class BlackSkeleton extends Unit implements UnitChargeShootable {
    public BlackSkeleton(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.BLACK_SKELETON_STATS;
    }
    
    
    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        for (Enemy enemy : enermies) {
            if ((enemy.getRow() - 1 == this.getRow() || enemy.getRow() == this.getRow() || enemy.getRow() + 1 == this.getRow())
                    && enemy.getX() > this.getX() && enemy.getX() + GamePanel.GRID_OFFSET_X < 1050) {
                System.out.println("Enemy within attacking range!!!");
                setStatus(ATK_STATUS);
                return true;
            }
        }
        setStatus(IDLE_STATUS, false);
        return false;
    }
    
    @Override
    public void shoot(List<Bullet> bullets) {
        Audio.play(AudioName.FIRE_TINY);
        bullets.add(new BoneViolet(col * GamePanel.CELL_WIDTH + 100, (row - 1) * GamePanel.CELL_HEIGHT + 30, atk));
        bullets.add(new BoneViolet(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, atk));
        bullets.add(new BoneViolet(col * GamePanel.CELL_WIDTH + 100, (row + 1) * GamePanel.CELL_HEIGHT + 30, atk));
    }
}
