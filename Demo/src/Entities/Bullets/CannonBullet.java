/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Bullets;

import Asset.ImgManager;

/**
 *
 * @author anawi
 */
public class CannonBullet extends Bullet {
    int bulletLife = 3;
    
    public CannonBullet(int x ,int y, int atk) {
        super(x, y, atk);
        spriteSheet = ImgManager.loadSprite("cannon_bullet");
        speed = 8;
    }

    @Override
    public void move() {
        x += speed;
    }

    public int getBulletLife() {
        return bulletLife;
    }

    public void useBulletLife() {
        bulletLife--;
    }
    
    
}
