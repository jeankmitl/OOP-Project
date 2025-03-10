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
public class Beta_bullet extends Bullet{

    public Beta_bullet (int x ,int y) {
        super(x, y);
        spriteSheet = ImgManager.loadSprite("BetaBullet");
    }

    @Override
    public void move() {
        x += speed;
    }
    
}
