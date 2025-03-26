package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
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

        st1 = new Rectangle(150, 200, 150, 150);
        st2 = new Rectangle(350, 200, 150, 150);
        st3 = new Rectangle(550, 200, 150, 150);
        st4 = new Rectangle(750, 200, 150, 150);
        st5 = new Rectangle(950, 200, 150, 150);
        st6 = new Rectangle(150, 425, 150, 150);
        st7 = new Rectangle(350, 425, 150, 150);
        st8 = new Rectangle(550, 425, 150, 150);
        st9 = new Rectangle(750, 425, 150, 150);
        st10 = new Rectangle(950, 425, 150, 150);
        homeBtn = new Rectangle(1170,15,75,75);
        resetBtn = new Rectangle(1170,110,75,75);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (st1.contains(e.getPoint())) {
                    selectStage("St1");
                    progress.set_Stage_Num(0);
                    save();
                } else if (st2 != null && progress.get_Stage_Num(1) && st2.contains(e.getPoint())) {
                    selectStage("St2");
                    progress.set_Stage_Num(1);
                    save();
                } else if (st3 != null && progress.get_Stage_Num(2) && st3.contains(e.getPoint())) {
                    selectStage("St3");
                    progress.set_Stage_Num(2);
                    save();
                } else if (st4 != null && progress.get_Stage_Num(3) && st4.contains(e.getPoint())) {
                    selectStage("St4");
                    progress.set_Stage_Num(3);
                    save();
                } else if (st5 != null && progress.get_Stage_Num(4) && st5.contains(e.getPoint())) {
                    selectStage("St5");
                    progress.set_Stage_Num(4);
                    save();
                } else if (st6 != null && progress.get_Stage_Num(5) && st6.contains(e.getPoint())) {
                    selectStage("St6");
                    progress.set_Stage_Num(5);
                    save();
                } else if (st7 != null && progress.get_Stage_Num(6) && st7.contains(e.getPoint())) {
                    selectStage("St7");
                    progress.set_Stage_Num(6);
                    save();
                } else if (st8 != null && progress.get_Stage_Num(7) && st8.contains(e.getPoint())) {
                    selectStage("St8");
                    progress.set_Stage_Num(7);
                    save();
                } else if (st9 != null && progress.get_Stage_Num(8) && st9.contains(e.getPoint())) {
                    selectStage("St9");
                    progress.set_Stage_Num(8);
                    save();
                } else if (st10 != null && progress.get_Stage_Num(9) && st10.contains(e.getPoint())) {
                    selectStage("St10");
                    progress.set_Stage_Num(9);
                    save();
                } else if (homeBtn.contains(e.getPoint())) {
                    selectStage("Main");
                } else if (resetBtn.contains(e.getPoint())){
                    File saveFile = new File("Save.bat");
                    if (saveFile.exists()) {
                        int res = JOptionPane.showConfirmDialog(frame, "Do you want to reset level? This can't be undo.",
                            "Reset Save Level", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (res == JOptionPane.YES_OPTION) {
                            boolean deleted = saveFile.delete();
                            if (deleted) {
                                System.out.println("Save file deleted successfully");
                                selectStage("Main");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "You have no Save Progress.");
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (st1.contains(e.getPoint()) ||
                    (progress.get_Stage_Num(1) && st2.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(2) && st3.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(3) && st4.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(4) && st5.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(5) && st6.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(6) && st7.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(7) && st8.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(8) && st9.contains(e.getPoint())) ||
                    (progress.get_Stage_Num(9) && st10.contains(e.getPoint()))||
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
    
    public BufferedImage unitIcon(String path, int frameIndex, int frame) {
        if(frame == 1){
            try {
                BufferedImage spriteSheet = ImageIO.read(getClass().getResource(path));
                int frameWidth = spriteSheet.getWidth() / 1;
                return spriteSheet.getSubimage(frameIndex * frameWidth, 0, frameWidth, spriteSheet.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (frame == 4){
            try {
                BufferedImage spriteSheet = ImageIO.read(getClass().getResource(path));
                int frameWidth = spriteSheet.getWidth() / 4;
                return spriteSheet.getSubimage(frameIndex * frameWidth, 0, frameWidth, spriteSheet.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (frame == 5){
            try {
                BufferedImage spriteSheet = ImageIO.read(getClass().getResource(path));
                int frameWidth = spriteSheet.getWidth() / 5;
                return spriteSheet.getSubimage(frameIndex * frameWidth, 0, frameWidth, spriteSheet.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (frame==8){
            try {
                BufferedImage spriteSheet = ImageIO.read(getClass().getResource(path));
                int frameWidth = spriteSheet.getWidth() / 8;
                return spriteSheet.getSubimage(frameIndex * frameWidth, 0, frameWidth, spriteSheet.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (type.equals("2p")) {
            g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
            g2d.setColor(Color.black);
            g2d.drawString("2-Player Mode", 200, 200);
        }
        BufferedImage unitIcon = unitIcon("/Asset/Img/SpriteSheets/explosive_tutle_Idle.png", 0, 4);
        Image stageLayout = ImgManager.loadIcon("frame_op1");
        BufferedImage background = unitIcon("/Asset/Img/Background/defense_of_dungeon_wallpaper.png", 0, 1);
        
        g2d.drawImage(background,0,0,1264,681,this);
        g2d.drawImage(stageLayout, st1.x, st1.y, st1.width, st1.height, this);
        g2d.drawImage(unitIcon, st1.x, st1.y, st1.width, st1.height, this);
        
        g2d.setFont(new Font("Algerian", Font.BOLD, 100));
        g2d.setPaint(Color.RED);
        g2d.drawString("Select the stage", 150, 135);
        g2d.setFont(new Font("Algerian", Font.BOLD, 40));
        g2d.drawString("Stage 1", 145, 400);

        if (progress.get_Stage_Num(1)){
            unitIcon = unitIcon("/Asset/Img/SpriteSheets/mini_lazer_idle.png", 0, 4);
            g2d.drawImage(stageLayout, st2.x, st2.y, st2.width, st2.height, this);
            g2d.drawImage(unitIcon, st2.x, st2.y, st2.width, st2.height, this);
            g2d.drawString("Stage 2", 345, 400);
        } 
        if (progress.get_Stage_Num(2)) {
            unitIcon = unitIcon("/Asset/Img/SpriteSheets/vampire_idle.png", 0, 4);
            g2d.drawImage(stageLayout, st3.x, st3.y, st3.width, st3.height, this);
            g2d.drawImage(unitIcon, st3.x, st3.y, st3.width, st3.height, this);
            g2d.drawString("Stage 3", 545, 400);
        }
        if (progress.get_Stage_Num(3)) {
            unitIcon = unitIcon("/Asset/Img/SpriteSheets/Golem_idle.png", 0, 4);
            g2d.drawImage(stageLayout, st4.x, st4.y, st4.width, st4.height, this);
            g2d.drawImage(unitIcon, st4.x, st4.y, st4.width, st4.height, this);
            g2d.drawString("Stage 4", 745, 400);
        }
        if (progress.get_Stage_Num(4)) {
            unitIcon = unitIcon("/Asset/Img/SpriteSheets/mipya_idle.png", 0, 4);
            g2d.drawImage(stageLayout, st5.x, st5.y, st5.width, st5.height, this);
            g2d.drawImage(unitIcon, st5.x, st5.y, st5.width, st5.height, this);
            g2d.drawString("Stage 5", 945, 400);
        }
        if (progress.get_Stage_Num(5)) {
            unitIcon = unitIcon("/Asset/Img/SpriteSheets/python_idle.png", 0, 4);
            g2d.drawImage(stageLayout, st6.x, st6.y, st6.width, st6.height, this);
            g2d.drawImage(unitIcon, st6.x, st6.y, st6.width, st6.height, this);
            g2d.drawString("Stage 6", 145, 625);
        }
        if (progress.get_Stage_Num(6)) {
            unitIcon = unitIcon("/Asset/Img/SpriteSheets/alpha_wolf_idle.png", 0, 5);
            g2d.drawImage(stageLayout, st7.x, st7.y, st7.width, st7.height, this);
            g2d.drawImage(unitIcon, st7.x, st7.y, st7.width, st7.height, this);
            g2d.drawString("Stage 7", 345, 625);
        }
        if (progress.get_Stage_Num(7)) {
            unitIcon = unitIcon("/Asset/Img/SpriteSheets/werewolf_idle.png", 0, 4);
            g2d.drawImage(stageLayout, st8.x, st8.y, st8.width, st8.height, this);
            g2d.drawImage(unitIcon, st8.x, st8.y, st8.width, st8.height, this);
            g2d.drawString("Stage 8", 545, 625);
        }
        if (progress.get_Stage_Num(8)) {
            unitIcon = unitIcon("/Asset/Img/SpriteSheets/werewolf_idle.png", 0, 4);
            g2d.drawImage(stageLayout, st9.x, st9.y, st9.width, st9.height, this);
            g2d.drawImage(unitIcon, st9.x, st9.y, st9.width, st9.height, this);
            g2d.drawString("BOSS", 755, 625);
        }
        if (progress.get_Stage_Num(9)) {
            unitIcon = unitIcon("/Asset/Img/SpriteSheets/nike.png", 0, 1);
            g2d.drawImage(stageLayout, st10.x, st10.y, st10.width, st10.height, this);
            g2d.drawImage(unitIcon, st10.x, st10.y, st10.width, st10.height, this);
            g2d.drawString("INFINITE", 945, 625);
        }
        Image homeIcon = ImgManager.loadIcon("home_Btn");
        g2d.drawImage(homeIcon, homeBtn.x, homeBtn.y, homeBtn.width, homeBtn.height, this);
        Image resetLvlIcon = ImgManager.loadIcon("del_save_btn");
        g2d.drawImage(resetLvlIcon, resetBtn.x, resetBtn.y, resetBtn.width, resetBtn.height, this);
        }
    }