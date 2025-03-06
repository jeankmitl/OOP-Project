/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author anawi
 */
public abstract class Entity {
    protected int health;
    protected int atk;
    protected double atkSpeed;
    
    protected int total_Frame_Idle = 4;
    protected int total_Frame_ATK = 8;
    public static final int frame_Width = 32, frame_Hight = 32;

    public Entity(EntityStats entityStats) {
        this.health = entityStats.getHealth();
        this.atk = entityStats.getAtk();
        this.atkSpeed = entityStats.getAtkSpeed();
    }
    
    public void takeDamage(int damage) {
        health -= damage;
    }
    
    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health <= 0;
    }
    
}
