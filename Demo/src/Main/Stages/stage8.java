/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.Stages;

import DSystem.DWait;
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
    public void summonEnemies(GamePanel game) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
