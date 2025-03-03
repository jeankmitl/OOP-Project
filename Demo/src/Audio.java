import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class Audio {

    private static final String AUDIO_FOLDER = "src/Sound";
    private static final ExecutorService soundPool = Executors.newFixedThreadPool(3);
    public static boolean isSoundEnable = true;
    public static boolean isMusicEnable = false;
    
    public static void play(String name) {
        if (!isSoundEnable) return;
        if (!name.contains(".wav")) {
            name += ".wav";
        }
        
        String nname = name;
        try {
            soundPool.execute(() -> {
                File file = new File(AUDIO_FOLDER, nname);
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
        File file = new File(AUDIO_FOLDER, name);
        new Thread(() -> {
            try (AudioInputStream sound = AudioSystem.getAudioInputStream(file);) {
                Clip clip = AudioSystem.getClip();
                clip.open(sound);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public static void shutDownMusic() {
        soundPool.shutdown();
    }
}
