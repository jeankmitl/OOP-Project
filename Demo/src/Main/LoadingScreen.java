package Main;

import java.awt.*;
import javax.swing.*;

public class LoadingScreen extends JFrame {
    public LoadingScreen() {

        try {
            JPanel pr = new JPanel();
            pr.setBackground(new Color(55, 55, 71));
            pr.setLayout(new BorderLayout());
            add(pr);

            setIconImage(new ImageIcon(getClass().getResource("/Asset/Img/Icons/icon.png")).getImage());
            ImageIcon load = new ImageIcon("src/Asset/Img/Icons/LoadingScreen.gif");
            JLabel loadingscreen = new JLabel(load, SwingConstants.CENTER);
            loadingscreen.setHorizontalAlignment(SwingConstants.CENTER);
            loadingscreen.setVerticalAlignment(SwingConstants.CENTER);
            pr.add(loadingscreen, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Wrong image path.");
        }

        setTitle("Loading...");
        setSize(1264, 681);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

    }

}

