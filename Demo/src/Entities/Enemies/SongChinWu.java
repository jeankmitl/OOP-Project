/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

import DSystem.DWait;
import DSystem.OTimer;
import DSystem.OWait;
import Main.BossFightGamePanel;
import static Main.GamePanel.CELL_HEIGHT;
import static Main.GamePanel.CELL_WIDTH;
import static Main.GamePanel.GRID_OFFSET_X;
import static Main.GamePanel.GRID_OFFSET_Y;
import Main.Stages.StageBossFight;
import static Main.UnitSelector.COLS;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author sarin
 */
public class SongChinWu extends Enemy {
    private Random random;
    private OTimer teleportTimer = new OTimer(5);
    private OTimer summonTimer = new OTimer(10);
    
    public static enum State { WALK, STAND_WITH_SWORD, DROP_SWORD, STAND_NO_SWORD_MOTIVATED, STAND_NO_SWORD, SUMMON_ENEMY, SHOOT, FINAL_ATTACK }
    private State state;
    private long stateStartTime;
    private final int targetX = 857; // Position for Stop walking
    private SongChinWuSpriteSheets spriteSheets;
    private boolean isDebuffed = false;

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
        if (x <= targetX) {
            setState(State.STAND_WITH_SWORD, game);
        }
    }
    
    @Override
    public void takeDamage(int damage) {
        if (isDebuffed) {
            int newDamage = (int) (damage + (damage * 0.3));
            if (health - newDamage < 0) {
                health = 0;
                return;
            }
            health -= newDamage;
        } else {
            if (health - damage < 0) {
                health = 0;
                return;
            }
            health -= damage;
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
                if (timePassed(8000)) {
                    setState(State.DROP_SWORD, game);
                }
                break;
                
            case DROP_SWORD:
                if (timePassed(700)) {
                    setState(State.STAND_NO_SWORD_MOTIVATED, game);
                }
                break;
                
            case STAND_NO_SWORD_MOTIVATED:
                if (!game.theBlueSwordIsAlive && !game.theRedSwordIsAlive)
                    setState(State.STAND_NO_SWORD, game);
                    isDebuffed = true;
                
//                // Enemy Summon Logic (Every 10s)
//                if (summonTimer.tick(10)) {
//                    summonEnemies(game);
//                }
//
//                // Teleportation Logic (Every 5s)
//                if (teleportTimer.tick(5)) {
//                    teleport();
//                }
                break;
                
            case STAND_NO_SWORD:
                // Do the same as STAND_NO_SWORD_MOTIVATED state
//                if (summonTimer.tick(10)) {
//                    summonEnemies(game);
//                }
//
//                if (teleportTimer.tick(5)) {
//                    teleport();
//                }
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
                setTotal_Frame_Idle(6);
                break;
            case STAND_NO_SWORD_MOTIVATED:
                actionIdle = spriteSheets.getActionIdleNoSwordMotivated();
                setTotal_Frame_Idle(4);
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
