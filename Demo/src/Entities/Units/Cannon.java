/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.Audio;
import Asset.AudioName;
import DSystem.DTimer;
import DSystem.DWait;
import Entities.Bullets.BigBallBullet;
import Entities.Bullets.Bullet;
import Entities.Bullets.CannonBullet;
import Entities.Enemies.Enemy;
import Entities.Units.Roles.UnitChargeShootable;
import Main.GamePanel;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author USER
 */
public class Cannon extends Unit implements UnitChargeShootable {
    public Cannon(int row, int col) {
        super(row, col, getUNIT_STATS());
        attackWait.fill();
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.CANNON_STATS;
    }
    
    @Override
    public void shoot(List<Bullet> bullets) {
        Audio.play(AudioName.FIRE_TINY);
        bullets.add(new CannonBullet(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, atk));
        new DWait(1, e -> setStatus("idle")).start();
    }
}
