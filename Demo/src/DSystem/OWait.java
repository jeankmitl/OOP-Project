package DSystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anawi
 */
public class OWait {
    private final double delay;
    private double elapsedTime = 0;
    private boolean stop = false;
    
    public OWait(double delay) {
        this.delay = delay;
    }
    
    public boolean tick(double deltaTime) {
        if (!stop) {
            elapsedTime += deltaTime;
            if (elapsedTime >= delay) {
                elapsedTime = 0;
                stop = true;
                return true;
            }
        }
        return false;
    }

    public boolean isStop() {
        return stop;
    }

    public void reset() {
        stop = false;
    }
    
}
