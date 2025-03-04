package Bullets;

import Asset.ImgManager;
import java.io.IOException;
import javax.imageio.ImageIO;

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
