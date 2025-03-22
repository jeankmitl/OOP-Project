/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.Stages;

import DSystem.DWait;
import Entities.Enemies.Bandit;
import Entities.Enemies.BanditV2;
import Main.EnemySummoner;
import Main.GamePanel;
import Main.StageConfig;
import Main.StageStats;
import java.awt.event.*;

/**
 *
 * @author USER
 */
public class stage2 implements EnemySummoner {
    protected DWait start,w1,w2,w3,w4,w5;

    @Override
    public StageStats getSTAGE_STATS() {
        return StageConfig.STAGE_2;
    }
    
    @Override
    public void summonEnemies(GamePanel game) {
        start = new DWait(20, e->{
            System.out.println("WAVE START");
            game.Spawn_Enemy(new Bandit(0, 0)); // 1
            w1.start();
        });
        
        w1 = new DWait(30, e->{
            System.out.println("WAVE 1");
            game.Spawn_Enemy(new Bandit(0, 0),2); //2
            w2.start();
        });
        
        w2 = new DWait(30, e->{
            System.out.println("WAVE 2");
            game.Spawn_Enemy(new Bandit(0, 0),2);
            game.Spawn_Enemy(new Bandit(0, 0),1,8); //4
            game.Spawn_Enemy(new BanditV2(0,0), 1);
            w3.start();
        });
        
        w3 = new DWait(30, e->{
            System.out.println("WAVE 3");
            game.Spawn_Enemy(new Bandit(0, 0),2,8);
            game.Spawn_Enemy(new Bandit(0, 0),2); //7
            game.Spawn_Enemy(new BanditV2(0,0), 3,6);
            w4.start();
        });
        
        w4 = new DWait(40, e->{
            System.out.println("WAVE 4");
            game.Spawn_Enemy(new Bandit(0, 0),4,10); // 8
            game.Spawn_Enemy(new BanditV2(0,0),4 ,5);
            w5.start();
        });
        
        w5 = new DWait(40, e->{
            System.out.println("WAVE Final");
            game.Spawn_Enemy(new Bandit(0, 0),4,10); //9
            game.Spawn_Enemy(new BanditV2(0,0), 5,6);
            System.out.println("how you survival this?");
        });
        
        
        start.start();
    }
    
    
    
    
    
}
