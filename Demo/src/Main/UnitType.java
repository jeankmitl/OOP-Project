/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Entities.Units.Unit;
import Entities.Units.UnitSpriteSheets;
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
    
    public Class getClassUse() {
        return unitClass;
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
    
    public int getHealth() {
        return unitStats.getHealth();
    }
    
    public int getAtk() {
        return unitStats.getAtk();
    }
    
    public double getAtkSpeed() {
        return unitStats.getAtkSpeed();
    }
    
    public int getRole() {
        return unitStats.getRole();
    }
    
    public String getRoleName() {
        return unitStats.getRoleName();
    }

    public void setCoolDownElapsed(double coolDownElapsed) {
        this.coolDownElapsed = coolDownElapsed;
    }
    
    public double getCoolDownElapsed() {
        return coolDownElapsed;
    }
    
    public UnitSpriteSheets getUnitSp() {
        return unitStats.getUnitSp();
    }
    
    public BufferedImage getProfileImg() {
        return unitStats.getUnitSp().getProfileImg();
    }
    
    public BufferedImage getRoleIconImg() {
        return unitStats.getUnitSp().getStatsIcon(unitStats.getRole());
    }
    
    public BufferedImage getRoleIconImg(int i) {
        return unitStats.getUnitSp().getStatsIcon(i);
    }
}