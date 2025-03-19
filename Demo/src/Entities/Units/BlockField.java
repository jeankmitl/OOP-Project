/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Entities.Enemies.Enemy;
import static Entities.Units.Candles6.getUNIT_STATS;
import Entities.Units.Roles.UnitInvisible;

/**
 *
 * @author anawi
 */
public class BlockField extends Unit implements UnitInvisible {

    public BlockField(int row, int col) {
        super(row, col, getUNIT_STATS());
        //WIP
    }
    
    public static UnitStats getUNIT_STATS() {
        return UnitConfig.BLOCK_FIELD_STATS;
    }
    
    @Override
    public void insersectEnemy(Enemy enemy) {
    }
}
