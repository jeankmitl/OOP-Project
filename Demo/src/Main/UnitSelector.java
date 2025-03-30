/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
import Asset.StringFormatter;
import CoOpSystem.CoOpFrame;
import Entities.Units.*;
import Main.SaveGame;
import Main.UnitType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 *
 * @author anawi
 */
public class UnitSelector extends JDialog {
    private static final List<UnitType> unitTypes = new ArrayList<>();
    private final List<UnitLabelBox> unitChosens = new ArrayList<>();
    private final JPanel unitPanelList, unitChosenPanelList, operatorPanel, unitStatsPanel, optionsPanel;
    private final JPanel leftRightPanel, upDownPanel;
    private final BGPreviewLabel bgPreviewLabel;
    private final UnitStatLabel healthStatLabel, atkStatLabel, atkSpeedLabel, cooldownLabel;
    private final UnitDescription unitDescription;
    private final JLabel unitNameLabel, roleLabel, selectorTypeLabel, warnLabel, warnNotReadyLabel;
    private final JButton goButton;
    
    public static final int CELL_WIDTH = 95;
    public static final int CELL_HEIGHT = 95;
    public static final int COLS = 4;
    public static final int GAP = 7;
    public static final int MAX_UNIT = 8;
    private UnitInsertBox[] unitInsertBoxs = new UnitInsertBox[MAX_UNIT];
    
    private final String type;
    private SaveGame progress = null;
    protected final boolean DEBUG_MODE = GamePanel.DEBUG_MODE; //go to GamePanel to set DEBUG

    private CoOpFrame cof;
    private boolean isCliReady = false;
    
    
    public UnitSelector(JFrame parent) {
        this(parent, "", null);
    }
    
    public UnitSelector(JFrame parent, String type) {
        this(parent, type, null);
    }

