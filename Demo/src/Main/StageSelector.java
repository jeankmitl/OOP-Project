package Main;

import Asset.Audio;
import CoOpSystem.CoOpFrame;
import Main.Stages.*;
import java.awt.HeadlessException;
import java.io.File;
import javax.swing.*;

public class StageSelector extends JFrame {
    private StageSelectorPanel panel;
    private GamePanel game;
    private String type;
    private CoOpFrame cof;

    public StageSelector() {
        this("solo", null);
    }
    
    public StageSelector(String type) {
        this(type, null);
    }
    
    public StageSelector(String type, CoOpFrame cof) {
        this.type = type;
        this.cof = cof;
        panel = new StageSelectorPanel(this, type);
        add(panel);
        setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
    
        if (type.equals("2p")) {
            setTitle("Select stage - 2 Players");
        } else {
            setTitle("Select stage");
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1264, 681);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        
        Audio.playMusic("mainMenu");
    }

    public void loadStage(String stageName) {
        if (stageName.equals("Main")){
            dispose();
            new MainMenu();
            return;
        }
        if (stageName.equals("StageSelector")){
            dispose();
            new StageSelector();
            return;
        }
        if (stageName.equals("Back")) {
            int res = JOptionPane.showConfirmDialog(this, "Do you want to Exit during the game?",
                    "Exit Level", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                game.stopGameLoop();
                getContentPane().removeAll();
                getContentPane().add(panel);
                setTitle("Select stage");
                revalidate();
                repaint();
                Audio.playMusic("mainMenu");
            }
            return;
        }
        getContentPane().removeAll();
        final EnemySummoner summoner;
        System.out.println(stageName);
        if (stageName.equals("St9")) { // Boss
            game = BossFightGamePanel.getInstance(this, new StageBossFight());
        } else {
            switch (stageName) {
                case "St10" -> summoner = new stage_beta();
                case "St1" -> summoner = new stage_Tutorial();
                case "St2" -> summoner = new stage2();
                case "St3" -> summoner = new stage3();
                case "St4" -> summoner = new stage4();
                case "St5" -> summoner = new stage5();
                case "St6" -> summoner = new stage6();
                case "St7" -> summoner = new stage7();
                case "St8" -> summoner = new stage8();
                default -> throw new AssertionError();
            }
            if (type.equals("2p")) {
                game = GamePanel2Player.getInstance(this, summoner);
            } else {
                game = GamePanel.getInstance(this, summoner);
            }
        }
        getContentPane().add(game);

        revalidate();
        repaint();
    }
}
