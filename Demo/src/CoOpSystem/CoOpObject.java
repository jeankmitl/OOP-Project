/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoOpSystem;

import Asset.VFX;
import Entities.Bullets.Bullet;
import Entities.Enemies.Enemy;
import Entities.Units.Unit;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author anawi
 */
public class CoOpObject implements Serializable {
    private List<Unit> units;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<VFX> vfxs;

    public CoOpObject() {
        
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public List<VFX> getVfxs() {
        return vfxs;
    }

    public void setVfxs(List<VFX> vfxs) {
        this.vfxs = vfxs;
    }
    
    
    
}
