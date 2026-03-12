package Asset;

import java.io.*;
import java.util.concurrent.*;
import javax.sound.sampled.*;

public abstract class Audio {

    private static final ExecutorService soundPool = Executors.newFixedThreadPool(3);
    private static boolean isSoundEnable = true;
    private static boolean isMusicEnable = true;
    private static Clip musicClip;
    private static String musicFile = "";
    private static FloatControl musicVolumeControl, soundVolumeControl;
    public static float musicVolume = 0.5f, soundVolume = 0.5f;

    public static void play(String name) {
        if (!isSoundEnable) return;
        if (!name.contains(".wav")) {
            name += ".wav";
        }

        String nname = name;
        try {
            soundPool.execute(() -> {
                try (InputStream raw = Audio.class.getResourceAsStream(Paths.SOUND_PATH + nname)) {
                    if (raw == null) {
                        System.out.println("Sound not found: " + Paths.SOUND_PATH + nname);
                        return;
                    }
                    try (AudioInputStream sound = AudioSystem.getAudioInputStream(
                            new BufferedInputStream(raw))) {
                        Clip clip = AudioSystem.getClip();
                        clip.open(sound);
                        clip.start();
                        soundVolumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        setSoundVolume(soundVolume);
                        clip.addLineListener(event -> {
                            if (event.getType() == LineEvent.Type.STOP) {
                                clip.close();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
        try (InputStream raw = Audio.class.getResourceAsStream(Paths.SOUND_PATH + name)) {
            if (raw == null) {
                System.out.println("Music not found: " + Paths.SOUND_PATH + name);
                return;
            }
            try (AudioInputStream sound = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(raw))) {
                musicClip = AudioSystem.getClip();
                musicClip.open(sound);
                musicClip.start();
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                musicVolumeControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                setMusicVolume(musicVolume);
            }
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

    public static void setMusicVolume(float volume) {
        if (musicVolumeControl != null) {
            float dB = 20f * (float) Math.log10(volume);
            musicVolumeControl.setValue(dB);
            musicVolume = volume;
        }
    }

    public static void setSoundVolume(float volume) {
        if (soundVolumeControl != null) {
            float dB = 20f * (float) Math.log10(volume);
            soundVolumeControl.setValue(dB);
            soundVolume = volume;
        }
    }
}
