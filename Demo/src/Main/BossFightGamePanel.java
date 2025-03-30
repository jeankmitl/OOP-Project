/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.VFX;
import DSystem.DWait;
import DSystem.OTimer;
import Entities.Bullets.BeamCleanRow;
import Entities.Bullets.BeamCleanRowPurple;
import Entities.Bullets.BigBallBullet;
import Entities.Bullets.Bite;
import Entities.Bullets.Bullet;
import Entities.Bullets.CannonBullet;
import Entities.Bullets.ExplosionBullet;
import Entities.Bullets.JavaPush;
import Entities.Bullets.Slash;
import Entities.Bullets.TurtleBullet;
import Entities.Enemies.AntKing;
import Entities.Enemies.Bandit;
import Entities.Enemies.BanditV2;
import Entities.Enemies.BanditV3;
import Entities.Enemies.Enemy;
import Entities.Enemies.IShowSpeed;
import Entities.Enemies.KnightWalker;
import Entities.Enemies.LittleRedHood;
import Entities.Enemies.Ninja;
import Entities.Enemies.RCBomber;
import Entities.Enemies.RobotMonoWheel;
import Entities.Enemies.SongChinWu;
import Entities.Enemies.Sorcerer;
import Entities.Enemies.TheBlueSword;
import Entities.Enemies.TheRedSword;
import Entities.Units.Candles6;
import Entities.Units.CandlesExplosion;
import Entities.Units.Mimic;
import Entities.Units.Roles.UnitInvisible;
import Entities.Units.Roles.UnitReflectable;
import Entities.Units.Unit;
import static Main.GamePanel.CELL_HEIGHT;
import static Main.GamePanel.CELL_WIDTH;
import static Main.GamePanel.GRID_OFFSET_X;
import static Main.GamePanel.GRID_OFFSET_Y;
import static Main.GamePanel.enemies;
import static Main.GamePanel.getVfxs;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.SwingWorker;

/**
 *
 * @author USER
 */
public class BossFightGamePanel extends GamePanel {
    private static BossFightGamePanel instance;
    
    // ---------------- ChinWu stuff ---------------------------
    public boolean hasUsedSummonSwords = false;
    public boolean hasSummonKnightWalker = false;
    public boolean theRedSwordIsAlive = false;
    public boolean theBlueSwordIsAlive = false;
    private boolean isHalfHealh = false;
    
    private OTimer damageTimer = new OTimer(1);
    private OTimer teleportTimer = new OTimer(7);
    private OTimer summonTimer = new OTimer(12);
    private OTimer summonPhaseTwoTimer = new OTimer(18);
    private OTimer teleportKnightTimer = new OTimer(9);
    private OTimer slashTimer = new OTimer(10);



    protected BossFightGamePanel(StageSelector stage, EnemySummoner summoner) {
        super(stage, summoner);
        System.out.println("hasUsedSummonSwords " + hasUsedSummonSwords);
        System.out.println("theRedSwordIsAlive " + theRedSwordIsAlive);
        System.out.println("theBlueSwordIsAlive " + theBlueSwordIsAlive);
    }

    public static GamePanel getInstance(StageSelector stage, EnemySummoner summoner) {
        if (instance == null)
            instance = new BossFightGamePanel(stage, summoner);
        instance.resetGamePanel(stage, summoner);
        return instance;
    }
    
    //run after setup GameLoop
    @Override
    protected void start() {
        increaseMana(450);
        
        new DWait(3, e -> {
            System.out.println("Enemies is coming!");
        }).start();
        
        for (int i=0; i<ROWS; i++) {
            units.add(new Candles6(i, -1));
        }
        if (getClass() != BossFightGamePanel.class && getClass() != BossFightGamePanel2PlayerRough.class) {
            Audio.playMusic("dungeon_song_raid.wav");
        }
    }

    public static List<Enemy> getEnemies() {
        return enemies;
    }

    public void summonSwords() {
        int col = 8; // Fixed column at the last column
        int xPos = col * CELL_WIDTH; // Convert column to X position

        Random random = new Random();
        int row1 = random.nextInt(5);
        int row2;

        // Ensure different rows for RedSword and BlueSword
        do {
            row2 = random.nextInt(5);
        } while (row2 == row1);

        this.spawnOneEnemyFixedPos(new TheBlueSword(0, 0), xPos, row1);
        this.spawnOneEnemyFixedPos(new TheRedSword(0, 0), xPos, row2);

        System.out.println("Summoned TheBlueSword at (" + xPos + "," + row1 + ")");
        System.out.println("Summoned TheRedSword at (" + xPos + "," + row2 + ")");
    }
    
    public void summonKnightWalker(Enemy boss) {
        int xPos = 8 * CELL_WIDTH + 20; // Spawn one column to the left
        int yPos = boss.getRow();
        spawnOneEnemyFixedPos(new KnightWalker(0, 0), xPos, yPos);
    }

