package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class StageSelectorPanel extends JPanel{
    private Rectangle st1, st2, st3, st4, st5, st6, st7, st8, st9, st10, homeBtn;
    private Image stageFrame;
    private boolean isButtonHovered = false;
    private StageSelector stageSelector;
    private String type;

    public StageSelectorPanel(StageSelector frame, String type) {
        this.type = type;
        this.stageSelector = frame;
        setLayout(null);

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
        homeBtn = new Rectangle(1180,15,75,75);

        try {
            stageFrame = ImageIO.read(getClass().getResource("/Asset/Img/Icons/frame_op1.png"));
        } catch (IOException | NullPointerException e) {
            System.out.println("Wrong image path.");
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (st1.contains(e.getPoint())) {
                    selectStage("St1");
                } else if (st2.contains(e.getPoint())) {
                    selectStage("St2");
                }else if (st3.contains(e.getPoint())) {
                    selectStage("St3");}
                else if (st4.contains(e.getPoint())) {
                    selectStage("St4");}
                else if (st5.contains(e.getPoint())) {
                    selectStage("St5");}
                else if (st6.contains(e.getPoint())) {
                    selectStage("St6");}
                else if (st7.contains(e.getPoint())) {
                    selectStage("St7");}
                else if (st8.contains(e.getPoint())) {
                    selectStage("St8");}
                else if (st9.contains(e.getPoint())) {
                    selectStage("St9");}
                else if (st10.contains(e.getPoint())) {
                    selectStage("St10");}
                else if(homeBtn.contains(e.getPoint())){
                    selectStage("Main");
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
    }

    private void selectStage(String stageName) {
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
                stageSelector.loadStage(stageName);
            }
        };
        worker.execute();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setFont(new Font("Algerian", Font.BOLD, 100));
        g2d.setPaint(Color.RED);
        g2d.drawString("Select the stage", 150, 175);
        if (type.equals("2p")) {
            g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
            g2d.setColor(Color.black);
            g2d.drawString("2-Player Mode", 200, 200);
        }
        Image image = ImgManager.loadIcon("home_btn");
        if (stageFrame != null) {
            g2d.drawImage(stageFrame, st1.x, st1.y, st1.width, st1.height,this);
            g2d.drawImage(stageFrame, st2.x, st2.y, st2.width, st2.height,this);
            g2d.drawImage(stageFrame, st3.x, st3.y, st3.width, st3.height,this);
            g2d.drawImage(stageFrame, st4.x, st4.y, st4.width, st4.height,this);
            g2d.drawImage(stageFrame, st5.x, st5.y, st5.width, st5.height,this);
            g2d.drawImage(stageFrame, st6.x, st6.y, st6.width, st6.height,this);
            g2d.drawImage(stageFrame, st7.x, st7.y, st7.width, st7.height,this);
            g2d.drawImage(stageFrame, st8.x, st8.y, st8.width, st8.height,this);
            g2d.drawImage(stageFrame, st9.x, st9.y, st9.width, st9.height,this);
            g2d.drawImage(stageFrame, st10.x, st10.y, st10.width, st10.height,this);         
            g2d.drawImage(stageFrame, homeBtn.x, homeBtn.y, homeBtn.width, homeBtn.height, this);
            g2d.drawImage(stageFrame, homeBtn.x, homeBtn.y, homeBtn.width, homeBtn.height, this);
            g2d.drawImage(image, homeBtn.x, homeBtn.y, homeBtn.width, homeBtn.height, this);
        }
    }
}
