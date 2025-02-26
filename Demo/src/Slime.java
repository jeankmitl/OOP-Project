import java.awt.Rectangle;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Slime extends Unit {

    private Timer costGenerationTimer;

    public Slime(int row, int col) {
        super(row, col, 50, 0, 0, 50);
        try {
            actionIdle = ImageIO.read(getClass().getResource("Asset/Slime.png"));
            actionATK = ImageIO.read(getClass().getResource("Asset/SlimeGenerate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        costGenerationTimer = new Timer(10000, e -> generateCost()); 
        costGenerationTimer.start();
    }

    public void stopGeneratingCost() {
        costGenerationTimer.stop();
    }

    // Add 50 cost every 10 seconds
    public void generateCost() {
        GamePanel.remainMana += 50;
        if (GamePanel.remainMana > GamePanel.MAX_MANA) {
            GamePanel.remainMana = GamePanel.MAX_MANA;
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
    public void updateFrame() {
        super.updateFrame();
    }

}
