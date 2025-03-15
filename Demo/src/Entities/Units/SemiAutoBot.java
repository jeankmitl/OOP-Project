/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;

import Asset.Audio;
import Asset.AudioName;
import DSystem.DRepeat;
import DSystem.DWait;
import Entities.Bullets.Bullet;
import Entities.Bullets.PistolBullet;
import Entities.Bullets.SemiAutoBullet;
import Entities.Units.Roles.UnitShootable;
import Main.GamePanel;
import java.util.List;

/**
 *
 * @author anawi
 */
public class SemiAutoBot extends Unit implements UnitShootable {
    public SemiAutoBot(int row, int col) {
        super(row, col, getUNIT_STATS());
    }

    public static UnitStats getUNIT_STATS() {
        return UnitConfig.SEMI_AUTO_BOT_STATS;
    }
    
    @Override
    public void shoot(List<Bullet> bullets) {
        new DRepeat(0.25, 3, true, e -> {
            setStatus("ATK");
            Audio.play(AudioName.FIRE_TINY);
            bullets.add(new SemiAutoBullet(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, atk));
        }, e -> {
            new DWait(0.5, eee -> setStatus("idle")).start();
        }).start();
        
//        Audio.play(AudioName.FIRE_TINY);
//        bullets.add(new SemiAutoBullet(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, atk));
//        new DWait(0.25, e -> {
//            setStatus("ATK");
//            Audio.play(AudioName.FIRE_TINY);
//            bullets.add(new SemiAutoBullet(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, atk));
//            new DWait(0.25, ee -> {
//                setStatus("ATK");
//                Audio.play(AudioName.FIRE_TINY);
//                bullets.add(new SemiAutoBullet(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30, atk));
//                new DWait(0.25, eee -> setStatus("idle")).start();
//            }).start();
//        }).start();
        
    }
}
