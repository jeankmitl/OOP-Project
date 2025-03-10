package Main;

import Entities.Enemies.*;
import Entities.Units.*;
import Entities.Bullets.BeamCleanRow;
import Entities.Bullets.Bullet;
import Entities.Bullets.Bone;
import Asset.VFX;
import DSystem.DWait;
import Asset.Audio;
import DSystem.*;
import Asset.AudioName;
import Asset.ImgManager;
import Entities.Bullets.Beta_bullet;
import Entities.Enemies.LittleRedHood;
import Entities.UnitFactory;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Entities.*;
import Entities.Enemies.*;
import Entities.Units.*;
import Entities.Bullets.*;
import DSystem.*;
import Entities.Units.Roles.*;

import Asset.VFX;
import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
    public static final int BAR_X = GRID_OFFSET_X, BAR_Y = CELL_HEIGHT * ROWS + GRID_OFFSET_Y + 11;
    public static final int SPAWN_POINT = 1000;
    public static final String COMIC_SANS = "Comic Sans MS";
    public static final double SPF = 1.0 / 60.0;
    // </editor-fold>

    public final int OTHER_THREAD;
    public static int remainMana = 50;
    public static final int MAX_MANA = 9999;
    public int wight = 0; //test mana
    
    private static List<Unit> units;
    private static List<Enemy> enemies;
    private static List<Bullet> bullets;
    private static List<VFX> vfxs;
    
    
    private final Random random = new Random();
    private final List<UnitType> unitTypes = new ArrayList<>(COLS - 1);
    
    private boolean draggingRecall = false;
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
        summonEnemies();
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
        unitTypes.add(new UnitType(Skeleton.class));
        unitTypes.add(new UnitType(Slime.class));
        unitTypes.add(new UnitType(Kaniwall.class));
        unitTypes.add(new UnitType(Mimic.class)); //BETA unit
        if (DEBUG_MODE) {
            unitTypes.add(new UnitType(Candles6.class));
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
            summonEnemies();
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
        
        updateCooldown(deltaTime);
        updateLogic(deltaTime);
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
    
    private void summonEnemies() {
        int randomBandit = random.nextInt(5);
        int randomNinja = random.nextInt(5);
        int randomSorcerer = random.nextInt(5);
        int randomLRH = random.nextInt(5);
        enemies.add(new Bandit(1280-GRID_OFFSET_X+random.nextInt(10)*10, randomBandit));
        enemies.add(new Ninja(1280-GRID_OFFSET_X+random.nextInt(10)*10, randomNinja));
        enemies.add(new Sorcerer(1280-GRID_OFFSET_X+random.nextInt(10)*10, randomSorcerer));
        enemies.add(new LittleRedHood(1280-GRID_OFFSET_X+random.nextInt(10)*10, randomLRH));
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
    
    public void updateCooldown(double deltaTime) {
        for (UnitType unit: unitTypes) {
            unit.coolDownTick(deltaTime);
        }
    }
    
    public void updateLogic(double deltaTime) {
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
                if (unit.getBounds().intersects(enemy.getBounds())) {
                    if (unit instanceof UnitInvisible) {
                        ((UnitInvisible)unit).insersectEnemy(enemy);
                    } else {
                        stop = true;
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - enemy.getLastAttackTime() >= 1000) {
                            enemy.attack(unit);
                            if (unit.isDead()) {
                                Audio.play(AudioName.KILL2);
                                getVfxs().add(new VFX(unit.getX(), unit.getY(), "dead_ghost_vfx"));
                            }
                            if (unit instanceof UnitReflectable) {
                                ((UnitReflectable)unit).reflectDamage(enemy);
                            }
                            enemy.setLastAttackTime(currentTime);
                        }
                    }
                    break;
                }
//                unit.getAtkSpeed();
//                if (unit instanceof UnitGeneratable) {
//                    ((UnitGeneratable)unit).generateByAtkSpeed();
//                }
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
                        enemy.takeDamage(Skeleton.getUNIT_STATS().getAtk());
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
                        enemy.takeDamage(Candles6.getUNIT_STATS().getAtk());
                    }
                }
                VFX vfx = new VFX((bcr.getCol() + 1) * CELL_WIDTH, bcr.getRow() * CELL_HEIGHT, "Beam2");
                vfx.setWidth(1280);
                getVfxs().add(vfx);
            } else if(bullet instanceof Beta_bullet){ //Mimic Beta test
                for (Enemy enemy : enemies) {
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        enemy.takeDamage(Mimic.getUNIT_STATS().getAtk());
                        getVfxs().add(new VFX(bullet.getX() - GRID_OFFSET_X, bullet.getY() - GRID_OFFSET_Y - 40, "bone_hit"));
                        bulletIterator.remove();
                        Audio.play(AudioName.HIT);
                        break;
                    }
                }
            }
        }

        units.removeIf(Unit::isDead);
    }
    
    private void paintHealthBar(Graphics g, Entity et) {
        if (et.getHealth() >= 9000) return;
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.setFont(new Font(COMIC_SANS, Font.BOLD, 15));
        int barHeight = 15;
        int x = et.getX() + GRID_OFFSET_X;
        int y = et.getY() + GRID_OFFSET_Y;
        g.setColor(Color.darkGray);
        g.fillRect(x, y, CELL_WIDTH, barHeight);
        g.setColor(new Color(0x80CBC4));
        if (et instanceof Enemy) {
            g.setColor(new Color(0xB03052));
        }
        g.fillRect(x, y, (int)(((double)et.getHealth()/et.getMaxHealth())*CELL_WIDTH), barHeight);
        g.setColor(Color.white);
        g.drawString(et.getHealth() + "", 
                x + ((CELL_WIDTH - metrics.stringWidth(et.getHealth() + ""))/2), y + barHeight - 2);
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
        g.setColor(Color.white);
        g.setFont(new Font(COMIC_SANS, Font.BOLD, 20));
        
        // Units & hover to see Health
        final int RENDER_X = GRID_OFFSET_X + CELL_WIDTH;
        final int RENDER_Y = GRID_OFFSET_Y + CELL_HEIGHT;
        
        for (int i=0; i<units.size(); i++) {
            Unit unit = units.get(i);
            BufferedImage img = unit.getBufferedImage();
            g.drawImage(img, unit.getX() + GRID_OFFSET_X, unit.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH, null);
            if (mouseX >= unit.getX() + GRID_OFFSET_X && mouseX <= unit.getX() + RENDER_X
                    && mouseY >= unit.getY() + GRID_OFFSET_Y && mouseY <= unit.getY() + RENDER_Y) {
                paintHealthBar(g, unit);
            }
        }

        // Enemies & hover to see Health
        for (int i=0; i<enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            BufferedImage img = enemy.getBufferedImage();
            g.drawImage(img, enemy.getX() + GRID_OFFSET_X, enemy.getY() + GRID_OFFSET_Y, GamePanel.CELL_HEIGHT, GamePanel.CELL_WIDTH, null);
            if (mouseX >= enemy.getX() + GRID_OFFSET_X && mouseX <= enemy.getX() + RENDER_X
                    && mouseY >= enemy.getY() + GRID_OFFSET_Y && mouseY <= enemy.getY() + RENDER_Y) {
                paintHealthBar(g, enemy);
            }
        }

        
        for (int i=0; i<bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet instanceof Bone) {
                BufferedImage img = ((Bone) bullet).getBufferedImage();
                g.drawImage(img, bullet.getX() - 40, bullet.getY() - 20, 64, 64, null);
            }
        }
        
        for (int i=0; i<vfxs.size(); i++) {
            VFX vfx = vfxs.get(i);
            BufferedImage img = vfx.getBufferedImage();
            g.drawImage(img, vfx.getX(), vfx.getY(), vfx.getWidth(), vfx.getHeight(), null);
        }


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
        
        // frame operator
        for (int i = 0; i < COLS; i++) {
            iconImage = ImgManager.loadIcon("frame_op1");
            g.drawImage(iconImage, BAR_X + CELL_WIDTH * i, BAR_Y, CELL_WIDTH, CELL_HEIGHT, this);
            iconImage = ImgManager.loadIcon("frame_operator");
            g.drawImage(iconImage, BAR_X + CELL_WIDTH * i, BAR_Y, CELL_WIDTH, CELL_HEIGHT, this);
        }
        iconImage = ImgManager.loadIcon("Recall");
        g.drawImage(iconImage, BAR_X + CELL_WIDTH * (COLS - 1) + 10, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20, this);
        
        if (draggingRecall) {
            iconImage = ImgManager.loadIcon("RecallDrag");
            g.drawImage(iconImage, mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH - 20, CELL_HEIGHT - 20, null);
        }
        // all Unit operator
        int k = 0;
        for (UnitType unit : unitTypes) {
            if (unit.isDragging()) {
                iconImage = ImgManager.loadIcon("frame_op2");
                g.drawImage(iconImage, BAR_X + CELL_WIDTH * k, BAR_Y, CELL_WIDTH, CELL_HEIGHT, this);
                iconImage = ImgManager.loadIcon("frame_operator");
                g.drawImage(iconImage, BAR_X + CELL_WIDTH * k, BAR_Y, CELL_WIDTH, CELL_HEIGHT, this);
            }
            g.drawImage(unit.getProfileImg(), BAR_X + CELL_WIDTH * k, BAR_Y, CELL_WIDTH, CELL_HEIGHT, this);
            g.drawImage(unit.getRoleIconImg(k), (BAR_X + CELL_WIDTH * k) + (CELL_WIDTH - 35), BAR_Y, 30, 30, this);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            g.setColor(new Color(162, 252, 255, 255));
            g.drawString(unit.getManaCost() + "", BAR_X + 5 + CELL_WIDTH * k, BAR_Y + CELL_HEIGHT - 5);
            if (unit.isDragging()) {
                g.setColor(Color.WHITE);
                g.drawImage(unit.getProfileImg(), mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH, CELL_HEIGHT, null);
            }
            if (!unit.isNoCoolDown()) {
                g.setColor(Color.WHITE);
                iconImage = ImgManager.loadIcon("blackLowOpacityBG");
                g.drawImage(iconImage, BAR_X + CELL_WIDTH * k, BAR_Y, CELL_WIDTH, CELL_HEIGHT, this);
                g.drawString((int)unit.getCoolDownElapsed() + 1 + "", BAR_X + 5 + CELL_WIDTH * k, BAR_Y + 20);
                g2d.setStroke(new BasicStroke(4));
                g2d.drawArc(BAR_X + 20 + CELL_WIDTH * k, BAR_Y + 20, 50, 50, 90, (int)((unit.getCoolDownElapsed()/unit.getCooldown())*360));
            }
            k++;
        }
        
        if (DEBUG_MODE) {
            g.setColor(Color.WHITE);
            g.drawString("FPS: " + GameLoop.getFps(), 10, 20);
            g.drawString("Thread: " + (Thread.activeCount() - OTHER_THREAD) + " else: " + OTHER_THREAD , 10, 45);
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
                if (e.getX() >= BAR_X + CELL_WIDTH * (COLS - 1) && e.getX() <= BAR_X + CELL_WIDTH * COLS 
                        && e.getY() >= BAR_Y + 10 && e.getY() <= BAR_Y + CELL_HEIGHT - 10) {
                    draggingRecall = true;
                    Audio.play(AudioName.PLANT_PICK_UP);
                    System.out.println("Dragging Recall");
                    return;
                } 
                for (int i = 0; i< unitTypes.size(); i++) {
                    UnitType unit = unitTypes.get(i);
                    int unitX = BAR_X + CELL_WIDTH * i;
                    
                    if (e.getX() >= unitX && e.getX() <= unitX + CELL_WIDTH 
                            && e.getY() >= BAR_Y + 10 && e.getY() <= BAR_Y + CELL_HEIGHT - 10) {
                        if (unit.isNoCoolDown()){
                            if (remainMana >= unit.getManaCost()) {
                                unit.setDragging(true);
                                Audio.play(AudioName.PLANT_PICK_UP);
                                System.out.println("Dragging: " + unit.getClassName() + "!");
                            } else {
                                System.out.println("Not enough mana for " + unit.getClassName() + "!");
                                Audio.play(AudioName.PLANT_CANT_PICK_UP);
                            }
                        } else {
                            System.out.println(unit.getClassName() + " is cooldown!");
                        }
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int col = (e.getX() - GRID_OFFSET_X) / CELL_WIDTH;
                int row = (e.getY() - GRID_OFFSET_Y) / CELL_HEIGHT;
                
                if (draggingRecall) {
                    recallUnit(col, row);
                    draggingRecall = false;
                    return;
                }
                for (UnitType unit: unitTypes) {
                    if (unit.isDragging()) {
                        placeUnit(unit, row, col);
                        unit.setDragging(false);
                    }
                }
            }

            private void placeUnit(UnitType unit, int row, int col) {
                if (col >= 0 && col < COLS && row >= 0 && row < ROWS && isFieldAvailable(col, row)) {
                    Unit unitIns = UnitFactory.createUnit(unit.unitClass, row, col);
                    units.add(unitIns);
                    remainMana -= unit.getManaCost();
                    unit.startCooldown();
                    Audio.play(AudioName.PLANT_PLACE);
                    getVfxs().add(new VFX(col * CELL_WIDTH, row * CELL_HEIGHT, "spawn_vfx"));
                } else {
//                    System.out.println("***Field is Not available***");
                }
            }
            
            private void recallUnit(int col, int row) {
                if (col >= 0 && col < COLS && row >= 0 && row < ROWS && !isFieldAvailable(col, row)) {
                    Iterator<Unit> unitIterator = units.iterator();
                    while (unitIterator.hasNext()) {
                        Unit unit = unitIterator.next();
                        if (unit.getRow() == row && unit.getCol() == col) {
                            unitIterator.remove();
                            getVfxs().add(new VFX(col * CELL_WIDTH, row * CELL_HEIGHT, "recall_vfx"));
                            Audio.play(AudioName.PLANT_DELETE);
                        }
                    }
                }
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
//                System.out.println(mouseX + "x" + mouseY);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggingRecall) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                } else {
                    for (UnitType unit : unitTypes) {
                        if (unit.isDragging()) {
                            mouseX = e.getX();
                            mouseY = e.getY();
                        }
                    }
                }
            }
        });

    }
}
