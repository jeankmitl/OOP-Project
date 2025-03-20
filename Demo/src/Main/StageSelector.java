package Main;

import javax.swing.*;

public class StageSelector extends JFrame {
    private StageSelectorPanel panel;
    
    public StageSelector() {
        setTitle("Select stage");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new StageSelectorPanel(this);
        add(panel);

        setVisible(true);
    }

    public void loadStage(String stageName) {
        getContentPane().removeAll();
        if (stageName.equals("St10")) {
            GamePanel gamePanel = new stage_beta();
            getContentPane().add(gamePanel);
            addKeyListener(gamePanel.new GameKeyboardListener());
            setTitle("Stage Beta");
        } else if (stageName.equals("St1")) {
            stage_Tutorial tutorial = new stage_Tutorial();
            getContentPane().add(tutorial);
            addKeyListener(tutorial.new GameKeyboardListener());
            setTitle("Stage 1");
        } else if (stageName.equals("St2")) {
            stage2 two = new stage2();
            getContentPane().add(two);
            addKeyListener(two.new GameKeyboardListener());
            setTitle("Stage 2");
        } else if (stageName.equals("St3")) {
            stage3 three = new stage3();
            getContentPane().add(three);
            addKeyListener(three.new GameKeyboardListener());
            setTitle("Stage 3");
        } else if (stageName.equals("St4")) {
            stage4 four = new stage4();
            getContentPane().add(four);
            addKeyListener(four.new GameKeyboardListener());
            setTitle("Stage 4");
        } else if (stageName.equals("St5")) {
            stage5 five = new stage5();
            getContentPane().add(five);
            addKeyListener(five.new GameKeyboardListener());
            setTitle("Stage 5");
        } else if (stageName.equals("St6")) {
            stage6 six = new stage6();
            getContentPane().add(six);
            addKeyListener(six.new GameKeyboardListener());
            setTitle("Stage 6");
        } else if (stageName.equals("St7")) {
            stage7 seven = new stage7();
            getContentPane().add(seven);
            addKeyListener(seven.new GameKeyboardListener());
            setTitle("Stage 7");
        } else if (stageName.equals("St8")) {
            stage8 eight = new stage8();
            getContentPane().add(eight);
            addKeyListener(eight.new GameKeyboardListener());
            setTitle("Stage 8");
        } else if (stageName.equals("St9")) {
            stagechinatown dlc = new stagechinatown();
            getContentPane().add(dlc);
            addKeyListener(dlc.new GameKeyboardListener());
            setTitle("Stage Special China Edition");
        }else if(stageName.equals(ABORT))
        revalidate();
        repaint();
    }
}

