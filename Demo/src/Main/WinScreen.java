package Main;

import java.awt.*;
import javax.swing.*;

public class WinScreen extends JFrame {

    public WinScreen() {

        try {
            JPanel pr = new JPanel();
            pr.setBackground(new Color(244,212,148));
            pr.setLayout(new BorderLayout());
            add(pr);

            setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
            ImageIcon win = new ImageIcon("src/Asset/Img/Icons/WinScreen.gif");
            JLabel winScreen = new JLabel(win, SwingConstants.CENTER);
            winScreen.setHorizontalAlignment(SwingConstants.CENTER);
            winScreen.setVerticalAlignment(SwingConstants.CENTER);
            pr.add(winScreen, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Wrong image path.");
        }

        setTitle("Win!!!");
        setSize(1264, 681);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        new Timer(5000, e -> dispose()).start();
    }

}
