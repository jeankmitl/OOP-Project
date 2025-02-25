import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bandit extends Enermy {
    
    public Bandit(double x, int row) {
        super(x, row, 100, 0.25);
        try {
            actionIdle = ImageIO.read(getClass().getResource("Asset/Ninja.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attack(Unit unit) {
        unit.takeDamage(20);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }

}
