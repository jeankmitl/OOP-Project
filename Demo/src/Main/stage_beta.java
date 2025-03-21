/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DSystem.DWait;
import Entities.Enemies.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author USER
 */
public class stage_beta extends GamePanel{
    protected DWait start;
    private StageSelector stage;

    public stage_beta(StageSelector stage) {
        super(9999);
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
    public void summonEnemies() {
        start = new DWait(10, e->{
            System.out.println("Endless Wave");
            super.Spawn_Enemy(new Bandit(0, 0),99);
            super.Spawn_Enemy(new BanditV2(0, 0),99);
            super.Spawn_Enemy(new BanditV3(0, 0),99);
            super.Spawn_Enemy(new Ninja(0, 0),99);
            super.Spawn_Enemy(new LittleRedHood(0, 0),99);
            super.Spawn_Enemy(new Sorcerer(0, 0),99);
            super.Spawn_Enemy(new RCBomber(0, 0),99);
            super.Spawn_Enemy(new RobotMonoWheel(0, 0),99);
            super.Spawn_Enemy(new Tank(0, 0),99);
        });
        start.start();
    }
    
    
    
}
