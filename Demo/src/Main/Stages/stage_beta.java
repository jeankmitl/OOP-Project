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
    protected DWait start;
    
    @Override
    public StageStats getSTAGE_STATS() {
        return StageConfig.STAGE_BETA;
    }
    
    @Override
    public void summonEnemies(GamePanel game) {
        start = new DWait(2, e->{
            System.out.println("Endless Wave");
            Audio.play(AudioName.NEXT_SUMMON);
            game.Spawn_Enemy(new Bandit(0, 0),99);
            game.Spawn_Enemy(new BanditV2(0, 0),99);
            game.Spawn_Enemy(new BanditV3(0, 0),99);
            game.Spawn_Enemy(new Ninja(0, 0),99);
            game.Spawn_Enemy(new LittleRedHood(0, 0),99);
            game.Spawn_Enemy(new Sorcerer(0, 0),99);
            game.Spawn_Enemy(new RCBomber(0, 0),99);
            game.Spawn_Enemy(new RobotMonoWheel(0, 0),99);
            game.Spawn_Enemy(new Tank(0, 0),99);
        });
        start.start();
    }
}
