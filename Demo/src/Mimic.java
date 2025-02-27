import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Mimic extends Unit{
    
    private Timer crunchSpeed;
    
    public Mimic(int row, int col) {
        super(row, col, 100, 10, 1500, 100);
        
        try {
            actionIdle = ImageIO.read(getClass().getResource("Asset/Mimic.png"));
            //actionATK = ImageIO.read(getClass().getResource("Asset/SkeletonThrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        crunchSpeed = new Timer(1000,e->{
            if(isEnemyInfront(GamePanel.getEnemies())){
                attack(GamePanel.getBullets());
                Audio.play(AudioName.FIRE_TINY);
            }
            });
        
        crunchSpeed.start();
    }
    ///BETA/// tester //I think i goona rework sprite
    @Override
    public void attack(List<Bullet> bullets) {
        bullets.add(new Bone(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30));
    }

    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        for (Enemy enermy : enermies) {
            if (enermy.getRow() == this.getRow() && enermy.getX() > this.getX()) {
                this.Status = "ATK";
                this.currentFrame = 0;
                return true;
            }}
            this.Status = "idle";
            this.currentFrame = 0;
            return false;}

    @Override
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }
    
    @Override // deaw gae
    public BufferedImage getBufferedImage() {
        return super.getBufferedImage();
        /*if (this.Status.equals("idle")){
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);}
        else {
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }*/
    }

    @Override
    public void updateFrame() {
        super.updateFrame();
        /*if (this.Status.equals("idle")){
            currentFrame = (currentFrame + 1) % total_Frame_Idle;
        }
        else {
            currentFrame = (currentFrame + 1) % total_Frame_ATK;
        }*/
    }
    
    
}
