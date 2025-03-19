/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.Audio;
import Asset.AudioName;
import Entities.Bullets.Bone;
import Entities.Bullets.Bullet;
import Entities.Bullets.PistolBullet;
import Entities.Units.Roles.UnitCommensalism;
import Entities.Units.Roles.UnitExtraFieldAvailable;
import Entities.Units.Roles.UnitShootable;
import Main.GamePanel;
import java.util.List;

/**
 *
 * @author anawi
 */
public class MiPya extends Unit implements UnitShootable, UnitCommensalism {
    public MiPya(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.MIPYA_STATS;
    }
    
    @Override
    public void shoot(List<Bullet> bullets) {
        Audio.play(AudioName.FIRE_TINY);
        bullets.add(new PistolBullet(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, atk));
    }

    @Override
    public void getUnitFromField(Unit unit) {
    }
}
