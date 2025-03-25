package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
import Entities.Units.UnitSpriteSheets;
import static Main.UnitSelector.CELL_HEIGHT;
import static Main.UnitSelector.CELL_WIDTH;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class StageSelectorPanel extends JPanel{
    private Rectangle st1, st2, st3, st4, st5, st6, st7, st8, st9, st10, homeBtn, resetBtn;
    private Image stageFrame;
    private boolean isButtonHovered = false;
    private StageSelector stageSelector;
    private String type;
    private SaveGame progress;

    public StageSelectorPanel(StageSelector frame, String type) {
        this.type = type;
        this.stageSelector = frame;
        setLayout(null);

        try(ObjectInputStream temp = new ObjectInputStream(new FileInputStream("Save.bat"))){
            progress = (SaveGame) temp.readObject();
            stageFrame = ImageIO.read(getClass().getResource("/Asset/Img/Icons/frame_op1.png"));
            System.out.println("Load Done");
        }catch(IOException ex){
            System.out.println("No Save Progress");
            progress = new SaveGame();
        }catch(ClassNotFoundException ee){
            ee.printStackTrace();
        }

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
        resetBtn = new Rectangle(1180,110,75,75);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (st1.contains(e.getPoint())) {
                    selectStage("St1");
                    progress.set_Stage_Num(1);
                    save();
                } else if (st2 != null && progress.get_Stage_Num(2) && st2.contains(e.getPoint())) {
                    selectStage("St2");
                    progress.set_Stage_Num(2);
                    save();
                } else if (st3 != null && progress.get_Stage_Num(3) && st3.contains(e.getPoint())) {
                    selectStage("St3");
                    progress.set_Stage_Num(3);
                    save();
                } else if (st4 != null && progress.get_Stage_Num(4) && st4.contains(e.getPoint())) {
                    selectStage("St4");
                    progress.set_Stage_Num(4);
                    save();
                } else if (st5 != null && progress.get_Stage_Num(5) && st5.contains(e.getPoint())) {
                    selectStage("St5");
                    progress.set_Stage_Num(5);
                    save();
                } else if (st6 != null && progress.get_Stage_Num(6) && st6.contains(e.getPoint())) {
                    selectStage("St6");
                    progress.set_Stage_Num(6);
                    save();
                } else if (st7 != null && progress.get_Stage_Num(7) && st7.contains(e.getPoint())) {
                    selectStage("St7");
                    progress.set_Stage_Num(7);
                    save();
                } else if (st8 != null && progress.get_Stage_Num(8) && st8.contains(e.getPoint())) {
                    selectStage("St8");
                    progress.set_Stage_Num(8);
                    save();
                } else if (st9 != null && progress.get_Stage_Num(9) && st9.contains(e.getPoint())) {
                    selectStage("St9");
                    progress.set_Stage_Num(9);
                    save();
                } else if (st10 != null && progress.get_Stage_Num(10) && st10.contains(e.getPoint())) {
                    selectStage("St10");
                    save();
                } else if (homeBtn.contains(e.getPoint())) {
                    selectStage("Main");
                } else if (resetBtn.contains(e.getPoint())){
                    File saveFile = new File("Save.bat");
                    if (saveFile.exists()) {
                        boolean deleted = saveFile.delete();
                        if (deleted) {
                            System.out.println("Save file deleted successfully");
                            selectStage("Main");
                        }
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (st1.contains(e.getPoint()) ||
                    (progress.get_Stage_Num(2) && st2.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(3) && st3.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(4) && st4.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(5) && st5.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(6) && st6.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(7) && st7.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(8) && st8.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(9) && st9.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(10) && st10.contains(e.getPoint()))||
                     homeBtn.contains(e.getPoint())||
                     resetBtn.contains(e.getPoint())) 
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
    
    private void save() {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Save.bat"))) {
                out.writeObject(progress);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
        Image image = ImgManager.loadIcon("frame_op1");
        g2d.drawImage(image, st1.x, st1.y, st1.width, st1.height, this);
        if (progress.get_Stage_Num(2)) g2d.drawImage(image, st2.x, st2.y, st2.width, st2.height, this);
        if (progress.get_Stage_Num(3)) g2d.drawImage(image, st3.x, st3.y, st3.width, st3.height, this);
        if (progress.get_Stage_Num(4)) g2d.drawImage(image, st4.x, st4.y, st4.width, st4.height, this);
        if (progress.get_Stage_Num(5)) g2d.drawImage(image, st5.x, st5.y, st5.width, st5.height, this);
        if (progress.get_Stage_Num(6)) g2d.drawImage(image, st6.x, st6.y, st6.width, st6.height, this);
        if (progress.get_Stage_Num(7)) g2d.drawImage(image, st7.x, st7.y, st7.width, st7.height, this);
        if (progress.get_Stage_Num(8)) g2d.drawImage(image, st8.x, st8.y, st8.width, st8.height, this);
        if (progress.get_Stage_Num(9)) g2d.drawImage(image, st9.x, st9.y, st9.width, st9.height, this);
        if (progress.get_Stage_Num(10)) g2d.drawImage(image, st10.x, st10.y, st10.width, st10.height, this);
        g2d.drawImage(image, homeBtn.x, homeBtn.y, homeBtn.width, homeBtn.height, this);
        image = ImgManager.loadIcon("home_btn");
        g2d.drawImage(image, homeBtn.x, homeBtn.y, homeBtn.width, homeBtn.height, this);
        image = ImgManager.loadIcon("icon");
        g2d.drawImage(image, resetBtn.x, resetBtn.y, resetBtn.width, resetBtn.height, this);
        }
    }