package Entities.Units;
import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bullet;
import DSystem.DTimer;
import Entities.Units.Roles.UnitInvisible;
import Entities.Units.Roles.UnitReflectable;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Kaniwall extends Unit {
    
    public Kaniwall(int row, int col) {
        super(row, col, getUNIT_STATS());

        animationTimer = new DTimer(0.25, e -> updateFrame(0.25));
        animationTimer.start();
    }

    @Override
    public void attack(List<Bullet> bullets) {}
    
    public void check_health(){
        if(this.health > 2667){
            setStatus(IDLE_STATUS, false);
        }else if(this.health > 1333 && this.health <= 2667){
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
    public void updateFrame(double x) {
        this.check_health();
        super.updateFrame();
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.KANIWALL_STATS;
    }
}
