/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.Audio;
import Asset.AudioName;
import DSystem.DWait;
import Entities.Bullets.Bone;
import Entities.Bullets.Bullet;
import Entities.Bullets.SemiAutoBullet;
import Entities.Units.Roles.UnitShootable;
import Main.GamePanel;
import java.util.List;

/**
 *
 * @author anawi
 */
public class Python extends Unit implements UnitShootable {
     public Python(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.PYTHON_STATS;
    }

    @Override
    public void shoot(List<Bullet> bullets) {
        Audio.play(AudioName.FIRE_TINY);
        bullets.add(new Bone(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, atk));
        new DWait(0.7, e -> {
            Audio.play(AudioName.FIRE_TINY);
            bullets.add(new SemiAutoBullet(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, atk));
        }).start();
    }
    
}
