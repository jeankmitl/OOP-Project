/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.Stages;

import DSystem.DWait;
import Entities.Enemies.Bandit;
import Entities.Enemies.BanditV2;
import Entities.Enemies.BanditV3;
import Entities.Enemies.LittleRedHood;
import Entities.Enemies.Ninja;
import Entities.Enemies.Sorcerer;
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
public class stage6 implements EnemySummoner { // Red hood appear
    protected DWait start,w1,w2,w3,w4,w5,w6,w7;
    
    @Override
    public StageStats getSTAGE_STATS() {
        return StageConfig.STAGE_6;
    }

    @Override
    public void summonEnemies(GamePanel game) {
        start = new DWait(10, e->{ //2
            game.Spawn_Enemy(new Bandit(0,0), 2);
            w1.start();
        });
        w1 = new DWait(20, e->{ //4
            System.out.println("Wave 1");
            game.Spawn_Enemy(new Bandit(0,0), 2);
            game.Spawn_Enemy(new BanditV2(0,0), 1);
            game.Spawn_Enemy(new Sorcerer(0,0));
            w2.start();
        });
        w2 = new DWait(35, e->{ //8
            System.out.println("Wave 2");
            game.Spawn_Enemy(new Bandit(0,0), 2,8);
            game.Spawn_Enemy(new BanditV2(0,0), 2);
            game.Spawn_Enemy(new BanditV3(0,0), 1);
            game.Spawn_Enemy(new Sorcerer(0,0));
            game.Spawn_Enemy(new Ninja(0,0),1);
            w3.start();
        });
        w3 = new DWait(35, e->{ //8
            System.out.println("Wave 3");
            game.Spawn_Enemy(new Bandit(0,0), 3,8);
            game.Spawn_Enemy(new BanditV2(0,0), 2);
            game.Spawn_Enemy(new BanditV3(0,0), 1);
            game.Spawn_Enemy(new LittleRedHood(0,0),1);
            game.Spawn_Enemy(new Sorcerer(0,0),2);
            w4.start();
        });
        w4 = new DWait(35, e->{ //8
            System.out.println("Wave 4");
            game.Spawn_Enemy(new Bandit(0,0), 2,6);
            game.Spawn_Enemy(new BanditV2(0,0), 2,8);
            game.Spawn_Enemy(new BanditV3(0,0), 2);
            game.Spawn_Enemy(new LittleRedHood(0,0),2);
            game.Spawn_Enemy(new Sorcerer(0,0),2);
            game.Spawn_Enemy(new Ninja(0,0),2,11);
            w5.start();
        });
        w5 = new DWait(40, e->{ //8
            System.out.println("Wave 5");
            game.Spawn_Enemy(new Bandit(0,0), 1);
            game.Spawn_Enemy(new BanditV2(0,0), 2,8);
            game.Spawn_Enemy(new BanditV3(0,0), 4,8);
            game.Spawn_Enemy(new LittleRedHood(0,0),2,12);
            game.Spawn_Enemy(new Sorcerer(0,0),2,6);
            game.Spawn_Enemy(new Ninja(0,0),1);
            w6.start();
        });
        w6 = new DWait(40, e->{ //8
            System.out.println("Final Wave");
            game.Spawn_Enemy(new BanditV2(0,0), 2,8);
            game.Spawn_Enemy(new BanditV3(0,0), 4,8);
            game.Spawn_Enemy(new LittleRedHood(0,0),3,12);
            game.Spawn_Enemy(new Sorcerer(0,0),2,8);
            game.Spawn_Enemy(new Ninja(0,0),4,8);
//            w6.start();
        });
        start.start();
    }
    
    
}
