package Entities.Bullets;
import Asset.ImgManager;

public class KitsuneBullet extends Bullet {

    public KitsuneBullet(int x, int y, int atk) {
        super(x, y, atk, "kitsune_bullet");
        speed = 1;
    }

    @Override
    public void move() {
        x += speed++;
    }

}
