/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DSystem.DWait;
import Entities.Enemies.Bandit;
import Entities.Enemies.BanditV2;
import Entities.Enemies.BanditV3;
import Entities.Enemies.Ninja;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author USER
 */
public class stage4 extends GamePanel{
    protected DWait start,w1,w2,w3,w4,w5,w6,w7;
    private StageSelector stage;

    public stage4(StageSelector stage) {
        super(45);
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
        
        w2 = new DWait(30, e->{
            System.out.println("Wave 2");
            Spawn_Enemy(new Bandit(0,0), 2, 5); //3
            Spawn_Enemy(new BanditV2(0,0),1);
            w3.start();
        });
        
        w3 = new DWait(30, e->{
            System.out.println("Wave 3"); //7
            Spawn_Enemy(new Bandit(0,0), 4, 8);
            Spawn_Enemy(new Ninja(0,0));
            Spawn_Enemy(new BanditV2(0,0), 2, 6);
            w4.start();
        });
        
        w4 = new DWait(40, e->{
            System.out.println("Wave4"); //11
            Spawn_Enemy(new Bandit(0,0), 6, 8);
            Spawn_Enemy(new Ninja(0,0), 2);
            Spawn_Enemy(new BanditV2(0,0), 3, 12);
            w5.start();
        });
        
        w5 = new DWait(50 ,e->{
            System.out.println("Wave 5"); // 12
            Spawn_Enemy(new Bandit(0,0), 4, 8);
            Spawn_Enemy(new BanditV2(0,0), 4, 9);
            Spawn_Enemy(new Ninja(0,0), 2);
            Spawn_Enemy(new BanditV3(0,0), 2,12);
            w6.start();
        });
        
        w6 = new DWait(60, e->{ //12
            System.out.println("Final Wave");
            Spawn_Enemy(new Bandit(0,0),2,8);
            Spawn_Enemy(new BanditV2(0,0), 4, 9);
            Spawn_Enemy(new Ninja(0,0), 2, 10);
            Spawn_Enemy(new BanditV3(0,0), 2, 12);
        });
        
        start.start();
    }
    
    
    
    
    
}
