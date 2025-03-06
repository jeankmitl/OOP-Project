package Entities.Units;
import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bullet;
import DSystem.DTimer;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Vinewall extends Unit {
    
    public Vinewall(int row, int col) {
        super(row, col, getUNIT_STATS());

        animationTimer = new DTimer(0.25, e -> updateFrame(0.25));
        animationTimer.start();
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
    public void attack(List<Bullet> bullets) {}
    
    public void check_health(){
        if(this.health > 2667){
            this.Status = "idle";
        }else if(this.health > 1333 && this.health <= 2667){
            this.Status = "ATK";
        }else{
            this.Status = "dead";
        }
    }
    
    @Override
    public BufferedImage getBufferedImage() {
        if (this.Status.equals("idle")){
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
        else if(this.Status.equals("ATK")){
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
        else{
            return actiondead.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
    }
    
        @Override
    public void updateFrame(double x) {
        this.check_health();
        currentFrame = (currentFrame + 1) % total_Frame_Idle;
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.VINEWALL_STATS;
    }
    
}
