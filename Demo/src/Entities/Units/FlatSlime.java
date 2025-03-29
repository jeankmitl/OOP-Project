/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.VFX;
import DSystem.DWait;
import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.OnFront;
import Entities.Units.Roles.UnitExtraFieldAvailable;
import Entities.Units.Roles.UnitGeneratable;
import Main.GamePanel;

/**
 *
 * @author anawi
 */
public class FlatSlime extends Unit implements UnitGeneratable, UnitExtraFieldAvailable, OnFront {
    public FlatSlime(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.FLAT_SLIME_STATS;
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

    @Override
    public void getUnitFromField(Unit unit) {
    }
}
