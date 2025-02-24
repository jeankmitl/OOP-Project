import java.awt.Rectangle;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Skeleton extends Unit {

    private Timer attackTimer;

    public Skeleton(int row, int col) {
        super(row, col, 100, 10, 1500, 100);

        try {
            spriteSheet = ImageIO.read(getClass().getResource("Asset\\Skeleton.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        attackTimer = new Timer(1500, e -> {
            if (isEnermyInfront(GamePanel.getEnermies())) {
                attack(GamePanel.getBullets());
            }
        });

        attackTimer.start();
    }

    public void stopAttacking() {
        attackTimer.stop(); // Stop the timer so it won't attack anymore
    }

    @Override
    public boolean isEnermyInfront(List<Enermy> enermies) {
        for (Enermy enermy : enermies) {
            if (enermy.getRow() == this.getRow() && enermy.getX() > this.getX()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void attack(List<Bullet> bullets) {
        bullets.add(new Bullet(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X,
                row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }

}
