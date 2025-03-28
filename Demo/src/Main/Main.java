package Main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        LoadingScreen loadingScreen = new LoadingScreen();
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(1500);
                return null;
            }

            @Override
            protected void done() {
                loadingScreen.dispose();
                new MainMenu();
            }
        };
        worker.execute();
    }

}
