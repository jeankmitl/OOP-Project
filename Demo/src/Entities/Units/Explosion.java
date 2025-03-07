/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bone;
import Entities.Bullets.Bullet;
import Asset.Audio;
import DSystem.DTimer;
import Asset.AudioName;
import DSystem.DWait;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Explosion extends Candles6 {
    
    private DWait reload_spell; //call when place to set stage to ready to explosion 1 time use
    private DTimer explosion; // call when enermy hit

    public Explosion(int row, int col) {
        super(row, col);
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.EXPLOSION_STATS;
    }
}
