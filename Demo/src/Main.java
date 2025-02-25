import javax.swing.*;

public class Main {

    public static JFrame frame;
    public static GamePanel gamePanel;

    public Main() {
        frame = new JFrame("Defense of the Dungeon");
        gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

}
