/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Asset.Audio;
import Asset.AudioName;
import DSystem.DTimer;
import DSystem.DWait;
import DSystem.OTimer;
import DSystem.OWait;
import Entities.Enemies.*;
import Entities.Enemies.Enemy;
import static Main.GamePanel.GRID_OFFSET_X;
import static Main.GamePanel.enemies;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 *
 * @author USER
 */
public class stage_Tutorial extends GamePanel{
    protected DWait start,w1,w2,w3,w4;
    private StageSelector stage;

    public stage_Tutorial(StageSelector stage) {
        super(14);
        this.stage = stage;
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(homeBtn.contains(e.getPoint())){
                    stage.loadStage("Back");
                }
            }
        });
    }
    
    @Override
    public void Spawn_Enemy(Enemy enemy,int num,int delay){
        Random random = new Random();
        for (int i=0;i<num;i++){
            new DWait(i*delay, e->{
               int randomBandit = 2; //fix Because Stage 1
                    enemies.add(enemy.createNew(1280 - GRID_OFFSET_X + random.nextInt(10) * 10, randomBandit));
                    System.out.println("Spawn Success");
                }).start();
           }
    }
    
    @Override
    public void summonEnemies() { // maybe 12 enermy
        
        start = new DWait(30, e->{
            System.out.println("WAVE START");
            Audio.play(AudioName.NEXT_SUMMON);
            Spawn_Enemy(new Bandit(0, 0));
            w1.start();
        });
        
        w1 = new DWait(30, e->{
            System.out.println("WAVE 1");
            Audio.play(AudioName.NEXT_SUMMON);
            Spawn_Enemy(new Bandit(0, 0),2);
            w2.start();
        });
        
        w2 = new DWait(30, e->{
            System.out.println("WAVE 2");
            Audio.play(AudioName.NEXT_SUMMON);
            Spawn_Enemy(new Bandit(0, 0),2);
            Spawn_Enemy(new Bandit(0, 0),1,8);
            w3.start();
        });
        
        w3 = new DWait(30, e->{
            System.out.println("WAVE 3");
            Audio.play(AudioName.NEXT_SUMMON);
            Spawn_Enemy(new Bandit(0, 0),2,8);
            Spawn_Enemy(new Bandit(0, 0),2);
            w4.start();
        });
        
        w4 = new DWait(40, e->{
            System.out.println("WAVE Final");
            Audio.play(AudioName.NEXT_SUMMON);
            Spawn_Enemy(new Bandit(0, 0),4,10);
            System.out.println("how you survival this?");
        });
        
        
        start.start();
    }
}
