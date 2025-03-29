/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Asset.ImgManager;
import java.awt.Image;

/**
 *
 * @author anawi
 */
public class StageStats {
    private String title;
    private int difficult;
    private int target;
    private Image background;
    private String bgMusicName;

    public StageStats() {
    }

    public StageStats(String title, int difficult, int target, String background, String bgMusicName) {
        this.title = title;
        this.difficult = difficult;
        this.target = target;
        this.background = ImgManager.loadBG(background);
        this.bgMusicName = bgMusicName;
    }

    public String getTitle() {
        return title;
    }

    public int getDifficult() {
        return difficult;
    }

    public int getTarget() {
        return target;
    }

    public Image getBackground() {
        return background;
    }

    public String getBgMusicName() {
        return bgMusicName;
    }
}
