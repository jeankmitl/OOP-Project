import java.awt.Rectangle;
import java.util.List;
import java.awt.image.BufferedImage;

public abstract class Unit {

    protected int row, col;
    protected int health;
    protected int atk;
    protected int atkSpeed;
    protected int cost;
    
    protected BufferedImage actionIdle, actionATK;
    protected int currentFrame = 0;
    protected int total_Frame_Idle = 4;
    protected int total_Frame_ATK = 8;
    protected int frame_Width = 32, frame_Hight = 32;
    protected String Status = "idle";
    
    public abstract boolean isEnermyInfront(List<Enermy> enermies);
    public abstract void attack(List<Bullet> bullets);
    public abstract Rectangle getBounds();

    public Unit(int row, int col, int health, int atk, int atkSpeed, int cost) {
        this.row = row;
        this.col = col;
        this.health = health;
        this.atk = atk;
        this.atkSpeed = atkSpeed;
        this.cost = cost;
    }
    
    public Unit getPlant() {
        return this;
    }
    
    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getX() {
        return col * GamePanel.CELL_WIDTH;
    }

    public int getY() {
        return row * GamePanel.CELL_HEIGHT;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    public BufferedImage getBufferedImage() {
        return actionIdle.getSubimage(currentFrame * frame_Width, 0, frame_Width, frame_Hight);
    }
    
    public void update_Frame() {
        currentFrame = (currentFrame + 1) % total_Frame_Idle;
    }

}
