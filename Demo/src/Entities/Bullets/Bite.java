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
public class Bite extends Bullet{
    public Bite (int x ,int y,int atk) {
            super(x, y, atk);
            spriteSheet = ImgManager.loadSprite("tooth.png");
    }

    @Override
    public void move() {
        x += speed;
    }
}
