package Entities.Enemies;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USER
 */
public class BanditV2 extends Enemy{

    public BanditV2(double x, int row) {
        super(x, row, getENEMY_STATS());
    }

    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.BANDIT_STATSV2;
    }
    
    @Override
    public Enemy createNew(int x, int y) {
        return new BanditV2(x, y);
    }
    
}
