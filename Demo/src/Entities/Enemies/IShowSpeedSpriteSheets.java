/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;

import Asset.ImgManager;
import java.awt.image.BufferedImage;

/**
 *
 * @author sarin
 */
public class IShowSpeedSpriteSheets extends EnemySpriteSheets {
    
    private BufferedImage actionIdleRage;
    private BufferedImage actionAtkRage;
    public IShowSpeedSpriteSheets(String idle, String atk, String idleRage, String atkRage) {
        super(idle, atk);
        this.actionAtkRage = ImgManager.loadSprite(atkRage);
        this.actionIdleRage = ImgManager.loadSprite(idleRage);
    }

    public BufferedImage getActionIdleRage() {
        return actionIdleRage;
    }

    public BufferedImage getActionAtkRage() {
        return actionAtkRage;
    }
}
