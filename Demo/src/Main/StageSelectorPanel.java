package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
import CoOpSystem.CoKeys;
import CoOpSystem.CoOpFrame;
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
    private boolean isAnim = false;
    private Rectangle animRect, animP2Rect;
    private CoOpFrame cof;
    private int unlock = 0, passCheck;

    public StageSelectorPanel(StageSelector frame, String type, CoOpFrame cof) {
        this.type = type;
        this.cof = cof;
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
        
        final Rectangle[] st = {st1, st2, st3, st4, st5, st6, st7, st8, st9, st10};
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (type.equals("cli")) return;
                for (int i=0; i<st.length; i++) {
                    if (st[i] == null) continue;
                    if (st[i].contains(e.getPoint())) {
                        if (st[i].equals(st1) || progress.get_Stage_Num(i)) {
                            selectStage("St" + (i+1));
                            passCheck = i;
                            System.out.println("pass ="+passCheck);
                            break;
                        }
                    }
                }
                if (homeBtn.contains(e.getPoint())) {
                    if (cof != null) {
                        JOptionPane.showMessageDialog(frame, 
                                "Sorry, you can't return go back in Socket Mode", "Socket Mode", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    selectStage("Main");
                } else if (resetBtn.contains(e.getPoint())){
                    Audio.play(AudioName.BUTTON_CLICK);
                    if (cof != null) {
                        JOptionPane.showMessageDialog(frame, 
                                "Sorry, you can't return go back in Socket Mode", "Socket Mode", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    File saveFile = new File("Save.bat");
                    if (saveFile.exists()) {
                        int res = JOptionPane.showConfirmDialog(frame, "Do you want to reset level? This can't be undo.",
                            "Reset Save Level", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (res == JOptionPane.YES_OPTION) {
                            unlock = 0;
                            boolean deleted = saveFile.delete();
                            if (deleted) {
                                System.out.println("Save file deleted successfully");
                                selectStage("StageSelector");
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
                boolean isAnySelect = false;
                for (int i=0; i<st.length; i++) {
                    if (st[i] == null) continue;
                    if (st[i].contains(e.getPoint())) {
                        if (st[i].equals(st1) || progress.get_Stage_Num(i)) {
                            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            if (!isAnySelect) {
                                if (type.equals("cli")) {
                                    animRect = st[i];
                                    cof.sendOne(CoKeys.HOVER_XY, animRect.x + " " + animRect.y);
                                } else {
                                    animRect = st[i];
                                    if (cof != null) {
                                        cof.sendOne(CoKeys.HOVER_XY, animRect.x + " " + animRect.y);
                                    }
                                }
                            }
                            isAnySelect = true;
                            break;
                        }
                    }
                    if (homeBtn.contains(e.getPoint())||resetBtn.contains(e.getPoint())) {
                        isAnySelect = true;
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                }
                if (isAnySelect) {
                    if (!isButtonHovered) {
                        isButtonHovered = true;
                        Audio.play(AudioName.BUTTON_HOVER);
                        revalidate();
                        repaint();
                    }
                } else {
                    setCursor(Cursor.getDefaultCursor());
                    isButtonHovered = false;
                    animRect = null;
                    revalidate();
                    repaint();
                }
            }
        }); 
        
        new Timer(500, e -> animate()).start();
    }
    
    private void animate() {
        isAnim = !isAnim;
        revalidate();
        repaint();
    }
    
    public void save() {
        unlock = progress.getMaxUnlockedStage()-1;
        System.out.println("unlock is"+unlock);
        if(passCheck == unlock){
            progress.set_Stage_Num(unlock);
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Save.bat"))) {
                out.writeObject(progress);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            unlock++; 
        }
    }
    
    private void selectStage(String stageName) {
        Audio.play(AudioName.BUTTON_CLICK);
        stageSelector.loadStage(stageName); 
    }
    
    public BufferedImage spriteSlicer(String path, int frame) {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResource(path));
            int frameWidth = spriteSheet.getWidth() / frame;
            return spriteSheet.getSubimage(0, 0, frameWidth, spriteSheet.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        BufferedImage unitIcon = spriteSlicer("/Asset/Img/SpriteSheets/explosive_tutle_Idle.png", 4);
        Image stageLayout = ImgManager.loadIcon("frame_op1");
        BufferedImage background = spriteSlicer("/Asset/Img/Background/defense_of_dungeon_wallpaper.png", 1);
        
        g2d.drawImage(background,0,0,1264,681,this);
        g2d.drawImage(stageLayout, st1.x, st1.y, st1.width, st1.height, this);
        g2d.drawImage(unitIcon, st1.x, st1.y, st1.width, st1.height, this);
        
        
        g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        g2d.setColor(Color.white);
        if (type.equals("2p")) {
            g2d.drawString("2-Player Mode" + ((cof != null) ? ": Server Host":""), 200, 170);
        } else if (type.equals("cli")) {
            g2d.drawString("2-Player Mode: Client (Can't select Level)", 200, 170);
        }
        g2d.setFont(new Font("Algerian", Font.BOLD, 100));
        g2d.setPaint(Color.RED);
        g2d.drawString("Select the stage", 150, 135);
        g2d.setFont(new Font("Algerian", Font.BOLD, 40));
        g2d.drawString("Stage 1", 145, 400);
        
        
        if (progress.get_Stage_Num(1)){
            unitIcon = spriteSlicer("/Asset/Img/SpriteSheets/mini_lazer_idle.png", 4);
            g2d.drawImage(stageLayout, st2.x, st2.y, st2.width, st2.height, this);
            g2d.drawImage(unitIcon, st2.x, st2.y, st2.width, st2.height, this);
            g2d.drawString("Stage 2", 345, 400);
        } 
        if (progress.get_Stage_Num(2)) {
            unitIcon = spriteSlicer("/Asset/Img/SpriteSheets/vampire_idle.png", 4);
            g2d.drawImage(stageLayout, st3.x, st3.y, st3.width, st3.height, this);
            g2d.drawImage(unitIcon, st3.x, st3.y, st3.width, st3.height, this);
            g2d.drawString("Stage 3", 545, 400);
        }
        if (progress.get_Stage_Num(3)) {
            unitIcon = spriteSlicer("/Asset/Img/SpriteSheets/Golem_idle.png", 4);
            g2d.drawImage(stageLayout, st4.x, st4.y, st4.width, st4.height, this);
            g2d.drawImage(unitIcon, st4.x, st4.y, st4.width, st4.height, this);
            g2d.drawString("Stage 4", 745, 400);
        }
        if (progress.get_Stage_Num(4)) {
            unitIcon = spriteSlicer("/Asset/Img/SpriteSheets/mipya_idle.png", 4);
            g2d.drawImage(stageLayout, st5.x, st5.y, st5.width, st5.height, this);
            g2d.drawImage(unitIcon, st5.x, st5.y, st5.width, st5.height, this);
            g2d.drawString("Stage 5", 945, 400);
        }
        if (progress.get_Stage_Num(5)) {
            unitIcon = spriteSlicer("/Asset/Img/SpriteSheets/python_idle.png", 4);
            g2d.drawImage(stageLayout, st6.x, st6.y, st6.width, st6.height, this);
            g2d.drawImage(unitIcon, st6.x, st6.y, st6.width, st6.height, this);
            g2d.drawString("Stage 6", 145, 625);
        }
        if (progress.get_Stage_Num(6)) {
            unitIcon = spriteSlicer("/Asset/Img/SpriteSheets/alpha_wolf_idle.png", 5);
            g2d.drawImage(stageLayout, st7.x, st7.y, st7.width, st7.height, this);
            g2d.drawImage(unitIcon, st7.x, st7.y, st7.width, st7.height, this);
            g2d.drawString("Stage 7", 345, 625);
        }
        if (progress.get_Stage_Num(7)) {
            unitIcon = spriteSlicer("/Asset/Img/SpriteSheets/werewolf_idle.png", 4);
            g2d.drawImage(stageLayout, st8.x, st8.y, st8.width, st8.height, this);
            g2d.drawImage(unitIcon, st8.x, st8.y, st8.width, st8.height, this);
            g2d.drawString("Stage 8", 545, 625);
        }
        if (progress.get_Stage_Num(8)) {
            unitIcon = spriteSlicer("/Asset/Img/SpriteSheets/SongChinWu_idle.png", 4);
            g2d.drawImage(stageLayout, st9.x, st9.y, st9.width, st9.height, this);
            g2d.drawImage(unitIcon, st9.x, st9.y, st9.width, st9.height, this);
            g2d.drawString("BOSS", 775, 625);
        }
        if (progress.get_Stage_Num(9)) {
            unitIcon = spriteSlicer("/Asset/Img/SpriteSheets/nike.png", 1);
            g2d.drawImage(stageLayout, st10.x, st10.y, st10.width, st10.height, this);
            g2d.drawImage(unitIcon, st10.x, st10.y, st10.width, st10.height, this);
            g2d.drawString("INFINITE", 945, 625);
        }
        if (cof != null && animP2Rect != null) {
            Image hover = ImgManager.loadIcon("blue_p2_place_hover");
            g2d.drawImage(hover, animP2Rect.x,animP2Rect.y, animP2Rect.width, animP2Rect.height,this);
        } 
        if (animRect != null) {
            Image hover;
            if (type.equals("cli")) {
                hover = ImgManager.loadIcon("white_less_place_hover");
            } else {
                hover = ImgManager.loadIcon("stage_select_hover_" + (isAnim ? "0":"1"));
            }
            g2d.drawImage(hover, animRect.x,animRect.y, animRect.width, animRect.height,this);
        }
        
        if (!type.equals("cli")) {
            Image homeIcon = ImgManager.loadIcon("home_Btn");
            g2d.drawImage(homeIcon, homeBtn.x, homeBtn.y, homeBtn.width, homeBtn.height, this);
            Image resetLvlIcon = ImgManager.loadIcon("del_save_btn");
            g2d.drawImage(resetLvlIcon, resetBtn.x, resetBtn.y, resetBtn.width, resetBtn.height, this);
        }
        
        }
    
    public void setHover(int x, int y) {
        animRect = new Rectangle(x, y, 150, 150);
        revalidate();
        repaint();
    }
    
    public void setP2Hover(int x, int y) {
        animP2Rect = new Rectangle(x, y, 150, 150);
        revalidate();
        repaint();
    }
}