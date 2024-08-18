// package src;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Spaceship {
    public int x;
    public int y;

    private int dx;
    private int width;
    private int height;
    private int SPEED = 5;

    public Spaceship(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 10;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public void update() {
        x += dx;
        if (x < 0) {
            x = 0;
        } else if (x > 400 - width) { // Assuming panel width is 400
            x = 400 - width;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -SPEED;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = SPEED;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Projectile shoot() {
        return new Projectile(x + width / 2, y - height, Projectile.Type.SPACESHIP);
    }
}