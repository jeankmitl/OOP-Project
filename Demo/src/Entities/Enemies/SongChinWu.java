/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

import DSystem.DWait;
import DSystem.OWait;
import Main.BossFightGamePanel;
import static Main.GamePanel.CELL_HEIGHT;
import static Main.GamePanel.CELL_WIDTH;
import static Main.GamePanel.GRID_OFFSET_X;
import static Main.GamePanel.GRID_OFFSET_Y;
import Main.Stages.StageBossFight;
import static Main.UnitSelector.COLS;
import java.util.List;
import java.util.Random;

/**
 *
 * @author sarin
 */
public class SongChinWu extends Enemy {
    private Random random;
    
    public static enum State { WALK, STAND_WITH_SWORD, DROP_SWORD, STAND_NO_SWORD_MOTIVATED, STAND_NO_SWORD, SUMMON_ENEMY, SHOOT, FINAL_ATTACK }
    private State state;
    private long stateStartTime;
    private final int targetX = 857; // Position for Stop walking
    private SongChinWuSpriteSheets spriteSheets;
    private boolean hasUsedSummonSwords = true;
    public boolean isDropedSword = true;

    public SongChinWu(double x, int row) {
        super(x, row, getENEMY_STATS());
        this.spriteSheets = (SongChinWuSpriteSheets) getENEMY_STATS().getEntitySp();
        this.state = State.WALK;
        this.stateStartTime = System.currentTimeMillis();
        random = new Random();
    }
    
    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.SONG_CHIN_WU_STATS;
    }

    public void move(BossFightGamePanel game) {
        x -= 0.7;
        System.out.println("Walking...");
        if (x <= targetX) {
            setState(State.STAND_WITH_SWORD, game);
        }
    }

    private boolean timePassed(int ms) {
        return System.currentTimeMillis() - stateStartTime >= ms;
    }

    public void setState(State newState , BossFightGamePanel game) {
        this.state = newState;
        this.stateStartTime = System.currentTimeMillis();
        updateAnimation(game);
    }
    
    public State getState() {
        return state;
    }
    
    public void update(BossFightGamePanel game) {
        switch (state) {
            case WALK:
                if (x <= targetX) {
                    setState(State.STAND_WITH_SWORD, game);
                }
                move(game);
                break;
                
            case STAND_WITH_SWORD:
                if (timePassed(8500)) {
                    setState(State.DROP_SWORD, game);
                }
                break;
                
            case DROP_SWORD:
                if (timePassed(500)) {
                    setState(State.STAND_NO_SWORD_MOTIVATED, game);
                }
                break;
                
            case STAND_NO_SWORD_MOTIVATED:
                break;
                
            case STAND_NO_SWORD:
                break;
                
            case SHOOT:
                break;
                
            case FINAL_ATTACK:
                break;
        }
    }
    
    public void updateAnimation(BossFightGamePanel game) {
        switch (state) {
            case WALK:
                actionIdle = spriteSheets.getActionWalk();
                break;
            case STAND_WITH_SWORD:
                actionIdle = spriteSheets.getActionIdleWithSword();
                break;
            case DROP_SWORD:
                actionIdle = spriteSheets.getActionDropSword();
                break;
            case STAND_NO_SWORD_MOTIVATED:
                actionIdle = spriteSheets.getActionIdleNoSwordMotivated();
                break;
            case STAND_NO_SWORD:
                actionIdle = spriteSheets.getActionIdleNoSword();
                break;
            case SHOOT:
                actionIdle = spriteSheets.getActionIdleNoSwordMotivated();
                break;
            case FINAL_ATTACK:
                actionIdle = spriteSheets.getActionFinalAtk();
                break;
        }
    }
}
