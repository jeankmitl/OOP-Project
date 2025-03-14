/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DSystem.DWait;
import Entities.Enemies.*;

/**
 *
 * @author USER
 */
public class stage_beta extends GamePanel{
    protected DWait start;
    
    @Override
    public void summonEnemies() {
        start = new DWait(10, e->{
            System.out.println("Endless Wave");
            super.Spawn_Enemy(new Bandit(0, 0),99);
            super.Spawn_Enemy(new Ninja(0, 0),99);
            super.Spawn_Enemy(new LittleRedHood(0, 0),99);
            super.Spawn_Enemy(new Sorcerer(0, 0),99);
//            Spawn_Enemy(new Bandit(0, 0),99);
//            Spawn_Enemy(new Bandit(0, 0),99);
//            Spawn_Enemy(new Bandit(0, 0),99);
        });
        start.start();
    }
    
    
    
}
