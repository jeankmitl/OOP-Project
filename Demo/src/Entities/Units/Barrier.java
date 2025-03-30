/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import static Entities.Units.Kaniwall.getUNIT_STATS;
import Entities.Units.Roles.OnBack;
import Entities.Units.Roles.UnitExtraFieldAvailable;
import java.awt.image.BufferedImage;

/**
 *
 * @author anawi
 */
public class Barrier extends Unit implements UnitExtraFieldAvailable, OnBack {

    public Barrier(int row, int col) {
        super(row, col, getUNIT_STATS());
    }
    
    public static UnitStats getUNIT_STATS() {
        return UnitConfig.BARRIER_STATS;
    }
    
    @Override
    public void getUnitFromField(Unit unit) {
        
    }
    
    public void check_health(){
        if(this.health > 1332){
            setStatus(IDLE_STATUS, false);
        }else if(this.health > 666 && this.health <= 1332){
            setStatus(ATK_STATUS, false);
        }else{
            setStatus(DEAD_STATUS, false);
        }
    }
    
    @Override
    public BufferedImage getBufferedImage() {
        if (this.status.equals("idle")){
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
        else if(this.status.equals("ATK")){
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
        else{
            return actiondead.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
    }
    
    @Override
    public void updateFrame() {
        this.check_health();
        super.updateFrame();
    }
    
}
