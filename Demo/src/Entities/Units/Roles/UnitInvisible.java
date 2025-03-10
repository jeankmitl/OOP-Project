/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Entities.Units.Roles;

import Entities.Enemies.Enemy;

/**
 *
 * @author anawi
 */
public interface UnitInvisible {
    //implements Unit == enemy don't hit &
    public void insersectEnemy(Enemy enemy);
}
