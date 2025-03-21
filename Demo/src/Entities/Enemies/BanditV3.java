/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

/**
 *
 * @author USER
 */
public class BanditV3 extends Enemy{

    public BanditV3(double x, int row) {
        super(x, row, getENEMY_STATS());
    }

    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.BANDIT_STATSV3;
    }
    
}
