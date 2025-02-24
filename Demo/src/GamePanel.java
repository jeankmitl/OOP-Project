import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel {

    private Image backgroundImage;

    private static final int ROWS = 5;
    private static final int COLS = 9;
    public static final int CELL_WIDTH = 95;
    public static final int CELL_HEIGHT = 95;
    public static final int GRID_OFFSET_X = 175; // Move grid right
    public static final int GRID_OFFSET_Y = 100; // Move grid down
    public static final int BAR_X = GRID_OFFSET_X, BAR_Y = CELL_HEIGHT*ROWS + GRID_OFFSET_Y + 10;
    public static final int SPAWN_POINT = 1000;

    // mana system
    public static int remainMana = 0;
    public static final int MAX_MANA = 1000;

    private static List<Unit> units;
    private static List<Enermy> enermies;
    private static List<Bullet> bullets;
    
    private boolean draggingRecall = false;
    private boolean draggingSkeleton = false;
    private boolean draggingSlime = false;
    private int mouseX, mouseY;

    public GamePanel() {
        backgroundImage = new ImageIcon(getClass().getResource("/Asset/chinaNo1.png")).getImage();

        units = new ArrayList<>();
        enermies = new ArrayList<>();
        bullets = new ArrayList<>();
        startGame();
        addMouseListeners();
        //------------------
        startAnimationThread();
        //------------------
        
        // every 0.1 sec +1 cost
        new Timer(10000, e -> {
            if (remainMana < MAX_MANA) {
                remainMana += 50;
                repaint(); // Update UI
            }
        }).start();
    }
    
    public static List<Enermy> getEnermies() {
        return enermies;
    }
    
    public static List<Bullet> getBullets() {
        return bullets;
    }
    //add//
    public void startGame() {
        new Timer(2000, e -> {
            Random random = new Random();
            int randomNumber = random.nextInt(5); // 0-5
            enermies.add(new Bandit(1280-GRID_OFFSET_X, randomNumber));
        }).start();

        new Timer(1000 / 60, e -> {
            update();
            repaint();
        }).start();
    }

//    public boolean isenermyInFront(unit1 unit) {
//        for (enermy enermy : enermies) {
//            if (enermy.getRow() == unit.getRow() && enermy.getX() > unit.getX()) {
//                return true;
//            }
//        }
//        return false;
//    }
    
    public void startAnimationThread() {
        new Thread(() -> {
            while (true) {
                for (Unit unit : units) {
                    unit.update_Frame();
                }
                repaint();
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                for (Enermy enermy : enermies) {
                    enermy.update_Frame();
                }
                repaint();
                try {
                    Thread.sleep(90);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean isFieldAvailable(int col, int row) {
        for (Unit unit : units) {
            if (unit.getRow() == row && unit.getCol() == col) {
                return false; // Field is occupied
            }
        }
        return true; // Field is free
    }

    public void update() {
        Iterator<Enermy> enermyIterator = enermies.iterator();
        while (enermyIterator.hasNext()) {
            Enermy enermy = enermyIterator.next();

            boolean stop = false;
            for (Unit unit : units) {
                if (((Skeleton)unit).getBounds().intersects(enermy.getBounds())) { //-------------------------fix after (cast unit to unit1)
                    stop = true;

                    long currentTime = System.currentTimeMillis();
                    if (currentTime - enermy.getLastAttackTime() >= 1000) {
                        enermy.attack((Skeleton)unit);                               //-------------------------fix after (cast unit to unit1)
                        System.out.println("eat!!");
                        enermy.setLastAttackTime(currentTime);
                    }
                    break;
                }
            }

            if (!stop) {
                enermy.move();
            }

            if (enermy.getX() + GRID_OFFSET_X <= 50) {
                System.out.println("Game Over!!! NOOB");
                System.exit(0);
            }

            if (enermy.isDead()) {
                enermyIterator.remove();
            }
        }

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.move();

            for (Enermy enermy : enermies) {
                if (bullet.getBounds().intersects(enermy.getBounds())) {
                    enermy.takeDamage(10);                                  // manual fix-bullet-damage
                    System.out.println(enermy.getHealth());
                    bulletIterator.remove();
                    break;
                }
            }
        }

        units.removeIf(Unit::isDead);
//        bullets.removeIf(bullet -> bullet.getTarget().isDead());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // draw background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
 
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Cost: " + remainMana, 20, 30); // Display at top-left

        // RED LINE
        g.setColor(Color.RED);
        g.drawLine(50, 0, 50, 850);

        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= ROWS; i++) {
            g.drawLine(GRID_OFFSET_X, GRID_OFFSET_Y + i * CELL_HEIGHT, GRID_OFFSET_X + COLS * CELL_WIDTH,
                    GRID_OFFSET_Y + i * CELL_HEIGHT);
        }
        for (int i = 0; i <= COLS; i++) {
            g.drawLine(GRID_OFFSET_X + i * CELL_WIDTH, GRID_OFFSET_Y, GRID_OFFSET_X + i * CELL_WIDTH,
                    GRID_OFFSET_Y + ROWS * CELL_HEIGHT);
        }

        for (Unit unit : units) {
//            g.setColor(Color.GREEN);
//            g.fillRect(unit.getX() + GRID_OFFSET_X, unit.getY() + GRID_OFFSET_Y, CELL_WIDTH, CELL_HEIGHT);

            if (unit instanceof Skeleton) {
                BufferedImage img = ((Skeleton)unit).getBufferedImage();
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); // Improve Quality
                                                                                                         // */
                // Image img =
                // unit.getBufferedImage().getScaledInstance(GamePanel.CELL_WIDTH,GamePanel.CELL_HEIGHT,Image.SCALE_SMOOTH);
                g.drawImage(img, unit.getX() + GRID_OFFSET_X, unit.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT,
                        GamePanel.CELL_WIDTH, null);
            }
            else if (unit instanceof Slime) {
                BufferedImage img = ((Slime)unit).getBufferedImage();
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); // Improve Quality
                                                                                                         // */
                // Image img =
                // unit.getBufferedImage().getScaledInstance(GamePanel.CELL_WIDTH,GamePanel.CELL_HEIGHT,Image.SCALE_SMOOTH);
                g.drawImage(img, unit.getX() + GRID_OFFSET_X, unit.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT,
                        GamePanel.CELL_WIDTH, null);
            }
        }

        for (Enermy enermy : enermies) {
//            g.setColor(Color.RED);
//            g.fillRect(enermy.getX() + GRID_OFFSET_X, enermy.getY() + GRID_OFFSET_Y, CELL_WIDTH, CELL_HEIGHT);
            
            if (enermy instanceof Bandit) {
                BufferedImage img = ((Bandit)enermy).getBufferedImage();
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); // Improve Quality
                                                                                                         // */
                // Image img =
                // unit.getBufferedImage().getScaledInstance(GamePanel.CELL_WIDTH,GamePanel.CELL_HEIGHT,Image.SCALE_SMOOTH);
                g.drawImage(img, enermy.getX() + GRID_OFFSET_X, enermy.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT,
                        GamePanel.CELL_WIDTH, null);
            }
        }

        for (Bullet bullet : bullets) {
            g.setColor(Color.YELLOW);
            g.fillRect(bullet.getX(), bullet.getY(), 30, 30);
        }

        g.setColor(Color.RED);
        for (int i = 0; i <= 1; i++) {
            g.drawLine(BAR_X, BAR_Y + i * CELL_HEIGHT, BAR_X + COLS * CELL_WIDTH, BAR_Y + i * CELL_HEIGHT);
        }
        for (int i = 0; i <= COLS; i++) {
            g.drawLine(BAR_X + i * CELL_WIDTH, BAR_Y, BAR_X + i * CELL_WIDTH, BAR_Y + CELL_HEIGHT);
        }

        g.setColor(Color.GREEN);
        g.fillRect(BAR_X + 10, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        g.setColor(Color.BLUE);
        g.fillRect(BAR_X + 10, BAR_Y + 105, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        g.setColor(Color.ORANGE);
        g.fillRect(BAR_X + CELL_WIDTH * (COLS - 1) + 10, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20);

        if (draggingSkeleton) {
            g.setColor(Color.GREEN);
            g.fillRect(mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        }

        if (draggingSlime) {
            g.setColor(Color.BLUE);
            g.fillRect(mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        }

        if (draggingRecall) {
            g.setColor(Color.ORANGE);
            g.fillRect(mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        }
    }

    public void addMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() >= BAR_X + 10 && e.getX() <= BAR_X + CELL_WIDTH - 10 && e.getY() >= BAR_Y + 10 && e.getY() <= BAR_Y + CELL_HEIGHT - 10) {
                    if (remainMana >= 50) {                                                    // manual fix-cost
                        draggingSkeleton = true;
                    } else {
                        System.out.println("Not enough cost!"); // Debug
                    }
                }
                else if (e.getX() >= BAR_X + 10 && e.getX() <= BAR_X + CELL_WIDTH - 10 && e.getY() >= BAR_Y + 105 && e.getY() <= BAR_Y + CELL_HEIGHT - 10) {
                    if (remainMana >= 50) {                                                    // manual fix-cost
                        draggingSlime = true;
                    } else {
                        System.out.println("Not enough cost!"); // Debug
                    }
                }
                else if (e.getX() >= BAR_X + CELL_WIDTH * (COLS - 1) && e.getX() <= BAR_X + CELL_WIDTH * COLS &&
                        e.getY() >= BAR_Y + 10 && e.getY() <= BAR_Y + CELL_HEIGHT - 10) {
                    draggingRecall = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (draggingSkeleton) {
                    int col = (e.getX() - GRID_OFFSET_X) / CELL_WIDTH;
                    int row = (e.getY() - GRID_OFFSET_Y) / CELL_HEIGHT;

                    if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                        if (isFieldAvailable(col, row)) { // Check if field is free
                            units.add(new Skeleton(row, col));
                            remainMana -= 50;                                             //manual fix-cost
                        } else {
                            System.out.println("***Field is occupied***");
                        }
                    }
                }
                else if (draggingSlime) {
                    int col = (e.getX() - GRID_OFFSET_X) / CELL_WIDTH;
                    int row = (e.getY() - GRID_OFFSET_Y) / CELL_HEIGHT;

                    if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                        if (isFieldAvailable(col, row)) { // Check if field is free
                            units.add(new Slime(row, col));
                            remainMana -= 50;                                             //manual fix-cost
                        } else {
                            System.out.println("***Field is occupied***");
                        }
                    }
                }
                else if (draggingRecall) {
                    int col = (e.getX() - GRID_OFFSET_X) / CELL_WIDTH;
                    int row = (e.getY() - GRID_OFFSET_Y) / CELL_HEIGHT;
                    if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                        if (!isFieldAvailable(col, row)) { // Check if Operator is exist on the field
                            Iterator<Unit> unitIterator = units.iterator();
                            while (unitIterator.hasNext()) {
                                Unit unit = unitIterator.next();
                                if (unit.getRow() == row && unit.getCol() == col) {
                                    ((Skeleton)unit).stopAttacking();
                                    unitIterator.remove(); // Remove unit
                                }
                            }
                        }
                    }
                }
                draggingSkeleton = false;
                draggingSlime = false;
                draggingRecall = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggingSlime || draggingRecall) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    repaint(); // Force repaint so the dragged unit updates smoothly
                }
                else if (draggingSkeleton || draggingRecall) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    repaint(); // Force repaint so the dragged unit updates smoothly
                }
            }
        });
    }
}
