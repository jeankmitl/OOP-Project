/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
import CoOpSystem.CoOpFrame;
import DSystem.OTimer;
import Entities.Units.Nike;
import static Main.GamePanel.BAR_X;
import static Main.GamePanel.BAR_Y;
import static Main.GamePanel.CELL_HEIGHT;
import static Main.GamePanel.CELL_WIDTH;
import static Main.GamePanel.COLS;
import static Main.GamePanel.ROWS;
import static Main.GamePanel.remainMana;
import static Main.GamePanel.unitTypes;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author anawi
 */
public class GamePanel2Player extends GamePanel {
    private static GamePanel2Player instance;
    
    public static final int CEMI_WIDTH = CELL_WIDTH / 2;
    public static final int CEMI_HEIGHT = CELL_HEIGHT / 2;
    
    private Image iconImage;
    private static List<UnitType> unitTypesP2;
    private StageSelector stage;
    
    //Player2 Controller
    private int p2Select = 0;
    private int p2PlaceX = 0;
    private int p2PlaceY = 0;
    
    private boolean confirmPlace = false;
    private boolean confirmRecall = false;
    
    protected static int remainManaP2 = 50;
    
    private String type;
    private CoOpFrame cof;
    
    private GamePanel2Player(StageSelector stage, EnemySummoner summoner) {
        super(stage, summoner);
        unitTypesP2 = new ArrayList<>();
        
        this.stage = stage;
        stage.addKeyListener(new P2KeyboardListener());
    }

