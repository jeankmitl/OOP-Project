package Asset;

import Main.GamePanel;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class VFX {
    private int x, y;
    protected BufferedImage spriteSheet;
    protected int currentFrame = 0;
    protected int total_Frame = 4;
    protected int frame_Width = 32, frame_Hight = 32;
    protected int width = 95, height = 95;

    public VFX(int x, int y, String name) {
        this.x = x;
        this.y = y;
        spriteSheet = ImgManager.loadVFX(name);
    }
    
    public int getX() {
        return x + GamePanel.GRID_OFFSET_X;
    }

    public int getY() {
        return y + GamePanel.GRID_OFFSET_Y;
    }

    public int getFrame_Width() {
        return frame_Width;
    }

    public int getFrame_Hight() {
        return frame_Hight;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    
    public BufferedImage getBufferedImage() {
        if (spriteSheet == null) return null;
        return spriteSheet.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
    }
    
    public void updateFrameVFXOnce() {
        if (currentFrame == total_Frame) {
            System.out.println("End");
        } else {
            currentFrame += 1;
        }
    }
    
    public boolean isFinishUpdate() {
        return currentFrame == total_Frame;
    }
    
}
