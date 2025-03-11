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
public class PistolBullet extends Bullet {
    public PistolBullet(int x, int y, int atk) {
        super(x, y, atk, "gold_bullet");
        speed = 20;
    }

    @Override
    public void move() {
        x += speed;
    }
}
