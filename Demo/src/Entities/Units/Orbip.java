/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.VFX;
import DSystem.DTimer;
import DSystem.DWait;
import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.UnitGeneratable;
import Main.GamePanel;

/**
 *
 * @author anawi
 */
public class Orbip extends Unit implements UnitGeneratable {
    private OrbipBarrier currentBarrier;
    
    public Orbip(int row, int col) {
        super(row, col, getUNIT_STATS());
        placeBarrier(row, col);
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.ORBIP_STATS;
    }
    
    @Override
    public void generateByAtkSpeed() {
        Unit leastHealth = null;
        if (health >= maxHealth) {
            for (Unit unit: GamePanel.getUnits()) {
                if (unit == this || unit.getClass().equals(OrbipBarrier.class) || unit.getRow() != row || unit.getHealth() >= 9999) continue;
                if (leastHealth == null || unit.getMaxHealth() - unit.getHealth() > leastHealth.getMaxHealth() - leastHealth.getHealth()) {
                    leastHealth = unit;
                }
            }
        }
        System.out.println("null?");
        if (leastHealth != null) {
            System.out.println("not null!");
            setStatus(ATK_STATUS);
            leastHealth.takeDamage(atk);
            if (leastHealth.getHealth() > leastHealth.getMaxHealth()) {
                leastHealth.setHealth(leastHealth.getMaxHealth());
            }
            GamePanel.getVfxs().add(new VFX(leastHealth.getX(), leastHealth.getY() - 50, "heal_vfx"));
            new DWait(1.5, (e) -> {
                setStatus(IDLE_STATUS);
            }).start();
            if (currentBarrier != null) {
                currentBarrier.setHealth(0);
            }
            currentBarrier = placeBarrier(leastHealth.getRow(), leastHealth.getCol());
            
        }
    }
    
    public OrbipBarrier placeBarrier(int row, int col) {
        OrbipBarrier barrier = new OrbipBarrier(row, col);
        barrier.setHealth(500);
        GamePanel.placeUnitByUnit(0, barrier);
        return barrier;
    }
}
