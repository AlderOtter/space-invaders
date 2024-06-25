package SpaceInvaders;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Explosion {
    private ArrayList<Particle> particles = new ArrayList<>();
    private Random random = new Random();

    public Explosion(int x, int y, int numParticles) {
        for (int i = 0; i < numParticles; i++) {
            double angle = 2 * Math.PI * random.nextDouble();
            double speed = 2 + 2 * random.nextDouble();
            int lifespan = 30 + random.nextInt(30);
            particles.add(new Particle(x, y, Math.cos(angle) * speed, Math.sin(angle) * speed, lifespan));
        }
    }

    public void update() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update();
            if (particle.lifespan <= 0) {
                iterator.remove();
            }
        }
    }

    public void draw(Graphics g) {
        for (Particle particle : particles) {
            particle.draw(g);
        }
    }

    public boolean isDone() {
        return particles.isEmpty();
    }

    private class Particle {
        private double x, y;
        private double vx, vy;
        private int lifespan;

        public Particle(double x, double y, double vx, double vy, int lifespan) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.lifespan = lifespan;
        }

        public void update() {
            x += vx;
            y += vy;
            vy += 0.2; // gravity
            lifespan--;
        }

        public void draw(Graphics g) {
            // Color color = Color.ORANGE;
            g.setColor(new Color(255, 0, 0, lifespan * 255 / 100));
            g.fillOval((int) x - 2, (int) y - 2, 4, 4);
        }
    }
}