package Main;
import Asset.Audio;
import Asset.AudioName;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class StageSelector extends JFrame {
    private Rectangle st1, st2, st3, st4, st5, st6, st7, st8, st9, st10;
    private Image frame;
    private boolean isButtonHovered = false;
  
    public StageSelector() {
        setTitle("Select stage");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        requestFocus();

        st1 = new Rectangle(150, 250, 150, 150);
        st2 = new Rectangle(350, 250, 150, 150);
        st3 = new Rectangle(550, 250, 150, 150);
        st4 = new Rectangle(750, 250, 150, 150);
        st5 = new Rectangle(950, 250, 150, 150);
        st6 = new Rectangle(150, 450, 150, 150);
        st7 = new Rectangle(350, 450, 150, 150);
        st8 = new Rectangle(550, 450, 150, 150);
        st9 = new Rectangle(750, 450, 150, 150);
        st10 = new Rectangle(950, 450, 150, 150);
        
        try {
            setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
            frame = ImageIO.read(getClass().getResourceAsStream("/Asset/Img/Icons/frame_op1.png"));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("Wrong image path.");
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (st1.contains(e.getPoint())) {
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
                            System.out.println("Finished loading.");
                            loadingScreen.dispose();
                            GamePanel gamePanel = new GamePanel();
                            getContentPane().removeAll();
                            getContentPane().add(gamePanel);
                            addKeyListener(gamePanel.new GameKeyboardListener());
                            System.out.println("Stage 1 selected!");
                            setTitle("Stage 1");
                        }
                    };
                    worker.execute();
                }
                if (st2.contains(e.getPoint())||st3.contains(e.getPoint())||st4.contains(e.getPoint())||st5.contains(e.getPoint())||
                        st6.contains(e.getPoint())||st7.contains(e.getPoint())||st8.contains(e.getPoint())||st9.contains(e.getPoint())||st10.contains(e.getPoint())){
                    Audio.play(AudioName.PLANT_CANT_PICK_UP);
                    System.out.println("Stage not yet finish.");
                }
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (st1.contains(e.getPoint()) ||
                    st2.contains(e.getPoint()) ||
                    st3.contains(e.getPoint()) ||
                    st4.contains(e.getPoint()) ||
                    st5.contains(e.getPoint()) ||
                    st6.contains(e.getPoint()) ||
                    st7.contains(e.getPoint()) ||
                    st8.contains(e.getPoint()) ||
                    st9.contains(e.getPoint()) ||
                    st10.contains(e.getPoint())) 
                {
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
        setVisible(true);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setFont(new Font("Algerian", Font.BOLD, 100));
        g2d.setPaint(Color.RED);
        g2d.drawString("Select the stage", 150, 175);
        if (frame != null) {
            g2d.drawImage(frame, st1.x, st1.y, st1.width, st1.height,this);
            g2d.drawImage(frame, st2.x, st2.y, st2.width, st2.height,this);
            g2d.drawImage(frame, st3.x, st3.y, st3.width, st3.height,this);
            g2d.drawImage(frame, st4.x, st4.y, st4.width, st4.height,this);
            g2d.drawImage(frame, st5.x, st5.y, st5.width, st5.height,this);
            g2d.drawImage(frame, st6.x, st6.y, st6.width, st6.height,this);
            g2d.drawImage(frame, st7.x, st7.y, st7.width, st7.height,this);
            g2d.drawImage(frame, st8.x, st8.y, st8.width, st8.height,this);
            g2d.drawImage(frame, st9.x, st9.y, st9.width, st9.height,this);
            g2d.drawImage(frame, st10.x, st10.y, st10.width, st10.height,this);            
        }
    }
}