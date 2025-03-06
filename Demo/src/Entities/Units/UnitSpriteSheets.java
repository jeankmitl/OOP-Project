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
public class UnitSpriteSheets extends Entities.EntitySpriteSheets {
    private BufferedImage profileImg;
    private BufferedImage allStatsIcons;
    private final int WH = 23;

    public UnitSpriteSheets(String idle, String atk) {
        this(idle, atk, null);
    }

    public UnitSpriteSheets(String idle, String atk, String dead) {
        super(idle, atk, dead);
        this.allStatsIcons = ImgManager.loadSprite("all_stats_icons");
        this.profileImg = getActionIdle().getSubimage(0, 0, 32, 32);
    }
    
    public BufferedImage getProfileImg() {
        return profileImg;
    }

    public BufferedImage getStatsIcon(int i) {
        BufferedImage statsIcon = allStatsIcons.getSubimage(i * WH, 0, WH, WH);
        return statsIcon;
    }
}
