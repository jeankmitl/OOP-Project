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
import Entities.Units.Roles.OnBack;
import Entities.Units.Roles.UnitExtraFieldAvailable;
import Entities.Units.Roles.UnitIgnoreFieldAvailable;
import Entities.Units.Roles.UnitInvisible;
import Entities.Units.Roles.UnitTriggerable;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Explosion extends Unit implements UnitInvisible, UnitTriggerable, UnitIgnoreFieldAvailable {
    public Explosion(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.EXPLOSION_STATS;
    }
    
    public void attack(List<Bullet> bullets) {
        bullets.add(new ExplosionBullet(col * GamePanel.CELL_WIDTH, row * GamePanel.CELL_HEIGHT + 30, atk, row, col));
    }

    @Override
    public void insersectEnemy(Enemy enemy) {
    }

    @Override
    public void getUnitFromField(Unit unit) {
    }

    @Override
    public void triggerWhenPlace() {
        new DWait(1, e -> {
            Audio.play(AudioName.CANDLE_ACTIVATE);
            setStatus("ATK");
            new DWait(1, ee -> {
                attack(GamePanel.getBullets());
                setHealth(0);
            }).start();
        }).start();
    }

    
}
