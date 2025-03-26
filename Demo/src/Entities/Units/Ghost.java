/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.Audio;
import Asset.AudioName;
import DSystem.DWait;
import DSystem.OWait;
import Entities.Bullets.Bullet;
import Entities.Bullets.ExplosionBullet;
import Entities.Enemies.Enemy;
import Entities.Units.Roles.UnitInvisible;
import Main.GamePanel;
import java.util.List;

/**
 *
 * @author anawi
 */
public class Ghost extends Unit implements UnitInvisible {
    private boolean isHaunted = false;
    
    public Ghost(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.GHOST_STATS;
    }
    
    public void attack() {
    }

    @Override
    public void insersectEnemy(Enemy enemy) {
        enemy.takeDamage(atk);
        setHealth(0);
        if (enemy.isDead() && !isHaunted) {
            System.out.println(enemy.ENEMY_STATS.getWalkSpeed());
            GamePanel.getUnits().add(new GhostHaunted(row, col, enemy.ENEMY_STATS));
            isHaunted = true;
        }
    }
}
