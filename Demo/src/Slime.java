import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Slime extends Unit {

    private Timer costGenerationTimer;

    public Slime(int row, int col) {
        super(row, col, 100, 0, 1500, 0); // Slime doesn't have attack power, health, or defense

        try {
            spriteSheet = ImageIO.read(getClass().getResource("Asset\\Slime.png")); // Load slime image
        } catch (IOException e) {
            e.printStackTrace();
        }

        costGenerationTimer = new Timer(5000, e -> generateCost()); // Generate cost every second
        costGenerationTimer.start();
    }

    // Stop cost generation when the unit is removed
    public void stopGeneratingCost() {
        costGenerationTimer.stop();
    }

    // Method for generating cost over time
    public void generateCost() {
        GamePanel.remainMana += 50; // Add 5 cost every second
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

}
