/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

import Entities.EntitySpriteSheets;

/**
 *
 * @author anawi
 */
public class EnemySpriteSheets extends EntitySpriteSheets {
    
    public EnemySpriteSheets(String idle, String atk) {
        this(idle, atk, null);
    }
    
    public EnemySpriteSheets(String idle, String atk, String dead) {
        super(idle, atk, dead);
    }
    
}
