package Main;

import CoOpSystem.CoOpFrame;
import Main.Stages.*;
import java.awt.HeadlessException;
import javax.swing.*;

public class StageSelector extends JFrame {
    private StageSelectorPanel panel;
    private GamePanel game;
    private CoOpFrame cof;

    public StageSelector() {
        this(null);
    }
    
    public StageSelector(CoOpFrame cof) {
        this.cof = cof;
        panel = new StageSelectorPanel(this);
        add(panel);
        setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
    
        setTitle("Select stage");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }

    public void loadStage(String stageName) {
        if(stageName.equals("Main")){
            dispose();
            new MainMenu();
            return;
        } else if (stageName.equals("Back")) {
            int res = JOptionPane.showConfirmDialog(this, "Do you want to Exit during the game?",
                    "Exit Level", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                game.stopGameLoop();
                getContentPane().removeAll();
                getContentPane().add(panel);
                setTitle("Select stage");
                revalidate();
                repaint();
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
            game = GamePanel.getInstance(this, summoner);
        }
        getContentPane().add(game);

        revalidate();
        repaint();
    }
}
