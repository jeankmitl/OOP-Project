/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author anawi
 */
public abstract class ImgManager {
    public static File toFile(String path, String name) {
        if (!name.contains(".png")) {
            name += ".png";
        }
        File file = new File(path, name);
        if (!file.exists()) {
            System.out.println(path + " not exists!");
        } 
        return file;
    }
    
    private static BufferedImage loadBufferedImage(String path, String name) {
        try {
            return ImageIO.read(toFile(path, name));
        } catch (IOException ex) {
            Logger.getLogger(ImgManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static Image loadImage(String path, String name) {
        try {
            if (!name.contains(".png")) {
                name += ".png";
            }
            return new ImageIcon(path + "/" + name).getImage();
        } catch (Exception ex) {
            Logger.getLogger(ImgManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static BufferedImage loadSprite(String name) {
        return loadBufferedImage(Paths.SPRITE_SHEET_PATH, name);
    }
    
    public static BufferedImage loadVFX(String name) {
        return loadBufferedImage(Paths.VFX_PATH, name);
    }
    
    public static Image loadBG(String name) {
        return loadImage(Paths.BG_PATH, name);
    }
    
    public static Image loadIcon(String name) {
        return loadImage(Paths.ICONS_PATH, name);
    }
    
}
