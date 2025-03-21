/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

import Entities.EntityStats;

/**
 *
 * @author anawi
 */
public class EnemyStats extends EntityStats {
    private EnemySpriteSheets enemySp;
    private double walkSpeed;
    private int role;
    
    public EnemyStats(EnemySpriteSheets enemySp, int health, int atk, double atkSpeed, double walkSpeed, int role) {
        super(enemySp, health, atk, atkSpeed, role, null);
        this.enemySp = enemySp;
        this.walkSpeed = walkSpeed;
    }
    
    public EnemyStats(EnemySpriteSheets enemySp, int health, int atk, double atkSpeed, double walkSpeed, int role, String desc) {
        super(enemySp, health, atk, atkSpeed, role, desc);
        this.enemySp = enemySp;
        this.walkSpeed = walkSpeed;
    }

    public EnemySpriteSheets getEnemySp() {
        return enemySp;
    }
    
    public double getWalkSpeed() {
        return walkSpeed;
    }
}
