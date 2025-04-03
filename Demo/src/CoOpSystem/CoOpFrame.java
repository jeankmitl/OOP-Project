/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoOpSystem;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
import Asset.VFX;
import Main.BossFightGamePanel2PlayerRough;
import Main.GamePanel;
import static Main.GamePanel.CELL_HEIGHT;
import static Main.GamePanel.CELL_WIDTH;
import static Main.GamePanel.getVfxs;
import Main.GamePanel2Player;
import Main.LoadingScreen;
import Main.MainMenu;
import Main.StageSelector;
import Main.UnitSelector;
import Main.UnitType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author anawi
 */
public class CoOpFrame extends JFrame implements ActionListener {
    
    private final boolean isJoinNoConfirm = true;
    private final boolean DEBUG_PRINT = false;
    private ExecutorService connectHandlerPool = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isServer = false;
    
    private JPanel inputPanel, leftPanel, mainPanel, topPanel, downPanel, msgPanel, bgPanel, msgTextFieldPanel;
    private JTextField ipTextField, portTextField;
    private JLabel ipLabel, portLabel, responseLabel;
    private JButton startButton, createServerButton, joinServerButton, sendButton, homeButton;
    private JTextArea msgTextArea;
    private JTextField msgTextField;
    private boolean isServerListening;
    private JScrollPane msgScrollPane;
    
    private StageSelector stageSelector;
    private ImageIcon serverImg, serverListeningImg, clientImg;
    
    public CoOpFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1264, 681);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        
        serverImg = new ImageIcon(getClass().getResource("/Asset/Img/Icons/svr_icon.png"));
        serverListeningImg = new ImageIcon(getClass().getResource("/Asset/Img/Icons/svr_icon_listen.png"));
        clientImg = new ImageIcon(getClass().getResource("/Asset/Img/Icons/cli_icon.png"));
        
        
        inputPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = ImgManager.loadIcon("bg_for_status");
                g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
            }
        };
        bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = ImgManager.loadBG("defense_of_dungeon_wallpaper");
                g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
                img = ImgManager.loadIcon("HowToConnect");
                g.drawImage(img, 100, 10, 600, 600, null);
            }
        };
        leftPanel = new JPanel();
        mainPanel = new JPanel();
        downPanel = new JPanel();
        topPanel = new JPanel();
        msgPanel = new JPanel();
        msgTextFieldPanel = new JPanel();
        ipTextField = new JTextField("localhost");
        portTextField = new JTextField("12345");
        ipLabel = new JLabel("   IP");
        portLabel = new JLabel("   Port");
        responseLabel = new JLabel("Not connect");
        startButton = new JButton("Start");
        createServerButton = new JButton("Create Server");
        joinServerButton = new JButton("Join Server");
        homeButton = new JButton("X");
        sendButton = new JButton("Send");
        msgTextArea = new JTextArea();
        msgTextField = new JTextField();
        msgScrollPane = new JScrollPane();
        
        Font defFont = new Font("Helvetica", Font.BOLD, 20);
        Font defBigFont = new Font("Helvetica", Font.BOLD, 28);
        Font defFieldFont = new Font("Arial", Font.PLAIN, 18);
        
        mainPanel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        msgTextFieldPanel.setLayout(new BorderLayout());
        msgTextFieldPanel.setPreferredSize(new Dimension(0, 30));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        topPanel.setBackground(new Color(0x2f2e35));
        downPanel.setBackground(new Color(0x2f2e35));
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        leftPanel.setLayout(new GridLayout(2, 1, 10, 10));
        leftPanel.setPreferredSize(new Dimension(500, 0));
        startButton.setEnabled(false);
        responseLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
        responseLabel.setHorizontalAlignment(JLabel.CENTER);
        responseLabel.setForeground(Color.white);
        ipLabel.setFont(defBigFont);
        ipLabel.setForeground(Color.white);
        portLabel.setFont(defBigFont);
        portLabel.setForeground(Color.white);
        ipTextField.setFont(defFieldFont);
        portTextField.setFont(defFieldFont);
        createServerButton.setMultiClickThreshhold(500);
        joinServerButton.setMultiClickThreshhold(500);
        msgTextArea.setEditable(false);
        msgTextArea.setLineWrap(true);
        msgTextArea.setWrapStyleWord(true);
        msgTextArea.setFont(defFieldFont);
        msgTextField.setFont(defFieldFont);
        sendButton.setFont(defFont);
        msgPanel.setPreferredSize(new Dimension(0, 150));
        msgPanel.setLayout(new BorderLayout());
        
        msgScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        homeButton.setFocusable(false);
        setTestMsgEnabled(false);
        bgPanel.setOpaque(true);
        // Style
        createServerButton.setForeground(Color.white);
        createServerButton.setBackground(new Color(0x27548A));
        createServerButton.setBorder(BorderFactory.createMatteBorder(3, 3, 5, 3, new Color(0x183B4E)));
        createServerButton.setIcon(serverImg);
        createServerButton.setHorizontalTextPosition(JButton.LEFT);
        createServerButton.setIconTextGap(20);
        createServerButton.setFont(defFont);
        joinServerButton.setForeground(Color.white);
        joinServerButton.setBackground(new Color(0xDDA853));
        joinServerButton.setBorder(BorderFactory.createMatteBorder(3, 3, 5, 3, new Color(0xB17F59)));
        joinServerButton.setFont(defFont);
        joinServerButton.setIcon(clientImg);
        joinServerButton.setHorizontalTextPosition(JButton.LEFT);
        joinServerButton.setIconTextGap(20);
        startButton.setForeground(Color.black);
        startButton.setBackground(Color.gray);
        startButton.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.lightGray, Color.darkGray));
        startButton.setFont(defBigFont);
        startButton.setPreferredSize(new Dimension(200, 50));
        homeButton.setForeground(Color.black);
        homeButton.setBackground(Color.gray);
        homeButton.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.lightGray, Color.darkGray));
        homeButton.setFont(defFont);
        homeButton.setPreferredSize(new Dimension(40, 30));
