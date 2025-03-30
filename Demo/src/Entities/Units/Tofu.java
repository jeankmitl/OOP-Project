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
    private boolean isHitUp = false;
    
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
                System.out.println(enemy.getClass().getName());
                getVfxs().add(new VFX(enemy.getX(), enemy.getY(), "wolf_scratch_hit"));
            }
        }
        getVfxs().add(new VFX(col * GamePanel.CELL_WIDTH + 200, (row - 1) * GamePanel.CELL_HEIGHT, isHitUp ? "werewolf_up_scratch" : "werewolf_down_scratch"));
        getVfxs().add(new VFX(col * GamePanel.CELL_WIDTH + 200, row * GamePanel.CELL_HEIGHT, isHitUp ? "werewolf_up_scratch" : "werewolf_down_scratch"));
        getVfxs().add(new VFX(col * GamePanel.CELL_WIDTH + 200, (row + 1) * GamePanel.CELL_HEIGHT, isHitUp ? "werewolf_up_scratch" : "werewolf_down_scratch"));
    }
    
    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        for (Enemy enemy : enermies) {
            if ((enemy.getRow() - 1 == this.getRow() || enemy.getRow() == this.getRow() || enemy.getRow() + 1 == this.getRow())
                    && enemy.getRow() == this.getRow() && enemy.getX() < this.getX()+200) {
                setStatus(ATK_STATUS);
                return true;
            } else {
                setStatus(IDLE_STATUS, false);
            }
        }
        return false;
    }
}
