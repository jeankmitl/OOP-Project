/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

/**
 *
 * @author anawi
 */
public class UnitStats {
    private UnitSpriteSheets unitSp;
    private int health;
    private int atk;
    private int atkSpeed;
    private int cost;
    private double cooldown;
    private char role;
    
    public UnitStats(UnitSpriteSheets unitSp, int health, int atk, int atkSpeed, int cost, double cooldown, char role) {
        this.unitSp = unitSp;
        this.health = health;
        this.atk = atk;
        this.atkSpeed = atkSpeed;
        this.cost = cost;
        this.cooldown = cooldown;
        this.role = role;
    }

    public UnitSpriteSheets getUnitSp() {
        return unitSp;
    }
    
    public int getHealth() {
        return health;
    }

    public int getAtk() {
        return atk;
    }

    public int getAtkSpeed() {
        return atkSpeed;
    }

    public int getCost() {
        return cost;
    }

    public double getCooldown() {
        return cooldown;
    }

    public char getRole() {
        return role;
    }
}
