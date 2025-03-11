package Entities.Units;


import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.BeamCleanRow;
import Entities.Bullets.Bullet;
import Asset.Audio;
import DSystem.DTimer;
import Asset.AudioName;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anawi
 */
public class Candles6 extends Unit {
    private final int FAKE_IMMU;
    private boolean isActivating = false;
    
    public Candles6(int row, int col) {
        super(row, col, getUNIT_STATS());
        FAKE_IMMU = getHealth();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }
    
    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        return false;
    }

    @Override
    public void attack(List<Bullet> bullets) {
        bullets.add(new BeamCleanRow(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, row, col));
    }
    
    public void check_health() {
        if (health < FAKE_IMMU && !isActivating) {
            System.out.println("ACTIVATE!!!!!");
            currentFrame = 0;
            isActivating = true;
            Audio.play(AudioName.CANDLE_ACTIVATE);
        }
    }

    @Override
    public BufferedImage getBufferedImage() {
        if (isActivating) {
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
        return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
    }

    @Override
    public void updateFrame() {
        check_health();
        if (isActivating){
            if (currentFrame == total_Frame_ATK - 1) {
                attack(GamePanel.getBullets());
                System.out.println("Kaboom");
                setHealth(0);
            } else {
                currentFrame = (currentFrame + 1) % total_Frame_ATK;
            }
        }
        else {
            currentFrame = (currentFrame + 1) % total_Frame_Idle;
        }
    }
    
    public static UnitStats getUNIT_STATS() {
        return UnitConfig.CANDLES6_STATS;
    }
}
