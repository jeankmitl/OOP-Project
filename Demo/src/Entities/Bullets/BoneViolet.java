package Entities.Bullets;
import Asset.ImgManager;

public class BoneViolet extends Bullet {

    public BoneViolet(int x, int y, int atk) {
        super(x, y, atk, "bone_violet");
    }

    @Override
    public void move() {
        x += speed;
    }

}
