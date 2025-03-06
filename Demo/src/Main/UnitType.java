/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Entities.Units.Unit;
import Entities.Units.UnitStats;
import java.awt.image.BufferedImage;

/**
 *
 * @author anawi
 */
public class UnitType {
    Class<? extends Unit> unitClass;
    private boolean dragging = false;
    private double coolDownElapsed = 0;
    private UnitStats unitStats;

    public UnitType(Class<? extends Unit> unitClass) {
        this.unitClass = unitClass;
        try {
            unitStats = (UnitStats)unitClass.getDeclaredMethod("getUNIT_STATS").invoke(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getClassName() {
        return unitClass.getSimpleName();
    }

    public void coolDownTick(double deltaTime) {
        if (coolDownElapsed > 0) {
            coolDownElapsed -= deltaTime;
        }
    }

    public void startCooldown() {
        coolDownElapsed = unitStats.getCooldown();
    }
    
    public double getCooldown() {
        return unitStats.getCooldown();
    }
    
    public boolean isNoCoolDown() {
        return coolDownElapsed <= 0;
    }
    

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }
    
    public int getManaCost() {
        return unitStats.getCost();
    }

    public double getCoolDownElapsed() {
        return coolDownElapsed;
    }
    
    public BufferedImage getProfileImg() {
        return unitStats.getUnitSp().getProfileImg();
    }
}