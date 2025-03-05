package Entities.Bullets;

import Main.GamePanel;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Bullet {

    protected int x, y;
    protected int speed = 10;
    protected BufferedImage spriteSheet;
    protected int currentFrame = 0;
    protected int total_Frame = 4;
    protected int frame_Width = 32, frame_Hight = 32;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void move();

    public int getX() {
        return x + GamePanel.GRID_OFFSET_X;
    }

    public int getY() {
        return y + GamePanel.GRID_OFFSET_Y;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), 30, 30);
    }

    public BufferedImage getBufferedImage() {
        return spriteSheet.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
    }

    public void updateFrame() {
        currentFrame = (currentFrame + 1) % total_Frame;
    }

}
