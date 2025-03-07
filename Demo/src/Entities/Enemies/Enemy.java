package Entities.Enemies;

import Main.GamePanel;
import Entities.Units.Unit;
import java.awt.Rectangle;
import DSystem.DTimer;
import DSystem.DWait;
import Entities.Entity;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {

    // Stats
    protected double speed;
    
    // Animation
    protected int total_Frame = 4;
    
    protected double x;
    protected int row; // Position on the grid
    protected long lastAttackTime = 0;
    protected int ATTACK_COOLDOWN;
    protected DTimer animationTimer;
    
    public final EnemyStats ENEMY_STATS = null;
    

    public Enemy(double x, int row, EnemyStats enemyStats) {
        super(enemyStats);
        this.x = x;
        this.row = row;
        this.speed = enemyStats.getWalkSpeed();
    }
   
    public Rectangle getBounds() {
        return new Rectangle((int) x + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }
    
    public void attack(Unit unit) {
        this.currentFrame = 0;
        setStatus("ATK");
        unit.takeDamage(atk);
        new DWait(0.8, (e) -> {
            this.currentFrame = 0;
            setStatus("idle");
        }).start();
    } 
    
    public void move() {
        x -= speed;
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
    
    public void ability(){}
    
    public static EnemyStats getENEMY_STATS() throws NoSuchMethodException {
        System.out.println("no implements");
        throw new NoSuchMethodException("Make sure to return their unit STATS");
    }
    
}
