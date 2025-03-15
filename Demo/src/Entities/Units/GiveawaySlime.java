/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.VFX;
import DSystem.DRepeat;
import DSystem.DWait;
import Entities.Enemies.Enemy;
import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.UnitIgnoreFieldAvailable;
import Entities.Units.Roles.UnitInvisible;
import Entities.Units.Roles.UnitTriggerable;
import Main.GamePanel;

/**
 *
 * @author anawi
 */
public class GiveawaySlime extends Unit implements UnitInvisible, UnitIgnoreFieldAvailable, UnitTriggerable {
    public GiveawaySlime(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.GIVEAWAY_SLIME_STATS;
    }

    @Override
    public void insersectEnemy(Enemy enemy) {
    }

    @Override
    public void getUnitFromField(Unit unit) {
    }

    @Override
    public void triggerWhenPlace() {
        setStatus(ATK_STATUS);
        new DRepeat(0.6, 3, false, e -> {
            GamePanel.increaeMana(atk);
            GamePanel.getVfxs().add(new VFX(getX(), getY() - 50, "get_mana_slime_vfx"));
        }, e -> {
            setHealth(0);
        }).start();
    }
    
    
}
