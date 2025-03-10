/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import DSystem.DTimer;
import DSystem.OTimer;
import DSystem.OWait;
import Main.GamePanel;
import java.awt.image.BufferedImage;

/**
 *
 * @author anawi
 */
public abstract class Entity {
    //Status
    protected final int maxHealth;
    protected int health;
    static protected int atk;
    protected double atkSpeed;
    protected int role;
    
//    OWait attackWait  = new OWait(atkSpeed);
//    protected boolean isReadyAttack = true;
//    protected boolean isAlwaysAttack = false;
    
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
        maxHealth = entityStats.getHealth();
        health = maxHealth;
        atk = entityStats.getAtk();
        atkSpeed = entityStats.getAtkSpeed();
        role = entityStats.getRole();
        
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

    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAtk() {
        return atk;
    }
    
    public void setAtk(int atk) {
        this.atk = atk;
    }

//    public void runAtk() {
//        if (isReadyAttack) {
//            isReadyAttack = false;
//            attackWait.reset();
//            attack();
//        }
//    }
//    
//    public void attack() {
//        
//    }
    
    public double getAtkSpeed() {
        return atkSpeed;
    }

    public void setAtkSpeed(double atkSpeed) {
        this.atkSpeed = atkSpeed;
    }
    
    public void setRole(int role) {
        this.role = role;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public String getStatus() {
        return status;
    }
    
    public abstract int getX();
    public abstract int getY();

    public void setStatus(String status) {
        setStatus(status, true);
    }
    
    public void setStatus(String status, boolean isReframe) {
        if (isReframe) {
            currentFrame = 0;
        }
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
        if (status.equals("idle")){
                    currentFrame = (currentFrame + 1) % total_Frame_Idle;
            } else {
                currentFrame = (currentFrame + 1) % total_Frame_ATK;
        }
    }
    
    public void updateFrame(double manualUpdateDelay){ // 2 Sprite Sheet
//        new DTimer(manualUpdateDelay, e -> {
            if (status.equals("idle")){
                    currentFrame = (currentFrame + 1) % total_Frame_Idle;
            } else {
                currentFrame = (currentFrame + 1) % total_Frame_ATK;
            }
//        }).start();
    }

    public static int getAtk() {
        return atk;
    }

    public static void setAtk(int atk) {
        Entity.atk = atk;
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
