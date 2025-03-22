package Main;

import Main.Stages.*;
import javax.swing.*;

public class StageSelector extends JFrame {
    private StageSelectorPanel panel;
    private GamePanel game;
    
    public StageSelector() {

        panel = new StageSelectorPanel(this);
        add(panel);
        setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
    
        setTitle("Select stage");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public StageSelector(GamePanel panel){
        this.game = panel;
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
            case "St9" -> summoner = new StageBossFight();
            default -> throw new AssertionError();
        }
        game = GamePanel.getInstance(this, summoner);
        getContentPane().add(game);
//            game = new stage2(this);
//            addKeyListener(game.new GameKeyboardListener());
//            getContentPane().add(game);
//            setTitle("Stage 2");
//        } else if (stageName.equals("St3")) {
//            game = new stage3(this);
//            addKeyListener(game.new GameKeyboardListener());
//            getContentPane().add(game);
//            setTitle("Stage 3");
//        } else if (stageName.equals("St4")) {
//            game = new stage4(this);
//            addKeyListener(game.new GameKeyboardListener());
//            getContentPane().add(game);
//            setTitle("Stage 4");
//        } else if (stageName.equals("St5")) {
//            game = new stage5(this);
//            addKeyListener(game.new GameKeyboardListener());
//            getContentPane().add(game);
//            setTitle("Stage 5");
//        } else if (stageName.equals("St6")) {
//            game = new stage6(this);
//            addKeyListener(game.new GameKeyboardListener());
//            getContentPane().add(game);
//            setTitle("Stage 6");
//        } else if (stageName.equals("St7")) {
//            game = new stage7(this);
//            addKeyListener(game.new GameKeyboardListener());
//            getContentPane().add(game);
//            setTitle("Stage 7");
//        } else if (stageName.equals("St8")) {
//            game = new stage8(this);
//            addKeyListener(game.new GameKeyboardListener());
//            getContentPane().add(game);
//            setTitle("Stage 8");
//        } else if (stageName.equals("St9")) {
//            game = new StageBossFight(this);
//            addKeyListener(game.new GameKeyboardListener());
//            getContentPane().add(game);
//            setTitle("No China Anymore, There is only HELL ahead of you! Now TATAKAE!!!");
//        }else if(stageName.equals("Back")){
//            int res = JOptionPane.showConfirmDialog(this, "Do you want to Exit during the game?",
//                    "Exit Level", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
//            if (res == JOptionPane.YES_OPTION) {
//                getContentPane().removeAll();
//                getContentPane().add(panel);
//                setTitle("Select stage");
//            }
//        }

        revalidate();
        repaint();
    }
}
