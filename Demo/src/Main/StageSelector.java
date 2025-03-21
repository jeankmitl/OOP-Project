package Main;

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
        if (!stageName.equals("Back")) {
            getContentPane().removeAll();
        }
        if (stageName.equals("St10")) {
            game = new stage_beta(this);
            addKeyListener(game.new GameKeyboardListener());
            getContentPane().add(game);
            setTitle("Stage Beta");
        } else if (stageName.equals("St1")) {
            game = new stage_Tutorial(this);
            addKeyListener(game.new GameKeyboardListener());
            getContentPane().add(game);
            setTitle("Stage 1");
        } else if (stageName.equals("St2")) {
            game = new stage2(this);
            addKeyListener(game.new GameKeyboardListener());
            getContentPane().add(game);
            setTitle("Stage 2");
        } else if (stageName.equals("St3")) {
            game = new stage3(this);
            addKeyListener(game.new GameKeyboardListener());
            getContentPane().add(game);
            setTitle("Stage 3");
        } else if (stageName.equals("St4")) {
            game = new stage4(this);
            addKeyListener(game.new GameKeyboardListener());
            getContentPane().add(game);
            setTitle("Stage 4");
        } else if (stageName.equals("St5")) {
            game = new stage5(this);
            addKeyListener(game.new GameKeyboardListener());
            getContentPane().add(game);
            setTitle("Stage 5");
        } else if (stageName.equals("St6")) {
            game = new stage6(this);
            addKeyListener(game.new GameKeyboardListener());
            getContentPane().add(game);
            setTitle("Stage 6");
        } else if (stageName.equals("St7")) {
            game = new stage7(this);
            addKeyListener(game.new GameKeyboardListener());
            getContentPane().add(game);
            setTitle("Stage 7");
        } else if (stageName.equals("St8")) {
            game = new stage8(this);
            addKeyListener(game.new GameKeyboardListener());
            getContentPane().add(game);
            setTitle("Stage 8");
        } else if (stageName.equals("St9")) {
            game = new stagechinatown(this);
            addKeyListener(game.new GameKeyboardListener());
            getContentPane().add(game);
            setTitle("Stage Special China Edition");
        }else if(stageName.equals("Back")){
            int res = JOptionPane.showConfirmDialog(this, "Do you want to Exit during the game?",
                    "Exit Level", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                getContentPane().removeAll();
                getContentPane().add(panel);
                setTitle("Select stage");
            }
        }else if(stageName.equals("Main")){
            dispose();
            new MainMenu();
        }
        revalidate();
        repaint();
    }
}
