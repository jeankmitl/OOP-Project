package Entities.Bullets;



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
    private int col;
    
    public BeamCleanRow(int x, int y, int row, int col) {
        super(x, y, 0);
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
