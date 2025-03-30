package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
import CoOpSystem.CoOpFrame;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class MainMenu extends JFrame {

    private Rectangle start, twoP, config, exit, menuBar, logo, option2P, local, keyboard;
    private Image menu, title, background, option2PImg;
    private boolean isButtonHovered = false;
    private boolean isShowOption2P = false;
    private boolean isHovered2Player = false;
    
    private Timer timer;
    private MainMenuPanel mainMenuPanel;
    
    public MainMenu() {

        logo = new Rectangle(450, 170, 800, 330);
        menuBar = new Rectangle(50, 65, 400, 550);
        start = new Rectangle(75, 80, 310, 110);
        twoP = new Rectangle(75, 215, 300, 110);
        config = new Rectangle(75, 350, 300, 115);
        exit = new Rectangle(75, 485, 300, 115);
        mainMenuPanel = new MainMenuPanel();
        
        option2P = new Rectangle(375, 179, 400, 175);
        local = new Rectangle(option2P.x + 70, option2P.y, 270, 70);
        keyboard = new Rectangle(option2P.x + 70, option2P.y + 100, 350, 70);
        
        try {
            setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
            menu = ImageIO.read(getClass().getResource("/Asset/Img/Icons/MenuBar.PNG"));
            title = ImageIO.read(getClass().getResource("/Asset/Img/Icons/defense_of_the_dungeon_no_s.png"));
            background = ImageIO.read(getClass().getResource("/Asset/Img/Background/defense_of_dungeon_wallpaper.png"));
            option2PImg = ImgManager.loadIcon("2-playere_2-options");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong image path.");
        }

        add(mainMenuPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // when Alt+F4
        setTitle("Defense of the Dungeon");
        setSize(1264, 681);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (start.contains(e.getPoint())) {
                    Audio.play(AudioName.BUTTON_CLICK);

                    SwingWorker<Void, Void> worker = new SwingWorker<>() {
                        LoadingScreen loadingScreen = new LoadingScreen();
                        
                        @Override
                        protected Void doInBackground() throws Exception {
                            Thread.sleep(1000);
                            new StageSelector();
                            dispose();
                            return null;
                        }

                        @Override
                        protected void done() {
                            loadingScreen.dispose();
                        }
                    };
                    worker.execute();
                }
                else if (twoP.contains(e.getPoint())) {
                    if (!isShowOption2P) {
                        isShowOption2P = true;
                    }
                    Audio.play(AudioName.BUTTON_HOVER);
                }
                else if (config.contains(e.getPoint())) {
                    Audio.play(AudioName.BUTTON_CLICK);
                    
                    SwingWorker<Void, Void> worker = new SwingWorker<>() {
                        LoadingScreen loadingScreen = new LoadingScreen();
                        
                        @Override
                        protected Void doInBackground() throws Exception {
                            Thread.sleep(200);
                            new ConfigScreen();
                            dispose();
                            return null;
                        }

                        @Override
                        protected void done() {
                            loadingScreen.dispose();
                        }
                    };
                    worker.execute();
                }
                else if (exit.contains(e.getPoint())) {
                    System.exit(0);
                }
                if (isShowOption2P) {
                    if (local.contains(e.getPoint())) {
                        Audio.play(AudioName.BUTTON_CLICK);
                        SwingWorker<Void, Void> worker = new SwingWorker<>() {
                            LoadingScreen loadingScreen = new LoadingScreen();

                            @Override
                            protected Void doInBackground() throws Exception {
                                Thread.sleep(1000);
                                new CoOpFrame();
                                dispose();
                                return null;
                            }

                            @Override
                            protected void done() {
                                loadingScreen.dispose();
                            }
                        };
                        worker.execute();
                    } else if (keyboard.contains(e.getPoint())) {
                        Audio.play(AudioName.BUTTON_CLICK);
                        SwingWorker<Void, Void> worker = new SwingWorker<>() {
                            LoadingScreen loadingScreen = new LoadingScreen();

                            @Override
                            protected Void doInBackground() throws Exception {
                                Thread.sleep(1000);
                                new StageSelector("2p");
                                dispose();
                                return null;
                            }

                            @Override
                            protected void done() {
                                loadingScreen.dispose();
                            }
                        };
                        worker.execute();
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (start.contains(e.getPoint()) || twoP.contains(e.getPoint()) || config.contains(e.getPoint()) || exit.contains(e.getPoint()) ||
                       (isShowOption2P && (local.contains(e.getPoint()) || keyboard.contains(e.getPoint()))) ) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    if (!isButtonHovered) {
                        isButtonHovered = true;
                        Audio.play(AudioName.BUTTON_HOVER);
                    }
                } else {
                    setCursor(Cursor.getDefaultCursor());
                    isButtonHovered = false;
                }
                if ((twoP.contains(e.getPoint()))) {
//                    isShowOption2P = true;
                } else {
                    if (!option2P.contains(e.getPoint())) {
                        isShowOption2P = false;
                    }
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (GamePanel.DEBUG_MODE) {
                    switch (e.getKeyChar()) {
                        case 'p':
                            dispose();
                            StageSelector stage = new StageSelector();
                            stage.loadStage("St10");
                            break;
                    }
                }
            }
        });

        timer = new Timer(32, new Animation());
        timer.start();
        
        Audio.playMusic("mainMenu");
    }


    private int titleAnimY = 0;
    private class Animation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            titleAnimY += 2;
            repaint();
        }
    }

    private class MainMenuPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(background, 0, 0, 1280, 720,this);
            g2d.drawImage(menu, menuBar.x, menuBar.y, menuBar.width, menuBar.height,this);
            g2d.drawImage(title, logo.x, logo.y + (int)(20 * Math.sin(Math.toRadians(titleAnimY))), logo.width, logo.height,this);
            if (isShowOption2P) g2d.drawImage(option2PImg, option2P.x, option2P.y, option2P.width, option2P.height, this);
        }
    }

}
