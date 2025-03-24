/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoOpSystem;

import Asset.ImgManager;
import Main.GamePanel;
import Main.LoadingScreen;
import Main.StageSelector;
import Main.VirtualStageSelector;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;

/**
 *
 * @author anawi
 */
public class CoOpFrame extends JFrame implements ActionListener {
    private ExecutorService connectHandlerPool = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isServer = false;
    
    private JPanel inputPanel, mainPanel, middlePanel, downPanel, msgPanel;
    private JTextField ipTextField, portTextField;
    private JLabel ipLabel, portLabel, responseLabel;
    private JButton startButton, createServerButton, joinServerButton, sendButton;
    private JTextArea msgTextArea;
    private JTextField msgTextField;
    private boolean isServerListening;
    private JScrollPane msgScrollPane;
    
    private StageSelector stageSelector;
    
    
    public CoOpFrame() {
        setTitle("Co-op room");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 360);
        setResizable(false);
        setLocationRelativeTo(null);
        
        inputPanel = new JPanel();
        mainPanel = new JPanel();
        downPanel = new JPanel();
        middlePanel = new JPanel();
        msgPanel = new JPanel();
        ipTextField = new JTextField("localhost");
        portTextField = new JTextField("12345");
        ipLabel = new JLabel("IP");
        portLabel = new JLabel("Port");
        responseLabel = new JLabel("Not connect");
        startButton = new JButton("Start");
        createServerButton = new JButton("Create Server");
        joinServerButton = new JButton("Join Server");
        sendButton = new JButton("Send");
        msgTextArea = new JTextArea();
        msgTextField = new JTextField();
        msgScrollPane = new JScrollPane();
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        inputPanel.setLayout(new GridLayout(3, 2, 5, 5));
        startButton.setEnabled(false);
        responseLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        createServerButton.setMultiClickThreshhold(500);
        joinServerButton.setMultiClickThreshhold(500);
        msgTextArea.setEditable(false);
        msgTextArea.setLineWrap(true);
        msgTextArea.setWrapStyleWord(true);
        msgPanel.setPreferredSize(new Dimension(0, 150));
        msgPanel.setLayout(new BorderLayout());
        msgScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setTestMsgEnabled(false);
        
        
        add(mainPanel);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        inputPanel.add(ipLabel);
        inputPanel.add(ipTextField);
        inputPanel.add(portLabel);
        inputPanel.add(portTextField);
        inputPanel.add(createServerButton);
        inputPanel.add(joinServerButton);
        
        mainPanel.add(middlePanel);
        middlePanel.add(responseLabel);
        
        mainPanel.add(msgPanel, BorderLayout.SOUTH);
        msgPanel.add(new JScrollPane(msgTextArea));
        msgPanel.add(msgTextField, BorderLayout.SOUTH);
        
        add(downPanel, BorderLayout.SOUTH);
        downPanel.add(startButton);
        
