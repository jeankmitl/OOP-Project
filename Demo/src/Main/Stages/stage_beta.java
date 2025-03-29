/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.Stages;

import Asset.Audio;
import Asset.AudioName;
import DSystem.DWait;
import Entities.Enemies.*;
import Main.EnemySummoner;
import Main.GamePanel;
import Main.StageConfig;
import Main.StageStats;

public class stage_beta implements EnemySummoner {
    protected DWait start,w1,w2,w3,w4,w5,w6,w7,w8,w9,w10;
    
    @Override
    public StageStats getSTAGE_STATS() {
        return StageConfig.STAGE_BETA;
    }
    
    @Override
    public void summonEnemies(GamePanel game) {
        start = new DWait(10, e->{
            System.out.println("Endless Wave");
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new Bandit(0, 0),99,8);
            w1.start();
        });
        w1 = new DWait(20, e->{
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new BanditV2(0, 0),99);
            w2.start();
        });
        w2 = new DWait(30, e->{
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new BanditV3(0, 0),99,12);
            w3.start();
        });
        w3 = new DWait(30, e->{
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new Ninja(0, 0),99,14);
            w4.start();
        });
        w4 = new DWait(40, e->{
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new LittleRedHood(0, 0),99);
            w5.start();
        });
        w5 = new DWait(40, e->{
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new RobotMonoWheel(0, 0),99,18);
            w6.start();
        });
        w6 = new DWait(40, e->{
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new Sorcerer(0, 0),99,20);
            w7.start();
        });
        w7 = new DWait(40, e->{
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new RCBomber(0, 0),99);
            w8.start();
        });
        w8 = new DWait(40, e->{
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new IShowSpeed(0, 0),99,20);
        });
        start.start();
    }
}
