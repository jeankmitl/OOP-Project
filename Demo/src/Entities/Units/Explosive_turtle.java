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
import static Entities.Units.Explosion.getUNIT_STATS;
import Entities.Units.Roles.UnitInvisible;
import Main.GamePanel;
import java.util.List;

/**
 *
 * @author anawi
 */
public class Explosive_turtle extends Unit implements UnitInvisible {
    private OWait waitBeforeActivate;
    private boolean isActivating = false; 

    public Explosive_turtle(int row, int col) {
        super(row, col, getUNIT_STATS());
        waitBeforeActivate = new OWait(0);
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.EXPLOSIVE_TURTLE_STATS;
    }
    
    @Override
    public void attack(List<Bullet> bullets) {
        System.out.println("attack");
        bullets.add(new ExplosionBullet(col * GamePanel.CELL_WIDTH, row * GamePanel.CELL_HEIGHT + 30, atk, row, col));
    }

    @Override
    public void insersectEnemy(Enemy enemy) {
        if (enemy.getX() > getX() - 10 && enemy.getX() + frame_Width < getX() + frame_Width + GamePanel.CELL_WIDTH) {
            if (waitBeforeActivate.tick(atkSpeed) && !isActivating) {
                setStatus("ATK");
                new DWait(2, e -> {
                    attack(GamePanel.getBullets());
                    setHealth(0);
                }).start();
            }
        }
    }
}
