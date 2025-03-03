import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class Slime extends Unit {

    private DTimer costGenerationTimer;
    
    public Slime(int row, int col) {
        super(row, col, 50, 0, 0, 50);
        try {
            actionIdle = ImageIO.read(getClass().getResource("Asset/Slime_re.png"));
            actionATK = ImageIO.read(getClass().getResource("Asset/SlimeGenerate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.setTotal_Frame_Idle(5);
        costGenerationTimer = new DTimer(15, e -> {
            if (isDead()) {
                costGenerationTimer.stop();
                return;
            }
            generateCost();
        });
        animationTimer = new DTimer(0.25, e -> updateFrame(0.25));
        costGenerationTimer.start();
        animationTimer.start();
    }
    
    public void stopGeneratingCost() {
        costGenerationTimer.stop();
    }

    // Add 50 cost every 15 seconds
    public void generateCost() {
        this.currentFrame = 0;
        this.Status = "ATK";
        GamePanel.remainMana += 50;
        if (GamePanel.remainMana > GamePanel.MAX_MANA) {
            GamePanel.remainMana = GamePanel.MAX_MANA;
        }
        new DWait(1.5, (e) -> {
            this.currentFrame = 0;
            this.Status ="idle";
            GamePanel.getVfxs().add(new VFX(getX(), getY() - 50, "get_mana_slime_vfx"));
        }).start();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(col * GamePanel.CELL_WIDTH + GamePanel.GRID_OFFSET_X, row * GamePanel.CELL_HEIGHT + GamePanel.GRID_OFFSET_Y, GamePanel.CELL_WIDTH, GamePanel.CELL_HEIGHT);
    }

    @Override
    public boolean isEnemyInfront(List<Enemy> enermies) {
        return false;
    }

    @Override
    public void attack(List<Bullet> bullets) {}

    @Override
    public BufferedImage getBufferedImage() {
        if (this.Status.equals("idle")){
            return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);}
        else {
            return actionATK.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
        }
    }
    
    @Override
    public void updateFrame(double x) {
        if (this.Status.equals("idle")){
                currentFrame = (currentFrame + 1) % total_Frame_Idle;
        }
        else {
            currentFrame = (currentFrame + 1) % total_Frame_ATK;
        }
    }

}
