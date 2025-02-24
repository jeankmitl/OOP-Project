/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.Rectangle;
import java.util.List;
import java.awt.image.BufferedImage;

/**
 *
 * @author sarin
 */
public abstract class Unit {
    protected int row, col;
    protected int health;
    protected int atk;
    protected int atkSpeed;
    protected int cost;
    
    // animation
    protected BufferedImage spriteSheet;
    protected int currentFrame = 0;
    protected int total_Frame = 4;
    protected int frame_Width = 32, frame_Hight = 32;

    public Unit(int row, int col, int health, int atk, int atkSpeed, int cost) {
        this.row = row;
        this.col = col;
        this.health = health;
        this.atk = atk;
        this.atkSpeed = atkSpeed;
        this.cost = cost;
    }
    
    public Unit getPlant() {
        return this;
    }
    
    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getX() {
        return col * GamePanel.CELL_WIDTH;
    }

    public int getY() {
        return row * GamePanel.CELL_HEIGHT;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    public BufferedImage getBufferedImage() {
        return spriteSheet.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
    }
    
    public void update_Frame() {
        currentFrame = (currentFrame + 1) % total_Frame;
    }

    public abstract boolean isEnermyInfront(List<Enermy> enermies);
    public abstract void attack(List<Bullet> bullets);
    public abstract Rectangle getBounds();
}
