/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DSystem.DWait;
import Entities.Enemies.Bandit;
import Entities.Enemies.BanditV2;
import Entities.Enemies.BanditV3;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author USER
 */
public class stage3 extends GamePanel{
    protected DWait start,w1,w2,w3,w4,w5;
    private StageSelector stage;
    
    public stage3(StageSelector stage) {
        super(34);
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
        start = new DWait(10, e->{//2
            System.out.println("Wave 1");
            Spawn_Enemy(new Bandit(0,0),2,8); //1
            w2.start();
        });
        
        w2 = new DWait(20, e->{
            System.out.println("Wave 2");
            Spawn_Enemy(new Bandit(0,0), 3, 5); //3
            w3.start();
        });
        
        w3 = new DWait(20, e->{
            System.out.println("Wave 3");
            Spawn_Enemy(new Bandit(0,0), 4,3); //5
            Spawn_Enemy(new BanditV2(0, 0),2,5);
            w4.start();
        });
        
        w4 = new DWait(30, e->{
            System.out.println("Wave 4");
            Spawn_Enemy(new Bandit(0,0), 4, 8); //9
            Spawn_Enemy(new BanditV2(0,0), 3, 6);
            Spawn_Enemy(new BanditV3(0,0),2,12);
            w5.start();
        });
        
        w5 = new DWait(40, e->{
            System.out.println("Wave Final"); //14
            Spawn_Enemy(new Bandit(0,0),6,4);
            Spawn_Enemy(new BanditV2(0,0),5,3);
            Spawn_Enemy(new BanditV3(0,0),3,8); 
        });
        start.start();
    }  
}
