package Entities.Enemies;

import Main.GamePanel;
import Entities.Units.Unit;
import java.awt.Rectangle;
import DSystem.DTimer;
import DSystem.DWait;
import DSystem.OTimer;
import Entities.Entity;
import Entities.UnitFactory;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Enemy extends Entity {

    // Stats
    protected final double defaultSpeed;
    protected double speed;
    
    // Animation
    protected int total_Frame = 4;
    
    protected double x;
    protected int row; // Position on the grid
    protected long lastAttackTime = 0;
    protected int ATTACK_COOLDOWN;
    protected DTimer animationTimer;
    
    public final EnemyStats ENEMY_STATS = null;
    protected boolean debuff_Chills = false; // Slow When Trigger
    protected boolean debuff_Stun = false; // Stun When Trigger
    protected boolean buff_rage = false; // Stun When Trigger
    
    public Enemy(double x, int row, EnemyStats enemyStats) {
        super(enemyStats);
        this.x = x;
        this.row = row;
        this.defaultSpeed = enemyStats.getWalkSpeed();
        this.speed = defaultSpeed;
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

    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return row * GamePanel.CELL_HEIGHT;
    }

    public int getRow() {
        return row;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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
    
    public void debuff_chill(){
        if (!debuff_Chills){
            System.out.println("Freeze");
            this.debuff_Chills = true;
            this.setSpeed(this.getSpeed()*0.25); //0.5 cheat
            DWait debuff = new DWait(5, e->{ // 10 sec cheat
                this.debuff_Chills = false;
                this.setSpeed(this.getSpeed()*4);
                System.out.println("Defrost");
            });
            debuff.start();
        }
    }
    
    public void debuff_stun(){
        Random RNG = new Random();
        int RNGChange = RNG.nextInt(5); //1-4 25% Change
        if (RNGChange == 2){
            if (!debuff_Stun){
                System.out.println("Stunned");
                this.debuff_Stun = true;
                double temp = this.getSpeed();
                this.setSpeed(this.getSpeed()*0); //0.5 cheat
                DWait debuff = new DWait(5, e->{ // 10 sec cheat
                    this.debuff_Stun = false;
                    this.setSpeed(temp);
                System.out.println("No longer Stunned");
            });
            debuff.start();
        }}
    }
    
    public static EnemyStats getENEMY_STATS() throws NoSuchMethodException {
        System.out.println("no implements");
        throw new NoSuchMethodException("Make sure to return their unit STATS");
    }
    
    public Enemy createNew(double x, int row) {
        try {
            return (Enemy)UnitFactory.createEntity(this.getClass(), x, row);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }
    
}
