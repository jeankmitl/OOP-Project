package Main;

import Asset.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;

public class ConfigScreen extends JFrame {

    private Rectangle homeBtn, musicBtn, soundBtn;
    private Image background, musicImg, soundImg;
    private boolean isButtonHovered = false;
    private ConfigScreenPanel configScreenPanel;
    private JSlider musicSld, soundSld;

    public ConfigScreen() {

        homeBtn = new Rectangle(1170, 15, 75, 75);
        musicBtn = new Rectangle(462, 240, 160, 160);
        soundBtn = new Rectangle(652, 240, 160, 160);
        configScreenPanel = new ConfigScreenPanel();

        try {
            setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
            background = ImageIO.read(getClass().getResource("/Asset/Img/Background/defense_of_dungeon_wallpaper.png"));
            soundImg = ImgManager.loadIcon((Audio.isSoundEnable()) ? "unmute_sound" : "mute_sound");
            musicImg = ImgManager.loadIcon((Audio.isMusicEnable()) ? "unmute_music" : "mute_music");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong image path.");
        }

        configScreenPanel.setLayout(null);
        add(configScreenPanel);

        musicSld = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (Audio.musicVolume * 100));
        int musicSliderY = musicBtn.y + musicBtn.height + 10;
        musicSld.setBounds(musicBtn.x, musicSliderY, musicBtn.width, 30);
        configScreenPanel.add(musicSld);

        soundSld = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (Audio.musicVolume * 100));
        int soundSliderY = soundBtn.y + soundBtn.height + 10;
        soundSld.setBounds(soundBtn.x, soundSliderY, soundBtn.width, 30);
        configScreenPanel.add(soundSld);

        musicSld.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = musicSld.getValue() / 100f;
                Audio.setMusicVolume(volume);
            }
        });

        soundSld.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = soundSld.getValue() / 100f;
                Audio.setSoundVolume(volume);
            }
        });

        setTitle("Config");
        setSize(1264, 681);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (homeBtn.contains(e.getPoint())) {
                    dispose();
                    Audio.play(AudioName.BUTTON_CLICK);
                    LoadingScreen loadingScreen = new LoadingScreen();
                    SwingWorker<Void, Void> worker = new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            Thread.sleep(1500);
                            return null;
                        }

                        @Override
                        protected void done() {
                            loadingScreen.dispose();
                            new MainMenu();
                        }
                    };
                    worker.execute();
                }
                else if (musicBtn.contains(e.getPoint())) {
                    Audio.play(AudioName.BUTTON_CLICK);
                    if (Audio.isMusicEnable()) {
                        Audio.setMusicEnable(false);
                        Audio.stopMusic();
                        musicImg = ImgManager.loadIcon("mute_music");
                    }
                    else {
                        Audio.setMusicEnable(true);
                        Audio.playMusic("mainMenu");
                        musicImg = ImgManager.loadIcon("unmute_music");
                    }
                    configScreenPanel.repaint();
                }
                else if (soundBtn.contains(e.getPoint())) {
                    Audio.play(AudioName.BUTTON_CLICK);
                    if (Audio.isSoundEnable()) {
                        Audio.setSoundEnable(false);
                        soundImg = ImgManager.loadIcon("mute_sound");
                    }
                    else {
                        Audio.setSoundEnable(true);
                        soundImg = ImgManager.loadIcon("unmute_sound");
                    }
                    configScreenPanel.repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (homeBtn.contains(e.getPoint()) || musicBtn.contains(e.getPoint()) || soundBtn.contains(e.getPoint())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    if (!isButtonHovered) {
                        isButtonHovered = true;
                        Audio.play(AudioName.BUTTON_HOVER);
                    }
                }
                else {
                    setCursor(Cursor.getDefaultCursor());
                    isButtonHovered = false;
                }
            }
        });

    }

    private class ConfigScreenPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Image homeIcon = ImgManager.loadIcon("home_Btn");
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(background, 0, 0, 1264, 681, this);
            g2d.drawImage(homeIcon, homeBtn.x, homeBtn.y, homeBtn.width, homeBtn.height, this);
            g2d.drawImage(musicImg, musicBtn.x, musicBtn.y, musicBtn.width, musicBtn.height, this);
            g2d.drawImage(soundImg, soundBtn.x, soundBtn.y, soundBtn.width, soundBtn.height, this);
        }
    }
}
