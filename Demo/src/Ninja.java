import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ninja extends Enemy {
    
    public Ninja(double x, int row) {
        super(x, row, 80, 0.25);
        try {
            actionIdle = ImageIO.read(getClass().getResource("Asset/Ninja.png"));
            actionATK = ImageIO.read(getClass().getResource("Asset/NinjaATK.png"));
            actionDead = ImageIO.read(getClass().getResource("Asset/NinjaDie.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attack(Unit unit) {
        this.currentFrame = 0;
        this.Status = "ATK";
        unit.takeDamage(20);
        new Thread(() -> {try{ //delay Time
            Thread.sleep(800);
            this.currentFrame = 0;
            this.Status ="idle";
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        }).start();
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }

    @Override
    public BufferedImage getBufferedImage() {
        if (this.Status.equals("idle")){
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);}
        else if(this.Status.equals("ATK")){
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }else{
            return actionDead.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
    }

    @Override
    public void updateFrame() {
        super.updateFrame();
        /*if (this.Status.equals("idle")){
            currentFrame = (currentFrame + 1) % total_Frame;
        }
        else if(this.Status.equals("ATK")) {
            currentFrame = (currentFrame + 1) % total_Frame;
        }
        else{
            currentFrame = (currentFrame + 1) % total_Frame;
        }*/
    }
    
    
}
