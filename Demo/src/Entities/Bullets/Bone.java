package Entities.Bullets;
import Asset.ImgManager;

public class Bone extends Bullet {

    public Bone(int x, int y) {
        super(x, y);
        spriteSheet = ImgManager.loadSprite("SkeletonBone");
    }

    @Override
    public void move() {
        x += speed;
    }

}