    public void spawnOneEnemyFixedPos(Enemy enemy, int xPos, int yPos) {
        new DWait(0, e -> {
            enemies.add(enemy.createNew(xPos, yPos));
        }).start();
    }
    
    public Enemy getRandomEnemy() {
        List<Class<? extends Enemy>> enemyTypes = Arrays.asList(
                RCBomber.class,
                Bandit.class,
                BanditV2.class,
                BanditV3.class,
                LittleRedHood.class,
                RobotMonoWheel.class,
                Sorcerer.class,
                IShowSpeed.class,
                Ninja.class
        );

        Random random = new Random();
        int index = random.nextInt(enemyTypes.size());

        try {
            return enemyTypes.get(index).getDeclaredConstructor(double.class, int.class).newInstance(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void teleport(SongChinWu boss) {
        int newRow = random.nextInt(3) + 1; // Random row (2, 3, 4)
        boss.setRow(newRow);
        System.out.println("SongChinWu teleported to row: " + newRow);
    }
    
    public void teleport(KnightWalker eliteBoss) {
        int newRow = random.nextInt(5);
        eliteBoss.setRow(newRow);
    }
    
    public void summonRandomThreeEnemies(Enemy boss) {
        int summonCol = 8 * CELL_WIDTH + 30; // Spawn one column to the left
        int currentRow = boss.getRow();
        int[] rows = {currentRow - 1, currentRow, currentRow + 1};
        for (int row : rows) {
            Enemy summonedEnemy = getRandomEnemy();
            if (summonedEnemy != null) {
                spawnOneEnemyFixedPos(summonedEnemy, summonCol, row);
            }
        }
    }
    
    public void summonThreeAntKing(Enemy boss) {
        int summonCol = 8 * CELL_WIDTH + 30;
        int currentRow = boss.getRow();
        int[] rows = {currentRow - 1, currentRow, currentRow + 1};
        for (int row : rows) {
            spawnOneEnemyFixedPos(new AntKing(0, 0), summonCol, row);
        }
    }

    @Override
    public void updateLogic(double deltaTime) {
        /**
         * Update. - enemies - attack: if INTERSECTS units ⚔️ - move: if NOT
         * attack ️▶️ - GAME OVER: if enemies reach 🏴 - remove: if isDead() 💀
         * - bullets - move - damage: if INTERSECTS enemies & - units - remove:
         * if isDead() 💀
         */
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            
            if (enemy instanceof IShowSpeed) {
                ((IShowSpeed) enemy).update();
            }
            
            if (enemy instanceof KnightWalker) {
                if (teleportKnightTimer.tick(deltaTime)) {
                    teleport((KnightWalker)enemy);
                }
            }
            
            // ----------------- SongChinWu Logic --------------------------
            if (enemy instanceof SongChinWu) {
                ((SongChinWu) enemy).update(this);
                if (((SongChinWu) enemy).getState() == SongChinWu.State.STAND_NO_SWORD_MOTIVATED) {
                    if (!hasUsedSummonSwords) {
                        summonSwords();
                        hasUsedSummonSwords = true;
                        theRedSwordIsAlive = true;
                        theBlueSwordIsAlive = true;
                    }
                    
                    // DOT every 1 second
                    if (damageTimer.tick(deltaTime)) { 
                        for (Unit currUnit : units) {
                            if (!(currUnit instanceof Candles6)) {
                                currUnit.takeDamage(2);
                            }
                        }
                    }
                }
                if (((SongChinWu) enemy).getState() == SongChinWu.State.STAND_NO_SWORD_MOTIVATED || ((SongChinWu) enemy).getState()== SongChinWu.State.STAND_NO_SWORD) {
                    if (teleportTimer.tick(deltaTime)) {
                        teleport((SongChinWu)enemy);
                        
                    }
                    if (!isHalfHealh) {
                        if (summonTimer.tick(deltaTime)) {
                            summonRandomThreeEnemies(enemy);
                        }
                    } else {
                        if(summonPhaseTwoTimer.tick(deltaTime)) {
                            summonThreeAntKing(enemy);
                        }
                    }
                    
                    if (enemy.getHealth() <= enemy.getMaxHealth()*0.5 && !hasSummonKnightWalker) {
                        summonKnightWalker(enemy);
                        hasSummonKnightWalker = true;
                        isHalfHealh = true;
                        
                    }
                }
            }
            // -------------------------------------------------------------

            boolean stop = false;
            for (Unit unit : units) {
                if (unit.getBounds().intersects(enemy.getBounds())) {
                    if (unit instanceof UnitInvisible) {
                        ((UnitInvisible) unit).insersectEnemy(enemy);
                    } else {
                        if (enemy instanceof Ninja) {
                            ((Ninja) enemy).ability();
                        } else if (enemy instanceof AntKing) {
                            ((AntKing) enemy).ability();
                        }
                        stop = true;
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - enemy.getLastAttackTime() >= enemy.getAtkSpeed() * 1000) {
                            enemy.attack(unit);
                            // --------------- RedHood Sustain Skill ----------------------
                            if (enemy instanceof LittleRedHood) {
                                LittleRedHood redHood = (LittleRedHood) enemy;
                                if (redHood.getHealth() + 10 > redHood.getMaxHealth()) {
                                    redHood.setHealth(redHood.getMaxHealth());
                                } else {
                                    redHood.setHealth(redHood.getHealth() + 10);
                                }
                                // -----------------------------------------------------------
                            } else if (enemy instanceof AntKing) {
                                AntKing antKing = (AntKing) enemy;
                                int hp = antKing.getHealth();
                                int lifeSteal = (int)(hp + ((antKing.getMaxHealth()-hp)*0.03));
                                if (hp + lifeSteal > antKing.getMaxHealth()) {
                                    antKing.setHealth(antKing.getMaxHealth());
                                } else {
                                    antKing.setHealth(hp + lifeSteal);
                                }
                            }
                            if (unit.isDead()) {
                                Audio.play(AudioName.KILL2);
                                getVfxs().add(new VFX(unit.getX(), unit.getY(), "dead_ghost_vfx"));
                            }
                            if (unit instanceof UnitReflectable) {
                                ((UnitReflectable) unit).reflectDamage(enemy);
                            }
                            enemy.setLastAttackTime(currentTime);
                        }
                    }
                    break;
                }
            }

            if (!stop) {
                if (!(enemy instanceof SongChinWu) && !(enemy instanceof TheBlueSword)
                        && !(enemy instanceof TheRedSword) && !(enemy instanceof KnightWalker)) {
                    enemy.move();
                } else {
                    if (enemy instanceof KnightWalker) {
                        if (slashTimer.tick(deltaTime)) {
                            ((KnightWalker)enemy).slash(bullets);
                        }
                    }
                }
            }

            if (enemy.getX() + GRID_OFFSET_X <= 50) {
                Audio.play(AudioName.BUTTON_CLICK);
                LoadingScreen loadingScreen = new LoadingScreen();
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        System.out.println("Game Over!!! NOOB");
                        stopGameLoop();
                        Thread.sleep(3000);
                        return null;
                    }

                    @Override
                    protected void done() {
                        loadingScreen.dispose();
                        stage.loadStage("Back");
                        Audio.playMusic("mainMenu");
                    }
                };
                worker.execute();
            }

            if (enemy.isDead()) {
                if (enemy instanceof SongChinWu) {
                    count_kill += 1;
                }
                if (enemy instanceof TheBlueSword) {
                    this.increaseMana(1000);
                    theBlueSwordIsAlive = false;
                }
                if (enemy instanceof TheRedSword) {
                    for (Unit unit : units) {
                        unit.setHealth(unit.getMaxHealth());
                    }
                    for (Enemy curEnemy : enemies) {
                        curEnemy.takeDamage((int)(curEnemy.getHealth() * 0.2));
                        theRedSwordIsAlive = false;
                    }
                }
                if (enemy instanceof KnightWalker) {
                    for (Enemy curEnemy : enemies) {
                        if (curEnemy instanceof SongChinWu) {
                            curEnemy.takeDamage((int) (curEnemy.getHealth()*0.5));
                        }
                    }
                }
                
                enemyIterator.remove();
                Audio.play(AudioName.KILL2);
                getVfxs().add(new VFX(enemy.getX(), enemy.getY(), "dead_ghost_vfx"));
            }
        }

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.move();

