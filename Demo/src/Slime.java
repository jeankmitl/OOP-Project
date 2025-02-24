import java.awt.Rectangle;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Slime extends Unit{

    private Timer ProductTimer;

    public Slime(int row,int col) {
        super(row, col, 100, 10, 1500, 100);

        try {
            spriteSheet = ImageIO.read(getClass().getResource("Asset\\Slime.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Why i can't found setter & Getter Remain Mana
        /*ProductTimer = new Timer(1500, e -> {
            mana

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X,
                row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    } */
}