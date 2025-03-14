/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DSystem.DTimer;
import DSystem.DWait;
import DSystem.OTimer;
import DSystem.OWait;
import Entities.Enemies.*;
import Entities.Enemies.Enemy;
import static Main.GamePanel.GRID_OFFSET_X;
import static Main.GamePanel.enemies;
import java.util.Random;

/**
 *
 * @author USER
 */
public class stage_Tutorial extends GamePanel{
    protected DWait start,w1,w2,w3,w4;
    
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
//    public void banndit(int num){
//        System.out.println("bandit");
//        int delay = 10;
//        // future maybe fix delay?
//        for (int i=0;i<num;i++){
//            new DWait(i*delay, e->{
//               int randomBandit = 2; 
//               enemies.add(new Bandit(1280-GRID_OFFSET_X+random.nextInt(10)*10, randomBandit));
//                System.out.println("spawn success"); 
//            }).start();
//        }
//    }
//    
//    public void banndit(int num,int delay){
//        // future maybe fix delay?
//        for (int i=0;i<num;i++){
//            new DWait(i*delay, e->{
//               int randomBandit = 2;
//                enemies.add(new Bandit(1280-GRID_OFFSET_X+random.nextInt(10)*10, randomBandit));
//                System.out.println("spawn success"); 
//            }).start();
//        }
//    }
    
    @Override
    public void summonEnemies() { // maybe 12 enermy
        
        start = new DWait(30, e->{
            System.out.println("WAVE START");
            Spawn_Enemy(new Bandit(0, 0));
            w1.start();
        });
        
        w1 = new DWait(30, e->{
            System.out.println("WAVE 1");
            Spawn_Enemy(new Bandit(0, 0),2);
            w2.start();
        });
        
        w2 = new DWait(30, e->{
            System.out.println("WAVE 2");
            Spawn_Enemy(new Bandit(0, 0),2);
            Spawn_Enemy(new Bandit(0, 0),1,8);
            w3.start();
        });
        
        w3 = new DWait(30, e->{
            System.out.println("WAVE 3");
            Spawn_Enemy(new Bandit(0, 0),2,8);
            Spawn_Enemy(new Bandit(0, 0),2);
            w4.start();
        });
        
        w4 = new DWait(40, e->{
            System.out.println("WAVE Final");
            Spawn_Enemy(new Bandit(0, 0),4,10);
            System.out.println("how you survival this?");
        });
        
        
        start.start();
    }
}
