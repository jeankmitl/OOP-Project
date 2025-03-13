package DSystem;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DTimer implements GameLoopListener {
    private double delay;
    private final ActionListener listener;
    private double elapsedTime = 0;
    private boolean isDoubleSpeed = false;
    
    public DTimer(double delay, ActionListener listener) {
        this.delay = delay;
        this.listener = listener;
    }

    @Override
    public void onUpdate(double deltaTime) {
        elapsedTime += deltaTime;
        if (isDoubleSpeed) {
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "DTimeLoop Tick"));
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "DTimeLoop Tick"));
        } else if (elapsedTime >= delay) {
            elapsedTime -= delay;
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "DTimeLoop Tick"));
        }
    }
    
    public void start() {
        elapsedTime = 0;
        GameLoop.addListener(this);
    }
    
    public void stop() {
        GameLoop.removeListener(this);
    }
    
    public boolean isRunning() {
        return GameLoop.isAddedListener(this);
    }

    public double getDelay() {
        return delay;
    }
    
    public void setDelay(double delay) {
        this.delay = delay;
    }

    public void setDoubleSpeed(boolean isDoubleSpeed) {
        this.isDoubleSpeed = isDoubleSpeed;
    }
}
