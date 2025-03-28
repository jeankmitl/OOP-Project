package Entities.Enemies;

import Main.GamePanel;
import DSystem.OTimer;

public class Sorcerer extends Enemy {

    private OTimer abilityTimer;

    public Sorcerer(double x, int row) {
        super(x, row, getENEMY_STATS());
        abilityTimer = new OTimer(30); // Steal mana every 20 seconds
    }

    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.SORCERER_STATS;
    }

    @Override
    public void move() {
        super.move(); // Move like a normal enemy

        // Check ability timer inside `move()`
        if (!isDead() && abilityTimer.tick(GamePanel.SPF)) {
            ability();
        }
    }

    @Override
    public void ability() {
        GamePanel.reduceMana(20);
        System.out.println("Mana Steal!!!");
    }
}
