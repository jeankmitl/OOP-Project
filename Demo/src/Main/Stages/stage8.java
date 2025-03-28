/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.Stages;

import DSystem.DWait;
import Entities.Enemies.*;
import Entities.Enemies.BanditV2;
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
public class stage8 implements EnemySummoner {
    protected DWait start,w1,w2,w3,w4,w5,w6,w7;
    
    @Override
    public StageStats getSTAGE_STATS() {
         return StageConfig.STAGE_8;
    }

    @Override
    public void summonEnemies(GamePanel game) {//85
        start = new DWait(10, e->{//3
            game.Spawn_Enemy(new Bandit(0,0), 2);
            game.Spawn_Enemy(new Sorcerer(0,0));
            w1.start();
        });
        
        w1 = new DWait(35, e->{ //5
            System.out.println("Wave 1");
            game.Spawn_Enemy(new Bandit(0,0), 2);
            game.Spawn_Enemy(new BanditV2(0,0), 1);
            game.Spawn_Enemy(new Sorcerer(0,0));
            game.Spawn_Enemy(new IShowSpeed(0,0));
            w2.start();
        });
        
        w2 = new DWait(50, e->{ //10
            System.out.println("Wave 2");
            game.Spawn_Enemy(new Bandit(0,0), 2,8);
            game.Spawn_Enemy(new BanditV2(0,0), 3,8);
            game.Spawn_Enemy(new BanditV3(0,0), 1);
            game.Spawn_Enemy(new Sorcerer(0,0),2,8);
            game.Spawn_Enemy(new RCBomber(0,0),2,5);
            w3.start();
        });
        
        w3 = new DWait(50, e->{ //14
            System.out.println("Wave 3");
            game.Spawn_Enemy(new Bandit(0,0), 1);
            game.Spawn_Enemy(new BanditV2(0,0), 3,8);
            game.Spawn_Enemy(new BanditV3(0,0), 2,8);
            game.Spawn_Enemy(new RobotMonoWheel(0, 0),1);
            game.Spawn_Enemy(new RCBomber(0, 0),3,6);
            game.Spawn_Enemy(new Sorcerer(0,0),2);
            game.Spawn_Enemy(new Ninja(0,0),2);
            w4.start();
        });
        
        w4 = new DWait(50, e->{ //12
            System.out.println("Wave 4");
            game.Spawn_Enemy(new Bandit(0,0), 1);
            game.Spawn_Enemy(new BanditV2(0,0), 3,8);
            game.Spawn_Enemy(new BanditV3(0,0), 3,6);
            game.Spawn_Enemy(new LittleRedHood(0,0),1);
            game.Spawn_Enemy(new IShowSpeed(0,0),1);
            game.Spawn_Enemy(new Sorcerer(0,0),1);
            game.Spawn_Enemy(new RCBomber(0, 0),2,2);
            w5.start();
        });
        
        w5 = new DWait(60, e->{ //12
            System.out.println("Wave 5");
            game.Spawn_Enemy(new RCBomber(0, 0),3,2);
            game.Spawn_Enemy(new RobotMonoWheel(0, 0),2,2);
            game.Spawn_Enemy(new IShowSpeed(0, 0),2,5);
            game.Spawn_Enemy(new Ninja(0, 0),3,6);
            game.Spawn_Enemy(new Sorcerer(0, 0),2,6);
            w6.start();
        });
        
        w6 = new DWait(60, e->{ //14
            System.out.println("Wave 6");
            game.Spawn_Enemy(new BanditV2(0,0), 2,8);
            game.Spawn_Enemy(new BanditV3(0,0), 3,8);
            game.Spawn_Enemy(new LittleRedHood(0,0),2,8);
            game.Spawn_Enemy(new Sorcerer(0,0),2,6);
            game.Spawn_Enemy(new Ninja(0,0),2,8);
            game.Spawn_Enemy(new RCBomber(0, 0),2,5);
            game.Spawn_Enemy(new IShowSpeed(0, 0),1);
            w7.start();
        });
        
        w7 = new DWait(60, e->{//15
            System.out.println("Final Wave");
            game.Spawn_Enemy(new LittleRedHood(0,0),3,12);
            game.Spawn_Enemy(new BanditV3(0,0), 2,8);
            game.Spawn_Enemy(new Ninja(0,0), 2);
            game.Spawn_Enemy(new RCBomber(0,0), 3,5);
            game.Spawn_Enemy(new Sorcerer(0, 0), 3,6);
            game.Spawn_Enemy(new IShowSpeed(0, 0), 2,8);
        });
        start.start();
    }
}
