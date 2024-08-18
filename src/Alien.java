// package src;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
public class Alien {
    public int x;
    public int y;

    private int direction;
    private int speed = 2;
    private final int WIDTH = 22;
    private final int HEIGHT = 16;
    private ImageIcon alienImage;

    public Alien(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = 1;
        alienImage = new ImageIcon("src/Alien.gif");
    }

    public void draw(Graphics g) {
        alienImage.paintIcon(null, g, x, y);
    }

    public void update() {
        x += direction * speed;
        
        // Keep the alien within the bounds
        if (x < 0) {
            x = 0;
            direction = 1;
            y += 30;
            speed *= 1.4;
        } else if (x > 400 - WIDTH) { // Assuming panel width is 400
            x = 400 - WIDTH;
            direction = -1;
            y += 30;
            speed *= 1.4;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
        
    }

    public Projectile shoot() {
        return new Projectile(x + WIDTH / 2, y + HEIGHT, Projectile.Type.ALIEN);
    }
}