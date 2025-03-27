package DSystem;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anawi
 */
public class DWait implements GameLoopListener {
    private final double delay;
    private final ActionListener listener;
    private double elapsedTime = 0;
    private boolean isCalled = false;
    
    public DWait(double delay, ActionListener listener) {
        this.delay = delay;
        this.listener = listener;
    }

    @Override
    public void onUpdate(double deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime >= delay && !isCalled) {
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "DTimeLoop Tick"));
            GameLoop.removeListener(this);
            isCalled = true;
        }
    }
    
    public void start() {
        elapsedTime = 0;
        isCalled = false;
        GameLoop.addListener(this);
    }
}
