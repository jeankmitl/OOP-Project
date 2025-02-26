import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sorcerer extends Enemy {
    
    public Sorcerer(double x, int row) {
        super(x, row, 80, 0.25);
        try {
            actionIdle = ImageIO.read(getClass().getResource("Asset/Sorcerer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attack(Unit unit) {
        unit.takeDamage(40);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }

}
