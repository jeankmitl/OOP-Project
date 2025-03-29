/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Bullets;

/**
 *
 * @author sarin
 */
public class Slash extends Bullet {
    public Slash(int x, int y, int atk) {
        super(x, y, atk, "KnightWalker_slash");
        setFrame_Hight(48);
        setFrame_Width(48);
    }

    @Override
    public void move() {
        this.x -= 5; // Move left towards allies
        updateFrame();
    }
}
