/*
 * ? Move and shoot
 * / Colission detection
 * / Explosions
 * / Aliens shoot back
 * / aliens move down when they reach the edge
 * / Total score 
 * / Combine bullet classes
 * / import graphics
 * / level counter
 * send explosions color
 * spaceship and alien should create the explosion
 * lives
 * Different types of aliens
 * get go to next level correct
 * barriers that wear down
 * moving backround
 * get the speed algorithm right: consider remaining aliens and y
 * Fire just one bullet at a time
 */

// package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;

public class Main extends JFrame {
    public Main() {
        this.add(new MainPanel());
        this.setTitle("Space Invaders");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
    }
}

class MainPanel extends JPanel implements ActionListener {
    private int score = 0;
    private int level = 1;
    private Timer timer;
    private Spaceship spaceship;
    private ArrayList<Projectile> projectiles  = new ArrayList<>();
    private ArrayList<Alien> aliens = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();

    private final int PANEL_WIDTH = 400;
    private final int PANEL_HEIGHT = 600;
    private final int DELAY = 30;

    public MainPanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new TAdapter());

        spaceship = new Spaceship(PANEL_WIDTH / 2, PANEL_HEIGHT - 60);
        
        aliens.add(new Alien(100, 100));
        aliens.add(new Alien(200, 130));
        aliens.add(new Alien(300, 160));
        aliens.add(new Alien(100, 190));
        aliens.add(new Alien(200, 220));
        aliens.add(new Alien(300, 250));

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Level: " + level, 200, 20);

        for (Explosion explosion : explosions) {
            explosion.draw(g);
        }

        spaceship.draw(g);

        for (Projectile bullet : projectiles) {
            bullet.draw(g);
        }

        for (Alien alien : aliens) {
            alien.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    private void update() {
        for (Alien alien : aliens) {
            if (Math.random() < 0.01) { // 1% chance to shoot each frame
                projectiles.add(alien.shoot());
            }
        }

        spaceship.update();

        for (Explosion explosion : explosions) {
            explosion.update();
        }

        for (Iterator<Projectile> it = projectiles.iterator(); it.hasNext(); ) {
            Projectile projectile = it.next();
            projectile.update();
            if (projectile.y < 0 || projectile.y > PANEL_HEIGHT) {
                it.remove();
            }
        }

        for (Alien alien : aliens) {
            alien.update();
        }

        checkCollisions();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                projectiles.add(spaceship.shoot());
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }
    }

    public void checkCollisions() {
        Iterator<Projectile> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            Rectangle projectileBounds = projectile.getBounds();
            
            Rectangle spaceshipBounds = spaceship.getBounds();
            if (projectile.type == Projectile.Type.ALIEN && projectileBounds.intersects(spaceshipBounds)) {
                // Collision detected! Remove bullet and alien.
                explosions.add(new Explosion(spaceship.x, spaceship.y, 10));
                projectileIterator.remove();
                break;
            }

            // Iterate over aliens
            Iterator<Alien> alienIterator = aliens.iterator();
            while (alienIterator.hasNext()) {
                Alien alien = alienIterator.next();
                Rectangle alienBounds = alien.getBounds();
    
                if (projectile.type == Projectile.Type.SPACESHIP && projectileBounds.intersects(alienBounds)) {
                    // Collision detected! Remove bullet and alien.
                    explosions.add(new Explosion(alien.x, alien.y, 10));
                    projectileIterator.remove(); 
                    alienIterator.remove();
                    score++;
                    break;
                }
            }
        }

        if(aliens.isEmpty()) {
            goToNextLevel();
        }
    }

    public void goToNextLevel() {
        level++;
            
        aliens.add(new Alien(100, 100));
        aliens.add(new Alien(200, 130));
        aliens.add(new Alien(300, 160));
        aliens.add(new Alien(100, 190));
        aliens.add(new Alien(200, 220));
        aliens.add(new Alien(300, 250));
        
    }
}
    

/*
private boolean leftPressed;
private boolean rightPressed;
private boolean spacePressed;

switch (e.getKeyCode()) {
    case KeyEvent.VK_LEFT:
        leftPressed = false;
        break;
    case KeyEvent.VK_RIGHT:
        rightPressed = false;
        break;
    case KeyEvent.VK_SPACE:
        spacePressed = false;
        break;
}
*/