/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Entities.EntityStats;

/**
 *
 * @author anawi
 */
public class UnitStats extends EntityStats {
    private UnitSpriteSheets unitSp;
    private int cost;
    private double cooldown;
    private int role;
    
    public UnitStats(UnitSpriteSheets unitSp, int health, int atk, double atkSpeed, int cost, double cooldown, int role) {
        super(health, atk, atkSpeed, role);
        this.unitSp = unitSp;
        this.cost = cost;
        this.cooldown = cooldown;
    }

    public UnitSpriteSheets getUnitSp() {
        return unitSp;
    }

    public int getCost() {
        return cost;
    }

    public double getCooldown() {
        return cooldown;
    }
}
