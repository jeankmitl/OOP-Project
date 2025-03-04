package Main;

import Entities.Enemies.Bandit;
import Entities.Enemies.Enemy;
import Entities.Enemies.Ninja;
import Entities.Enemies.Sorcerer;
import Entities.Units.Candles6;
import Entities.Bullets.BeamCleanRow;
import Entities.Bullets.Bullet;
import Entities.Bullets.Bone;
import Entities.Units.Skeleton;
import Entities.Units.Slime;
import Entities.Units.Unit;
import Entities.Units.Vinewall;
import Asset.VFX;
import DSystem.DWait;
import Asset.Audio;
import DSystem.DTimer;
import DSystem.GameLoop;
import Asset.AudioName;
import Asset.ImgManager;
import DSystem.OWait;
import DSystem.OTimer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.*;

public class GamePanel extends JPanel {
    
    //TURN OFF IF NOT DEBUG: set mana, faster spawn, etc...
    private final boolean DEBUG_MODE = true;
    
    private Image backgroundImage;
    private Image iconImage;
    
    // <editor-fold defaultstate="collapsed" desc="All static final variables">
    private static final int ROWS = 5;
    private static final int COLS = 9;
    public static final int CELL_WIDTH = 95;
    public static final int CELL_HEIGHT = 95;
    public static final int GRID_OFFSET_X = 175; // Move grid right
    public static final int GRID_OFFSET_Y = 100; // Move grid down
    public static final int BAR_X = GRID_OFFSET_X, BAR_Y = CELL_HEIGHT * ROWS + GRID_OFFSET_Y + 10;
    public static final int SPAWN_POINT = 1000;
    // </editor-fold>

    public final int OTHER_THREAD;
    public static int remainMana = 0; // for test only, normal is 0
    public static final int MAX_MANA = 9999;
    public int wight = 0; //test mana
    
    private static List<Unit> units;
    private static List<Enemy> enemies;
    private static List<Bullet> bullets;
    private static List<VFX> vfxs;
    
    private final Random random = new Random();
    private static Set<Class<?>> unitDefaultBehaviorClasses = new HashSet<>(Arrays.asList(
        Skeleton.class, Slime.class, Vinewall.class, Candles6.class));
    
    private static List<Class<?>> selectionList = new ArrayList<>(Arrays.asList(
          Skeleton.class, Slime.class, Vinewall.class
    ));
    
    private boolean draggingSkeleton = false;
    private boolean draggingSlime = false;
    private boolean draggingVinewall = false;
    private boolean draggingRecall = false;
    ///cd System///
    private boolean cd_Skeleton = false;
    private boolean cd_slime = false;
    /*private boolean cd_Skeleton = false;
    private boolean cd_Skeleton = false;
    private boolean cd_Skeleton = false;*/
    ///cd Animation///
    private double cd_time_Skeleton = 0;
    private double anglecolone = 0;
    ///////
    private int mouseX, mouseY;
    
    private OTimer manaRecoverTimer15 = new OTimer(15);
    private OTimer spawnEnemiesTimer10 = new OTimer(10);
    private OTimer animSpriteTimer2 = new OTimer(0.15);
    private OTimer delayNidNoyTimer1 = new OTimer(0.1235);
    
    private OWait enemiesIsComingWait3 = new OWait(3);
    
    //same as: public awake()
    public GamePanel() {
        /**
         * Awake. = for create Object w/ 'new'
         * - play: music 🎵, 
         * - set: BG image 🎨
         * - start: Game Loop 🔁
         */
        System.out.println("Now implementing File Manager!");
        Audio.playMusic(AudioName.MUSIC_ONE);

        OTHER_THREAD = Thread.activeCount();
        backgroundImage = ImgManager.loadBG("Stage1");

        units = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        vfxs = new ArrayList<>();
        
        startGameLoop(); // Always call on last GamePanel
    }
    
    // <editor-fold defaultstate="collapsed" desc="(Ignore) Game Loop, Late Update, Debug Setup">
    private void startGameLoop() {
        GameLoop gameLoop = new GameLoop();
//        GameLoop.addListener((d) -> System.out.println("Frame Update!"));
        GameLoop.setLateListener((d) -> lateUpdate());
        gameLoop.start();
        
        final double SPF = 1.0 / 60.0;
        new DTimer(SPF, e -> {
            fixedUpdate(SPF);
        }).start();
        
        start();
    }
    
    // after run All Timer
    private void lateUpdate() {
        revalidate();
        repaint();
    }
    
    private void runDebugMode() {
        remainMana = 500;
        
        int randomBandit = random.nextInt(5);
        int randomNinja = random.nextInt(5);
        int randomSorcerer = random.nextInt(5);
        enemies.add(new Bandit(1280-GRID_OFFSET_X, randomBandit));
        enemies.add(new Ninja(1280-GRID_OFFSET_X, randomNinja));
        enemies.add(new Sorcerer(1280-GRID_OFFSET_X, randomSorcerer));
    }
    // </editor-fold>
    