        createServerButton.addActionListener(this);
        joinServerButton.addActionListener(this);
        msgTextField.addActionListener(this);
        startButton.addActionListener(this);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new CoOpFrame();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Test in Co-op Room">
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(createServerButton)) {
            if (serverSocket != null && !serverSocket.isClosed()) {
                setinputEnabled(false);
                stopServer();
                createServerButton.setText("Create Server");
                isServerListening = false;
            } else {
                if (ipTextField.getText().isBlank() || portTextField.getText().isBlank()) {
                    JOptionPane.showMessageDialog(this, "Both IP and Port not be empty.");
                    return;
                }
                try {
                    String ip = ipTextField.getText();
                    int port = Integer.parseInt(portTextField.getText());
                    setinputEnabled(false);
                    createServer(ip, port);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Port Should be Integer.");
                    return;
                }
                createServerButton.setText("Cancel");
                isServerListening = true;
            }
        } else if (e.getSource().equals(joinServerButton)) {
            if (ipTextField.getText().isBlank() || portTextField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Both IP and Port not be empty.");
                return;
            }
            try {
                String ip = ipTextField.getText();
                int port = Integer.parseInt(portTextField.getText());
                setinputEnabled(false);
                joinServer(ip, port);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Port Should be Integer.");
                return;
            }
        } 
        if (socket != null && out != null) {
            Properties properties = new Properties();
            if (e.getSource().equals(msgTextField) && !msgTextField.getText().isBlank()) {
                String msgTest = msgTextField.getText();
                properties.setProperty(CoKeys.MSG_TEST, msgTest);
                msgTextField.setText("");
            } else if (e.getSource().equals(startButton)) {
                properties.setProperty(CoKeys.IS_READY_PLAY, "true");
                startButton.setEnabled(false);
            }
            requestMsg(properties);
        }

    }
    
    private void setinputEnabled(boolean enabled) {
        ipTextField.setEnabled(enabled);
        portTextField.setEnabled(enabled);
        createServerButton.setEnabled(enabled);
        joinServerButton.setEnabled(enabled);
    }
    
    private void setTestMsgEnabled(boolean enabled) {
        msgTextArea.setEnabled(enabled);
        msgTextField.setEnabled(enabled);
        startButton.setEnabled(enabled);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Backend Server/Client">
    private void createServer(String ip, int port) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Server started on port 12345...");
                SwingUtilities.invokeLater(() -> {
                    responseLabel.setText("Server: Started on port " + port + "...");
                    responseLabel.setForeground(new Color(0x143D60));
                    createServerButton.setEnabled(true);
                });
                while (!serverSocket.isClosed()) {
                    try {
                        socket = serverSocket.accept();
                        System.out.println("Client connected!");
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream(), true);
                        connectHandlerPool.execute(() -> listenForUpdates());
                        isServer = true;
                        SwingUtilities.invokeLater(() -> {
                            responseLabel.setText("Server: Client connected!");
                            responseLabel.setForeground(new Color(0x2973B2));
                            setTitle("Co-op room - Server");
                            msgTextArea.append("---connected---\n");
                            setTestMsgEnabled(true);
                            startButton.setText("<Start>");
                            startButton.setEnabled(false);
                        });
                    } catch (SocketException ex) {
                        System.out.println("Server stopped accepting connections.");
                        SwingUtilities.invokeLater(() -> {
                            responseLabel.setText("Server: Stopped accepting connections.");
                            responseLabel.setForeground(new Color(0xBE3144));
                        });
                    }
                }
            } catch (BindException ex) {
                System.out.println("IP already in use");
                SwingUtilities.invokeLater(() -> {
                    responseLabel.setText("Server: IP already in use.");
                    responseLabel.setForeground(new Color(0xBE3144));
                });
                setinputEnabled(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                stopServer();
            }
        }).start();
    }
    
    private void listenForUpdates() {
        String msg;
        try {
            StringBuilder allMsg = new StringBuilder();
            while ((msg = in.readLine()) != null) {
                if (msg.equals("")) {
                    responseMsg(allMsg.toString());
                    allMsg.setLength(0);
                } else {
                    System.out.println("append");
                    allMsg.append(msg);
                    allMsg.append('\n');
                }
            }
        } catch (SocketException e) {
            System.out.println("Client disconnected.");
            SwingUtilities.invokeLater(() -> {
                msgTextArea.append("---disconnected---\n");
                responseLabel.setText("Disconnected.");
                responseLabel.setForeground(new Color(0xBE3144));
                setTestMsgEnabled(false);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server stopped.");
                SwingUtilities.invokeLater(() -> {
                    setinputEnabled(true);
                    setTestMsgEnabled(false);
                });
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void joinServer(String ip, int port) {
        new Thread(() -> {
            try  {
                socket = new Socket(ip, port);
                System.out.println("Connected to server!");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                connectHandlerPool.execute(() -> listenForUpdates());
                isServer = false;
                SwingUtilities.invokeLater(() -> {
                    responseLabel.setText("Client: Connected to server!");
                    responseLabel.setForeground(new Color(0x2973B2));
                    setTitle("Co-op room - Client");
                    startButton.setText("Ready!");
                    msgTextArea.append("---connected---\n");
                    setTestMsgEnabled(true);
                });
            } catch (IOException ex) {
                System.out.println("Cannot connect to server or server closed.");
                SwingUtilities.invokeLater(() -> {
                    responseLabel.setText("Client: Can't connect to server or server closed.");
                    responseLabel.setForeground(new Color(0xBE3144));
                    setinputEnabled(true);
                });
            }
        }).start();
    }
    // </editor-fold>

    public boolean isServer() {
        return isServer;
    }
    
    public void requestMsg(Properties properties) {
        StringWriter writer = new StringWriter();
        try {
            properties.store(writer, null);
            out.println(writer.toString());
            processMsg(properties, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void responseMsg(String message) {
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(message));            
            processMsg(properties, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private VirtualStageSelector vtStage;
    private StageSelector stage;
    private GamePanel gamePanel;
    
    private void startGame() {
        LoadingScreen loadingScreen = new LoadingScreen();
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Thread.sleep(1500);
                    return null;
                }

                @Override
                protected void done() {
                    System.out.println("Finished loading.");
                    loadingScreen.dispose();
                    if (isServer) {
                        stage = new StageSelector(CoOpFrame.this);
                        stage.loadStage("St10");
                    } else {
                        vtStage = new VirtualStageSelector(CoOpFrame.this);
                    }
            }
        };
        worker.execute();
    }
    
    private void processMsg(Properties properties, boolean isYou) {
        String msgTest = properties.getProperty(CoKeys.MSG_TEST);
        String isReadyPlay = properties.getProperty(CoKeys.IS_READY_PLAY);
        String stageName = properties.getProperty(CoKeys.STAGE_NAME);
        
        SwingUtilities.invokeLater(() -> {
            if (msgTest != null) {
                msgTextArea.append((isYou?"(You)":"B") + ": " + msgTest + "\n");
            }
            if (isReadyPlay != null) {
                if (isServer && isYou || !isServer && !isYou) {
                    msgTextArea.append("---Start Play...---\n");
                    startGame();
                } else {
                    msgTextArea.append("---" + (isYou?"(You) are":"B is") + " Ready---\n");
                    if (isServer) {
                        startButton.setEnabled(true);
                    }
                }
            }
            if (stageName != null) {
                System.out.println("Hellooosadfpja");
                if (isServer) {
                    gamePanel = stage.getGamePanel();
                } else {
                    vtStage.loadStage(stageName);
                }
            }
        });
    }
}
