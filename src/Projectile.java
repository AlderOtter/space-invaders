// package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile {
    public enum Type {
        SPACESHIP, ALIEN
    }

    public int x;
    public int y;
    public Type type;
    
    private int speed = 10;
    private int width = 10;
    private int height = 10;
    private Color color;

    public Projectile(int x, int y, Type type) {
        this.type = type;

        configureByType();

        this.x = x - this.width / 2; // Center the bullet on the spaceship
        this.y = y;
    }

    private void configureByType() {
        switch (type) {
            case SPACESHIP:
                speed = 10;
                width = 5;
                height = 20;
                color = Color.BLUE;
                break;
            case ALIEN:
                speed = -10;
                width = 3;
                height = 15;
                color = Color.RED;
                break;
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void update() {
        y -= speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
        
    }
}
