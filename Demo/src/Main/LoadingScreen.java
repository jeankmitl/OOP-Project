package Main;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JFrame {
    public LoadingScreen() {
        setTitle("Loading...");
        setSize(1280,720);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());

        JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Algerian", Font.BOLD, 100));
        add(loadingLabel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new LoadingScreen();
    }
}