            // slash bullet (from Knight Walker))
            if (bullet instanceof Slash) {
                for (Unit unit : units) {
                    if (!(unit instanceof Candles6)) {
                        if (bullet.getBounds().intersects(unit.getBounds())) {
                            unit.takeDamage(5);
                            break;
                        }
                    }
                }
                if (bullet.getX() <= 0) {
                    bulletIterator.remove();
                }
            }

            else if (bullet instanceof BeamCleanRow) {
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
            } else if (bullet instanceof BeamCleanRowPurple) {
                BeamCleanRowPurple bcr = (BeamCleanRowPurple)bullet;
                    int cleanRow = bcr.getRow();
                    Audio.play(AudioName.BEAM_CLEAN_ROW);
                    bulletIterator.remove();
                    for (Enemy enemy : enemies) {
                        if (enemy.getRow() == cleanRow) {
                            enemy.takeDamage(CandlesExplosion.getUNIT_STATS().getAtk());
                            enemy.debuff_chill();
                        }
                    }
                    VFX vfx = new VFX(-GRID_OFFSET_X, bcr.getRow() * CELL_HEIGHT, "beam_purple");
                    vfx.setWidth(1280);
                    getVfxs().add(vfx);
            } else if (bullet instanceof TurtleBullet) {
                TurtleBullet exp = (TurtleBullet)bullet;
                int cleanRow = exp.getRow();
                Audio.play(AudioName.BEAM_CLEAN_ROW);
                bulletIterator.remove();
                for (Enemy enemy : enemies) {
                    if (cleanRow == enemy.getRow() && ((exp.getCol() - 1) * CELL_WIDTH < enemy.getX()) && ((exp.getCol() + 1) * CELL_WIDTH > enemy.getX())) {
                        enemy.takeDamage(exp.getAtk());
                    }
                }
                VFX vfx = new VFX(exp.getCol() * CELL_WIDTH - 30, exp.getRow() * CELL_HEIGHT - 20, "explosion_vfx");
                vfx.setWidth(CELL_WIDTH + 60);
                vfx.setHeight(CELL_HEIGHT + 20);
                getVfxs().add(vfx);
                
            }  else if (bullet instanceof ExplosionBullet) {
                ExplosionBullet exp = (ExplosionBullet)bullet;
                int cleanRow = exp.getRow();
                Audio.play(AudioName.BEAM_CLEAN_ROW);
                bulletIterator.remove();
                for (Enemy enemy : enemies) {
                    if ((cleanRow == enemy.getRow() || cleanRow + 1 == enemy.getRow() || cleanRow - 1 == enemy.getRow())
                            && ((exp.getCol() - 1) * CELL_WIDTH < enemy.getX()) && ((exp.getCol() + 1) * CELL_WIDTH > enemy.getX())) {
                        enemy.takeDamage(exp.getAtk());
                    }
                }
                VFX vfx2 = new VFX(exp.getCol() * CELL_WIDTH, (exp.getRow() * CELL_HEIGHT) - (500 - CELL_HEIGHT), "explosion_beam_vfx");
                vfx2.setHeight(500);
                getVfxs().add(vfx2);
                
                VFX vfx = new VFX(exp.getCol() * CELL_WIDTH - 50, exp.getRow() * CELL_HEIGHT - 40, "explosion_vfx");
                vfx.setWidth(CELL_WIDTH + 100);
                vfx.setHeight(CELL_HEIGHT + 40);
                getVfxs().add(vfx);
                
            } 
            else if (bullet instanceof Bite) { //Mimic Beta test
                for (Enemy enemy : enemies) {
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        enemy.takeDamage(Mimic.getUNIT_STATS().getAtk());
                        getVfxs().add(new VFX(bullet.getX() - GRID_OFFSET_X, bullet.getY() - GRID_OFFSET_Y - 40, "bone_hit"));
                        bulletIterator.remove();
                        Audio.play(AudioName.HIT);
                        break;
                    }
                }
            } else if (bullet instanceof CannonBullet) {
                CannonBullet cb = (CannonBullet)bullet;
                for (Enemy enemy : enemies) {
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        for (Enemy enemyBadLuck: enemies) {
                            if (enemy.getRow() == enemyBadLuck.getRow() && 
                                    (enemy.getX() - CELL_WIDTH < enemyBadLuck.getX()) && (enemy.getX() + CELL_WIDTH > enemyBadLuck.getX())) {
                                enemyBadLuck.takeDamage(bullet.getAtk());
                            }
                        }
                        Audio.play(AudioName.BEAM_CLEAN_ROW);
                        VFX vfx = new VFX(enemy.getX(), enemy.getY(), "explosion_vfx");
                        vfx.setWidth(CELL_WIDTH + 60);
                        vfx.setHeight(CELL_HEIGHT + 20);
                        GamePanel.getVfxs().add(vfx);
                        getVfxs().add(vfx);
                        
                        cb.useBulletLife();
                        if (cb.getBulletLife() <= 0) {
                            bulletIterator.remove();
                        }
                        break;
                    }
                }
            }
            else { //Keep same stat with this here
                for (Enemy enemy : enemies) {
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        enemy.takeDamage(bullet.getAtk());
                        if (enemy.getMaxHealth() <= 1300) {
                            if (bullet instanceof JavaPush) enemy.move(-5);
                            if (bullet instanceof BigBallBullet) enemy.move(-20);
                        }
                        
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
    
    @Override
    protected void resetGamePanel(StageSelector stage, EnemySummoner summoner) {
        super.resetGamePanel(stage, summoner);
        hasUsedSummonSwords = false;
        theRedSwordIsAlive = false;
        theBlueSwordIsAlive = false;
        hasSummonKnightWalker = false;
        isHalfHealh = false;
    }
}
