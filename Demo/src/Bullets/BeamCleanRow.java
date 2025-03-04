package Bullets;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anawi
 */
public class BeamCleanRow extends Bullet {
    
    private int row;
    
    public BeamCleanRow(int x, int y, int row) {
        super(x, y);
        this.row = row;
    }
    
    @Override
    public void move() {
        x += speed;
    }

    public int getRow() {
        return row;
    }
    
}
