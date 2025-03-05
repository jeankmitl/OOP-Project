package Entities.Enemies;

import Asset.ImgManager;
import DSystem.DTimer;
import Main.GamePanel;
import Entities.Units.Unit;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Sorcerer extends Enemy {
    
    public Sorcerer(double x, int row) {
        super(x, row, 80, 0.25);
        actionIdle = ImgManager.loadSprite("Sorcerer");
        animationTimer = new DTimer(0.25, e -> updateFrame(0.25));
        animationTimer.start();
    }

    @Override
    public void attack(Unit unit) {
        unit.takeDamage(40);
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