    public UnitSelector(JFrame parent, String type, CoOpFrame cof) {
        super(parent);
        this.cof = cof;
        this.type = type;

        ImageIcon bgPreviewImg = new ImageIcon(ImgManager.loadIcon("bg_for_preview"));
        
        try(ObjectInputStream temp = new ObjectInputStream(new FileInputStream("Save.bat"))){
            progress = (SaveGame) temp.readObject();
            loader(progress);
            System.out.println("Load Done");
        }catch(IOException ex){
            System.out.println("No Save Progress");
            this.loader(new SaveGame());
            if(DEBUG_MODE){this.loader(new SaveGame());} //<--- For No Save And Debug mode
        }catch(ClassNotFoundException ee){
            ee.printStackTrace();
        }
        unitPanelList = new JPanel();
        unitChosenPanelList = new JPanel();
        optionsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = ImgManager.loadIcon("bg_for_status2");
                g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
            }
        };
        operatorPanel = new JPanel();
        leftRightPanel = new JPanel();
        upDownPanel = new JPanel();
        unitStatsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = ImgManager.loadIcon("bg_for_status");
                g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
            }
        };

        bgPreviewLabel = new BGPreviewLabel(bgPreviewImg);
        unitNameLabel = new JLabel("???");
        roleLabel = new JLabel("role: ");
        selectorTypeLabel = new JLabel();
        warnLabel = new JLabel("Need at least 3 units");
        warnNotReadyLabel = new JLabel(" / Client is not Ready");
        goButton = new JButton("Go!");
        healthStatLabel = new UnitStatLabel("Health", 1000);
        atkStatLabel = new UnitStatLabel("Atk", 200);
        atkSpeedLabel = new UnitStatLabel("Atk speed", 100, 1000);
        cooldownLabel = new UnitStatLabel("Cooldown", 100, 300);
        unitDescription = new UnitDescription();
        
        unitPanelList.setLayout(new BoxLayout(unitPanelList, BoxLayout.Y_AXIS));
        unitPanelList.setBackground(new Color(0x1B1B24));
        unitChosenPanelList.setLayout(new GridLayout(1, 8));
        
        upDownPanel.setLayout(new BorderLayout());
        leftRightPanel.setLayout(new BorderLayout());
        operatorPanel.setPreferredSize(new Dimension(0, 200));
        operatorPanel.setLayout(new GridLayout(2, 1));
        unitStatsPanel.setLayout(new BoxLayout(unitStatsPanel, BoxLayout.Y_AXIS));
        unitNameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        unitNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 2, 0));
        unitNameLabel.setForeground(Color.white);
        roleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        roleLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 7, 0));
        roleLabel.setForeground(Color.white);
        goButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.lightGray, Color.darkGray));
        goButton.setBackground(Color.gray);
        goButton.setFocusable(false);
        goButton.setEnabled(false);
        goButton.setPreferredSize(new Dimension(100, 50));
        goButton.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
        
        selectorTypeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        selectorTypeLabel.setForeground(Color.white);
        warnLabel.setForeground(Color.lightGray);
        warnNotReadyLabel.setForeground(Color.lightGray);
        switch (type) {
            case "p1":
                selectorTypeLabel.setText("Player 1: ");
                break;
            case "p2":
                selectorTypeLabel.setText("Player 2: ");
                break;
            case "cli":
                selectorTypeLabel.setText("Client");
                goButton.setText("Ready!");
                break;
            case "server":
                selectorTypeLabel.setText("Server");
                goButton.setText("<Go>");
                break;
        }

        for (int i = 0; i < unitTypes.size(); i += COLS) {
            JPanel rowPanel = new JPanel(new GridLayout(1, COLS, GAP, 0));
            rowPanel.setOpaque(false);
            rowPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, 0, GAP + 13));
            for (int j = 0; j < COLS; j++) {
                int index = i + j;
                if (index < unitTypes.size()) {
                    rowPanel.add(new UnitLabelBox(unitTypes.get(index)));
                } else {
                    rowPanel.add(new JLabel());  // Empty
                }
            }
            unitPanelList.add(rowPanel);
        }
        for (int i = 0; i < MAX_UNIT; i++) {
            UnitInsertBox uib = new UnitInsertBox();
            unitChosenPanelList.add(uib);
            unitInsertBoxs[i] = uib;
        }

        JScrollPane scrollUnitPanelList = new JScrollPane(unitPanelList);
        scrollUnitPanelList.getVerticalScrollBar().setUnitIncrement(16);
        scrollUnitPanelList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollUnitPanelList.setBorder(BorderFactory.createLineBorder(Color.black));

        add(upDownPanel);
        upDownPanel.add(leftRightPanel);
        upDownPanel.add(operatorPanel, BorderLayout.SOUTH);
        leftRightPanel.add(scrollUnitPanelList, BorderLayout.WEST);
        leftRightPanel.add(unitStatsPanel);
        
        unitStatsPanel.add(bgPreviewLabel);
        unitStatsPanel.add(unitNameLabel);
        unitStatsPanel.add(roleLabel);
        unitStatsPanel.add(healthStatLabel);
        unitStatsPanel.add(atkStatLabel);
        unitStatsPanel.add(atkSpeedLabel);
        unitStatsPanel.add(cooldownLabel);
        unitStatsPanel.add(unitDescription);
        
        operatorPanel.add(optionsPanel);
        operatorPanel.add(unitChosenPanelList);
        
        optionsPanel.add(selectorTypeLabel);
        optionsPanel.add(goButton);
//        optionsPanel.add((type.equals("cli"))? readyCliButton : goButton);
        optionsPanel.add(warnLabel);
        if (type.equals("server")) optionsPanel.add(warnNotReadyLabel);
        goButton.addActionListener(new ButtonListener());
        
        setTitle("Select Unit");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(CELL_WIDTH * MAX_UNIT, 681);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        new Timer(250, e -> updateAnimation()).start();
        new Timer(16, e -> updateFPS()).start();
    }

