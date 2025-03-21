/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.VFX;
import DSystem.DWait;
import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.UnitGeneratable;
import Main.GamePanel;

/**
 *
 * @author anawi
 */
public class Vampire extends Unit implements UnitGeneratable {
    
    public Vampire(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.VAMPIRE_STATS;
    }
    
    // Add 50 cost every 15 seconds
    @Override
    public void generateByAtkSpeed() {
        Unit leastHealth = this;
        if (health >= maxHealth) {
            for (Unit unit: GamePanel.getUnits()) {
                if (unit.getRow() != row || unit.getHealth() >= 9999) continue;
                if (leastHealth == null || unit.getMaxHealth() - unit.getHealth() > leastHealth.getMaxHealth() - leastHealth.getHealth()) {
                    leastHealth = unit;
                }
            }
        }
        if (leastHealth != null) {
            if (leastHealth.getHealth() >= leastHealth.getMaxHealth()) return;
            setStatus(ATK_STATUS);
            leastHealth.takeDamage(atk);
            GamePanel.getVfxs().add(new VFX(leastHealth.getX(), leastHealth.getY() - 50, "heal_vfx"));
            new DWait(1, (e) -> {
                setStatus(IDLE_STATUS);
            }).start();
        }
    }
}
