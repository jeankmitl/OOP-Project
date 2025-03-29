/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Bullets;

/**
 *
 * @author anawi
 */
public class JavaPush extends Bullet {
    public JavaPush(int x, int y, int atk) {
        super(x, y, atk, "java_icon_bullet");
    }

    @Override
    public void move() {
        x += speed;
    }
}
