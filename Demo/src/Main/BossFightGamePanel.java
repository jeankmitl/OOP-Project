/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Asset.Audio;
import Asset.AudioName;
import Asset.ImgManager;
import Asset.VFX;
import DSystem.DTimer;
import DSystem.DWait;
import DSystem.OTimer;
import DSystem.OWait;
import Entities.Bullets.BeamCleanRow;
import Entities.Bullets.Bullet;
import Entities.Bullets.ExplosionBullet;
import Entities.Enemies.Bandit;
import Entities.Enemies.BanditV2;
import Entities.Enemies.BanditV3;
import Entities.Enemies.Enemy;
import Entities.Enemies.LittleRedHood;
import Entities.Enemies.Ninja;
import Entities.Enemies.RCBomber;
import Entities.Enemies.RobotMonoWheel;
import Entities.Enemies.SongChinWu;
import Entities.Enemies.Sorcerer;
import Entities.Enemies.Tank;
import Entities.Enemies.TheBlueSword;
import Entities.Enemies.TheRedSword;
import Entities.Units.Candles6;
import Entities.Units.Roles.UnitInvisible;
import Entities.Units.Roles.UnitReflectable;
import Entities.Units.Unit;
import Main.EnemySummoner;
import Main.GamePanel;
import Main.StageConfig;
import Main.StageStats;
import static Main.GamePanel.CELL_HEIGHT;
import static Main.GamePanel.CELL_WIDTH;
import static Main.GamePanel.GRID_OFFSET_X;
import static Main.GamePanel.SPF;
//import static Main.GamePanel.bullets;
//import static Main.GamePanel.enemies;
import static Main.GamePanel.getVfxs;
//import static Main.GamePanel.units;
//import static Main.GamePanel.vfxs;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author USER
 */
public class BossFightGamePanel extends GamePanel {
    private static BossFightGamePanel instance;
    
    // ---------------- ChinWu stuff ---------------------------
    public boolean hasUsedSummonSwords = true;
    public boolean theRedSwordIsAlive = false;
    public boolean theBlueSwordIsAlive = false;
    private OTimer damageTimer = new OTimer(1);
    private OTimer teleportTimer = new OTimer(7);
    private OTimer summonTimer = new OTimer(16);


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

    public static List<Enemy> getEnemies() {
        return enemies;
    }

    public void summonSwords() {
        int col = 8; // Fixed column at the last column
        int xPos = col * CELL_WIDTH; // Convert column to X position

        Random random = new Random();
        int row1 = random.nextInt(5); // Random row for BlueSword
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
                Ninja.class,
                LittleRedHood.class,
                RobotMonoWheel.class,
                Sorcerer.class
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
    
    public void teleport(Enemy boss) {
        int newRow = random.nextInt(3) + 1; // Random row (2, 3, 4)
        boss.setRow(newRow);
        System.out.println("SongChinWu teleported to row: " + newRow);
    }
    
    public void summonRandomThreeEnemies(Enemy boss) {
        int summonCol = 8 * CELL_WIDTH + 30; // Spawn one column to the left
        int currentRow = boss.getRow();
        int[] rows = {currentRow - 1, currentRow, currentRow + 1};
        for (int row : rows) {
            if (row < 0 || row > 4) {
                continue; // Prevent out-of-bounds
            }
            Enemy summonedEnemy = getRandomEnemy();
            if (summonedEnemy != null) {
                spawnOneEnemyFixedPos(summonedEnemy, summonCol, row);
            }
        }

        System.out.println("SongChinWu summoned 3 enemies at column " + summonCol);
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

            // ----------------- SongChinWu Logic --------------------------
            if (enemy instanceof SongChinWu) {
                ((SongChinWu) enemy).update(this);
                if (((SongChinWu) enemy).getState() == SongChinWu.State.STAND_NO_SWORD_MOTIVATED) {
                    if (hasUsedSummonSwords) {
                        summonSwords();
                        hasUsedSummonSwords = false;
                        theRedSwordIsAlive = true;
                        theBlueSwordIsAlive = true;
                    }
                    
                    // Damage units every 1 second
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
                        teleport(enemy);
                        
                    }
                    if (summonTimer.tick(deltaTime)) {
                        summonRandomThreeEnemies(enemy);
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
                        }
                        stop = true;
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - enemy.getLastAttackTime() >= enemy.getAtkSpeed() * 1000) {
                            enemy.attack(unit);
                            // --------------- RedHood Sustain Skill ----------------------
                            if (enemy instanceof LittleRedHood) {
                                LittleRedHood redHood = (LittleRedHood) enemy;
                                if (redHood.getHealth() + 10 > 181) {
                                    redHood.setHealth(181);
                                } else {
                                    redHood.setHealth(redHood.getHealth() + 10);
                                }
                                // -----------------------------------------------------------
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
                        && !(enemy instanceof TheRedSword)) {
                    enemy.move();
                }
            }

            if (enemy.getX() + GRID_OFFSET_X <= 50) {
                System.out.println("Game Over!!! NOOB");
                System.exit(0);
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
                BeamCleanRow bcr = (BeamCleanRow) bullet;
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
                ExplosionBullet exp = (ExplosionBullet) bullet;
                int cleanRow = exp.getRow();
                Audio.play(AudioName.BEAM_CLEAN_ROW);
                bulletIterator.remove();
                for (Enemy enemy : enemies) {
                    System.out.println(enemy.getX());
                    System.out.println((exp.getCol() * CELL_WIDTH < enemy.getX()) + " / "
                            + ((exp.getCol() + 1) * CELL_WIDTH > enemy.getX()));
                    if (enemy.getRow() == cleanRow && ((exp.getCol() - 1) * CELL_WIDTH < enemy.getX())
                            && ((exp.getCol() + 1) * CELL_WIDTH > enemy.getX())) {
                        System.out.println("hit");
                        enemy.takeDamage(exp.getAtk());
                    }
                }
                VFX vfx = new VFX(exp.getCol() * CELL_WIDTH - 30, exp.getRow() * CELL_HEIGHT - 20, "explosion_vfx");
                vfx.setWidth(CELL_WIDTH + 60);
                vfx.setHeight(CELL_HEIGHT + 20);
                GamePanel.getVfxs().add(vfx);
                getVfxs().add(vfx);
            } // else if (bullet instanceof Bite) { //Mimic Beta test
              // for (Enemy enemy : enemies) {
              // if (bullet.getBounds().intersects(enemy.getBounds())) {
              // enemy.takeDamage(Mimic.getUNIT_STATS().getAtk());
              // getVfxs().add(new VFX(bullet.getX() - GRID_OFFSET_X, bullet.getY() -
              // GRID_OFFSET_Y - 40, "bone_hit"));
              // bulletIterator.remove();
              // Audio.play(AudioName.HIT);
              // break;
              // }
              // }
              // }
            else { // Keep same stat with this here
                for (Enemy enemy : enemies) {
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        // enemy.debuff_stun();
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
    
    @Override
    protected void resetGamePanel(StageSelector stage, EnemySummoner summoner) {
        super.resetGamePanel(stage, summoner);
        hasUsedSummonSwords = true;
        theRedSwordIsAlive = false;
        theBlueSwordIsAlive = false;
        remainMana = 500;
    }
}