    //run after setup GameLoop
    private void start() {
        if (DEBUG_MODE) runDebugMode();
        addMouseListeners();
        
        new DWait(3, e -> {
            System.out.println("Enemies is coming!");
        }).start();
        
        for (int i=0; i<ROWS; i++) {
            units.add(new Candles6(i, -1));
        }
        
    }
   
    
    // SPF = 0.016666666666666666 (99% 60fps)
    private void fixedUpdate(double deltaTime) {
        // Add 50 cost every 15 seconds
        if (manaRecoverTimer15.tick(deltaTime)) {
            remainMana += 25;
            if (GamePanel.remainMana > GamePanel.MAX_MANA) {
                GamePanel.remainMana = GamePanel.MAX_MANA;
            }
        }
        
        // spawn: enemies every 10s ➕
        if (spawnEnemiesTimer10.tick(deltaTime)) {
            int randomBandit = random.nextInt(5);
            int randomNinja = random.nextInt(5);
            int randomSorcerer = random.nextInt(5);
            enemies.add(new Bandit(1280-GRID_OFFSET_X, randomBandit));
            enemies.add(new Ninja(1280-GRID_OFFSET_X, randomNinja));
            enemies.add(new Sorcerer(1280-GRID_OFFSET_X, randomSorcerer));
        }
        
        // update: animation every 2s
        if (animSpriteTimer2.tick(deltaTime)) { 
            updateAnimation();
        }
        
        if (delayNidNoyTimer1.tick(deltaTime)) { // <-- fix time that
            if(wight< 116){
                wight += 1;
            } else{
                wight = 0;
            }
        }
        
        if (enemiesIsComingWait3.tick(deltaTime)) {
            System.out.println("Show Text: Ready. Set. Go!");
//            enemiesIsComingWait3.reset();  // like OTimer but use OTimer better
        }
        
 
        updateLogic();
    }
    
    // <editor-fold defaultstate="collapsed" desc="All Reuse method">
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
    
    
    public static List<Enemy> getEnemies() {
        return enemies;
    }
    
    public static List<Bullet> getBullets() {
        return bullets;
    }
    
    public static List<VFX> getVfxs() {
        return vfxs;
    }
    // </editor-fold>
    
    public void updateAnimation() {
        /**
         * - update: Frame ️🖼️
         *    - of units, enemies, bullets, vfxs
         */
        /*for (Unit unit : units) {
            unit.updateFrame();
        }
        for (Enemy enemy : enemies) {
            enemy.updateFrame();
        }*/
        for (Bullet bullet : bullets) {
            bullet.updateFrame();
        }
        Iterator<VFX> vfxIter = vfxs.iterator();
        while (vfxIter.hasNext()) {
            VFX vfx = vfxIter.next();
            if (!vfx.isFinishUpdate()) {
                vfx.updateFrameVFXOnce();
            } else {
                vfxIter.remove();
            }
        }
    }
    
