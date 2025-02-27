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
    private Image iconImage;

    private static final int ROWS = 5;
    private static final int COLS = 9;
    public static final int CELL_WIDTH = 95;
    public static final int CELL_HEIGHT = 95;
    public static final int GRID_OFFSET_X = 175; // Move grid right
    public static final int GRID_OFFSET_Y = 100; // Move grid down
    public static final int BAR_X = GRID_OFFSET_X, BAR_Y = CELL_HEIGHT * ROWS + GRID_OFFSET_Y + 10;
    public static final int SPAWN_POINT = 1000;

    public static int remainMana = 0; // for test only, normal is 0
    public static final int MAX_MANA = 9999;
    public int wight = 0; //test mana
    
    private static List<Unit> units;
    private static List<Enemy> enemies;
    private static List<Bullet> bullets;
    private static List<VFX> vfxs;
    
    private boolean draggingSkeleton = false;
    private boolean draggingSlime = false;
    private boolean draggingVinewall = false;
    private boolean draggingRecall = false;

    private int mouseX, mouseY;

    public GamePanel() {
        /**
         * Constructor.
         * - play: music 🎵, 
         * - set: BG image 🎨
         * - start: Game Loop 🔁
         */
        Audio.playMusic(AudioName.MUSIC_ONE);
        backgroundImage = new ImageIcon(getClass().getResource("Asset/chinaNo1.png")).getImage();

        units = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        vfxs = new ArrayList<>();
        
        startGame();
        addMouseListeners();
        startAnimationThread();

        // Add 50 cost every 15 seconds
        new Timer(15000, e -> {
            remainMana += 25;
            if (GamePanel.remainMana > GamePanel.MAX_MANA) {
                GamePanel.remainMana = GamePanel.MAX_MANA;
            }
            repaint();
        }).start();
        
        new Timer(124, e->{ // delay nid noy bacause i can't input 123.5
            if(wight< 116){
                wight += 1;
                repaint();
            }else{wight = 0;}
        }).start();
    }

    public static List<Enemy> getEnemies() {
        return enemies;
    }
    
    public static List<Bullet> getBullets() {
        return bullets;
    }
    
    public static List<VFX> getVfxs() {
        return vfxs;
    }

    public void startGame() {
        /**
         * Start Game.
         * - every 10s
         *    - spawn: enemies ➕
         * - every 60fps
         *    - update: Game Logic 🔁
         */
        new Timer(10000, e -> {
            Random random = new Random();
            int randomBandit = random.nextInt(5);
            int randomNinja = random.nextInt(5);
            int randomSorcerer = random.nextInt(5);
            enemies.add(new Bandit(1280-GRID_OFFSET_X, randomBandit));
            enemies.add(new Ninja(1280-GRID_OFFSET_X, randomNinja));
            enemies.add(new Sorcerer(1280-GRID_OFFSET_X, randomSorcerer));
        }).start();

        new Timer(1000 / 60, e -> {
            update();
            repaint();
        }).start();
    }

    public void startAnimationThread() {
        /**
         * Start Animation Thread.
         * - update: Frame ️🖼️
         *    - of units, enemies, bullets
         */
        new Thread(() -> {
            while (true) {
                for (Unit unit : units) {
                    unit.updateFrame();
                }
                repaint();
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                for (Enemy enemy : enemies) {
                    enemy.updateFrame();
                }
                repaint();
                try {
                    Thread.sleep(90);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (Bullet bullet : bullets) {
                    bullet.updateFrame();
                }
                repaint();
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                System.out.println(vfxs.size());
                Iterator<VFX> vfxIter = vfxs.iterator();
                while (vfxIter.hasNext()) {
                    VFX vfx = vfxIter.next();
                    if (!vfx.isFinishUpdate()) {
                        vfx.updateFrameVFXOnce();
                    } else {
                        vfxIter.remove();
                    }
                }
                repaint();
            }
        }).start();
    }

    public boolean isFieldAvailable(int col, int row) {
        /**
         * Is Field Available?
         * - true: if you can place units
         * 
         * mostly use.
         * - mouseReleased()
         */
        for (Unit unit : units) {
            if (unit.getRow() == row && unit.getCol() == col) {
                return false;
            }
        }
        return true;
    }

    public void update() {
        /**
         * Update.
         * - enemies
         *    - attack: if INTERSECTS units ⚔️
         *    - move: if NOT attack ️▶️
         *    - GAME OVER: if enemies reach 🏴
         *    - remove: if isDead() 💀
         * - bullets
         *    - move
         *    - damage: if INTERSECTS enemies & 
         * - units
         *    - remove: if isDead() 💀
         */
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();

            boolean stop = false;
            for (Unit unit : units) {
                if (unit instanceof Skeleton) {
                    Skeleton skeleton = (Skeleton) unit;
                    if (skeleton.getBounds().intersects(enemy.getBounds())) {
                        stop = true;
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - enemy.getLastAttackTime() >= 1000) {
                            enemy.attack(skeleton);
                            enemy.setLastAttackTime(currentTime);
                        }
                        break;
                    }
                }
                else if (unit instanceof Slime) {
                    Slime slime = (Slime) unit;
                    if (slime.getBounds().intersects(enemy.getBounds())) {
                        stop = true;
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - enemy.getLastAttackTime() >= 1000) {
                            enemy.attack(slime);
                            enemy.setLastAttackTime(currentTime);
                        }
                        break;
                    }
                }
                else if (unit instanceof Vinewall) {
                    Vinewall vinewall = (Vinewall) unit;
                    if (vinewall.getBounds().intersects(enemy.getBounds())) {
                        stop = true;
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - enemy.getLastAttackTime() >= 1000) {
                            enemy.attack(vinewall);
                            enemy.setLastAttackTime(currentTime);
                        }
                        break;
                    }
                }
            }
            

            if (!stop) {
                enemy.move();
            }

            if (enemy.getX() + GRID_OFFSET_X <= 50) {
                System.out.println("Game Over!!! NOOB");
                System.exit(0);
            }

            if (enemy.isDead()) {
                enemyIterator.remove();
                Audio.play(AudioName.KILL2);
                getVfxs().add(new VFX(enemy.getX(), enemy.getY(), "dead_ghost_vfx"));
            }
        }

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.move();

            for (Enemy enemy : enemies) {
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    enemy.takeDamage(10);
                    getVfxs().add(new VFX(bullet.getX() - GRID_OFFSET_X, bullet.getY() - GRID_OFFSET_Y - 40, "bone_hit"));
                    bulletIterator.remove();
                    Audio.play(AudioName.HIT);
                    break;
                }
            }
        }

        units.removeIf(Unit::isDead);
    }

    @Override
    public void paintComponent(Graphics g) {
        /**
         * Paint Component
         * - draw: BG 🖼️
         * - draw: Text 💬
         *    * Mana
         * - draw: Line 🔗
         *    * RED LINE
         *    * Grid #
         *    * Bar (units)
         * - draw: Characters 👨
         *    * all units
         *    * all enemies
         *    * all bullets
         * - draw: Drag & Drop 🖱️
         */
        super.paintComponent(g);
        
        //g: for Smooth ex. picture
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
        // RED LINE
        g.setColor(Color.RED);
        g.drawLine(50, 0, 50, 850);

        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= ROWS; i++) {
            g.drawLine(GRID_OFFSET_X, GRID_OFFSET_Y + i * CELL_HEIGHT, GRID_OFFSET_X + COLS * CELL_WIDTH, GRID_OFFSET_Y + i * CELL_HEIGHT);
        }
        for (int i = 0; i <= COLS; i++) {
            g.drawLine(GRID_OFFSET_X + i * CELL_WIDTH, GRID_OFFSET_Y, GRID_OFFSET_X + i * CELL_WIDTH, GRID_OFFSET_Y + ROWS * CELL_HEIGHT);
        }

        //g2d: for Pixel Art ex. SpriteSheet
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        for (Unit unit : units) {
            if (unit instanceof Skeleton) {
                BufferedImage img = ((Skeleton) unit).getBufferedImage();
                g.drawImage(img, unit.getX() + GRID_OFFSET_X, unit.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH, null);
            }
            else if (unit instanceof Slime) {
                BufferedImage img = ((Slime) unit).getBufferedImage();
                g.drawImage(img, unit.getX() + GRID_OFFSET_X, unit.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH, null);
            }
            else if (unit instanceof Vinewall) {
                BufferedImage img = ((Vinewall) unit).getBufferedImage();
                g.drawImage(img, unit.getX() + GRID_OFFSET_X, unit.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH, null);
            }

        }

        for (Enemy enemy : enemies) {

            if (enemy instanceof Bandit) {
                BufferedImage img = ((Bandit) enemy).getBufferedImage();
                g.drawImage(img, enemy.getX() + GRID_OFFSET_X, enemy.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH, null);
            }
            else if (enemy instanceof Ninja) {
                BufferedImage img = ((Ninja) enemy).getBufferedImage();
                g.drawImage(img, enemy.getX() + GRID_OFFSET_X, enemy.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH, null);
            }
            else if (enemy instanceof Sorcerer) {
                BufferedImage img = ((Sorcerer) enemy).getBufferedImage();
                g.drawImage(img, enemy.getX() + GRID_OFFSET_X, enemy.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH, null);
            }

        }

        for (Bullet bullet : bullets) {

            if (bullet instanceof Bone) {
                BufferedImage img = ((Bone) bullet).getBufferedImage();
                g.drawImage(img, bullet.getX() - 40, bullet.getY() - 20, 64, 64, null);
            }

        }
        
        for (VFX vfx : vfxs) {
            BufferedImage img = vfx.getBufferedImage();
            g.drawImage(img, vfx.getX(), vfx.getY(), GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT, null);
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
        g.fillRect(BAR_X + 105, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        g.setColor(Color.YELLOW);
        g.fillRect(BAR_X + 200, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        iconImage = new ImageIcon(getClass().getResource("Asset/Recall.png")).getImage();
        g.drawImage(iconImage, BAR_X + CELL_WIDTH * (COLS - 1) + 10, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20, this);
        ///
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(BAR_X + 740, BAR_Y - 50, 116, 50);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.drawString("" + remainMana, BAR_X + 800, BAR_Y - 20); // Display at top-left
        g.setColor(new Color(162, 252, 255, 255));
        iconImage = new ImageIcon(getClass().getResource("Asset/Mana_icon.png")).getImage();
        g.drawImage(iconImage, BAR_X+730, BAR_Y-63,70,70, null);
        g.fillRect(BAR_X + 740, BAR_Y - 5, wight, 5);
        ///show mana system
           ///AddCha vvvv ///
        if (draggingSkeleton) {
            g.setColor(Color.GREEN);
            g.fillRect(mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        }

        if (draggingSlime) {
            g.setColor(Color.BLUE);
            g.fillRect(mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        }

        if (draggingVinewall) {
            g.setColor(Color.YELLOW);
            g.fillRect(mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        }

        if (draggingRecall) {
            iconImage = new ImageIcon(getClass().getResource("Asset/RecallDrag.png")).getImage();
            g.drawImage(iconImage, mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH - 20, CELL_HEIGHT - 20, null);
        }
    }

    public void addMouseListeners() {
        /**
         * Add Mouse Listeners.
         * mouse
         * - pressed
         *    - drag: units if Mana enough
         * - released
         *    - place: units if isFieldAvailable()
         *    - remove: units if use Recall
         * mouse motion
         * - moved: get mouse x, y
         * - dragged: get mouse x, y if dragging
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getX() >= BAR_X && e.getX() <= BAR_X + CELL_WIDTH && e.getY() >= BAR_Y + 10 && e.getY() <= BAR_Y + CELL_HEIGHT - 10) {
                    if (remainMana >= 100) {
                        draggingSkeleton = true;
                        System.out.println("Dragging Skeleton");
                        Audio.play(AudioName.PLANT_PICK_UP);
                        
                    }
                    else {
                        System.out.println("Not enough mana for Skeleton!");
                        Audio.play(AudioName.PLANT_CANT_PICK_UP);
                    }
                }
                else if (e.getX() >= BAR_X + CELL_WIDTH && e.getX() <= BAR_X + CELL_WIDTH * 2 && e.getY() >= BAR_Y + 10 && e.getY() <= BAR_Y + CELL_HEIGHT - 10) {
                    if (remainMana >= 50) {
                        draggingSlime = true;
                        System.out.println("Dragging Slime");
                        Audio.play(AudioName.PLANT_PICK_UP);
                    }
                    else {
                        System.out.println("Not enough mana for Slime!");
                        Audio.play(AudioName.PLANT_CANT_PICK_UP);
                    }
                }
                else if (e.getX() >= BAR_X + CELL_WIDTH * 2 && e.getX() <= BAR_X + CELL_WIDTH * 3 && e.getY() >= BAR_Y + 10 && e.getY() <= BAR_Y + CELL_HEIGHT - 10) {
                    if (remainMana >= 50) {
                        draggingVinewall = true;
                        System.out.println("Dragging Golem");
                        Audio.play(AudioName.PLANT_PICK_UP);
                    }
                    else {
                        System.out.println("Not enough mana for Golem!");
                        Audio.play(AudioName.PLANT_CANT_PICK_UP);
                    }
                }
                else if (e.getX() >= BAR_X + CELL_WIDTH * (COLS - 1) && e.getX() <= BAR_X + CELL_WIDTH * COLS && e.getY() >= BAR_Y + 10 && e.getY() <= BAR_Y + CELL_HEIGHT - 10) {
                    draggingRecall = true;
                    System.out.println("Dragging Recall");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (draggingSkeleton) {
                    int col = (e.getX() - GRID_OFFSET_X) / CELL_WIDTH;
                    int row = (e.getY() - GRID_OFFSET_Y) / CELL_HEIGHT;

                    if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                        if (isFieldAvailable(col, row)) {
                            units.add(new Skeleton(row, col));
                            remainMana -= 100;
                            Audio.play(AudioName.PLANT_PLACE);
                            getVfxs().add(new VFX(col * CELL_WIDTH, row * CELL_HEIGHT, "spawn_vfx"));
                        } else {
                            getVfxs().add(new VFX(mouseX - GRID_OFFSET_X - CELL_WIDTH / 2, mouseY - GRID_OFFSET_Y - CELL_HEIGHT / 2, "cross_NOT_vfx"));
                            System.out.println("***Field is Not available***");
                        }
                    }
                }
                else if (draggingSlime) {
                    int col = (e.getX() - GRID_OFFSET_X) / CELL_WIDTH;
                    int row = (e.getY() - GRID_OFFSET_Y) / CELL_HEIGHT;

                    if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                        if (isFieldAvailable(col, row)) {
                            units.add(new Slime(row, col));
                            remainMana -= 50;
                            Audio.play(AudioName.PLANT_PLACE);
                            getVfxs().add(new VFX(col * CELL_WIDTH, row * CELL_HEIGHT, "spawn_vfx"));
                        } else {
                            System.out.println("***Field is Not available***");
                        }
                    }
                }
                else if (draggingVinewall) {
                    int col = (e.getX() - GRID_OFFSET_X) / CELL_WIDTH;
                    int row = (e.getY() - GRID_OFFSET_Y) / CELL_HEIGHT;

                    if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                        if (isFieldAvailable(col, row)) {
                            units.add(new Vinewall(row, col));
                            remainMana -= 50;
                            Audio.play(AudioName.PLANT_PLACE);
                        } else {
                            System.out.println("***Field is Not available***");
                        }
                    }
                }
                else if (draggingRecall) {
                    int col = (e.getX() - GRID_OFFSET_X) / CELL_WIDTH;
                    int row = (e.getY() - GRID_OFFSET_Y) / CELL_HEIGHT;
                    if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                        if (!isFieldAvailable(col, row)) {
                            Iterator<Unit> unitIterator = units.iterator();
                            while (unitIterator.hasNext()) {
                                Unit unit = unitIterator.next();
                                if (unit.getRow() == row && unit.getCol() == col) {
                                    if (unit instanceof Skeleton) {
                                        ((Skeleton) unit).stopAttacking();
                                    }
                                    else if (unit instanceof Slime) {
                                        ((Slime) unit).stopGeneratingCost();
                                    }
                                    unitIterator.remove();
                                    getVfxs().add(new VFX(col * CELL_WIDTH, row * CELL_HEIGHT, "recall_vfx"));
                                    Audio.play(AudioName.PLANT_DELETE);
                                }
                            }
                        }
                    }
                }
                draggingSkeleton = false;
                draggingSlime = false;
                draggingVinewall = false;
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
                if (draggingSkeleton || draggingSlime || draggingVinewall || draggingRecall) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                    repaint();
                }
            }
        });

    }

}
