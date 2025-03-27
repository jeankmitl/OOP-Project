package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class ConfigScreen extends JFrame {

    private boolean isButtonHovered = false;
    private ConfigScreenPanel configScreenPanel;
    private Image background;
    private Rectangle homeBtn;

    public ConfigScreen() {

        configScreenPanel = new ConfigScreenPanel();
        add(configScreenPanel);

        try {
            setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
            background = ImageIO.read(getClass().getResource("/Asset/Img/Background/defense_of_dungeon_wallpaper.png")); // Initialize background
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong image path.");
        }

        homeBtn = new Rectangle(1170, 15, 75, 75);

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
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (homeBtn.contains(e.getPoint())) {
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

        setTitle("Config");
        setSize(1264, 681);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

    }

    private class ConfigScreenPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Image homeIcon = ImgManager.loadIcon("home_Btn");
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(background, 0, 0, 1264, 681, this);
            g2d.drawImage(homeIcon, homeBtn.x, homeBtn.y, homeBtn.width, homeBtn.height, this);
        }
    }
}
