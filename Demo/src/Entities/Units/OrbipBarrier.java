/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import static Entities.Entity.ATK_STATUS;
import static Entities.Entity.DEAD_STATUS;
import static Entities.Entity.IDLE_STATUS;
import Entities.Units.Roles.OnBack;
import Entities.Units.Roles.UnitExtraFieldAvailable;
import Entities.Units.Roles.UnitIgnoreFieldAvailable;
import java.awt.image.BufferedImage;

/**
 *
 * @author anawi
 */
public class OrbipBarrier extends Unit implements UnitIgnoreFieldAvailable, OnBack {

    public OrbipBarrier(int row, int col) {
        super(row, col, getUNIT_STATS());
    }
    
    public static UnitStats getUNIT_STATS() {
        return UnitConfig.ORBIP_BARRIER_STATS;
    }
    
    @Override
    public void getUnitFromField(Unit unit) {
    }
    
    public void check_health(){
        if(this.health > 250){
            setStatus(IDLE_STATUS, false);
        } else{
            setStatus(ATK_STATUS, false);
        }
    }
    
    @Override
    public void updateFrame() {
        this.check_health();
        super.updateFrame();
    }
}

