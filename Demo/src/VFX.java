
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anawi
 */
public class VFX {
    private int x, y;
    protected BufferedImage spriteSheet;
    protected int currentFrame = 0;
    protected int total_Frame = 4;
    protected int frame_Width = 32, frame_Hight = 32;

    public VFX(int x, int y, String name) {
        this.x = x;
        this.y = y;
        if (!name.contains(".png")) {
            name += ".png";
        }
        System.out.println(name);
        try {
            spriteSheet = ImageIO.read(getClass().getResource("VFX/" + name));
            System.out.println(frame_Width / spriteSheet.getWidth());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        return new Rectangle(getX(), getY(), 30, 30);
    }
    
    public BufferedImage getBufferedImage() {
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
