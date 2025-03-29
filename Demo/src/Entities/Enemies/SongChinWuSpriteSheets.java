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
public class SongChinWuSpriteSheets extends EnemySpriteSheets {
    private BufferedImage actionIdleNoSword;
    private BufferedImage actionIdleWithSword;
    private BufferedImage actionIdleNoSwordMotivated;
    private BufferedImage actionDropSword;
    private BufferedImage actionWalk;

    public SongChinWuSpriteSheets(String idleNoSword, String idleWithSword, String idleNoSwordMotivated, String dropSword, String walk) {
        super(walk, null, null);
        this.actionIdleNoSword = ImgManager.loadSprite(idleNoSword);
        this.actionIdleWithSword = ImgManager.loadSprite(idleWithSword);
        this.actionIdleNoSwordMotivated = ImgManager.loadSprite(idleNoSwordMotivated);
        this.actionDropSword = ImgManager.loadSprite(dropSword);
        this.actionWalk = ImgManager.loadSprite(walk);
    }

    public BufferedImage getActionIdleNoSword() {
        return actionIdleNoSword;
    }

    public BufferedImage getActionIdleWithSword() {
        return actionIdleWithSword;
    }

    public BufferedImage getActionIdleNoSwordMotivated() {
        return actionIdleNoSwordMotivated;
    }

    public BufferedImage getActionDropSword() {
        return actionDropSword;
    }

    public BufferedImage getActionWalk() {
        return actionWalk;
    }
}

