import java.awt.Rectangle;

public class Bullet {
    private int x, y;
    private int speed = 10;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += speed;
    }

    public int getX() {
        return x + GamePanel.GRID_OFFSET_X;
    }

    public int getY() {
        return y + GamePanel.GRID_OFFSET_Y;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), 30, 30);
    }

}
