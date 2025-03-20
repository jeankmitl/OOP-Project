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
    private static int remainMana = 50;
    public static final int MAX_MANA = 9999;
    public int manaRegenPct = 0; //test mana
    
    private static List<Unit> units;
    protected static List<Enemy> enemies;
    private static List<Bullet> bullets;
    private static List<VFX> vfxs;
    
    
    protected final Random random = new Random();
    private static final List<UnitType> unitTypes = new ArrayList<>(COLS - 1);
    
    private boolean draggingRecall = false;
    private int mouseX, mouseY;
    
    private int hoverPlaceX, hoverPlaceY;
    
    private final DTimer gameTimer;
    
    private final OTimer manaRecoverTimer10 = new OTimer(5);
    private final OTimer spawnEnemiesTimer10 = new OTimer(10);
    private final OTimer delayNidNoyTimer1 = new OTimer(0.1235);
    
    private final OTimer slowAnimTimer = new OTimer(0.15);
    private final OTimer mediumAnimTimer = new OTimer(0.25);
    
    private final OWait enemiesIsComingWait3 = new OWait(3);
    
    private boolean isAnyUnitDragging = false;
    
    protected int target,count_kill=0;
    private boolean victory = false;
    
    //same as: public awake()
    public GamePanel(int target) {
        /**
         * Awake. = for create Object w/ 'new'
         * - play: music 🎵, 
         * - set: BG image 🎨
         * - start: Game Loop 🔁
         */
        Audio.playMusic(AudioName.MUSIC_ONE);

        OTHER_THREAD = Thread.activeCount();
        backgroundImage = ImgManager.loadBG("bg_test");

        units = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        vfxs = new ArrayList<>();
        this.target = target;
        
        gameTimer = new DTimer(SPF, e -> fixedUpdate(SPF));
        addKeyListener(new GameKeyboardListener());
        startGameLoop(); // Always call on last GamePanel
        summonEnemies(); // spawn Enermy in this insted 
    }
    
    // <editor-fold defaultstate="collapsed" desc="(Ignore) Game Loop, Late Update, Debug Setup">
    private void startGameLoop() {
        GameLoop gameLoop = new GameLoop();
        GameLoop.setLateListener((d) -> lateUpdate());
        gameLoop.start();
        gameTimer.start();
        start();
    }
    
    // after run All Timer
    private void lateUpdate() {
        revalidate();
        repaint();
    }
    
    private void runDebugMode() {
        remainMana = 500;
//        summonEnemies(); //<<--- Off this bebore play stage 1
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
//        unitTypes.add(new UnitType(BigBall.class));
//        unitTypes.add(new UnitType(GolemSupport.class));
//        unitTypes.add(new UnitType(Explosion.class));
//        unitTypes.add(new UnitType(Explosive_turtle.class));
//        unitTypes.add(new UnitType(Nike.class));
//        unitTypes.add(new UnitType(SemiAutoBot.class));
//        unitTypes.add(new UnitType(GiveawaySlime.class));
        unitTypes.add(new UnitType(MiPya.class));
        unitTypes.add(new UnitType(Snake.class));
        unitTypes.add(new UnitType(Python.class));
        unitTypes.add(new UnitType(GiveawaySlime.class));
        
        
//        if (DEBUG_MODE) {
//            unitTypes.add(new UnitType(Candles6.class));
//        }
    }
   
    
    // SPF = 0.016666666666666666 (99% 60fps)
    private void fixedUpdate(double deltaTime) {
        // Add 50 cost every 15 seconds
        if (manaRecoverTimer10.tick(deltaTime)) {
            increaseMana(10);
        }
        manaRegenPct = (int)((manaRecoverTimer10.getElapsedTime() / manaRecoverTimer10.getDelay()) * 100);
        
        // spawn: enemies every 10s ➕
       if (this.target == this.count_kill && !this.victory) { // <<--- Off this bebore play stage 1
            System.out.println("You win");
            this.victory = true;
        }
        
        // update: animation every 2s
        updateAnimation(deltaTime);
        
        if (enemiesIsComingWait3.tick(deltaTime)) {
            System.out.println("Show Text: Ready. Set. Go!");
//            enemiesIsComingWait3.reset();  // like OTimer but use OTimer better
        }
        updateCooldown(deltaTime);
        updateLogic(deltaTime);
    }
    
    // <editor-fold defaultstate="collapsed" desc="All Reuse method">
    public boolean isFieldAvailable(int col, int row) {
        for (Unit unit : units) {
            if (unit.getRow() == row && unit.getCol() == col) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isFieldExtraAvailable(UnitType unit, int row, int col) {
        if (UnitCommensalism.class.isAssignableFrom(unit.unitClass)) {
            return getUnitCountFromField(col, row) == 1;
        } else if (UnitExtraFieldAvailable.class.isAssignableFrom(unit.unitClass)) {
            return getUnitCountFromField(col, row) < 2;
        } else if (UnitIgnoreFieldAvailable.class.isAssignableFrom(unit.unitClass)) {
            return true;
        }
        return false;
    }
    
    public boolean isFieldRestricted(UnitType unit, int row, int col) {
        if (UnitCommensalism.class.isAssignableFrom(unit.unitClass)) {
            System.out.println("hi");
            return getUnitCountFromField(col, row) != 1;
        }
        return false;
    }
    
    public Unit getUnitFromField(int col, int row) {
        for (Unit unit : units) {
            if (unit.getRow() == row && unit.getCol() == col) {
                return unit;
            }
        }
        return null;
    }
    
    public int getUnitCountFromField(int col, int row) {
        int i = 0;
        for (Unit unit : units) {
            if (unit.getRow() == row && unit.getCol() == col) {
                i++;
            }
        }
        return i;
    }
    
    public static void increaseMana(int mana) {
        remainMana += mana;
        if (remainMana > MAX_MANA) {
            remainMana = MAX_MANA;
        }
    }
    
    public static void reduceMana(int mana) {
        if (remainMana - mana < 0) {
            remainMana = 0;
        } else {
            remainMana -= mana;
        }
    }
    
    public void Spawn_Enemy(Enemy enemy){
        this.Spawn_Enemy(enemy, 1, 1);
    }
    
    public void Spawn_Enemy(Enemy enemy,int num){
        this.Spawn_Enemy(enemy, num, 10);
    }
    public void Spawn_Enemy(Enemy enemy,int num,int delay){
        Random random = new Random();
        for (int i=0;i<num;i++){
            new DWait(i*delay, e->{
               int randomBandit = random.nextInt(5);
                    enemies.add(enemy.createNew(1280 - GRID_OFFSET_X + random.nextInt(10) * 10, randomBandit));
                    System.out.println("Spawn Sucess");
                }).start();
           }
    }
    
    public void summonEnemies(){}
    
    public static List<Enemy> getEnemies() {
        return enemies;
    }
    
    public static List<Bullet> getBullets() {
        return bullets;
    }
    
    public static List<VFX> getVfxs() {
        return vfxs;
    }

    public static List<UnitType> getUnitTypes() {
        return unitTypes;
    }
    // </editor-fold>
    
    public void updateAnimation(double deltaTime) {
        /**
         * - update: Frame ️🖼️
         *    - of units, enemies, bullets, vfxs
         */
        if (mediumAnimTimer.tick(deltaTime)) {
            for (Unit unit : units) {
                unit.updateFrame();
            }
            for (Enemy enemy : enemies) {
                enemy.updateFrame();
            }
        }
        if (slowAnimTimer.tick(deltaTime)) {
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
                        if (enemy instanceof Ninja) {
                            ((Ninja)enemy).ability();
                        }
                        stop = true;
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - enemy.getLastAttackTime() >= 1000) {
                            enemy.attack(unit);
                            //--------------- RedHood Sustain Skill ----------------------
                            if (enemy instanceof LittleRedHood) {
                                LittleRedHood redHood = (LittleRedHood)enemy;
                                if (redHood.getHealth() + 5 > 181) {
                                    redHood.setHealth(181);
                                } else {
                                    redHood.setHealth(redHood.getHealth()+5);
                                }
                            // -----------------------------------------------------------
                            }
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
            }
            

            if (!stop) {
                enemy.move();
            }

            if (enemy.getX() + GRID_OFFSET_X <= 50) {
                System.out.println("Game Over!!! NOOB");
                System.exit(0);
            }

            if (enemy.isDead()) {
                count_kill += 1;
                System.out.println(count_kill);
                enemyIterator.remove();
                Audio.play(AudioName.KILL2);
                getVfxs().add(new VFX(enemy.getX(), enemy.getY(), "dead_ghost_vfx"));
            }
        }

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.move();

            if (bullet instanceof BeamCleanRow) {
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
            } else if (bullet instanceof ExplosionBullet) {
                ExplosionBullet exp = (ExplosionBullet)bullet;
                int cleanRow = exp.getRow();
                Audio.play(AudioName.BEAM_CLEAN_ROW);
                bulletIterator.remove();
                for (Enemy enemy : enemies) {
                        System.out.println(enemy.getX());
                        System.out.println((exp.getCol() * CELL_WIDTH < enemy.getX()) + " / " + ((exp.getCol() + 1) * CELL_WIDTH > enemy.getX()));
                    if (enemy.getRow() == cleanRow && ((exp.getCol() - 1) * CELL_WIDTH < enemy.getX()) && ((exp.getCol() + 1) * CELL_WIDTH > enemy.getX())) {
                        System.out.println("hit");
                        enemy.takeDamage(exp.getAtk());
                    }
                }
                VFX vfx = new VFX(exp.getCol() * CELL_WIDTH - 30, exp.getRow() * CELL_HEIGHT - 20, "explosion_vfx");
                vfx.setWidth(CELL_WIDTH + 60);
                vfx.setHeight(CELL_HEIGHT + 20);
                GamePanel.getVfxs().add(vfx);
                getVfxs().add(vfx);
            } 
//            else if (bullet instanceof Bite) { //Mimic Beta test
//                for (Enemy enemy : enemies) {
//                    if (bullet.getBounds().intersects(enemy.getBounds())) {
//                        enemy.takeDamage(Mimic.getUNIT_STATS().getAtk());
//                        getVfxs().add(new VFX(bullet.getX() - GRID_OFFSET_X, bullet.getY() - GRID_OFFSET_Y - 40, "bone_hit"));
//                        bulletIterator.remove();
//                        Audio.play(AudioName.HIT);
//                        break;
//                    }
//                }
//            } 
            else { //Keep same stat with this here
                for (Enemy enemy : enemies) {
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
//                        enemy.debuff_stun();
                        enemy.takeDamage(bullet.getAtk());
                        getVfxs().add(bullet.getHitVfx());
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
    
    private void paintSmallHealthBar(Graphics g, Entity et) {
        if (et.getHealth() >= 9000) return;
        if (et.getHealth() != et.getMaxHealth()) {
            int x = et.getX() + GRID_OFFSET_X;
            int y = et.getY() + GRID_OFFSET_Y;
            int barHeight = 5;
            g.setColor(Color.darkGray);
            g.fillRect(x, y, CELL_WIDTH, barHeight);
            g.setColor(new Color(0x80CBC4));
            if (et instanceof Enemy) {
                g.setColor(new Color(0xB03052));
            }
            g.fillRect(x, y, (int)(((double)et.getHealth()/et.getMaxHealth())*CELL_WIDTH), barHeight);
        }
    }
    
    private void runDynamicHover(int row, int col, double leap) {
        int gridX = col * CELL_WIDTH + GRID_OFFSET_X;
        int gridY = row * CELL_HEIGHT + GRID_OFFSET_Y;
        hoverPlaceX = (int)(hoverPlaceX + leap * (gridX - hoverPlaceX));
        hoverPlaceY = (int)(hoverPlaceY + leap * (gridY - hoverPlaceY));
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

        //GRID
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
            } else {
                paintSmallHealthBar(g, unit);
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
            } else {
                paintSmallHealthBar(g, enemy);
            }
        }

        
        for (int i=0; i<bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            BufferedImage img = bullet.getBufferedImage();
            if (img != null) {
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
        g.fillRect(BAR_X + 740, BAR_Y - 5, (int)(manaRegenPct * (116.0 / 100.0)), 5);
        ///show mana system
        g.setColor(new Color(0,0,0,180));
        g.fillRect(550, 0, 150, 30);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        g.drawString(count_kill+" / "+target, 600, 20); // Display at top-left
        /////Count Enemy
        // frame operator
        for (int i = 0; i < COLS; i++) {
            iconImage = ImgManager.loadIcon("frame_op1");
            g.drawImage(iconImage, BAR_X + CELL_WIDTH * i, BAR_Y, CELL_WIDTH, CELL_HEIGHT, this);
            iconImage = ImgManager.loadIcon("frame_operator");
            g.drawImage(iconImage, BAR_X + CELL_WIDTH * i, BAR_Y, CELL_WIDTH, CELL_HEIGHT, this);
        }
        iconImage = ImgManager.loadIcon("Recall");
        g.drawImage(iconImage, BAR_X + CELL_WIDTH * (COLS - 1) + 10, BAR_Y + 10, CELL_WIDTH - 20, CELL_HEIGHT - 20, this);
        
        int countDragging = 0;
        int col = (mouseX - GRID_OFFSET_X) / CELL_WIDTH;
        int row = (mouseY - GRID_OFFSET_Y) / CELL_HEIGHT;
        if (draggingRecall) {
            runDynamicHover(row, col, 0.3);
            if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                if (!isFieldAvailable(col, row)) {
                    iconImage = ImgManager.loadIcon("recall_place_hover");
                    g.drawImage(iconImage, hoverPlaceX,  hoverPlaceY, CELL_WIDTH, CELL_HEIGHT, this);
                }
            }
            iconImage = ImgManager.loadIcon("RecallDrag");
            g.drawImage(iconImage, mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH - 20, CELL_HEIGHT - 20, null);
            countDragging++;
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
            g.drawImage(unit.getRoleIconImg(), (BAR_X + CELL_WIDTH * k) + (CELL_WIDTH - 35), BAR_Y, 30, 30, this);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            
            if (remainMana >= unit.getManaCost()) {
                g.setColor(new Color(162, 252, 255)); //blue
            } else {
                g.setColor(new Color(255, 110, 120)); //red
            }
            g.drawString(unit.getManaCost() + "", BAR_X + 5 + CELL_WIDTH * k, BAR_Y + CELL_HEIGHT - 5);
            if (unit.isDragging()) {
                runDynamicHover(row, col, 0.3);
                if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                    if ((isFieldAvailable(col, row) || isFieldExtraAvailable(unit, row, col)) && !isFieldRestricted(unit, row, col)) {
                        iconImage = ImgManager.loadIcon("green_place_hover");
                        g.drawImage(iconImage, hoverPlaceX,  hoverPlaceY, CELL_WIDTH, CELL_HEIGHT, this);
                    } else {
                        iconImage = ImgManager.loadIcon("red_place_hover");
                        g.drawImage(iconImage, hoverPlaceX, hoverPlaceY, CELL_WIDTH, CELL_HEIGHT, this);
                    }
                }
                g.setColor(Color.WHITE);
                if (unit.getClassUse().equals(Nike.class)) {
                    g.drawImage(unit.getProfileImg(), mouseX - (CELL_WIDTH * 10) / 2, mouseY - (CELL_HEIGHT * 10) / 2, CELL_WIDTH * 10, CELL_HEIGHT * 10, null);
                } else {
                    g.drawImage(unit.getProfileImg(), mouseX - CELL_WIDTH / 2, mouseY - CELL_HEIGHT / 2, CELL_WIDTH, CELL_HEIGHT, null);
                }
                countDragging++;
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
        isAnyUnitDragging = countDragging > 0;
        
        // about information for unit_operator
        if (!isAnyUnitDragging) {
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            FontMetrics metrics = getFontMetrics(g.getFont());
            if (mouseX >= BAR_X + CELL_WIDTH * (COLS - 1) && mouseX <= BAR_X + CELL_WIDTH * COLS 
                            && mouseY >= BAR_Y + 10 && mouseY <= BAR_Y + CELL_HEIGHT - 10) {
                g.setColor(new Color(0, 0, 0, 100));
                String msg = "Recall: teleport unit back to their operator (remove)";
                g.fillRect(BAR_X + (((COLS * CELL_WIDTH) / 2) - metrics.stringWidth(msg) /2) - 5, BAR_Y - metrics.getHeight(), metrics.stringWidth(msg) + 10, metrics.getHeight());
                g.setColor(Color.WHITE);
                g.drawString(msg, BAR_X + (((COLS * CELL_WIDTH) / 2) - metrics.stringWidth(msg) /2), BAR_Y - 5);
                
                runDynamicHover(row, col, 0.5);
                iconImage = ImgManager.loadIcon("white_place_hover");
                g.drawImage(iconImage, hoverPlaceX,  hoverPlaceY + 13, CELL_WIDTH, CELL_HEIGHT , this);
            } else {
                for (int i = 0; i< unitTypes.size(); i++) {
                    UnitType unit = unitTypes.get(i);
                    int unitX = BAR_X + CELL_WIDTH * i;
                    if (mouseX >= unitX && mouseX <= unitX + CELL_WIDTH 
                            && mouseY >= BAR_Y + 10 && mouseY <= BAR_Y + CELL_HEIGHT - 10) {
                        g.setColor(new Color(0, 0, 0, 100));
                        String msg = "";
                        if (unit.getRole() == UnitConfig.COST_GEN) {
                            msg = unit.getClassName() + "  -  " + unit.getHealth() + " hp | " + unit.getAtkSpeed()+ " secs/gen";
                        } else if (unit.getRole() == UnitConfig.DEFENDER) {
                            msg = unit.getClassName() + "  -  " + unit.getHealth() + " hp";
                        } else {
                            msg = unit.getClassName() + "  -  " + unit.getHealth() + " hp | " + unit.getAtk() + " atk";
                        }
                        g.fillRect(BAR_X + (((COLS * CELL_WIDTH) / 2) - metrics.stringWidth(msg) /2) - 5, BAR_Y - metrics.getHeight(), metrics.stringWidth(msg) + 10, metrics.getHeight());
                        g.setColor(Color.WHITE);
                        g.drawString(msg, BAR_X + (((COLS * CELL_WIDTH) / 2) - metrics.stringWidth(msg) /2), BAR_Y - 5);
                        runDynamicHover(row, col, 0.5);
                        if (!unit.isNoCoolDown()) {
                            iconImage = ImgManager.loadIcon("white_less_place_hover");
                        } else if (remainMana < unit.getManaCost()) {
                            iconImage = ImgManager.loadIcon("red_not_enough_place_hover");
                        } else {
                            iconImage = ImgManager.loadIcon("white_place_hover");
                        }
                        g.drawImage(iconImage, hoverPlaceX,  hoverPlaceY + 13, CELL_WIDTH, CELL_HEIGHT , this);
                    }
                }
            }
        }
        
        
        if (DEBUG_MODE) {
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
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
//                                System.out.println("Dragging: " + unit.getClassName() + "!");
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
                if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                    
                    if ((isFieldAvailable(col, row) || isFieldExtraAvailable(unit, row, col)) && !isFieldRestricted(unit, row, col)) {
                        Unit unitIns = UnitFactory.createUnit(unit.unitClass, row, col);
                        units.add(unitIns);
                        remainMana -= unit.getManaCost();
                        unit.startCooldown();
                        if (unitIns instanceof UnitTriggerable) {
                            ((UnitTriggerable)unitIns).triggerWhenPlace();
                        }
                        if (unitIns instanceof UnitIgnoreFieldAvailable) {
                            ((UnitIgnoreFieldAvailable)unitIns).getUnitFromField(getUnitFromField(col, row));
                        }
                        Audio.play(AudioName.PLANT_PLACE);
                        getVfxs().add(new VFX(col * CELL_WIDTH, row * CELL_HEIGHT, "select_vfx"));
                        getVfxs().add(new VFX(col * CELL_WIDTH, row * CELL_HEIGHT, "spawn_vfx"));
                    } else {
                        getVfxs().add(new VFX(col * CELL_WIDTH, row * CELL_HEIGHT, "cross_NOT_vfx"));
//                      System.out.println("***Field is Not available***");
                    }
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
    
    public class GameKeyboardListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    gameTimer.setDoubleSpeed(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    if (gameTimer.isRunning()) {
                        gameTimer.stop();
                    } else {
                        gameTimer.start();
                    }
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    gameTimer.setDoubleSpeed(false);
                    break;
            }
        }
        
        
    }
}
