package Main;

import Asset.Audio;
import Asset.AudioName;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainMenu extends JFrame {
    private Rectangle start, dict, config, exit, menuBar, logo;
    private Image menu, title, background;
    private boolean isButtonHovered = false;

    public MainMenu() {
        setTitle("Defense of the Dungeon");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        logo = new Rectangle(465, 170, 800, 400);
        menuBar = new Rectangle(100, 100, 400, 550);
        start = new Rectangle(125, 110, 310, 125);
        dict = new Rectangle(125, 250, 300, 110);
        config = new Rectangle(125, 385, 300, 115);
        exit = new Rectangle(125, 525, 300, 115);
        try {
            setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
            menu = ImageIO.read(getClass().getResource("/Asset/Img/Icons/MenuBar.PNG"));
            title = ImageIO.read(getClass().getResource("/Asset/Img/Icons/defense_of_the_dungeon_no_s.png"));
            background = ImageIO.read(getClass().getResource("/Asset/Img/Background/defense_of_dungeon_wallpaper.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong image path.");
        }

        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (start.contains(e.getPoint())) {
                    Audio.play(AudioName.BUTTON_CLICK);
                    System.out.println("Choose the stage you want");

                    LoadingScreen loadingScreen = new LoadingScreen();
                    SwingWorker<Void, Void> worker = new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            Thread.sleep(1500);
                            return null;
                        }

                        @Override
                        protected void done() {
                            System.out.println("Finished loading.");
                            loadingScreen.dispose();
                            dispose();
                            new StageSelector();
                        }
                    };
                    worker.execute();
                } else if (dict.contains(e.getPoint())) {
                    Audio.play(AudioName.PLANT_CANT_PICK_UP);
                    System.out.println("This feature hasn't been finished");
                } else if (config.contains(e.getPoint())) {
                    Audio.play(AudioName.PLANT_CANT_PICK_UP);
                    System.out.println("This feature hasn't been finished");
                } else if (exit.contains(e.getPoint())) {
                    Audio.play(AudioName.BUTTON_CLICK);
                    System.out.println("Bye Bye");
                    System.exit(0);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (start.contains(e.getPoint()) ||
                    dict.contains(e.getPoint()) ||
                    config.contains(e.getPoint()) ||
                    exit.contains(e.getPoint())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    if (!isButtonHovered) {
                        isButtonHovered = true;
                        Audio.play(AudioName.BUTTON_HOVER);
                    }
                } else {
                    setCursor(Cursor.getDefaultCursor());
                    isButtonHovered = false;
                }
            }
        });
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println(e.getKeyChar());
                switch (e.getKeyChar()) {
                    case 'p':
                        dispose();
                        StageSelector stage = new StageSelector();
                        GamePanel gamePanel = new stage_beta();
                        stage.getContentPane().removeAll();
                        stage.getContentPane().add(gamePanel);
                        stage.addKeyListener(gamePanel.new GameKeyboardListener());
                        break;
                }
            }
            
        });
        
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (background != null) {
            g2d.drawImage(background, 0, 0, 1280, 720,this);
        }
        if (menu != null) {
            g2d.drawImage(menu, menuBar.x, menuBar.y, menuBar.width, menuBar.height,this);
        }
        if (title != null) {
            g2d.drawImage(title, logo.x, logo.y, logo.width, logo.height,this);
        }
    }
}
