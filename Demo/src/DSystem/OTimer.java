package DSystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anawi
 */
public class OTimer {
    private final double delay;
    private double elapsedTime = 0;
    
    public OTimer(double delay) {
        this.delay = delay;
    }
    
    public boolean tick(double deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime >= delay) {
            elapsedTime -= delay;
            return true;
        }
        return false;
    }

    public double getDelay() {
        return delay;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }
}
