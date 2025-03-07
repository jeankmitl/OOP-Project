/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

import DSystem.DTimer;
import Entities.Units.Unit;

/**
 *
 * @author USER
 */
public class LittleRedHood extends Enemy {
    
    public LittleRedHood(double x, int row) {
        super(x, row, getENEMY_STATS());
        animationTimer = new DTimer(0.25, e -> updateFrame(0.25));
        animationTimer.start();
    }

    @Override
    public void attack(Unit unit) {
        super.attack(unit);
    }

    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.LITTLE_RED_HOOD_STATS;
    }
}
