package Entities.Units;

import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bullet;
import DSystem.DTimer;
import Entities.Entity;
import java.awt.Rectangle;
import java.util.List;
import java.awt.image.BufferedImage;

public abstract class Unit extends Entity {

    //Stats
    protected int cost;
    protected double cooldown;
    
    protected int row, col;
    
    protected DTimer animationTimer;
    
    public final UnitStats UNIT_STATS = null;
    
    public abstract void attack(List<Bullet> bullets);
    
    public Unit(int row, int col, UnitStats unitStats) {
        super(unitStats);
        this.row = row;
        this.col = col;
        this.cost = unitStats.getCost();
        this.cooldown = unitStats.getCooldown();
    }

    @Override
    public int getX() {
        return col * GamePanel.CELL_WIDTH;
    }

    @Override
    public int getY() {
        return row * GamePanel.CELL_HEIGHT;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }
    
    public boolean isEnemyInfront(List<Enemy> enermies) {
        return false;
    }

    public static UnitStats getUNIT_STATS() throws NoSuchMethodException {
        throw new NoSuchMethodException("Make sure to return their unit STATS");
    }
}
