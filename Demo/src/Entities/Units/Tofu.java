/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.VFX;
import Entities.Bullets.Bullet;
import Entities.Enemies.Enemy;
import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.UnitChargeShootable;
import Main.GamePanel;
import static Main.GamePanel.getVfxs;
import java.util.List;

/**
 *
 * @author anawi
 */
public class Tofu extends Unit implements UnitChargeShootable {
    
    public Tofu(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.TOFU_STATS;
    }
    
    @Override
    public void shoot(List<Bullet> bullets) {
        for (Enemy enemy : GamePanel.getEnemies()) {
            if ((enemy.getRow() - 1 == this.getRow() || enemy.getRow() == this.getRow() || enemy.getRow() + 1 == this.getRow())
                    && enemy.getX() < this.getX()+200) {
                enemy.takeDamage(atk);
            }
        }
        getVfxs().add(new VFX((col + 1) * GamePanel.CELL_WIDTH, (row - 1) * GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH * 2 - 50, GamePanel.CELL_HEIGHT, "tofu_fire"));
        getVfxs().add(new VFX((col + 1) * GamePanel.CELL_WIDTH, row * GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH * 2 - 50, GamePanel.CELL_HEIGHT, "tofu_fire"));
        getVfxs().add(new VFX((col + 1) * GamePanel.CELL_WIDTH, (row + 1) * GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH * 2 - 50, GamePanel.CELL_HEIGHT, "tofu_fire"));
        
    }
    
    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        for (Enemy enemy : enermies) {
            if ((enemy.getRow() - 1 == this.getRow() || enemy.getRow() == this.getRow() || enemy.getRow() + 1 == this.getRow())
                    && enemy.getX() < this.getX()+200) {
                setStatus(ATK_STATUS);
                return true;
            } else {
                setStatus(IDLE_STATUS, false);
            }
        }
        return false;
    }
}
