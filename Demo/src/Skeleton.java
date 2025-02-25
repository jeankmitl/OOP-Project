import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Skeleton extends Unit {

    private Timer attackTimer;

    public Skeleton(int row, int col) {

        super(row, col, 100, 10, 1500, 100);

        try {
            actionIdle = ImageIO.read(getClass().getResource("Asset/Skeleton.png"));
            actionATK = ImageIO.read(getClass().getResource("Asset/SkeletonThrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        attackTimer = new Timer(1500, e -> {
            if (isEnermyInfront(GamePanel.getEnermies())) {
                attack(GamePanel.getBullets());
                Audio.play(AudioName.FIRE_TINY);
            }
        });

        attackTimer.start();
    }

    public void stopAttacking() {
        attackTimer.stop();
    }

    @Override
    public boolean isEnermyInfront(List<Enermy> enermies) {
        for (Enermy enermy : enermies) {
            if (enermy.getRow() == this.getRow() && enermy.getX() > this.getX()) {
                this.Status = "ATK";
                this.currentFrame = 0;
                return true;
            }
        }
        this.Status = "idle";
        this.currentFrame = 0;
        return false;
    }

    @Override
    public void attack(List<Bullet> bullets) {
        bullets.add(new Bone(col * GamePanel.CELL_WIDTH + 100, row * GamePanel.CELL_HEIGHT + 30));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }

    @Override
    public BufferedImage getBufferedImage() {
        if (this.Status.equals("idle")){
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);}
        else {
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
    }

    @Override
    public void update_Frame() {
        if (this.Status.equals("idle")){
            currentFrame = (currentFrame + 1) % total_Frame_Idle;
        }
        else {
            currentFrame = (currentFrame + 1) % total_Frame_ATK;
        }
    }

}
