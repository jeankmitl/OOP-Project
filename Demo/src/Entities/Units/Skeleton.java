package Entities.Units;

import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bone;
import Entities.Bullets.Bullet;
import Asset.Audio;
import DSystem.DTimer;
import Asset.AudioName;
import Entities.Units.Roles.UnitChargeShootable;
import Entities.Units.Roles.UnitShootable;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Skeleton extends Unit implements UnitChargeShootable {

    public Skeleton(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.SKELETON_STATS;
    }
    
    @Override
    public void shoot(List<Bullet> bullets) {
        Audio.play(AudioName.FIRE_TINY);
        bullets.add(new Bone(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, atk));
    }
}
