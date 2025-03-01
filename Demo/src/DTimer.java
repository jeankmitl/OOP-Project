
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DTimer implements GameLoopListener {
    private final double delay;
    private final ActionListener listener;
    private double elapsedTime = 0;
    
    public DTimer(double delay, ActionListener listener) {
        this.delay = delay;
        this.listener = listener;
    }

    @Override
    public void onUpdate(double deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime >= delay) {
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
    
}
