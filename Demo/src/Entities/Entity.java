/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.awt.image.BufferedImage;

/**
 *
 * @author anawi
 */
public abstract class Entity {
    //Status
    protected int health;
    protected int atk;
    protected double atkSpeed;
    protected int role;
    
    //Animation
    protected int frame_Width = 32, frame_Hight = 32;
    protected int currentFrame = 0;
    protected int total_Frame_Idle;
    protected int total_Frame_ATK;
    
    protected BufferedImage actionIdle, actionATK, actiondead;
    protected String status = "idle";
    
    public static final String IDLE_STATUS = "idle";
    public static final String ATK_STATUS = "ATK";
    public static final String DEAD_STATUS = "dead";

    public Entity(EntityStats entityStats) {
        this.health = entityStats.getHealth();
        this.atk = entityStats.getAtk();
        this.atkSpeed = entityStats.getAtkSpeed();
        this.role = entityStats.getRole();
        
        actionIdle = entityStats.getEntitySp().getActionIdle();
        actionATK = entityStats.getEntitySp().getActionAtk();
        actiondead = entityStats.getEntitySp().getActionDead();
        
        total_Frame_Idle = actionIdle.getWidth() / frame_Width;
        total_Frame_ATK = actionATK.getWidth() / frame_Width;
//        System.out.println(total_Frame_Idle);
//        System.out.println(total_Frame_ATK);
    }
    
    public void takeDamage(int damage) {
        health -= damage;
    }
    
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        currentFrame = 0;
        this.status = status;
    }
    
    public BufferedImage getBufferedImage() { //2 Sprite Sheet
        if (status.equals("idle")){
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);}
        else {
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
    }
    
    public void updateFrame() {
        currentFrame = (currentFrame + 1) % total_Frame_Idle;
    }
    
    public void updateFrame(double Dtime){ // 2 Sprite Sheet
        if (status.equals("idle")){
                currentFrame = (currentFrame + 1) % total_Frame_Idle;
        } else {
            currentFrame = (currentFrame + 1) % total_Frame_ATK;
        }
    }
    
    public int getTotal_Frame_Idle() {
        return total_Frame_Idle;
    }

    public void setTotal_Frame_Idle(int total_Frame_Idle) {
        this.total_Frame_Idle = total_Frame_Idle;
    }
    
    public int getTotal_Frame_ATK() {
        return total_Frame_ATK;
    }

    public void setTotal_Frame_ATK(int total_Frame_ATK) {
        this.total_Frame_ATK = total_Frame_ATK;
    }

    public int getFrame_Width() {
        return frame_Width;
    }

    public int getFrame_Hight() {
        return frame_Hight;
    }
}
