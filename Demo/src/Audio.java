import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

    private static final String AUDIO_FOLDER = "src/Sound";
    private static final ExecutorService soundPool = Executors.newFixedThreadPool(4);
    public static boolean isSoundEnable = true;
    public static boolean isMusicEnable = false;
    
    public static void play(String name) {
        if (!isSoundEnable) return;
        if (!name.contains(".wav")) {
            name += ".wav";
        }
        File file = new File(AUDIO_FOLDER, name);
        soundPool.execute(() -> {
            try (AudioInputStream sound = AudioSystem.getAudioInputStream(file);) {
                Clip clip = AudioSystem.getClip();
                clip.open(sound);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public static void shutDownMusic() {
        soundPool.shutdown();
    }

}
