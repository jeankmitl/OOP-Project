/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author anawi
 */
public class EntityStats {
    private EntitySpriteSheets entitySp;
    private int health;
    private int atk;
    private double atkSpeed;
    private int role;
    
    public EntityStats(EntitySpriteSheets entitySp, int health, int atk, double atkSpeed, int role) {
        this.entitySp = entitySp;
        this.health = health;
        this.atk = atk;
        this.atkSpeed = atkSpeed;
        this.role = role;
    }

    public EntitySpriteSheets getEntitySp() {
        return entitySp;
    }

    public int getHealth() {
        return health;
    }

    public int getAtk() {
        return atk;
    }

    public double getAtkSpeed() {
        return atkSpeed;
    }

    public int getRole() {
        return role;
    }
}
