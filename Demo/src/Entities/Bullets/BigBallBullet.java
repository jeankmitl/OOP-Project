/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Bullets;

import Asset.ImgManager;

/**
 *
 * @author USER
 */
public class BigBallBullet extends Bullet{

    public BigBallBullet (int x ,int y, int atk) {
        super(x, y, atk);
        spriteSheet = ImgManager.loadSprite("BigBallBullet");
    }

    @Override
    public void move() {
        x += speed;
    }
    
}
