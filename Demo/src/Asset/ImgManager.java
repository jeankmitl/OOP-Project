/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author anawi
 */
public abstract class ImgManager {

    /**
     * Internal loader: reads an image from the classpath (works both in IDE
     * and when running from a JAR).
     */
    private static BufferedImage loadBufferedImage(String classpathDir, String name) {
        if (!name.contains(".png") && !name.contains(".PNG")
                && !name.contains(".gif") && !name.contains(".jpg")) {
            name += ".png";
        }
        String resourcePath = classpathDir + name;
        try (InputStream is = ImgManager.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.out.println("Resource not found: " + resourcePath);
                return null;
            }
            return ImageIO.read(is);
        } catch (IOException ex) {
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
        return loadBufferedImage(Paths.BG_PATH, name);
    }

    public static Image loadIcon(String name) {
        return loadBufferedImage(Paths.ICONS_PATH, name);
    }

}
