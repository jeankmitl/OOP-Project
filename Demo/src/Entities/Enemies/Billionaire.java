/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

/**
 *
 * @author anawi
 */
public class Billionaire extends Enemy {
    public Billionaire(double x, int row) {
        super(x, row, getENEMY_STATS());
    }
    
    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.BILLIONAIRE_STATS;
    }
}
