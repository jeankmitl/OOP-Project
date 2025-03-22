/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

/**
 *
 * @author sarin
 */
public class SongChinWu extends Enemy {

    private SongChinWuState state;
    private long stateStartTime;
    private final int targetX = 850; // Position for Stop walking

    public SongChinWu(double x, int row) {
        super(x, row, getENEMY_STATS());
        this.state = SongChinWuState.WALKING;
        this.stateStartTime = System.currentTimeMillis();

    }

    @Override
    public void move() {
        x -= 0.7;
    }
    
    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.SONG_CHIN_WU_STATS;
    }
    
    private boolean timePassed(int ms) {
        return System.currentTimeMillis() - stateStartTime >= ms;
    }


    public void update() {
        switch (state) {
            case WALKING:
                move();
                if (this.x <= targetX) {
                    changeState(SongChinWuState.STANDING);
                }
                break;

            case STANDING:
                if (timePassed(2500)) { // 2.5 secs
//                    dropSword();
                    changeState(SongChinWuState.SUMMONING);
                }
                break;

//            case SUMMONING:
//                summonSwords();
//                changeState(SongChinWuState.ATTACKING);
//                break;
//
//            case ATTACKING:
//                if (this.health > this.maxHealth * 0.1) {
//                    teleportRandomly();
//                    useRandomSkill();
//                } else {
//                    changeState(SongChinWuState.FINAL_PHASE);
//                }
//                break;
//
//            case FINAL_PHASE:
//                if (!nukeStarted) {
//                    startNuke();
//                } else if (timePassed(10000)) {
//                    playerLoses();
//                }
//                break;
        }
    }

    private void changeState(SongChinWuState newState) {
        this.state = newState;
        this.stateStartTime = System.currentTimeMillis(); // Reset timer
    }

    private void performAttack() {
        System.out.println("Boss is attacking!");
        // Add attack logic herew 
    }

    private void performSpecialSkill() {
        System.out.println("Boss uses special skill!");
        // Add special skill logic here
    }
}
