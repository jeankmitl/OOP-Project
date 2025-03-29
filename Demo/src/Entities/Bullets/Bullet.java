package Entities.Bullets;

import Asset.ImgManager;
import Asset.VFX;
import Main.GamePanel;
import static Main.GamePanel.GRID_OFFSET_X;
import static Main.GamePanel.GRID_OFFSET_Y;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Bullet {

    protected int x, y;
    protected int atk;
    protected int speed = 10;
    protected BufferedImage spriteSheet;
    protected int currentFrame = 0;
    protected int total_Frame = 4;
    protected int frame_Width = 32, frame_Hight = 32;
    
    public Bullet(int x, int y, int atk) {
        this(x, y, atk, null);
    }
    
    public Bullet(int x, int y, int atk, String spriteName) {
        this.x = x;
        this.y = y;
        this.atk = atk;
        if (spriteName != null) {
            spriteSheet = ImgManager.loadSprite(spriteName);
            total_Frame = spriteSheet.getWidth() / frame_Width;
//            System.out.println(total_Frame);
        }
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
        if (spriteSheet == null) {
            return null;
        }

        int maxFrames = spriteSheet.getWidth() / frame_Width;  // Total frames in sheet
        int safeFrame = Math.min(currentFrame, maxFrames - 1); // Ensure valid frame index

        return spriteSheet.getSubimage(safeFrame * frame_Width, 0, frame_Width, frame_Hight);
    }

    public void updateFrame() {
        currentFrame = (currentFrame + 1) % total_Frame;
    }

    public int getAtk() {
        return atk;
    }
    
    public VFX getHitVfx() {
        return new VFX(getX() - GRID_OFFSET_X, getY() - GRID_OFFSET_Y - 40, "bone_hit");
    }

    public void setFrame_Width(int frame_Width) {
        this.frame_Width = frame_Width;
    }

    public void setFrame_Hight(int frame_Hight) {
        this.frame_Hight = frame_Hight;
    }

}
