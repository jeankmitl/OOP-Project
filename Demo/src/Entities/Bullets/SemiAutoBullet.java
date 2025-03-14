/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Bullets;

/**
 *
 * @author anawi
 */
public class SemiAutoBullet extends Bullet {
    public SemiAutoBullet(int x, int y, int atk) {
        super(x, y, atk, "mini_lazer_bullet");
        speed = 20;
    }

    @Override
    public void move() {
        x += speed;
    }
}
