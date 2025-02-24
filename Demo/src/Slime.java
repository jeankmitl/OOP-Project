import java.awt.Rectangle;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Slime extends Unit {

    private Timer costGenerationTimer;

    public Slime(int row, int col) {
        // Slime doesn't have attack power, health, or defense, so set them to appropriate values
        super(row, col, 100, 0, 1500, 0); // Slime doesn't attack, no cost yet for attack

        try {
            // Ensure the path is correct based on where the asset is located
            spriteSheet = ImageIO.read(getClass().getResource("Asset\\Slime.png")); // Relative path
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Timer to generate cost every 5 seconds (5000 milliseconds)
        costGenerationTimer = new Timer(5000, e -> generateCost()); 
        costGenerationTimer.start();
    }

    // Stop cost generation when the unit is removed
    public void stopGeneratingCost() {
        costGenerationTimer.stop();
    }

    // Method for generating cost over time
    public void generateCost() {
        GamePanel.remainMana += 50; // Add 50 cost every 5 seconds
        if (GamePanel.remainMana > GamePanel.MAX_MANA) {
            GamePanel.remainMana = GamePanel.MAX_MANA; // Cap mana to maximum
        }
        System.out.println("Cost generated: " + GamePanel.remainMana);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X,
                row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }

    @Override
    public boolean isEnermyInfront(List<Enermy> enermies) {
        return false;
    }

    @Override
    public void attack(List<Bullet> bullets) {}

    @Override
    public void update_Frame() {
        super.update_Frame();
    }

}
