package Entities.Enemies;



public class Ninja extends Enemy {

    public boolean notDashYet = true;
    
    public Ninja(double x, int row) {
        super(x, row, getENEMY_STATS());
    }
    
    public static EnemyStats getENEMY_STATS() {
        return EnemyConfig.NINJA_STATS;
    }

    @Override
    public void ability() {
        if (notDashYet && x >= 90) {
            this.x -= 189;
            notDashYet = false;
        }
    }
}
