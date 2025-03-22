/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.Stages;

import DSystem.DWait;
import Entities.Enemies.Bandit;
import Entities.Enemies.BanditV2;
import Entities.Enemies.BanditV3;
import Entities.Enemies.Ninja;
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
public class stage4 implements EnemySummoner { // Ninja appear
    protected DWait start,w1,w2,w3,w4,w5,w6,w7;

    @Override
    public StageStats getSTAGE_STATS() {
        return StageConfig.STAGE_4;
    }

    @Override
    public void summonEnemies(GamePanel game) {
        start = new DWait(10, e->{//2
            System.out.println("Wave 1");
            game.Spawn_Enemy(new Bandit(0,0),2,8); //1
            w2.start();
        });
        
        w2 = new DWait(30, e->{
            System.out.println("Wave 2");
            game.Spawn_Enemy(new Bandit(0,0), 2, 5); //3
            game.Spawn_Enemy(new BanditV2(0,0),1);
            w3.start();
        });
        
        w3 = new DWait(30, e->{
            System.out.println("Wave 3"); //7
            game.Spawn_Enemy(new Bandit(0,0), 4, 8);
            game.Spawn_Enemy(new Ninja(0,0));
            game.Spawn_Enemy(new BanditV2(0,0), 2, 6);
            w4.start();
        });
        
        w4 = new DWait(40, e->{
            System.out.println("Wave4"); //11
            game.Spawn_Enemy(new Bandit(0,0), 6, 8);
            game.Spawn_Enemy(new Ninja(0,0), 2);
            game.Spawn_Enemy(new BanditV2(0,0), 3, 12);
            w5.start();
        });
        
        w5 = new DWait(50 ,e->{
            System.out.println("Wave 5"); // 12
            game.Spawn_Enemy(new Bandit(0,0), 4, 8);
            game.Spawn_Enemy(new BanditV2(0,0), 4, 9);
            game.Spawn_Enemy(new Ninja(0,0), 2);
            game.Spawn_Enemy(new BanditV3(0,0), 2,12);
            w6.start();
        });
        
        w6 = new DWait(60, e->{ //12
            System.out.println("Final Wave");
            game.Spawn_Enemy(new Bandit(0,0),2,8);
            game.Spawn_Enemy(new BanditV2(0,0), 4, 9);
            game.Spawn_Enemy(new Ninja(0,0), 2, 10);
            game.Spawn_Enemy(new BanditV3(0,0), 2, 12);
        });
        
        start.start();
    }
    
    
    
    
    
}
