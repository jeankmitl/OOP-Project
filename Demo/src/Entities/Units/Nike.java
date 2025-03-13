/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Units;
import Asset.Audio;
import Asset.VFX;
import Entities.Enemies.Enemy;
import Entities.Units.Roles.UnitTriggerable;
import static Entities.Units.Skeleton.getUNIT_STATS;
import Main.GamePanel;

/**
 *
 * @author anawi
 */
public class Nike extends Unit implements UnitTriggerable {
    public Nike(int row, int col) {
        super(row, col, getUNIT_STATS());
    }
    
    public static UnitStats getUNIT_STATS() {
        return UnitConfig.NIKE_STATS;
    }

    @Override
    public void triggerWhenPlace() {
        for (Enemy enemy: GamePanel.getEnemies()) {
            enemy.takeDamage(atk);
        }
        VFX vfx = new VFX(-GamePanel.GRID_OFFSET_X, -GamePanel.GRID_OFFSET_Y, "blight_vfx");
        vfx.setWidth(1280);
        vfx.setHeight(720);
        GamePanel.getVfxs().add(vfx);
        Audio.play("beam_clean_row");
        Audio.play("kill1");
        Audio.play("kill2");
        setHealth(0);
    }
}
