/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Bullets;

/**
 *
 * @author anawi
 */
public class TurtleBullet extends Bullet {
    private int row;
    private int col;
    
    public TurtleBullet(int x, int y, int atk, int row, int col) {
        super(x, y, atk);
        this.row = row;
        this.col = col;
    }
    
    @Override
    public void move() {
        x += speed;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
