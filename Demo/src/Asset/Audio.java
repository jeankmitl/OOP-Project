package Asset;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public abstract class Audio {

    private static final ExecutorService soundPool = Executors.newFixedThreadPool(3);
    private static boolean isSoundEnable = true;
    private static boolean isMusicEnable = true;
    private static Clip musicClip;
    private static String musicFile = "";
    
    public static void play(String name) {
        if (!isSoundEnable) return;
        if (!name.contains(".wav")) {
            name += ".wav";
        }
        
        String nname = name;
        try {
            soundPool.execute(() -> {
                File file = new File(Paths.SOUND_PATH, nname);
                try (AudioInputStream sound = AudioSystem.getAudioInputStream(file)) {
                    Clip clip = AudioSystem.getClip();
                    clip.open(sound);
                    clip.start();
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close(); // Close to reduce Thread
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            //Ignore because after shutdown Game
        }
    }

    public static void playMusic(String name) {
        if (!isMusicEnable) return;
        if (!name.contains(".wav")) {
            name += ".wav";
        }
        if (musicFile.equals(name)) {
            return;
        } else {
            musicFile = name;
        }
        stopMusic();
        File file = new File(Paths.SOUND_PATH, name);
        try (AudioInputStream sound = AudioSystem.getAudioInputStream(file);) {
            musicClip = AudioSystem.getClip();
            musicClip.open(sound);
            musicClip.start();
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicFile = "";
            musicClip.stop();
            musicClip.close();
        }
    }

    public static boolean isSoundEnable() {
        return isSoundEnable;
    }

    public static boolean isMusicEnable() {
        return isMusicEnable;
    }

    public static void setSoundEnable(boolean isSoundEnable) {
        Audio.isSoundEnable = isSoundEnable;
    }

    public static void setMusicEnable(boolean isMusicEnable) {
        Audio.isMusicEnable = isMusicEnable;
    }
}