//    public static void main(String[] args) {
//        new UnitSelector();
//    }
    private void loader(SaveGame progress){
        unitTypes.clear();
        unitTypes.add(new UnitType(Skeleton.class));
        unitTypes.add(new UnitType(Slime.class));
        unitTypes.add(new UnitType(Kaniwall.class));
        if (progress.get_Stage_Num(1) || DEBUG_MODE){
            unitTypes.add(new UnitType(Explosive_turtle.class));
            unitTypes.add(new UnitType(Mimic.class));
        }if(progress.get_Stage_Num(2)|| DEBUG_MODE){
            unitTypes.add(new UnitType(SemiAutoBot.class));
            unitTypes.add(new UnitType(BigBall.class));
        }if(progress.get_Stage_Num(3)|| DEBUG_MODE){
            unitTypes.add(new UnitType(Vampire.class));
            unitTypes.add(new UnitType(FlatSlime.class));
        }if(progress.get_Stage_Num(4)|| DEBUG_MODE){
            unitTypes.add(new UnitType(Moai.class));
            unitTypes.add(new UnitType(GiveawaySlime.class));
        }if(progress.get_Stage_Num(5)|| DEBUG_MODE){
            unitTypes.add(new UnitType(MiPya.class));
            unitTypes.add(new UnitType(BlackSkeleton.class));
        }if(progress.get_Stage_Num(6)|| DEBUG_MODE){
            unitTypes.add(new UnitType(Explosion.class));
            unitTypes.add(new UnitType(Snake.class));
        }if(progress.get_Stage_Num(7)|| DEBUG_MODE){
            unitTypes.add(new UnitType(AlphaWolf.class));
            unitTypes.add(new UnitType(Werewolf.class));
        }if(progress.get_Stage_Num(8)|| DEBUG_MODE){
            unitTypes.add(new UnitType(Python.class));
            unitTypes.add(new UnitType(Cannon.class));
        }if(progress.get_Stage_Num(9)|| DEBUG_MODE){
            unitTypes.add(new UnitType(Nike.class));
            unitTypes.add(new UnitType(Ghost.class));
            unitTypes.add(new UnitType(JavaSkeleton.class));
            unitTypes.add(new UnitType(CandlesExplosion.class));
            unitTypes.add(new UnitType(Tofu.class));
        }
    }
    

    private void updateAnimation() {
        bgPreviewLabel.updateFrame();
        repaint();
    }

    private void updateFPS() {
        healthStatLabel.updateFrame();
        atkStatLabel.updateFrame();
        atkSpeedLabel.updateFrame();
        cooldownLabel.updateFrame();
    }
    
    private void updateUnitInsertBox() {
        for (int i = 0; i < MAX_UNIT; i++) {
            if (i <= unitChosens.size() - 1) {
                unitInsertBoxs[i].setUnit(unitChosens.get(i));
            } else {
                unitInsertBoxs[i].setUnit(null);
            }
        }
        if (unitChosens.size() >= 3) {
            goButton.setBackground(Color.gray);
            if (cof != null && type.equals("server")) {
                if (isCliReady) {
                    goButton.setEnabled(true);
                }
            } else {
                goButton.setEnabled(true);
            }
            
            warnLabel.setText(type.equals("server") ? 
                    "     ":"                                   ");
        } else {
            goButton.setEnabled(false);
            warnLabel.setText("Need at least 3 units");
        }
        revalidate();
        repaint();
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(goButton)) {
                if (unitChosens.size() >= 3) {
                    if (cof != null && type.equals("server")) {
                        if (isCliReady) {
                            dispose();
                        }
                    } else {
                        dispose();
                    }
                } else {
                    System.out.println("You need to have Units at least 3");
                }
            }
        }
    }
    
    // PUBLIC use
    public List<UnitType> getResultUnits() {
        List<UnitType> unitTypes = new ArrayList<>();
        for (UnitLabelBox ulb: unitChosens) {
            unitTypes.add(ulb.getUnitType());
        }
        return unitTypes;
    }
    
    //for Socket
    public static List<UnitType> getAllUnitTypes() {
        return unitTypes;
    }
    
    public static UnitType getUnitTypeFromName(String name) {
        for (UnitType unitType: unitTypes) {
            if (unitType.getClassName().equals(name)) {
                return unitType;
            }
        }
        return null;
    }
    
    public static String toUnitTypesStr(List<UnitType> unitTypes1) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (UnitType unitType: unitTypes1) {
            String msg = (isFirst ? "":" ") + unitType.getClassName();
            sb.append(msg);
            isFirst = false;
        }
        return sb.toString();
    }

    public void cliReady() {
        isCliReady = true;
        warnNotReadyLabel.setVisible(false);
        updateUnitInsertBox();
    }
    
    private class BGPreviewLabel extends JLabel {
        private UnitSpriteSheets unitSp;
        private int currentFrameIdle, currentFrameAtk;
        private BufferedImage actionIdle, actionATK;
        private final int frame_Width = 32;
        private int total_Frame_ATK;
        private int total_Frame_Idle;
        public static final int OFFSET_X = 47, OFFSET_Y = 61;
        
        public BGPreviewLabel(Icon image) {
            super(image);
        }

        public void setUnitSp(UnitSpriteSheets unitSp) {
            this.unitSp = unitSp;
            currentFrameIdle = 0;
            currentFrameAtk = 0;
            actionIdle = unitSp.getActionIdle();
            actionATK = unitSp.getActionAtk();
            
            total_Frame_Idle = actionIdle.getWidth() / frame_Width;
            total_Frame_ATK = actionATK.getWidth() / frame_Width;
            repaint();
        }
        
        public void updateFrame() {
            if (unitSp != null) {
                currentFrameIdle = (currentFrameIdle + 1) % total_Frame_Idle;
                currentFrameAtk = (currentFrameAtk + 1) % total_Frame_ATK;
            } 
        }
        
        public BufferedImage getFrameIdle() {
            return actionIdle.getSubimage(currentFrameIdle * frame_Width, 0, frame_Width, frame_Width);
        }
        
        public BufferedImage getFrameAtk() {
            return actionATK.getSubimage(currentFrameAtk * frame_Width, 0, frame_Width, frame_Width);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (unitSp != null) {
                g.drawImage(getFrameIdle(), OFFSET_X, OFFSET_Y, CELL_WIDTH, CELL_HEIGHT, null);
                g.drawImage(getFrameAtk(), OFFSET_X + CELL_WIDTH, OFFSET_Y, CELL_WIDTH, CELL_HEIGHT, null);
            }
        }
    }
    
    private class UnitLabelBox extends JLabel{
        private UnitType unit;
        private boolean isMouseHovered = false;
        private boolean isChosen = false;
        
        public UnitLabelBox(UnitType unitType) {
            super();
            setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
            this.unit = unitType;
            addMouseListener(new BoxMouseListenr());
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image iconImage;
            iconImage = ImgManager.loadIcon("frame_op1");
            g.drawImage(iconImage, 0, 0, CELL_WIDTH, CELL_HEIGHT, this);
            iconImage = ImgManager.loadIcon("frame_operator");
            g.drawImage(iconImage, 0, 0, CELL_WIDTH, CELL_HEIGHT, this);
            
            if (unit != null) {
                g.drawImage(unit.getProfileImg(), 0, 0, CELL_WIDTH, CELL_HEIGHT, this);
                g.drawImage(unit.getRoleIconImg(), CELL_WIDTH - 35, 0, 30, 30, this);
                g.setColor(new Color(162, 252, 255));
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
                g.drawString(unit.getManaCost() + "", 5, CELL_HEIGHT - 5);
                if (isChosen) {
                    iconImage = ImgManager.loadIcon("blackLowOpacityBG");
                    g.drawImage(iconImage, 0, 0, CELL_WIDTH, CELL_HEIGHT, this);
                }
                if (isMouseHovered) {
                    if (unitChosens.size() >= MAX_UNIT) {
                        iconImage = ImgManager.loadIcon("white_less_place_hover");
                    } else if (unitChosens.contains(this)) {
                        iconImage = ImgManager.loadIcon("red_registered_hover");
                    } else {
                        iconImage = ImgManager.loadIcon("green_registerable_hover");
                    }
                    g.drawImage(iconImage, 0, 0, CELL_WIDTH, CELL_HEIGHT, this);
                }
            }
        }

        public UnitType getUnitType() {
            return unit;
        }
        
        public void setIsMouseHovered(boolean isMouseHovered) {
            this.isMouseHovered = isMouseHovered;
            repaint();
        }

        public void setIsChosen(boolean isChosen) {
            this.isChosen = isChosen;
        }
    }
    
    private class UnitInsertBox extends JLabel {
        private UnitLabelBox unitBox;
        private boolean isMouseHovered = false;
        
        public UnitInsertBox() {
            super();
            setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
            addMouseListener(new BoxMouseListenr());
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image iconImage;
            iconImage = ImgManager.loadIcon("frame_op1");
            g.drawImage(iconImage, 0, 0, CELL_WIDTH, CELL_HEIGHT, this);
            iconImage = ImgManager.loadIcon("frame_operator");
            g.drawImage(iconImage, 0, 0, CELL_WIDTH, CELL_HEIGHT, this);
            
            if (unitBox != null) {
                UnitType unitType = unitBox.getUnitType();
                g.drawImage(unitType.getProfileImg(), 0, 0, CELL_WIDTH, CELL_HEIGHT, this);
                g.drawImage(unitType.getRoleIconImg(), CELL_WIDTH - 35, 0, 30, 30, this);
                g.setColor(new Color(162, 252, 255));
                g.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
                g.drawString(unitType.getManaCost() + "", 5, CELL_HEIGHT - 5);
                if (isMouseHovered) {
                    iconImage = ImgManager.loadIcon("recall_mini_place_hover");
                    g.drawImage(iconImage, 0, 0, CELL_WIDTH, CELL_HEIGHT, this);
                }
            }
        }

        public UnitLabelBox getUnit() {
            return unitBox;
        }

        public void setUnit(UnitLabelBox unit) {
            this.unitBox = unit;
        }

        public void setIsMouseHovered(boolean isMouseHovered) {
            this.isMouseHovered = isMouseHovered;
            repaint();
        }
    }
    
    private class UnitStatLabel extends JLabel {
        private JProgressBar progressBar;
        private JLabel label;
        private final String text;
        private int value;
        private int motionValue;
        private int reverseValue;
        
        
        public UnitStatLabel(String text) {
            this(text, 500, -1);
        }
        
        public UnitStatLabel(String text, int maxValue) {
            this(text, maxValue, -1);
        }
        
        public UnitStatLabel(String text, int maxValue, int reverseValue) {
            this.text = text;
            this.reverseValue = reverseValue;
            motionValue = value;
            
            
            setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
            setLayout(new GridLayout(1, 2));
            setMaximumSize(new Dimension(250, 30));
            setAlignmentX(CENTER);
            
            label = new JLabel();
            progressBar = new JProgressBar(0, maxValue);
            setValue(value);
            
            label.setHorizontalAlignment(JLabel.RIGHT);
            label.setFont(new Font("Helvetica", Font.BOLD, 14));
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            label.setForeground(Color.white);
            
            progressBar.setBackground(new Color(0x222222));
            progressBar.setForeground(new Color(0xA6F3EE));
            progressBar.setBorderPainted(false);

            add(label);
            add(progressBar);
        }
        
        public void setValue(int value) {
            label.setText(text + ":  " + String.format("%03d", value));
            if (reverseValue == -1) {
                this.value = value;
            } else {
                if (value != 0) {
                    this.value = (reverseValue == -1) ? value : reverseValue / value;
                } else {
                    this.value = 0;
                }
            }
//            System.out.println(reverseValue / value);
        }
        
        public void updateFrame() {
            motionValue = (int) Math.round(motionValue + 0.3 * (value - motionValue));
            progressBar.setValue(motionValue);
        }
    }
    
    private class UnitDescription extends JLabel {
        private final JTextArea unitDescTextArea;
        
        public UnitDescription() {
            unitDescTextArea = new JTextArea("???");
            
            setLayout(new GridLayout(1, 1));
            setMaximumSize(new Dimension(270, 100));
            setBorder(BorderFactory.createEmptyBorder(5, 2, 0, 2));
            unitDescTextArea.setBorder(BorderFactory.createLineBorder(Color.darkGray));
            unitDescTextArea.setEnabled(false);
            unitDescTextArea.setDisabledTextColor(Color.white);
            unitDescTextArea.setLineWrap(true);
            unitDescTextArea.setWrapStyleWord(true);
            unitDescTextArea.setBackground(Color.darkGray);
            add(unitDescTextArea);
        }
        
        public void setText(String text) {
            if (unitDescTextArea != null) {
                unitDescTextArea.setText(text);
            }
        }
    }
    
    
    private void updateStatus(UnitType unitType) {
        bgPreviewLabel.setUnitSp(unitType.getUnitSp());
        unitNameLabel.setText(StringFormatter.formatString(unitType.getClassName()));
        unitNameLabel.setIcon(new ImageIcon(unitType.getRoleIconImg()));
        roleLabel.setText("Role: " + unitType.getRoleName() + "  -  " + unitType.getManaCost() + " c");
        healthStatLabel.setValue(unitType.getHealth());
        atkStatLabel.setValue(unitType.getAtk());
        atkSpeedLabel.setValue((int)(unitType.getAtkSpeed() * 10));
        cooldownLabel.setValue((int)unitType.getCooldown());
        unitDescription.setText(unitType.getDesc());
        
    }
    
    private class BoxMouseListenr extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() instanceof UnitLabelBox) {
                UnitLabelBox unitLabelBox = (UnitLabelBox)e.getSource();
                unitLabelBox.setIsMouseHovered(true);
                UnitType unitType = unitLabelBox.getUnitType();
                Audio.play(AudioName.BUTTON_HOVER);
                updateStatus(unitType);
                
            } else if (e.getSource() instanceof UnitInsertBox) {
                Audio.play(AudioName.BUTTON_HOVER);
                UnitInsertBox unitLabelBox = (UnitInsertBox)e.getSource();
                unitLabelBox.setIsMouseHovered(true);
                UnitLabelBox unit = unitLabelBox.getUnit();
                if (unit != null) {
                    updateStatus(unit.getUnitType());
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() instanceof UnitLabelBox) {
                UnitLabelBox unitLabelBox = (UnitLabelBox)e.getSource();
                unitLabelBox.setIsMouseHovered(false);
            } else if (e.getSource() instanceof UnitInsertBox) {
                UnitInsertBox unitLabelBox = (UnitInsertBox)e.getSource();
                unitLabelBox.setIsMouseHovered(false);
            }
        }
        
        

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() instanceof UnitLabelBox) {
                if (unitChosens.size() < MAX_UNIT) {
                    UnitLabelBox unitLabelBox = (UnitLabelBox)e.getSource();
                    if (!unitChosens.contains(unitLabelBox)) {
                        Audio.play(AudioName.PLANT_PICK_UP);
                        unitChosens.add(unitLabelBox);
                        unitLabelBox.setIsChosen(true);
                        updateUnitInsertBox();
                    } else {
                        Audio.play(AudioName.PLANT_CANT_PICK_UP);
                        System.out.println("cannot use same field");
                    }
                } else {
                    Audio.play(AudioName.PLANT_CANT_PICK_UP);
                    System.out.println("maximum!");
                }
            } else if (e.getSource() instanceof UnitInsertBox) {
                if (!unitChosens.isEmpty()) {
                    UnitInsertBox unitLabelBox = (UnitInsertBox)e.getSource();
                    UnitLabelBox unit = unitLabelBox.getUnit();
                    if (unit != null) {
                        Audio.play(AudioName.PLANT_DELETE);
                        unit.setIsChosen(false);
                        unitChosens.remove(unit);
                        updateUnitInsertBox();
                    }
                }
            }
            repaint();
        }
    }
    
    
}
