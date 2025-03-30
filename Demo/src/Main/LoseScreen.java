package Main;

import java.awt.*;
import javax.swing.*;

public class LoseScreen extends JFrame {

    public LoseScreen() {

        try {
            JPanel pr = new JPanel();
            pr.setBackground(new Color(0x1a3a46));
            pr.setLayout(new BorderLayout());
            add(pr);

            setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
            ImageIcon win = new ImageIcon("src/Asset/Img/Icons/you_lose_img.png");
            JLabel winScreen = new JLabel(win, SwingConstants.CENTER);
            winScreen.setHorizontalAlignment(SwingConstants.CENTER);
            winScreen.setVerticalAlignment(SwingConstants.CENTER);
            pr.add(winScreen, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Wrong image path.");
        }

        setTitle("Lose...");
        setSize(1264, 681);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }

}
