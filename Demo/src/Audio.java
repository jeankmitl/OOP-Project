import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

    private static final String AUDIO_FOLDER = "src/Sound";
    public static boolean isSoundEnable = true;
    public static boolean isMusicEnable = true;
    
    public static void play(String name) {
        if (!isSoundEnable) return;
        if (!name.contains(".wav")) {
            name += ".wav";
        }
        File file = new File(AUDIO_FOLDER, name);
        new Thread(() -> {
            try (AudioInputStream sound = AudioSystem.getAudioInputStream(file);) {
                Clip clip = AudioSystem.getClip();
                clip.open(sound);
                clip.start();
                Thread.sleep(500);
                clip.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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
                while (clip.isRunning() && isMusicEnable) {
                    Thread.sleep(1000);
                }
                clip.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        Audio.play(AudioName.FIRE);
        Audio.play(AudioName.HIT);
        Audio.play(AudioName.PLANT_PICK_UP);
        Audio.play(AudioName.PLANT_DELETE);
        Audio.play(AudioName.PLANT_PLACE);
    }

}
