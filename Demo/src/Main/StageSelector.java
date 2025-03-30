package Main;

import Asset.Audio;
import Asset.AudioName;
import CoOpSystem.CoKeys;
import CoOpSystem.CoOpFrame;
import Main.Stages.*;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.*;

public class StageSelector extends JFrame {
    private StageSelectorPanel panel;
    private GamePanel game;
    private GamePanel2Player game2P;
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
        panel = new StageSelectorPanel(this, type, cof);
        add(panel);
        setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
    
        if (type.equals("2p")) {
            setTitle("Stage 2 Players" + ((cof != null) ? ": Server":""));
        } else if (type.equals("cli")) {
            setTitle("Stage 2 Players: Client");
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
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                LoadingScreen loadingScreen = new LoadingScreen();

                @Override
                protected Void doInBackground() throws Exception {
                    Thread.sleep(500);
                    new MainMenu();
                    dispose();
                    return null;
                }

                @Override
                protected void done() {
                    loadingScreen.dispose();
                }
            };
            worker.execute();
            return;
        }
        if (stageName.equals("StageSelector")){
            LoadingScreen loadingScreen = new LoadingScreen();
            SwingWorker<Void, Void> worker = new SwingWorker<>() {

                @Override
                protected Void doInBackground() throws Exception {
                    Thread.sleep(500);
                    new StageSelector();
                    dispose();
                    return null;
                }

                @Override
                protected void done() {
                    loadingScreen.dispose();
                }
            };
            worker.execute();
            return;
        }
        System.out.println("who close");

        if (stageName.equals("Back")) {
            game.stopGameLoop();
            getContentPane().removeAll();
            getContentPane().add(panel);
            setTitle("Select stage");
            revalidate();
            repaint();
            return;
        }
        if (stageName.equals("win")) {
            game.stopGameLoop();
            panel.save();
            getContentPane().removeAll();
            getContentPane().add(panel);
            setTitle("Select stage");
            revalidate();
            repaint();
            return;
        }
        getContentPane().removeAll();
        final EnemySummoner summoner;
        if (cof != null && !type.equals("cli")) {
            cof.sendOne(CoKeys.STAGE_NAME, stageName);
        }
        if (stageName.equals("St9")) { // Boss
            if (type.equals("2p") || type.equals("cli")) {
                game = BossFightGamePanel2PlayerRough.getInstance(this, new StageBossFight());
            } else {
                game = BossFightGamePanel.getInstance(this, new StageBossFight());
            }
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
            if (type.equals("2p") || type.equals("cli")) {
                game2P = GamePanel2Player.getInstance(this, summoner, type, cof);
                game = game2P;
                if (cof != null) cof.invoke(CoKeys.GET_GAME_PANEL);
            } else {
                game = GamePanel.getInstance(this, summoner);
            }
        }
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            LoadingScreen loadingScreen = new LoadingScreen();
            
            @Override
            protected Void doInBackground() throws Exception {
                loadingScreen.toFront();
                getContentPane().add(game);
                Thread.sleep(500);
                revalidate();
                repaint();
                return null;
            }

            @Override
            protected void done() {
                loadingScreen.dispose();
            }
        };
        worker.execute();
    }
    
    // All of this is for Socket
    public GamePanel2Player getGamePanel2Player() {
        return game2P;
    }
    
    public void setHover(int x, int y) {
        panel.setHover(x, y);
    }
    
    public void setP2Hover(int x, int y) {
        panel.setP2Hover(x, y);
    }
}