    @Override
    protected void selectUnitBefore() {
        SwingUtilities.invokeLater(() -> {
            if (cof == null) {
                UnitSelector unitSelector = new UnitSelector(stage, "p1");
                unitSelector.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        unitTypes = ((UnitSelector)e.getSource()).getResultUnits();
                        UnitSelector unitSelector2P = new UnitSelector(stage, "p2");
                        unitSelector2P.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                unitTypesP2 = ((UnitSelector)e.getSource()).getResultUnits();
                                startGameLoop(); // Always call on last GamePanel
                                summonEnemies(); // spawn Enermy in this insted
                            }
                        });
                    }
                });
            } else {
                if (type.equals("cli")) {
                    UnitSelector unitSelector = new UnitSelector(stage, "cli");
                } else {
                    UnitSelector unitSelector = new UnitSelector(stage, "server");
                }
            }
        });
    }
    
    public static GamePanel getInstance(StageSelector stage, EnemySummoner summoner, String type, CoOpFrame cof) {
        if (instance == null) instance = new GamePanel2Player(stage, summoner);
        instance.type = type;
        instance.cof = cof;
        instance.resetGamePanel(stage, summoner);
        return instance;
    }

    @Override
    protected void resetGamePanel(StageSelector stage, EnemySummoner summoner) {
        unitTypesP2.clear();
        super.resetGamePanel(stage, summoner);
        remainManaP2 = remainMana;
    }

    @Override
    public void updateCooldown(double deltaTime) {
        super.updateCooldown(deltaTime);
        for (UnitType unit: unitTypesP2) {
            unit.coolDownTick(deltaTime);
        }
    }
    
    public static void increaseMana(int mana) {
        remainManaP2 += mana;
        if (remainManaP2 > MAX_MANA) {
            remainManaP2 = MAX_MANA;
        }
    }
    
    public static void reduceMana(int mana) {
        if (remainManaP2 - mana < 0) {
            remainManaP2 = 0;
        } else {
            remainManaP2 -= mana;
        }
    }

    @Override
    protected void fixedUpdate(double deltaTime) {
        super.fixedUpdate(deltaTime);
    }
    
    
    
    private int hoverSelectP2X, hoverSelectP2Y;
    private int hoverPlaceP2X, hoverPlaceP2Y;
    private void runDynamicSelectP2(int col, double leap) {
        int gridX = col * CEMI_WIDTH + GRID_OFFSET_X;
        hoverSelectP2X = (int)(hoverSelectP2X + leap * (gridX - hoverSelectP2X));
    }
    
    private void runDynamicHoverP2(int row, int col, double leap) {
        int gridX = col * CELL_WIDTH + GRID_OFFSET_X;
        int gridY = row * CELL_HEIGHT + GRID_OFFSET_Y;
        hoverPlaceP2X = (int)(hoverPlaceP2X + leap * (gridX - hoverPlaceP2X));
        hoverPlaceP2Y = (int)(hoverPlaceP2Y + leap * (gridY - hoverPlaceP2Y));
    }

    @Override
    protected boolean isAllowOn2Player() {
        if (cof != null) {
            return false;
        }
        return true;
    }
    
    @Override
    public void paint2PComp(Graphics g) {
        int BAR_Y2 = BAR_Y - CEMI_HEIGHT;
        
        //Mana P2
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(BAR_X + 635, BAR_Y - 40, 100, 40);
        g.setColor(Color.lightGray);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        g.drawString("" + remainManaP2, BAR_X + 675, BAR_Y - 13); // Display at top-left
        g.setColor(new Color(162, 252, 255, 255));
        iconImage = ImgManager.loadIcon("Mana_icon");
        g.drawImage(iconImage, BAR_X+625, BAR_Y-45,50,50, null);
        
        
        //Player 2
        iconImage = ImgManager.loadIcon("p2_control");
        g.drawImage(iconImage, BAR_X - CELL_WIDTH, BAR_Y, CELL_WIDTH - 30, CELL_HEIGHT - 30, this);
        
        for (int i = 0; i < COLS - 1; i++) {
            iconImage = ImgManager.loadIcon("frame_op1");
            g.drawImage(iconImage, BAR_X + CEMI_WIDTH * i, BAR_Y2, CEMI_WIDTH, CEMI_HEIGHT, this);
            iconImage = ImgManager.loadIcon("frame_operator");
            g.drawImage(iconImage, BAR_X + CEMI_WIDTH * i, BAR_Y2, CEMI_WIDTH, CEMI_HEIGHT, this);
        }
        
        int k = 0;
        for (UnitType unit : unitTypesP2) {
            g.drawImage(unit.getProfileImg(), BAR_X + CEMI_WIDTH * k, BAR_Y2, CEMI_WIDTH, CEMI_HEIGHT, this);
            g.drawImage(unit.getRoleIconImg(), (BAR_X + CEMI_WIDTH * k) + (CEMI_WIDTH - 30), BAR_Y2, 15, 15, this);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            
            if (remainManaP2 >= unit.getManaCost()) {
                g.setColor(new Color(162, 252, 255)); //blue
            } else {
                g.setColor(new Color(255, 110, 120)); //red
            }
            g.drawString(unit.getManaCost() + "", BAR_X + 5 + CEMI_WIDTH * k, BAR_Y2 + CEMI_HEIGHT - 5);
            if (!unit.isNoCoolDown()) {
                g.setColor(Color.WHITE);
                iconImage = ImgManager.loadIcon("blackLowOpacityBG");
                g.drawImage(iconImage, BAR_X + CEMI_WIDTH * k, BAR_Y2, CEMI_WIDTH, CEMI_HEIGHT, this);
                g.drawString((int)unit.getCoolDownElapsed() + 1 + "", BAR_X + 5 + CEMI_WIDTH * k, BAR_Y2 + 15);
            }
            k++;
        }
        

        if (!unitTypesP2.isEmpty()) {
            UnitType unit = unitTypesP2.get(p2Select);
            
            int row = p2PlaceY;
            int col = p2PlaceX;
            runDynamicHoverP2(row, col, 0.3);
            if (confirmPlace) {
                if ((isFieldAvailable(col, row) || isFieldExtraAvailable(unit, row, col)) && !isFieldRestricted(unit, row, col)) {
                    iconImage = ImgManager.loadIcon("green_p2_place_hover");
                } else {
                    iconImage = ImgManager.loadIcon("red_p2_place_hover");
                }
            } else if (confirmRecall) {
                if (!isFieldAvailable(col, row)) {
                    iconImage = ImgManager.loadIcon("recall_p2_place_hover");
                } else {
                    iconImage = ImgManager.loadIcon("red_p2_place_hover");
                }
            } else {
                if (!unit.isNoCoolDown()) {
                    iconImage = ImgManager.loadIcon("blue_wait_p2_place_hover");
                } else if (remainManaP2 < unit.getManaCost()) {
                    iconImage = ImgManager.loadIcon("blue_not_enough_p2_place_hover");
                } else {
                    iconImage = ImgManager.loadIcon("blue_p2_place_hover");
                }
            }
            g.drawImage(iconImage, hoverPlaceP2X, hoverPlaceP2Y, CELL_WIDTH, CELL_HEIGHT, this);
            
            runDynamicSelectP2(p2Select, 0.5);
            if (!unit.isNoCoolDown()) {
                iconImage = ImgManager.loadIcon("white_less_place_hover");
            } else if (remainManaP2 < unit.getManaCost()) {
                iconImage = ImgManager.loadIcon("red_not_enough_place_hover");
            } else {
                iconImage = ImgManager.loadIcon("white_place_hover");
            }
            g.drawImage(iconImage, hoverSelectP2X,  (CELL_HEIGHT * (ROWS + 1)) + 15 - CEMI_HEIGHT, CEMI_WIDTH, CEMI_HEIGHT , this);
        }

        //END Player 2
    }

    private void resetConfirm() {
        confirmPlace = false;
        confirmRecall = false;
    }
    
    private class P2KeyboardListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_S:
                    p2PlaceY = (p2PlaceY + 1) % ROWS;
                    resetConfirm();
                    break;
                case KeyEvent.VK_W:
                    if (--p2PlaceY < 0) p2PlaceY = ROWS-1;
                    resetConfirm();
                    break;
                case KeyEvent.VK_A:
                    if (--p2PlaceX < 0) p2PlaceX = COLS-1;
                    resetConfirm();
                    break;
                case KeyEvent.VK_D:
                    p2PlaceX = (p2PlaceX + 1) % COLS;
                    resetConfirm();
                    break;
                case KeyEvent.VK_Q:
                    if (--p2Select < 0) p2Select = unitTypesP2.size()-1;
                    System.out.println(p2Select);
                    break;
                case KeyEvent.VK_E:
                    p2Select = (p2Select + 1) % unitTypesP2.size();
                    break;
                case KeyEvent.VK_F:
                    UnitType unit = unitTypesP2.get(p2Select);
                    if (unit.isNoCoolDown() && remainManaP2 >= unit.getManaCost()) {
                        if (confirmPlace) {
                            placeUnit(unit, p2PlaceY, p2PlaceX);
                            remainMana += unit.getManaCost();
                            remainManaP2 -= unit.getManaCost();
                            resetConfirm();
                        } else {
                            confirmPlace = true;
                            confirmRecall = false;
                        }
                    } else {
                        Audio.play(AudioName.PLANT_CANT_PICK_UP);
                    }
                    break;
                case KeyEvent.VK_R:
                    if (confirmRecall) {
                        recallUnit(p2PlaceX, p2PlaceY);
                        resetConfirm();
                    } else {
                        confirmPlace = false;
                        confirmRecall = true;
                    }
            }
        }
    }
}
