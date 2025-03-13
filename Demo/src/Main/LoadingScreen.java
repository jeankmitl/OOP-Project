package Main;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JFrame {
    public LoadingScreen() {
        setTitle("Loading...");
        setSize(1280,720);
        setLocationRelativeTo(null);
        setUndecorated(true);
//        setLayout(new BorderLayout());
        
        
        JPanel pr = new JPanel();
        pr.setBackground(new Color(55, 55, 71));
        pr.setLayout(new BorderLayout());
        
        ImageIcon load = new ImageIcon("src/Asset/Img/Icons/LoadingScreen.gif");
        JLabel loadingscreen = new JLabel(load, SwingConstants.CENTER);
        loadingscreen.setHorizontalAlignment(SwingConstants.CENTER);
        loadingscreen.setVerticalAlignment(SwingConstants.CENTER);
        pr.add(loadingscreen, BorderLayout.CENTER);
        add(pr);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new LoadingScreen();
    }
}

