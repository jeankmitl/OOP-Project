/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bone;
import Entities.Bullets.Bullet;
import Asset.Audio;
import DSystem.DTimer;
import Asset.AudioName;
import DSystem.DWait;
import DSystem.OWait;
import Entities.Bullets.BeamCleanRow;
import Entities.Bullets.ExplosionBullet;
import Entities.Units.Roles.UnitInvisible;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Explosion extends Unit implements UnitInvisible {
    
    private OWait waitBeforeActivate;
    private boolean isActivating = false; 

    public Explosion(int row, int col) {
        super(row, col, getUNIT_STATS());
        waitBeforeActivate = new OWait(0.1);
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.EXPLOSION_STATS;
    }
    
    @Override
    public void attack(List<Bullet> bullets) {
        System.out.println("attack");
        bullets.add(new ExplosionBullet(col * GamePanel.CELL_WIDTH, row * GamePanel.CELL_HEIGHT + 30, row, col));
    }

    @Override
    public void insersectEnemy(Enemy enemy) {
        if (enemy.getX() > getX() - 10 && enemy.getX() + frame_Width < getX() + frame_Width + 10) {
            if (waitBeforeActivate.tick(atkSpeed) && !isActivating) {
                System.out.println("ACTIVATE!!!!!");
                Audio.play(AudioName.CANDLE_ACTIVATE);
                setStatus("ATK");
                new DWait(1, e -> {
                    attack(GamePanel.getBullets());
                    setHealth(0);
                }).start();
            }
        }
    }

    
}
