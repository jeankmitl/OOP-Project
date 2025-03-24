/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.Stages;

import Asset.Audio;
import Asset.AudioName;
import DSystem.DWait;
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
    protected DWait start;
    
    @Override
    public StageStats getSTAGE_STATS() {
        return StageConfig.STAGE_BOSS;
    }

    @Override
    public void summonEnemies(GamePanel game) {
        start = new DWait(5, e -> {//2
            System.out.println("The Last Fight is BEGIN!");
            game.spawnEnemy(new SongChinWu(1300-GRID_OFFSET_X, 2), 1); //1
            
            Audio.isMusicEnable = true;
            Audio.playMusic(AudioName.BOSS_THEME);
//            game.Spawn_Enemy(Enemy enemy, int num, int delay);
        });
        start.start();
    }
}
