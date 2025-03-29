/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

/**
 *
 * @author sarin
 */
public class AntKing extends Enemy {
    
    public boolean notDashYet = true;
    
    public AntKing(double x, int row) {
        super(x, row, getENEMY_STATS());
        setFrame_Hight(48);
        setFrame_Width(48);
    }
    
    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.BERU_STATS;
    }
    
    @Override
    public void ability() {
        if (notDashYet && x >= 90) {
            this.x -= 189;
            notDashYet = false;
        }
    }
    
    @Override
    public void move() {
        if (notDashYet) {
            x -= 2;
            return;
        } 
        x -= 0.1;
    } 
    
}
