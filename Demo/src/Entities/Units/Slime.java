package Entities.Units;

import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bullet;
import Asset.VFX;
import DSystem.DWait;
import DSystem.DTimer;
import Entities.Units.Roles.UnitExtraFieldAvailable;
import Entities.Units.Roles.UnitGeneratable;
import Entities.Units.Roles.UnitIgnoreFieldAvailable;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Slime extends Unit implements UnitGeneratable {

    public Slime(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.SLIME_STATS;
    }
    
    // Add 50 cost every 15 seconds
    @Override
    public void generateByAtkSpeed() {
        setStatus(ATK_STATUS);
        new DWait(1.5, (e) -> {
            GamePanel.increaseMana(atk);
            setStatus(IDLE_STATUS);
            GamePanel.getVfxs().add(new VFX(getX(), getY() - 50, "get_mana_slime_vfx"));
        }).start();
    }
}
