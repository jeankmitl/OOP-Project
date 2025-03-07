/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Entities.Bullets.Bullet;
import Entities.Enemies.Enemy;
import java.awt.Rectangle;
import java.util.List;

/**
 *
 * @author anawi
 */
public class GolemSupport extends Unit {
    public GolemSupport(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.GOLEM_SUPPORT_STATS;
    }

    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void attack(List<Bullet> bullets) {}

    @Override
    public Rectangle getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