//        sendButton.setForeground(Color.black);
        sendButton.setBackground(Color.lightGray);
        sendButton.setFocusable(false);
        sendButton.setBorder(BorderFactory.createMatteBorder(2, 2, 3, 2, Color.darkGray));
        sendButton.setPreferredSize(new Dimension(70, 0));
        bgPanel.setBackground(Color.red);
        
        add(mainPanel);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        leftPanel.add(inputPanel);
        inputPanel.add(ipLabel);
        inputPanel.add(ipTextField);
        inputPanel.add(portLabel);
        inputPanel.add(portTextField);
        inputPanel.add(createServerButton);
        inputPanel.add(joinServerButton);
        
        leftPanel.add(msgPanel);
        msgPanel.add(new JScrollPane(msgTextArea));
        msgPanel.add(msgTextFieldPanel, BorderLayout.SOUTH);
        msgTextFieldPanel.add(msgTextField);
        msgTextFieldPanel.add(sendButton, BorderLayout.EAST);
        
        mainPanel.add(bgPanel);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        topPanel.add(responseLabel);
        topPanel.add(homeButton, BorderLayout.EAST);
        
        add(downPanel, BorderLayout.SOUTH);
        downPanel.add(startButton);
        
        createServerButton.addActionListener(this);
        joinServerButton.addActionListener(this);
        msgTextField.addActionListener(this);
        startButton.addActionListener(this);
        homeButton.addActionListener(this);
        sendButton.addActionListener(this);
        
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
                createServerButton.setIcon(serverImg);
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
                createServerButton.setIcon(serverListeningImg);
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
        } else if (e.getSource().equals(homeButton)) {
            Audio.play(AudioName.BUTTON_CLICK);
            int res = JOptionPane.showConfirmDialog(stage, "Do you want to disconnect and leave from CoOp?",
            "Leave Level", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                Audio.play(AudioName.BUTTON_CLICK);
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    LoadingScreen loadingScreen = new LoadingScreen();
                    
                    @Override
                    protected Void doInBackground() throws Exception {
                        Thread.sleep(500);
                        dispose();
                        new MainMenu();
                        return null;
                    }

                    @Override
                    protected void done() {
                        loadingScreen.dispose();
                    }
                };
                worker.execute();
            }
        }
        if (socket != null && out != null) {
            Properties properties = new Properties();
            if ((e.getSource().equals(msgTextField) || e.getSource().equals(sendButton)) && !msgTextField.getText().isBlank()) {
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
        sendButton.setEnabled(enabled);
    }
    
    private void debugPrint(boolean isYou, String msg) {
        if (DEBUG_PRINT) {
            System.out.println(((isServer) ? "Server":"Client") + ((isYou) ? " (You)":"")  + ": " + msg);
        }
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
                    responseLabel.setForeground(new Color(0x3b82d6));
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
                            responseLabel.setForeground(new Color(0x00cbff));
                            setTitle("Co-op room - Server");
                            msgTextArea.append("---connected---\n");
                            setTestMsgEnabled(true);
                            startButton.setText("<Start>");
                            startButton.setEnabled(false);
                        });
                        if (isJoinNoConfirm) startGame();
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
            if (gamePanel != null) {
                gamePanel.coOp.warnCliDisconnect();
            }
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
                    responseLabel.setForeground(new Color(0x00cbff));
                    setTitle("Co-op room - Client");
                    startButton.setText("Ready!");
                    msgTextArea.append("---connected---\n");
                    setTestMsgEnabled(true);
                });
                
                if (isJoinNoConfirm) startGame();
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
    
    public void invoke(String key) {
        this.sendOne(key, "");
    }
    
    public void sendOne(String key, String str) {
        Properties prop = new Properties();
        prop.setProperty(key, str);
        requestMsg(prop);
    }
    
    public void send(Properties prop) {
        requestMsg(prop);
    }
    
    private void requestMsg(Properties properties) {
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
    
    private StageSelector stage;
    private GamePanel2Player gamePanel;
    private UnitSelector unitSelector;
    
    private void startGame() {
        LoadingScreen loadingScreen = new LoadingScreen();
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    dispose();
                    Thread.sleep(1500);
                    return null;
                }

                @Override
                protected void done() {
                    System.out.println("Finished loading.");
                    loadingScreen.dispose();
                    GamePanel.DEBUG_MODE = false;
                    if (isServer) {
                        stage = new StageSelector("2p", CoOpFrame.this);
                    } else {
                        stage = new StageSelector("cli", CoOpFrame.this);
                    }
            }
        };
        worker.execute();
    }
    
    private void processMsg(Properties prop, boolean isYou) {
        boolean isForCli= !isServer && !isYou;
        boolean isForSvr = isServer && !isYou;
        
        String msgTest = prop.getProperty(CoKeys.MSG_TEST);
        String isReadyPlay = prop.getProperty(CoKeys.IS_READY_PLAY);
        String stageName = prop.getProperty(CoKeys.STAGE_NAME);
        String hoverXY = prop.getProperty(CoKeys.HOVER_XY);
        String wantThisStage = prop.getProperty(CoKeys.WANT_THIS_STAGE);
        
        String getGamePanel = prop.getProperty(CoKeys.GET_GAME_PANEL);
        String getUnitSelector = prop.getProperty(CoKeys.GET_UNIT_SELECTOR);
        
        String readyUnitSelector = prop.getProperty(CoKeys.READY_UNIT_SELECTOR);
        String setP2Unit = prop.getProperty(CoKeys.SET_P2_UNIT);
        String startGame = prop.getProperty(CoKeys.START_GAME);
        
        
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
            if (hoverXY != null) {
                String hoverXYs[] = hoverXY.split(" ");
                int x = Integer.parseInt(hoverXYs[0]);
                int y = Integer.parseInt(hoverXYs[1]);
                if (!isYou) {
                    stage.setP2Hover(x, y);
                }
            }
            if (wantThisStage != null) {
                if (isForSvr) {
                    stage.p2WantThisStage();
                }
            }
            if (getGamePanel != null) {
                if (isYou) {
                    gamePanel = stage.getGamePanel2Player();
                    if (gamePanel != null) debugPrint(isYou, "getGamePanel!");
                }
            }
            if (stageName != null) {
                if (!isServer) {
                    stage.loadStage(stageName);
                }
            }
            // UnitSelector
            if (getUnitSelector != null) {
                if (isYou) {
                    unitSelector = gamePanel.getUnitSelectorSocket();
                    if (unitSelector != null) debugPrint(isYou, "getStageSelector!");
                }
            }
            if (readyUnitSelector != null) {
                if (isForSvr) {
                    unitSelector.cliReady();
                }
            } 
            if (setP2Unit != null) {
                debugPrint(isYou, "Should set P2 for Server");
                debugPrint(isYou, setP2Unit);
                if (!isYou) {
                    gamePanel.setP2Unit(setP2Unit);
                }
            }
            if (startGame != null) {
                debugPrint(isYou, "Start Game");
                gamePanel.startGameLoop();
                if (isServer) {
                    gamePanel.summonEnemies();
                }
            }
            // GamePanel2Player
            final String placeXYStr;
            if ((placeXYStr = prop.getProperty(CoKeys.BOTH_PLACE_XY)) != null) {
                String[] placeXYStrSplit = placeXYStr.split(",");
                int placeX = Integer.parseInt(placeXYStrSplit[0]);
                int placeY = Integer.parseInt(placeXYStrSplit[1]);
                
                if (!isYou) {
                    gamePanel.coOp.updateP2PlaceXY(placeX, placeY);
                }
            }
            final String updateCli = prop.getProperty(CoKeys.UPDATE_CLI);
            if (updateCli != null && isForCli) {
                String allUnitID = prop.getProperty(CoKeys.ALL_UNIT_ID);
                if (allUnitID != null) {
                    String[] allUnitIdSplit = allUnitID.split(" ");
                    Set<Integer> buildID = new HashSet<>();
                    try {
                        for (String unitId: allUnitIdSplit) {
                            String unit = prop.getProperty(CoKeys.UNIT_ + unitId);
                            debugPrint(isYou, unit);
                            // set data
                            String[] unitSplit = unit.split(" ");
                            int id = Integer.parseInt(unitId);
                            String name = unitSplit[0];
                            int row = Integer.parseInt(unitSplit[1]);
                            int col = Integer.parseInt(unitSplit[2]);
                            int health = Integer.parseInt(unitSplit[3]);
                            gamePanel.coOp.updateUnitAt(id, name, row, col, health);
                            buildID.add(id);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                
                String allEnemyID = prop.getProperty(CoKeys.ALL_ENEMY_ID);
                if (allEnemyID != null) {
                    String[] allEnemyIdSplit = allEnemyID.split(" ");
                    try {
                        for (String enemyId: allEnemyIdSplit) {
                            String enemy = prop.getProperty(CoKeys.ENEMY_ + enemyId);
                            debugPrint(isYou, enemy);
                            // set data
                            String[] enemySplit = enemy.split(" ");
                            int id = Integer.parseInt(enemyId);
                            String name = enemySplit[0];
                            int row = Integer.parseInt(enemySplit[1]);
                            int x = Integer.parseInt(enemySplit[2]);
                            int health = Integer.parseInt(enemySplit[3]);
                            gamePanel.coOp.updateEnemyAt(id, name, row, x, health);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            final String reqPlaceUnit;
            if ((reqPlaceUnit = prop.getProperty(CoKeys.REQ_PLACE_UNIT)) != null) {
                String[] unitSplit = reqPlaceUnit.split(" ");
                try {
                    String name = unitSplit[0];
                    int row = Integer.parseInt(unitSplit[1]);
                    int col = Integer.parseInt(unitSplit[2]);
                    if (isForSvr) {
                        UnitType unitType = AllEntityTypes.getUnitTypeFromName(name);
                        gamePanel.coOp.placeUnitClient(unitType, row, col);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            final String reqRecallUnit;
            if ((reqRecallUnit = prop.getProperty(CoKeys.REQ_RECALL_UNIT)) != null) {
                String[] splited = reqRecallUnit.split(" ");
                try {
                    int row = Integer.parseInt(splited[0]);
                    int col = Integer.parseInt(splited[1]);
                    if (isForSvr) {
                        gamePanel.coOp.recallUnitClient(row, col);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            final String manaCli;
            if ((manaCli = prop.getProperty(CoKeys.MANA_CLI)) != null) {
                int mana = Integer.parseInt(manaCli);
                if (isForCli) {
                    gamePanel.coOp.setRemainMana(mana);
                }
            }
            final String manaSvr;
            if ((manaSvr = prop.getProperty(CoKeys.MANA_SVR)) != null) {
                int mana = Integer.parseInt(manaSvr);
                if (isForCli) {
                    gamePanel.coOp.setRemainManaP2(mana);
                }
            }
            final String resetManaRecover = prop.getProperty(CoKeys.RESET_MANA_RECOVER);
            if (resetManaRecover != null) {
                if (isForCli) {
                    gamePanel.coOp.resetManaRecover();
                }
            }
            final String cooldownCli, cooldownSvr;
            if ((cooldownCli = prop.getProperty(CoKeys.COOLDOWN_CLI)) != null
                && (cooldownSvr = prop.getProperty(CoKeys.COOLDOWN_SVR)) != null) {
                if (isForCli) {
                    gamePanel.coOp.setCooldown(cooldownCli);
                    gamePanel.coOp.setCooldown2(cooldownSvr);
                }
            }
            final String soundCli;
            if ((soundCli = prop.getProperty(CoKeys.SOUND_CLI)) != null) {
                if (isForCli) {
                    Audio.play(soundCli);
                }
            }
            final String vfxCli;
            if ((vfxCli = prop.getProperty(CoKeys.VFX_CLI)) != null) {
                String[] splited = vfxCli.split(" ");
                try {
                    int x = Integer.parseInt(splited[0]);
                    int y = Integer.parseInt(splited[1]);
                    String name = splited[2];
                    if (isForCli) {
                        GamePanel.getVfxs().add(new VFX(x, y, name));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                
            }
            final String winCli;
            if ((winCli = prop.getProperty(CoKeys.WIN_CLI)) != null) {
                if (isForCli) {
                    System.out.println("cli win cli too!!!!!!");
                    gamePanel.coOp.win();
                }
            }
            final String fakePlaceCli;
            final String fakeRecallCli;
            if ((fakePlaceCli = prop.getProperty(CoKeys.FAKE_PLACE_CLI)) != null) {
                if (isForCli) {
                    String[] splited = fakePlaceCli.split(" ");
                    try {
                        int col = Integer.parseInt(splited[0]);
                        int row = Integer.parseInt(splited[1]);
                        gamePanel.coOp.updateP2PlaceXY(col, row, 'p');
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            if ((fakeRecallCli = prop.getProperty(CoKeys.FAKE_RECALL_CLI)) != null) {
                if (isForCli) {
                    String[] splited = fakeRecallCli.split(" ");
                    try {
                        int col = Integer.parseInt(splited[0]);
                        int row = Integer.parseInt(splited[1]);
                        getVfxs().add(new VFX(col * CELL_WIDTH, row * CELL_HEIGHT, "recall_vfx"));
                        Audio.play(AudioName.PLANT_DELETE);
                        gamePanel.coOp.updateP2PlaceXY(col, row, 'r');
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
