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
import Entities.Bullets.BeamCleanRow;
import Entities.Bullets.Bullet;
import Entities.Bullets.ExplosionBullet;
import Entities.Enemies.Bandit;
import Entities.Enemies.BanditV2;
import Entities.Enemies.BanditV3;
import Entities.Enemies.Enemy;
import Entities.Enemies.LittleRedHood;
import Entities.Enemies.Ninja;
import Entities.Enemies.SongChinWu;
import Entities.Enemies.Sorcerer;
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
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author USER
 */
public class BossFightGamePanel extends GamePanel {
    private static BossFightGamePanel instance;

    private BossFightGamePanel(StageSelector stage, EnemySummoner summoner) {
        super(stage, summoner);
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
        Random random = new Random();
        int col = random.nextInt(4) + 6; // Random column between 6-9
        int xPos = GRID_OFFSET_X + col * CELL_WIDTH;

        int row = random.nextInt(5); // Random row
        int yPos = GRID_OFFSET_Y + row * CELL_HEIGHT;

        enemies.add(new TheBlueSword(xPos, yPos, TheBlueSword.getENEMY_STATS()));
        enemies.add(new TheRedSword(xPos, yPos, TheRedSword.getENEMY_STATS()));
        System.out.println("summon THE SWORD");
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
                    if (((SongChinWu) enemy).isDropedSword) {
                        summonSwords();
                        ((SongChinWu) enemy).isDropedSword = false; // Prevent duplicate summoning
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
    //
    // @Override
    // public void updateGame() {
    // super.updateGame();
    // if (boss != null) {
    // boss.update();
    // }
    //
    // if (boss != null && boss.getHealth() > 0) {
    // if (boss.getHealth() <= boss.getMaxHealth() * 0.1 && !isFinalPhase) {
    // startFinalPhase();
    // } else if (!isFinalPhase && System.currentTimeMillis() - lastSkillTime >
    // SKILL_COOLDOWN) {
    // useRandomSkill();
    // lastSkillTime = System.currentTimeMillis();
    // }
    // }
    //
    // if (isFinalPhase && System.currentTimeMillis() - finalPhaseStartTime >
    // FINAL_PHASE_DURATION) {
    // playerLose();
    // }
    // }
    //
    // private void useRandomSkill() {
    // int skill = random.nextInt(3);
    // switch (skill) {
    // case 0:
    // summonSwords();
    // break;
    // case 1:
    // summonEnemies();
    // break;
    // case 2:
    // boss.shootProjectiles();
    // break;
    // }
    // }
    //
    // private void summonSwords() {
    // summonedSwords.clear();
    // for (int i = 0; i < 2; i++) {
    // int row = random.nextInt(5) + 1;
    // int col = random.nextInt(3) + 7; // Last 3 columns
    // summonedSwords.add(new SummonedSword(row, col));
    // }
    // }
    //
    // @Override
    // public void summonEnemies() {
    // int row = random.nextInt(5);
    // addEnemy(new Enemy(row, 9));
    // if (row < 4) {
    // addEnemy(new Enemy(row + 1, 9));
    // }
    // }
    //
    // public void startFinalPhase() {
    // isFinalPhase = true;
    // finalPhaseStartTime = System.currentTimeMillis();
    // clearAllies();
    // }
    //
    // @Override
    // public void drawGrapics(Graphics g) {
    // super.drawGame(g);
    // if (boss != null) {
    // boss.draw(g);
    // }
    // for (SummonedSword sword : summonedSwords) {
    // sword.draw(g);
    // }
    // }
}
