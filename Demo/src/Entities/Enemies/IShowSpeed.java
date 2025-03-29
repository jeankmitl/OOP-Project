/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;
import Main.BossFightGamePanel;
import Main.GamePanel;

/**
 *
 * @author sarin
 */
public class IShowSpeed extends Enemy {
    public static enum State { NORMAL, RAGE }
    private State state;
    private IShowSpeedSpriteSheets spriteSheets;
    
    public IShowSpeed(double x, int row) {
        super(x, row, getENEMY_STATS());
        state = State.NORMAL;
        this.spriteSheets = (IShowSpeedSpriteSheets) getENEMY_STATS().getEntitySp();
    }
    
    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.ISHOWSPEED_STATS;
    }
    
    public boolean isHPlessThan190() {
        return this.health <= 190;
    }
    
    @Override
    public void move() {
        if (state == State.RAGE) {
            x -= 4;
        } else {
            x -= EnemyConfig.WALK_SLOW;
        }
    }
    
    public void update() {
        switch (state) {
            case NORMAL:
                if (isHPlessThan190()) {
                    setState(State.RAGE);
                    setAtkSpeed(0.85);
                }
                break;
                
            case RAGE:
                break;
        }
    }
    
    public void updateAnimation() {
        switch (state) {
            case NORMAL:
                break;
            case RAGE:
                actionIdle = spriteSheets.getActionIdleRage();
                actionATK = spriteSheets.getActionAtkRage();
                break;
        }
    }
    
    public void setState(State state) {
        this.state = state;
        updateAnimation();
    }
    
    public State getState() {
        return this.state;
    }
}
