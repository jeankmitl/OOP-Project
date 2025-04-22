package Entities.Bullets;
import Asset.ImgManager;
import Entities.Enemies.Enemy;
import java.util.ArrayList;

public class RomaBullet extends Bullet {
    private int speedDelay;
    private ArrayList<Enemy> hitEnemy;

    public RomaBullet(int x, int y, int atk) {
        super(x, y, atk, "Roma_bullet");
        hitEnemy = new ArrayList<>();
        speed = 17;
    }

    @Override
    public void move() {
        x += speed;
        if (speedDelay >= 2) {
            speed--;
            speedDelay = 0;
        } else {
            speedDelay++;
        }
    }

    public void addHitEnemy(Enemy enemy) {
        hitEnemy.add(enemy);
    }

    public boolean containHitEnemy(Enemy enemy) {
        return hitEnemy.contains(enemy);
    }
}
