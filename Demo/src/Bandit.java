import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bandit extends Enemy {
    
    public Bandit(double x, int row) {
        super(x, row, 100, 0.25);
        try {
            actionIdle = ImageIO.read(getClass().getResource("Asset/Bandit.png"));
            //actionATK = ImageIO.read(getClass().getResource("Asset/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        animationTimer = new DTimer(0.25, e -> updateFrame(0.25));
        animationTimer.start();
    }
    
    @Override
    public void attack(Unit unit) {
        unit.takeDamage(20);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }

    @Override
    public BufferedImage getBufferedImage() {
        return super.getBufferedImage();
    }
    
    @Override
    public void updateFrame(double x) {
        super.updateFrame(x);
    }
}
