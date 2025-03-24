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

/**
 *
 * @author USER
 */
public class stage_Tutorial implements EnemySummoner {
    protected DWait start,w1,w2,w3,w4;

    @Override
    public StageStats getSTAGE_STATS() {
        return StageConfig.STAGE_TUTORIAL;
    }
    
    @Override
    public void summonEnemies(GamePanel game) { // maybe 12 enermy
        
        start = new DWait(20, e->{
            System.out.println("WAVE START");
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new Bandit(0, 0));
            w1.start();
        });
        
        w1 = new DWait(30, e->{
            System.out.println("WAVE 1");
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new Bandit(0, 0),2);
            w2.start();
        });
        
        w2 = new DWait(30, e->{
            System.out.println("WAVE 2");
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new Bandit(0, 0),2);
            game.Spawn_Enemy(new Bandit(0, 0),1,8);
            w3.start();
        });
        
        w3 = new DWait(30, e->{
            System.out.println("WAVE 3");
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new Bandit(0, 0),2,8);
            game.Spawn_Enemy(new Bandit(0, 0),2);
            w4.start();
        });
        
        w4 = new DWait(40, e->{
            System.out.println("WAVE Final");
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new Bandit(0, 0),4,10);
            System.out.println("how you survival this?");
        });
        
        
        start.start();
    }
}
