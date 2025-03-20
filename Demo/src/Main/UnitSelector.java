/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
import Asset.StringFormatter;
import Entities.Units.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 *
 * @author anawi
 */
public class UnitSelector extends JFrame {
    private static final List<UnitType> unitTypes = new ArrayList<>();
    private final JPanel unitPanelList, operatorPanel, unitStatsPanel;
    private final JPanel leftRightPanel, upDownPanel;
    private final BGPreviewLabel bgPreviewLabel;
    private final UnitStatLabel healthStatLabel, atkStatLabel, atkSpeedLabel, cooldownLabel;
    private final JLabel unitNameLabel, roleLabel;
    
    public static final int CELL_WIDTH = 95;
    public static final int CELL_HEIGHT = 95;
    public static final int COLS = 4;
    public static final int GAP = 7;
    
    public UnitSelector() {
        setTitle("Select Unit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 720);
        setResizable(false);
        setLocationRelativeTo(null);
        ImageIcon bgPreviewImg = new ImageIcon(ImgManager.loadIcon("bg_for_preview"));
        
        unitPanelList = new JPanel();
        operatorPanel = new JPanel();
        leftRightPanel = new JPanel();
        upDownPanel = new JPanel();
        unitStatsPanel = new JPanel();
        bgPreviewLabel = new BGPreviewLabel(bgPreviewImg);
        unitNameLabel = new JLabel("???");
        roleLabel = new JLabel("role: ");
        healthStatLabel = new UnitStatLabel("Health", 1000);
        atkStatLabel = new UnitStatLabel("Atk", 200);
        atkSpeedLabel = new UnitStatLabel("Atk speed", 100);
        cooldownLabel = new UnitStatLabel("Cooldown", 100);
        
        
        unitPanelList.setLayout(new BoxLayout(unitPanelList, BoxLayout.Y_AXIS));
        unitPanelList.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.lightGray, Color.yellow));
        upDownPanel.setLayout(new BorderLayout());
        leftRightPanel.setLayout(new BorderLayout());
        operatorPanel.setPreferredSize(new Dimension(0, 200));
        unitStatsPanel.setLayout(new BoxLayout(unitStatsPanel, BoxLayout.Y_AXIS));
        unitNameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        unitNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 2, 0));
        roleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        roleLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 7, 0));
        
        
        for (int i=0; i<10; i++) {
            unitTypes.add(new UnitType(Skeleton.class));
            unitTypes.add(new UnitType(Slime.class));
            unitTypes.add(new UnitType(Kaniwall.class));
            unitTypes.add(new UnitType(Mimic.class));
            unitTypes.add(new UnitType(SemiAutoBot.class));
            unitTypes.add(new UnitType(BigBall.class));
            unitTypes.add(new UnitType(GolemSupport.class));
            unitTypes.add(new UnitType(Explosive_turtle.class));
            unitTypes.add(new UnitType(Nike.class));
            unitTypes.add(new UnitType(MiPya.class));
            unitTypes.add(new UnitType(Snake.class));
            unitTypes.add(new UnitType(Python.class));
            unitTypes.add(new UnitType(Explosion.class));
            unitTypes.add(new UnitType(GiveawaySlime.class));
            
        }
        
        for (int i = 0; i < unitTypes.size(); i += COLS) {
            JPanel rowPanel = new JPanel(new GridLayout(1, COLS, GAP, 0));
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

        JScrollPane scrollUnitPanelList = new JScrollPane(unitPanelList);
        scrollUnitPanelList.getVerticalScrollBar().setUnitIncrement(16);
        scrollUnitPanelList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        
        
        
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
        
        setVisible(true);
        
        new Timer(250, e -> updateAnimation()).start();
        new Timer(16, e -> updateFPS()).start();
    }
    
    public static void main(String[] args) {
        new UnitSelector();
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
    
    private class BGPreviewLabel extends JLabel {
        private UnitSpriteSheets unitSp;
        private int currentFrameIdle, currentFrameAtk;
        private BufferedImage actionIdle, actionATK;
        private final int frame_Width = 32;
        private int total_Frame_ATK;
        private int total_Frame_Idle;
        public static final int OFFSET_X = 40, OFFSET_Y = 61;
        
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
            g.drawImage(unit.getProfileImg(), 0, 0, CELL_WIDTH, CELL_HEIGHT, this);
            g.drawImage(unit.getRoleIconImg(), CELL_WIDTH - 35, 0, 30, 30, this);
            
            g.setColor(new Color(162, 252, 255));
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            g.drawString(unit.getManaCost() + "", 5, CELL_HEIGHT - 5);
            
        }

        public UnitType getUnitType() {
            return unit;
        }
    }
    
    private class UnitStatLabel extends JLabel {
        private JProgressBar progressBar;
        private JLabel label;
        private final String text;
        private int value;
        private int motionValue;
        
        
        public UnitStatLabel(String text) {
            this(text, 500);
        }
        
        public UnitStatLabel(String text, int maxValue) {
            this.text = text;
            motionValue = value;
            
            setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
            setLayout(new GridLayout(1, 2));
            setMaximumSize(new Dimension(250, 30));
            
            label = new JLabel();
            progressBar = new JProgressBar(0, maxValue);
            setValue(value);
            
            label.setHorizontalAlignment(JLabel.RIGHT);
            label.setFont(new Font("Helvetica", Font.BOLD, 14));
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            
            
            progressBar.setBackground(Color.DARK_GRAY);
            progressBar.setForeground(new Color(0xA6F3EE));
            progressBar.setBorderPainted(false);

            add(label);
            add(progressBar);
        }
        
        public void setValue(int value) {
            label.setText(text + ":  " + String.format("%03d", value));
            this.value = value;
        }
        
        public void updateFrame() {
            motionValue = (int) Math.round(motionValue + 0.3 * (value - motionValue));
            progressBar.setValue(motionValue);
        }
    }
    
    private class BoxMouseListenr extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() instanceof UnitLabelBox) {
                UnitLabelBox unitLabelBox = (UnitLabelBox)e.getSource();
                UnitType unitType = unitLabelBox.getUnitType();
                System.out.println(unitType.getManaCost());
                Audio.play(AudioName.BUTTON_HOVER);
                
                bgPreviewLabel.setUnitSp(unitType.getUnitSp());
                unitNameLabel.setText(StringFormatter.formatString(unitType.getClassName()));
                roleLabel.setText("Role: " + unitType.getRoleName() + "  -  " + unitType.getManaCost() + " c");
                healthStatLabel.setValue(unitType.getHealth());
                atkStatLabel.setValue(unitType.getAtk());
                atkSpeedLabel.setValue((int)(unitType.getAtkSpeed() * 10));
                cooldownLabel.setValue((int)unitType.getCooldown());
                
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Audio.play(AudioName.PLANT_PICK_UP);
        }
    }
    
}