    public void updateLogic() {
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
                if (unitDefaultBehaviorClasses.contains(unit.getClass())) {
                    if (unit.getBounds().intersects(enemy.getBounds())) {
                        stop = true;
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - enemy.getLastAttackTime() >= 1000) {
                            enemy.attack(unit);
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

            if (bullet instanceof Bone) {
                for (Enemy enemy : enemies) {
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        enemy.takeDamage(10);
                        getVfxs().add(new VFX(bullet.getX() - GRID_OFFSET_X, bullet.getY() - GRID_OFFSET_Y - 40, "bone_hit"));
                        bulletIterator.remove();
                        Audio.play(AudioName.HIT);
                        break;
                    }
                }
            } else if (bullet instanceof BeamCleanRow) {
                BeamCleanRow bcr = (BeamCleanRow)bullet;
                int cleanRow = bcr.getRow();
                Audio.play(AudioName.BEAM_CLEAN_ROW);
                bulletIterator.remove();
                for (Enemy enemy : enemies) {
                    if (enemy.getRow() == cleanRow) {
                        enemy.takeDamage(999);
                    }
                }
                VFX vfx = new VFX(0, bcr.getRow() * CELL_HEIGHT, "beam");
                vfx.setWidth(1280);
                getVfxs().add(vfx);
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
            BufferedImage img = unit.getBufferedImage();
            g.drawImage(img, unit.getX() + GRID_OFFSET_X, unit.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH, null);
        }

        for (Enemy enemy : enemies) {
            BufferedImage img = enemy.getBufferedImage();
            g.drawImage(img, enemy.getX() + GRID_OFFSET_X, enemy.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH, null);
        }

        for (Bullet bullet : bullets) {
            if (bullet instanceof Bone) {
                BufferedImage img = ((Bone) bullet).getBufferedImage();
                g.drawImage(img, bullet.getX() - 40, bullet.getY() - 20, 64, 64, null);
            }
        }
        
        vfxs.forEach((vfx) -> {
            BufferedImage img = vfx.getBufferedImage();
            g.drawImage(img, vfx.getX(), vfx.getY(), vfx.getWidth(), vfx.getHeight(), null);
        });

        g.setColor(Color.BLUE);
//        for (int i = 0; i <= 1; i++) {
////            g.drawLine(BAR_X, BAR_Y + i * CELL_HEIGHT, BAR_X + COLS * CELL_WIDTH, BAR_Y + i * CELL_HEIGHT);
//        }
        for (int i = 0; i < COLS; i++) {
            iconImage = ImgManager.loadIcon("frame_operator");
            g.drawImage(iconImage, BAR_X + CELL_WIDTH * i, BAR_Y + 1, CELL_WIDTH, CELL_HEIGHT, this);
//            g.drawLine(BAR_X + i * CELL_WIDTH, BAR_Y, BAR_X + i * CELL_WIDTH, BAR_Y + CELL_HEIGHT);
        }

        g.setColor(Color.GREEN);
        g.fillRect(BAR_X + 10, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        g.setColor(Color.BLUE);
        g.fillRect(BAR_X + 105, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        g.setColor(Color.YELLOW);
        g.fillRect(BAR_X + 200, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20);
        iconImage = ImgManager.loadIcon("Recall");
        g.drawImage(iconImage, BAR_X + CELL_WIDTH * (COLS - 1) + 10, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20, this);
        ///
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(BAR_X + 740, BAR_Y - 50, 116, 50);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        g.drawString("" + remainMana, BAR_X + 800, BAR_Y - 20); // Display at top-left
        g.setColor(new Color(162, 252, 255, 255));
        iconImage = ImgManager.loadIcon("Mana_icon");
        g.drawImage(iconImage, BAR_X+730, BAR_Y-63,70,70, null);
        g.fillRect(BAR_X + 740, BAR_Y - 5, wight, 5);
        ///show mana system
        if (cd_Skeleton){ 
            Graphics2D ggd = (Graphics2D) g;
            ggd.setStroke(new BasicStroke(4));
            ggd.setColor(Color.WHITE);
            ggd.drawArc(BAR_X + 20, BAR_Y + 20, 50, 50, 90, (int) anglecolone);//(int) angle);
        }
        ////^^^ BETA CD System ^^^/////
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
            iconImage = ImgManager.loadIcon("RecallDrag");
            g.drawImage(iconImage, mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH - 20, CELL_HEIGHT - 20, null);
        }
        
        if (DEBUG_MODE) {
            g.setColor(Color.WHITE);
            g.drawString("FPS: " + GameLoop.getFps(), 10, 20);
            g.drawString("Thread: " + (Thread.activeCount() - OTHER_THREAD) + " else: " + OTHER_THREAD , 10, 45);
        }
    }
    
    public void Global_cd(String name){
        if(this.DEBUG_MODE == !false){ // << On/Off Cd System just add !
            if (name == "Skeleton"){
                this.cd_Skeleton = true;
                    DTimer temp = new DTimer(0.1, e->{
                        if(anglecolone < 360){
                            anglecolone += 7; // aprrox 7 pix/0.1 sec
                        }else{
                            anglecolone = 0;
                        }
                    });
                    temp.start();
                    new DWait(Skeleton.getCooldown(), e -> {
                        this.cd_Skeleton = false;
                        System.out.println("Reflesh Cd");
                        temp.stop();
                        anglecolone = 0;
                    }).start();
            }
            }else if(name == "Slime"){
                this.cd_slime = true;
                    new DWait(Slime.getCooldown(), e -> {
                        this.cd_slime = false;
                        System.out.println("Reflesh Cd");
                }).start();
            }
        else{}
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
                    if (remainMana >= 100 && !cd_Skeleton) {
                        draggingSkeleton = true;
//                        System.out.println("Dragging Skeleton");
                        Audio.play(AudioName.PLANT_PICK_UP);   
                    }else if(remainMana >= 100 && cd_Skeleton){
                        System.out.println("Skeleton are cooldown!");
                        Audio.play(AudioName.PLANT_CANT_PICK_UP);
                    }
                    else {
                        System.out.println("Not enough mana for Skeleton!");
                        Audio.play(AudioName.PLANT_CANT_PICK_UP);
                    }
                }
                else if (e.getX() >= BAR_X + CELL_WIDTH && e.getX() <= BAR_X + CELL_WIDTH * 2 && e.getY() >= BAR_Y + 10 && e.getY() <= BAR_Y + CELL_HEIGHT - 10) {
                    if (remainMana >= 50) {
                        draggingSlime = true;
//                        System.out.println("Dragging Slime");
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
//                        System.out.println("Dragging Golem");
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
                            Global_cd("Skeleton");
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
                }
            }
        });

    }

    
    
}
