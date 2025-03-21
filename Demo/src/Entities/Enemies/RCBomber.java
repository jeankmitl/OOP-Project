/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

import Asset.VFX;
import DSystem.DWait;
import Entities.Units.Unit;
import Main.GamePanel;
import static Main.GamePanel.CELL_HEIGHT;
import static Main.GamePanel.CELL_WIDTH;
import static Main.GamePanel.getVfxs;

/**
 *
 * @author anawi
 */
public class RCBomber extends Enemy {
    public RCBomber(double x, int row) {
        super(x, row, getENEMY_STATS());
    }

    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.RC_BOMBER_STATS;
    }

    @Override
    public void attack(Unit unit) {
        setSpeed(0);
        new DWait(0.8, (e) -> {
            if (getHealth() > 0) {
                setHealth(0);
                unit.takeDamage(atk);
                VFX vfx = new VFX(getX(), getY(), "explosion_vfx");
                vfx.setWidth(CELL_WIDTH + 60);
                vfx.setHeight(CELL_HEIGHT + 20);
                GamePanel.getVfxs().add(vfx);
                getVfxs().add(vfx);
            }
        }).start();
    }

//    @Override
//    public Enemy createNew(int x, int y) {
//        return new RCBomber(x, y);
//    }
    
}
