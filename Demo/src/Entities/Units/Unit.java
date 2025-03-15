package Entities.Units;

import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bullet;
import DSystem.DTimer;
import Entities.Entity;
import Entities.Units.Roles.*;
import java.awt.Rectangle;
import java.util.List;
import java.awt.image.BufferedImage;

public abstract class Unit extends Entity {

    //Stats
    protected int cost;
    protected double cooldown;
    protected int row, col;
    
    public final UnitStats UNIT_STATS = null;
    
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
        for (Enemy enermy : enermies) {
            if (enermy.getRow() == this.getRow() && enermy.getX() > this.getX()) {
                setStatus(ATK_STATUS);
                return true;
            }
        }
        setStatus(IDLE_STATUS, false);
        return false;
    }

    public static UnitStats getUNIT_STATS() throws NoSuchMethodException {
        throw new NoSuchMethodException("Make sure to return their unit STATS");
    }

    @Override
    public void attackSystem() {
        if (this instanceof UnitGeneratable) {
            ((UnitGeneratable)this).generateByAtkSpeed();
            attackWait.reset();
        } else if (this instanceof UnitShootable) {
            if (this instanceof UnitChargeShootable) {
                if (isEnemyInfront(GamePanel.getEnemies())) {
                    if (!isFirstAttack) {
                        ((UnitShootable)this).shoot(GamePanel.getBullets());
                    }
                    isFirstAttack = false;
                    attackWait.reset();
                } else {
                    isFirstAttack = true;
                }
            } else {
                if (isEnemyInfront(GamePanel.getEnemies())) {
                    ((UnitShootable)this).shoot(GamePanel.getBullets());
                    attackWait.reset();
                }
            }
        }
    }
    
   
}
