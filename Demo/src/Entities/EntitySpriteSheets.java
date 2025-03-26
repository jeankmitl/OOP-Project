/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Asset.ImgManager;
import java.awt.image.BufferedImage;

/**
 *
 * @author anawi
 */
public class EntitySpriteSheets {
    private String idle;
    private String atk;
    private String dead;
    private BufferedImage actionIdle;
    private BufferedImage actionAtk;
    private BufferedImage actionDead;

    public EntitySpriteSheets() {
        this(null, null, null);
    }
        
    public EntitySpriteSheets(String idle, String atk, String dead) {
        this.idle = idle;
        this.atk = atk;
        this.dead = dead;
        this.actionIdle = ImgManager.loadSprite(idle != null ? idle : "needIdle");
        this.actionAtk = ImgManager.loadSprite(atk != null ? atk : "needAtk");
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
    
    public void setIdle(String idle) {
        this.idle = idle;
    }
}
