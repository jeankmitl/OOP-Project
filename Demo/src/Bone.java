import java.io.IOException;
import javax.imageio.ImageIO;

public class Bone extends Bullet {

    public Bone(int x, int y) {
        super(x, y);
        try {
            spriteSheet = ImageIO.read(getClass().getResource("Asset/SkeletonBone.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move() {
        x += speed;
    }

}
