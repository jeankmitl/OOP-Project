/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Entities.Units.UnitConfig;

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
    private String desc;
    
    public EntityStats(EntitySpriteSheets entitySp, int health, int atk, double atkSpeed, int role, String desc) {
        this.entitySp = entitySp;
        this.health = health;
        this.atk = atk;
        this.atkSpeed = atkSpeed;
        this.role = role;
        this.desc = desc;
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
    
    public String getRoleName() {
        String name = "";
        switch (role) {
            case UnitConfig.ATTACKER -> name = "Attacker";
            case UnitConfig.COST_GEN -> name = "Cost Gen";
            case UnitConfig.DEFENDER -> name = "Defender";
            case UnitConfig.EXPLOTION -> name = "Explotion";
            case UnitConfig.GUARD -> name = "Guard";
        }
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
