/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.Stages;

import DSystem.DWait;
import Entities.Enemies.Bandit;
import Entities.Enemies.BanditV2;
import Entities.Enemies.BanditV3;
import Main.EnemySummoner;
import Main.GamePanel;
import Main.StageConfig;
import Main.StageStats;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author USER
 */
public class stage3 implements EnemySummoner {
    protected DWait start,w1,w2,w3,w4,w5;
    
    @Override
    public StageStats getSTAGE_STATS() {
        return StageConfig.STAGE_3;
    }

    @Override
    public void summonEnemies(GamePanel game) {
        start = new DWait(10, e->{//2
            System.out.println("Wave 1");
            game.Spawn_Enemy(new Bandit(0,0),2,8); //1
            w2.start();
        });
        
        w2 = new DWait(20, e->{
            System.out.println("Wave 2");
            game.Spawn_Enemy(new Bandit(0,0), 3, 5); //3
            w3.start();
        });
        
        w3 = new DWait(20, e->{
            System.out.println("Wave 3");
            game.Spawn_Enemy(new Bandit(0,0), 4,3); //5
            game.Spawn_Enemy(new BanditV2(0, 0),2,5);
            w4.start();
        });
        
        w4 = new DWait(30, e->{
            System.out.println("Wave 4");
            game.Spawn_Enemy(new Bandit(0,0), 4, 8); //9
            game.Spawn_Enemy(new BanditV2(0,0), 3, 6);
            game.Spawn_Enemy(new BanditV3(0,0),2,12);
            w5.start();
        });
        
        w5 = new DWait(40, e->{
            System.out.println("Wave Final"); //14
            game.Spawn_Enemy(new Bandit(0,0),6,4);
            game.Spawn_Enemy(new BanditV2(0,0),5,3);
            game.Spawn_Enemy(new BanditV3(0,0),3,8); 
        });
        start.start();
    }  
}
