/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.ImgManager;
import java.awt.image.BufferedImage;

/**
 *
 * @author anawi
 */
public class UnitSpriteSheets {
    private String idle;
    private String atk;
    private String dead;
    private BufferedImage actionIdle;
    private BufferedImage actionAtk;
    private BufferedImage actionDead;
    private BufferedImage profileImg;

    public UnitSpriteSheets(String idle, String atk) {
        this(idle, atk, null);
    }

    public UnitSpriteSheets(String idle, String atk, String dead) {
        this.idle = idle;
        this.atk = atk;
        this.dead = dead;
        this.actionIdle = ImgManager.loadSprite(idle);
        this.actionAtk = ImgManager.loadSprite(atk);
        this.profileImg = actionIdle.getSubimage(0, 0, 32, 32);
        if (dead != null) this.actionDead = ImgManager.loadSprite(dead);
    }

    public String getIdle() {
        return idle;
    }

    public String getAtk() {
        return atk;
    }

    public String getDead() {
        return dead;
    }

    public BufferedImage getActionIdle() {
        return actionIdle;
    }

    public BufferedImage getActionAtk() {
        return actionAtk;
    }

    public BufferedImage getActionDead() {
        return actionDead;
    }

    public BufferedImage getProfileImg() {
        return profileImg;
    }
}
