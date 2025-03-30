package Entities.Units;


import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.BeamCleanRow;
import Entities.Bullets.Bullet;
import Asset.Audio;
import DSystem.DTimer;
import Asset.AudioName;
import Entities.Bullets.BeamCleanRowPurple;
import Entities.Units.Roles.UnitIgnoreFieldAvailable;
import Entities.Units.Roles.UnitInvisible;
import Entities.Units.Roles.UnitTriggerable;
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
public class CandlesExplosion extends Unit implements UnitInvisible, UnitTriggerable, UnitIgnoreFieldAvailable {
    private boolean isActivating = false;
    
    public CandlesExplosion(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public void attack(List<Bullet> bullets) {
        bullets.add(new BeamCleanRowPurple(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, row, col));
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
        return UnitConfig.CANDLES_EXPLOSION_STATS;
    }

    @Override
    public void triggerWhenPlace() {
        isActivating = true;
    }

    @Override
    public void getUnitFromField(Unit unit) {
    }

    @Override
    public void insersectEnemy(Enemy enemy) {
    }
}
