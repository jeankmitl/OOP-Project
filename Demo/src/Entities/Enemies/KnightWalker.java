/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.Enemies;
import Entities.Bullets.Bullet;
import Entities.Bullets.Slash;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

/**
 *
 * @author sarin
 */
public class KnightWalker extends Enemy {

    private EnemySpriteSheets spriteSheets;
    protected boolean isAttacking = false;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public KnightWalker(double x, int row) {
        super(x, row, getENEMY_STATS());
        setFrame_Hight(48);
        setFrame_Width(48);
    }

    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.KNIGHT_WALKER_STATS;
    }

    @Override
    public void setStatus(String status, boolean isReframe) {
        super.setStatus(status, isReframe);

        if (spriteSheets != null) {
            if (status.equals(IDLE_STATUS)) {
                actionIdle = spriteSheets.getActionIdle();
            } else if (status.equals(ATK_STATUS)) {
                actionATK = spriteSheets.getActionAtk();
            }
        }
    }

    public void slash(List<Bullet> bullets) {
        setStatus(ATK_STATUS, true);

        scheduler.schedule(() -> {
            bullets.add(new Slash((int) getX(), (int) getY(), getAtk()));
            setStatus(IDLE_STATUS, true);
        }, 2800, TimeUnit.MILLISECONDS);

    }
}
