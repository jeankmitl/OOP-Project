/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

/**
 *
 * @author sarin
 */
public class TheBlueSword extends Enemy {
    
    public TheBlueSword(double x, int row, EnemyStats enemyStats) {
        super(x, row, getENEMY_STATS());
    }
    
    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.THE_BLUE_SWORD;
    }
    
}
