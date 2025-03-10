/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units.Roles;

import Entities.Bullets.Bullet;
import java.util.List;

/**
 *
 * @author anawi
 */
public interface UnitShootable {
    public void attack(List<Bullet> bullets);
}
