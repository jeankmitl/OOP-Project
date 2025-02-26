import java.awt.Rectangle;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class Vinewall extends Unit {

    public Vinewall(int row, int col) {
        super(row, col, 1000, 0, 0, 50);
        try {
            actionIdle = ImageIO.read(getClass().getResource("Asset/Vinewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public void update_Frame() {
        super.update_Frame();
    }

}
