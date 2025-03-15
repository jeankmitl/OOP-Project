/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author anawi
 */
public class DRepeat implements GameLoopListener {
    private final double delay;
    private final int repeat;
    private final boolean isDoBefore;
    private final ActionListener listener;
    private final ActionListener callBackListener;
    private double elapsedTime = 0;
    private int repeatTime = 0;
    
    public DRepeat(double delay, int repeat, ActionListener listener) {
        this(delay, repeat, false, listener, null);
    }
    
    public DRepeat(double delay, int repeat, boolean isDoBefore, ActionListener listener) {
        this(delay, repeat, isDoBefore, listener, null);
    }
    
    public DRepeat(double delay, int repeat, boolean isDoBefore, ActionListener listener, ActionListener callback) {
        this.delay = delay;
        this.repeat = repeat;
        this.isDoBefore = isDoBefore;
        this.listener = listener;
        this.callBackListener = callback;
    }

    @Override
    public void onUpdate(double deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime >= delay) {
            elapsedTime -= delay;
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "DTimeLoop Tick"));
            if (++repeatTime > repeat - 1) {
                GameLoop.removeListener(this);
                if (callBackListener != null) {
                    callBackListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "DTimeLoop Tick"));
                }
            }
        }
    }
    
    public void start() {
        if (isDoBefore) {
            elapsedTime = delay;
        } else {
            elapsedTime = 0;
        }
        repeatTime = 0;
        GameLoop.addListener(this);
    }
}
