package Entities.Enemies;

import Main.GamePanel;
import Entities.Units.Unit;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import DSystem.DTimer;

public abstract class Enemy {

    protected double x;
    protected int row; // Position on the grid
    protected int health;
    protected double speed;
    protected long lastAttackTime = 0;
    protected int ATTACK_COOLDOWN;
    protected String Status = "idle";
    protected DTimer animationTimer;
    
    protected BufferedImage actionIdle, actionATK, actionDead;
    protected int currentFrame = 0;
    protected int total_Frame = 4;
    protected int frame_Width = 32, frame_Hight = 32;

    public abstract Rectangle getBounds();
    public abstract void attack(Unit unit);

    public Enemy(double x, int row, int health, double speed) {
        this.x = x;
        this.row = row;
        this.health = health;
        this.speed = speed;
    }

    public int getHealth() {
        return this.health;
    }

    public void move() {
        x -= speed;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return row * GamePanel.CELL_HEIGHT;
    }

    public int getRow() {
        return row;
    }

    public void setLastAttackTime(long time) {
        this.lastAttackTime = time;
    }

    public long getLastAttackTime() {
        return this.lastAttackTime;
    }

    public int getAttackCooldown() {
        return ATTACK_COOLDOWN;
    }

    public BufferedImage getBufferedImage() { //2 Sprite Sheet
        if (this.Status.equals("idle")){
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);}
        else {
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
    }

    public void updateFrame() {
        currentFrame = (currentFrame + 1) % total_Frame;
    }
    
    public void updateFrame(double Dtime){ //2 Sprite Sheet
        if (this.Status.equals("idle")){
                currentFrame = (currentFrame + 1) % total_Frame;
        }
        else {
            currentFrame = (currentFrame + 1) % total_Frame;
        }
    }
    
    public void ability(){}
}
