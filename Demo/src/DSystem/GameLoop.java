package DSystem;


import Main.GamePanel;
import java.util.ArrayList;
import java.util.List;


public class GameLoop extends Thread {
    private static GameLoop gameLoop;
    private static final List<GameLoopListener> listeners = new ArrayList<>();
    private static GameLoopLateListener lateListener;
    
    private final int TARGET_FPS = 60;
    private final double OPTIMAL_TIME = 1_000_000_000.0 / TARGET_FPS;
    private static int fps = 0;
    private boolean stopLoop = false;
    private double deltaTime = 0;
    private static int speedMode = 0;

    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        int frameCount = 0;
        while (!stopLoop) {                
            frameCount++;
            long now = System.nanoTime();
            deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;
            notifyListeners(deltaTime);
            notifyLateListener(deltaTime);
            
            // FPS Calculation (update every second)  
            if (System.currentTimeMillis() - timer > 1000) {
                fps = frameCount;
                frameCount = 0;
                timer += 1000;
//                System.out.println("FPS: " + fps);
            }

            // sleep to maintain fps 💤
            long sleepTime = (long) ((lastTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static GameLoop getInstance() {
        if (gameLoop == null) gameLoop = new GameLoop();
        return gameLoop;
    }

    //Call every 60fps 
    private static void notifyListeners(double deltaTime) {
        try {
            for (int i = 0; i < listeners.size(); i++) {
                GameLoopListener listener = listeners.get(i);
                listener.onUpdate(deltaTime);
                if (speedMode >= 1) listener.onUpdate(deltaTime);
                if (speedMode >= 2) listener.onUpdate(deltaTime);
                if (GamePanel.isHardMode) listener.onUpdate(deltaTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void notifyLateListener(double deltaTime) {
        lateListener.onLateUpdate(deltaTime);
    }
    
    public static void addListener(GameLoopListener listener) {
        if (!isAddedListener(listener)) {
            listeners.add(listener);
        }
    }
    
    public static void removeListener(GameLoopListener listener) {
        if (isAddedListener(listener)) {
            listeners.remove(listener);
        }
    }
    
    public static boolean isAddedListener(GameLoopListener listener) {
        return listeners.contains(listener);
    }
    
    public static void setLateListener(GameLoopLateListener lateListener) {
        GameLoop.lateListener = lateListener;
    }
    
    public static void clearListener() {
        listeners.clear();
    }
    
    public void stopLoop() {
        this.stopLoop = true;
    }
    
    public static int getFps() {
        return fps;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public static int getSpeedMode() {
        return speedMode;
    }

    public static void setSpeedMode(int speedMode) {
        GameLoop.speedMode = speedMode;
    }

    
}
