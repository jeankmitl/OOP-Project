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

    public stage_beta() {
        super(9999);
    }
    
    @Override
    public void summonEnemies() {
        start = new DWait(10, e->{
            System.out.println("Endless Wave");
            super.Spawn_Enemy(new Bandit(0, 0),99);
            super.Spawn_Enemy(new BanditV2(0, 0),99);
            super.Spawn_Enemy(new BanditV3(0, 0),99);
            super.Spawn_Enemy(new Ninja(0, 0),99);
            super.Spawn_Enemy(new LittleRedHood(0, 0),99);
            super.Spawn_Enemy(new Sorcerer(0, 0),99);
            super.Spawn_Enemy(new RCBomber(0, 0),99);
            super.Spawn_Enemy(new RobotMonoWheel(0, 0),99);
        });
        start.start();
    }
    
    
    
}
