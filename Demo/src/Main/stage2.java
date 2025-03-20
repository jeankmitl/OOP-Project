/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DSystem.DWait;
import Entities.Enemies.Bandit;
import Entities.Enemies.BanditV2;
import Entities.Enemies.Enemy;
import static Main.GamePanel.GRID_OFFSET_X;
import static Main.GamePanel.enemies;
import java.util.Random;

/**
 *
 * @author USER
 */
public class stage2 extends GamePanel{
    protected DWait start,w1,w2,w3,w4,w5;
    
    public stage2(int target) {
        super(31);
    }

    @Override
    public void Spawn_Enemy(Enemy enemy, int num, int delay) {
        Random random = new Random();
        for (int i=0;i<num;i++){
            new DWait(i*delay, e->{
               int randomBandit = random.nextInt(3)+1; //fix Because Stage 1
                    enemies.add(enemy.createNew(1280 - GRID_OFFSET_X + random.nextInt(10) * 10, randomBandit));
                    System.out.println("Spawn Success");
                }).start();
           }
    }

    @Override
    public void summonEnemies() {
        start = new DWait(20, e->{
            System.out.println("WAVE START");
            Spawn_Enemy(new Bandit(0, 0)); // 1
            w1.start();
        });
        
        w1 = new DWait(30, e->{
            System.out.println("WAVE 1");
            Spawn_Enemy(new Bandit(0, 0),2); //2
            w2.start();
        });
        
        w2 = new DWait(30, e->{
            System.out.println("WAVE 2");
            Spawn_Enemy(new Bandit(0, 0),2);
            Spawn_Enemy(new Bandit(0, 0),1,8); //4
            Spawn_Enemy(new BanditV2(0,0), 1);
            w3.start();
        });
        
        w3 = new DWait(30, e->{
            System.out.println("WAVE 3");
            Spawn_Enemy(new Bandit(0, 0),2,8);
            Spawn_Enemy(new Bandit(0, 0),2); //7
            Spawn_Enemy(new BanditV2(0,0), 3,6);
            w4.start();
        });
        
        w4 = new DWait(40, e->{
            System.out.println("WAVE 4");
            Spawn_Enemy(new Bandit(0, 0),4,10); // 8
            Spawn_Enemy(new BanditV2(0,0),4 ,5);
        });
        
        w5 = new DWait(60, e->{
            System.out.println("WAVE Final");
            Spawn_Enemy(new Bandit(0, 0),4,10); //9
            Spawn_Enemy(new BanditV2(0,0), 5,6);
            System.out.println("how you survival this?");
        });
        
        
        start.start();
    }
    
    
    
    
    
}
