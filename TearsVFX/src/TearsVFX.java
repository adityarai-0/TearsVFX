import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class TearsVFX extends JPanel implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    private static final int TEAR_COUNT = 2;
    private static final int TEAR_SPAWN_DELAY = 800; // milliseconds

    private ArrayList<Tear> tears;
    private Timer tearTimer;
    private Timer spawnTimer;
    private Point eyeLeft;
    private Point eyeRight;
    private Random random;

    public TearsVFX() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        tears = new ArrayList<>();
        random = new Random();

        // Position the eyes
        eyeLeft = new Point(WIDTH / 2 - 50, HEIGHT / 3);
        eyeRight = new Point(WIDTH / 2 + 50, HEIGHT / 3);

        // Timer for tear animation
        tearTimer = new Timer(16, this); // ~60 FPS
        tearTimer.start();

        // Timer for spawning new tears
        spawnTimer = new Timer(TEAR_SPAWN_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnTears();
            }
        });
        spawnTimer.start();
    }

    private void spawnTears() {
        // Spawn tears from each eye
        for (int i = 0; i < TEAR_COUNT; i++) {
            // Left eye tear
            int offsetX = random.nextInt(11) - 5; // -5 to +5
            tears.add(new Tear(eyeLeft.x + offsetX, eyeLeft.y + 15));

            // Right eye tear
            offsetX = random.nextInt(11) - 5;
            tears.add(new Tear(eyeRight.x + offsetX, eyeRight.y + 15));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update tear positions
        Iterator<Tear> it = tears.iterator();
        while (it.hasNext()) {
            Tear tear = it.next();
            tear.update();

            // Remove tears that are out of the screen
            if (tear.y > HEIGHT) {
                it.remove();
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw face
        drawFace(g2d);

        // Draw tears
        g2d.setColor(new Color(120, 185, 255, 180)); // Semi-transparent blue
        for (Tear tear : tears) {
            tear.draw(g2d);
        }
    }

    private void drawFace(Graphics2D g) {
        // Draw face circle
        g.setColor(new Color(255, 220, 175));
        g.fillOval(WIDTH/2 - 100, HEIGHT/3 - 100, 200, 200);

        // Draw eyes
        g.setColor(Color.WHITE);
        g.fillOval(eyeLeft.x - 20, eyeLeft.y - 15, 40, 30);
        g.fillOval(eyeRight.x - 20, eyeRight.y - 15, 40, 30);

        // Draw pupils
        g.setColor(new Color(50, 100, 150));
        g.fillOval(eyeLeft.x - 7, eyeLeft.y - 7, 14, 14);
        g.fillOval(eyeRight.x - 7, eyeRight.y - 7, 14, 14);

        // Draw sad mouth
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2f));
        g.drawArc(WIDTH/2 - 40, HEIGHT/3 + 40, 80, 40, 0, 180);
    }

    // Tear class to represent each individual tear drop
    private class Tear {
        private float x, y;
        private float speed;
        private float size;
        private float sway;
        private float swayAmount;
        private float alpha;

        public Tear(int x, int y) {
            this.x = x;
            this.y = y;
            this.speed = 1 + random.nextFloat() * 2; // 1-3 speed
            this.size = 3 + random.nextFloat() * 5; // 3-8 size
            this.sway = 0;
            this.swayAmount = (random.nextFloat() * 2 - 1) * 0.5f; // -0.5 to 0.5
            this.alpha = 0.6f + random.nextFloat() * 0.4f; // 0.6-1.0 alpha
        }

        public void update() {
            y += speed;
            speed += 0.05f; // Accelerate due to gravity

            // Add subtle swaying motion
            sway += 0.1f;
            x += Math.sin(sway) * swayAmount;

            // Increase size slightly as it falls
            size += 0.02f;
        }

        public void draw(Graphics2D g) {
            // Make tears more transparent at top to simulate forming
            float currentAlpha = alpha;
            if (y < eyeLeft.y + 50) {
                currentAlpha *= (y - eyeLeft.y) / 50f;
            }

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha));

            // Draw a teardrop shape
            int tearWidth = (int)size;
            int tearHeight = (int)(size * 1.8f);

            // Teardrop upper part (circle)
            g.fillOval((int)x - tearWidth/2, (int)y - tearWidth/2, tearWidth, tearWidth);

            // Teardrop lower part (triangle)
            int[] xPoints = {
                    (int)x - tearWidth/2,
                    (int)x + tearWidth/2,
                    (int)x
            };
            int[] yPoints = {
                    (int)y,
                    (int)y,
                    (int)y + tearHeight
            };
            g.fillPolygon(xPoints, yPoints, 3);

            // Reset composite
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tears Visual Effect");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TearsVFX());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}