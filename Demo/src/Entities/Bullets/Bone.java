package Entities.Bullets;
import Asset.ImgManager;

public class Bone extends Bullet {

    public Bone(int x, int y, int atk) {
        super(x, y, atk, "SkeletonBone");
    }

    @Override
    public void move() {
        x += speed;
    }

}
