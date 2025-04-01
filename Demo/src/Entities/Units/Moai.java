/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.VFX;
import DSystem.DWait;
import Entities.Bullets.Bullet;
import Entities.Enemies.Enemy;
import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.UnitGeneratable;
import Main.GamePanel;
import Main.UnitType;
import java.awt.Rectangle;
import java.util.List;

/**
 *
 * @author anawi
 */
public class Moai extends Unit implements UnitGeneratable {
    public Moai(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.GOLEM_SUPPORT_STATS;
    }

    @Override
    public void generateByAtkSpeed() {
        setStatus(ATK_STATUS);
        GamePanel.reduceCooldown(10);
        new DWait(1.5, (e) -> {
            setStatus(IDLE_STATUS);
            GamePanel.getVfxs().add(new VFX(getX(), getY() - 50, "dec_cooldown_vfx"));
        }).start();
    }
}
