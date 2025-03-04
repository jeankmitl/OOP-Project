package Entities.Units;

import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bullet;
import DSystem.DTimer;
import java.awt.Rectangle;
import java.util.List;
import java.awt.image.BufferedImage;

public abstract class Unit {

    //Status
    protected int health;
    protected int atk;
    protected int atkSpeed;
    protected int cost;
    protected static double cooldown;
    protected char role;
    
    protected int row, col;
    
    protected BufferedImage actionIdle, actionATK, actiondead;
    protected int currentFrame = 0;
    protected int total_Frame_Idle = 4;
    protected int total_Frame_ATK = 8;
    protected int frame_Width = 32, frame_Hight = 32;
    protected String Status = "idle";
    protected String Stage = "not_cooldown";
    protected DTimer animationTimer;
    
    public abstract boolean isEnemyInfront(List<Enemy> enermies);
    public abstract void attack(List<Bullet> bullets);
    public abstract Rectangle getBounds();

    public Unit() {}
    
    public Unit(int row, int col, int health, int atk, int atkSpeed, int cost, double cooldown, char role) {
        this.row = row;
        this.col = col;
        this.health = health;
        this.atk = atk;
        this.atkSpeed = atkSpeed;
        this.cost = cost;
        Unit.cooldown = cooldown;
        this.role = role;
    }

    public String getStage() {
        return Stage;
    }

    public void setStage(String Stage) {
        this.Stage = Stage;
    }
    
    public Unit getPlant() {
        return this;
    }
    
    public void takeDamage(int damage) {
        health -= damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getX() {
        return col * GamePanel.CELL_WIDTH;
    }

    public int getY() {
        return row * GamePanel.CELL_HEIGHT;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    public BufferedImage getBufferedImage() { // 2 Sprite Sheet
        if (this.Status.equals("idle")){
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);}
        else {
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
    }
    
    public void updateFrame() {
        currentFrame = (currentFrame + 1) % total_Frame_Idle;
    }
    
    public void updateFrame(double Dtime){ // 2 Sprite Sheet
        if (this.Status.equals("idle")){
                currentFrame = (currentFrame + 1) % total_Frame_Idle;
        }
        else {
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

    public static double getCooldown() {
        return cooldown;
    }

    public static void setCooldown(double cooldown) {
        Unit.cooldown = cooldown;
    }

    public char getRole() {
        return role;
    }

    public void setRole(char role) {
        this.role = role;
    }
    
    
    
}
