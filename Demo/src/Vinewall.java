import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class Vinewall extends Unit {

    private boolean stageChange = false;

    public Vinewall(int row, int col) {
        super(row, col, 4000, 0, 0, 50);
        try {
            actionIdle = ImageIO.read(getClass().getResource("Asset/Vinewall.png"));
            actionATK = ImageIO.read(getClass().getResource("Asset/VinewallCrack.png"));
            actiondead = ImageIO.read(getClass().getResource("Asset/VinewallCracker.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
