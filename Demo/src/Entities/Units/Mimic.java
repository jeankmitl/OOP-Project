package Entities.Units;

import Main.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Bullets.Bullet;
//import Entities.Bullets.Bone;
import Asset.Audio;
import DSystem.DTimer;
import Asset.AudioName;
import DSystem.DWait;
//import DSystem.OWait;
import Entities.Bullets.Beta_bullet;
import Entities.Bullets.Bite;
import Entities.Units.Roles.UnitChargeShootable;
import Entities.Units.Roles.UnitShootable;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class Mimic extends Unit {
    
    private DWait crunchSpeed; // cd time when eat again
    private boolean crunch_avalible = true;//check when eat enermy
    private DTimer attackTimer;
    
    public Mimic(int row, int col) {
        super(row, col, getUNIT_STATS());

        attackTimer = new DTimer(1,e->{
            if(this.getHealth() <= 0){
                    attackTimer.stop();
                }
            if(isEnemyInfront(GamePanel.getEnemies())){
                    attack(GamePanel.getBullets());
                    Audio.play(AudioName.FIRE_TINY);
            }
        });
        attackTimer.start();
    }
    
    public void cd_stage(){
        crunchSpeed = new DWait(15,e->{
            crunch_avalible = true;
        });
        crunchSpeed.start();
    }
    
    public void attack(List<Bullet> bullets) {
        bullets.add(new Bite(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30,atk));
    }
    @Override
        public boolean isEnemyInfront(List<Enemy> enermies) {
        for (Enemy enermy : enermies) {
                if (enermy.getRow() == this.getRow() && enermy.getX() < this.getX()+150 && crunch_avalible) {
                    setStatus(ATK_STATUS);
                    crunch_avalible = false;
                    cd_stage();
                    return true;
            }
        }
        setStatus(IDLE_STATUS);
        return false;
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }
    
    public static UnitStats getUNIT_STATS() {
        return UnitConfig.MIMIC_STATS;
    }
}