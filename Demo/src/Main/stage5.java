/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DSystem.DWait;
import Entities.Enemies.*;
import Entities.Enemies.BanditV2;
import Entities.Enemies.Sorcerer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author USER
 */
public class stage5 extends GamePanel{ // Sorcerer appear
    protected DWait start,w1,w2,w3,w4,w5,w6,w7;
    private StageSelector stage;
    
    public stage5(StageSelector stage) {
        super(55);
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
            Spawn_Enemy(new Bandit(0,0),2,8); //2
            w2.start();
        });
        
        w1 = new DWait(25, e->{ //4
            System.out.println("Wave 1");
            Spawn_Enemy(new Bandit(0,0), 2, 8);
            Spawn_Enemy(new BanditV2(0,0), 1);
            Spawn_Enemy(new Sorcerer(0,0),1);
            w2.start();
        });
        w2 = new DWait(25, e->{ //7
            System.out.println("Wave 2");
            Spawn_Enemy(new Bandit(0,0), 2, 8);
            Spawn_Enemy(new BanditV2(0,0), 2);
            Spawn_Enemy(new Sorcerer(0,0),2,10);
            Spawn_Enemy(new Ninja(0, 0),1);
            w3.start();
        });
        w3 = new DWait(30, e->{//9
            System.out.println("Wave 3");
            Spawn_Enemy(new Bandit(0,0), 2, 8);
            Spawn_Enemy(new BanditV2(0,0), 3,8);
            Spawn_Enemy(new BanditV3(0,0), 2,12);
            Spawn_Enemy(new Ninja(0, 0),2,8);
            w4.start();
        });
        w4 = new DWait(30, e->{//12
            System.out.println("Wave 4");
            Spawn_Enemy(new Bandit(0,0), 3, 6);
            Spawn_Enemy(new BanditV2(0,0), 3,8);
            Spawn_Enemy(new BanditV3(0,0), 2,10);
            Spawn_Enemy(new Sorcerer(0,0), 2);
            Spawn_Enemy(new Ninja(0, 0),2,8);
            w5.start();
        });
        w5 = new DWait(30, e->{//12
            System.out.println("Wave 5");
            Spawn_Enemy(new Bandit(0,0), 1);
            Spawn_Enemy(new BanditV2(0,0), 4,8);
            Spawn_Enemy(new BanditV3(0,0), 3,10);
            Spawn_Enemy(new Sorcerer(0,0), 1);
            Spawn_Enemy(new Ninja(0, 0),3,8);
            w6.start();
        });
        w6 = new DWait(40, e->{//15
            System.out.println("Final Wave");
            Spawn_Enemy(new Bandit(0,0), 2);
            Spawn_Enemy(new BanditV2(0,0), 4,8);
            Spawn_Enemy(new BanditV3(0,0), 3,12);
            Spawn_Enemy(new Sorcerer(0,0), 2,8);
            Spawn_Enemy(new Ninja(0, 0),2);
        });
        
        start.start();
    }
    
    
}
