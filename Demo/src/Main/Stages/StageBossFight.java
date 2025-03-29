/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.Stages;

import Asset.Audio;
import Asset.AudioName;
import DSystem.DWait;
import Entities.Enemies.Bandit;
import Entities.Enemies.SongChinWu;
import Main.EnemySummoner;
import Main.GamePanel;
import Main.StageConfig;
import Main.StageStats;
import static Main.GamePanel.GRID_OFFSET_X;

/**
 *
 * @author anawi
 */
public class StageBossFight implements EnemySummoner {
    protected DWait start, swordsWave;
    private SongChinWu boss;
    
    @Override
    public StageStats getSTAGE_STATS() {
        return StageConfig.STAGE_BOSS;
    }

    @Override
    public void summonEnemies(GamePanel game) {
        start = new DWait(7, e -> {//2
            System.out.println("The Last Fight is BEGIN!");
            boss = new SongChinWu(1300-GRID_OFFSET_X, 2);
            game.spawnBossOnly(boss, 2); //1
            
            Audio.playMusic(AudioName.BOSS_THEME);
        });
        
        start.start();
    }
    
    
}
