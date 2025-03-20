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
        if (stageName.equals("Beta")) {
            GamePanel gamePanel = new stage_beta();
            getContentPane().add(gamePanel);
            addKeyListener(gamePanel.new GameKeyboardListener());
            setTitle("Stage Beta");
        } else if (stageName.equals("St1")) {
            stage_Tutorial tutorial = new stage_Tutorial();
            getContentPane().add(tutorial);
            addKeyListener(tutorial.new GameKeyboardListener());
            setTitle("Stage 1");
        }
        revalidate();
        repaint();
    }
}

