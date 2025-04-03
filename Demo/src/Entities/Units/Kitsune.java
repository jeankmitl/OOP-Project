/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.Audio;
import Asset.AudioName;
import Asset.VFX;
import DSystem.DTimer;
import DSystem.DWait;
import Entities.Bullets.Bone;
import Entities.Bullets.Bullet;
import Entities.Bullets.KitsuneBullet;
import Entities.Enemies.Enemy;
import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.UnitChargeShootable;
import Entities.Units.Roles.UnitGeneratable;
import Main.GamePanel;
import java.util.List;

/**
 *
 * @author anawi
 */
public class Kitsune extends Unit implements UnitGeneratable {
    private DTimer attackTimer;
    
    public Kitsune(int row, int col) {
        super(row, col, getUNIT_STATS());
        
        attackTimer = new DTimer(1.5,e->{
            if(this.getHealth() <= 0){
                    attackTimer.stop();
                }
            if(isEnemyInfront(GamePanel.getEnemies())){
                    attack(GamePanel.getBullets());
                    Audio.play(AudioName.FIRE_TINY);
            }
        });
        attackTimer.start();
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.KITSUNE_STATS;
    }
    
    // Add 50 cost every 15 seconds
    @Override
    public void generateByAtkSpeed() {
        setStatus(ATK_STATUS);
        for (Unit unit: GamePanel.getUnits()) {
            if (unit.getHealth() >= unit.getMaxHealth() || getRow() != unit.getRow()) continue;
            unit.takeDamage(atk);
            GamePanel.getVfxs().add(new VFX(unit.getX(), unit.getY() - 50, "heal_vfx"));
        }
        new DWait(1, (e) -> {
            setStatus(IDLE_STATUS);
        }).start();
    }

    public void attack(List<Bullet> bullets) {
        bullets.add(new KitsuneBullet(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, 7));
    }

    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        for (Enemy enemy : enermies) {
            if (enemy.getRow() == this.getRow() && enemy.getX() > this.getX() && enemy.getX() + GamePanel.GRID_OFFSET_X < 1050) {
                return true;
            }
        }
        return false;
    }
    
}
