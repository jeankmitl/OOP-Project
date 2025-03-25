/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.VFX;
import Entities.Bullets.Bullet;
import Entities.Enemies.Enemy;
import Entities.Enemies.EnemyStats;
import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.UnitFlipImg;
import Entities.Units.Roles.UnitShootable;
import Main.GamePanel;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author anawi
 */
public class GhostHaunted extends Unit implements UnitShootable, UnitFlipImg {

    public GhostHaunted(int row, int col, EnemyStats enemy) {
        super(row, col, new UnitStats(new UnitSpriteSheets(
                enemy.getEnemySp().getIdle(), 
                enemy.getEnemySp().getAtk()), 
                enemy.getHealth(), enemy.getAtk(), enemy.getAtkSpeed(), 0, 0, -1));
    }
    
    @Override
    public void shoot(List<Bullet> bullets) {
        for (Enemy enemy : GamePanel.getEnemies()) {
            if (enemy.getRow() == this.getRow() && enemy.getX() < this.getX()+GamePanel.CELL_WIDTH) {
                enemy.takeDamage(atk);
            }
        }
    }
    
    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        for (Enemy enermy : enermies) {
            if (enermy.getRow() == this.getRow() && enermy.getX() < this.getX()+GamePanel.CELL_WIDTH) {
                setStatus(ATK_STATUS);
                return true;
            } else {
                setStatus(IDLE_STATUS, false);
            }
        }
        return false;
    }
    
}
