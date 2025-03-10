/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.Audio;
import Asset.AudioName;
import DSystem.DTimer;
import DSystem.DWait;
import Entities.Bullets.Bullet;
import Entities.Enemies.Enemy;
import Main.GamePanel;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author USER
 */
public class Cannon extends Skeleton{
    
    private DWait reload_speed; // cd time when eat again
    private boolean ammo = true;//check when eat enermy
    private DTimer attackTimer;
    
    public Cannon(int row, int col) {
        super(row, col);
        
        attackTimer = new DTimer(1,e->{
            if(isEnemyInfront(GamePanel.getEnemies())){
                attack(GamePanel.getBullets());
                if (isDead()) {
                    attackTimer.stop();
                    return;
                }
                attack(GamePanel.getBullets());
                Audio.play(AudioName.FIRE_TINY);
            }
            });
        attackTimer.start();
    }

    public void cd_stage() {
        reload_speed = new DWait(15,e->{
            ammo = true;
        });
        reload_speed.start();
    }

    @Override
    public void attack(List<Bullet> bullets) { // wait for bullet
        super.attack(bullets); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        return super.isEnemyInfront(enermies); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Rectangle getBounds() {
        return super.getBounds(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public BufferedImage getBufferedImage() {
        return super.getBufferedImage(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void updateFrame() {
        super.updateFrame(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public static UnitStats getUNIT_STATS() {
        return UnitConfig.CANNON_STATS;
    }
    
    
    
    
}
