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
            actionATK = ImageIO.read(getClass().getResource("Asset/VinewallCracker.png"));
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
    public BufferedImage getBufferedImage() {
        if (this.getHealth() <= 1333) {
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
        else if (this.getHealth() <= 2667) {
            if (!stageChange) {
                try {
                    actionIdle = ImageIO.read(getClass().getResource("Asset/VinewallCrack.png"));
                    stageChange = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
        else {
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
    }

}
