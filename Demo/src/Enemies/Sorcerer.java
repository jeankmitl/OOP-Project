package Enemies;

import Asset.ImgManager;
import Main.GamePanel;
import Units.Unit;
import java.awt.Rectangle;

public class Sorcerer extends Enemy {
    
    public Sorcerer(double x, int row) {
        super(x, row, 80, 0.25);
        actionIdle = ImgManager.loadSprite("Sorcerer");
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
