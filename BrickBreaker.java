import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

public class BrickBreaker extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX = -1;
    private int ballDirY = -2;

    private int[][] map;
    private int brickWidth;
    private int brickHeight;

    public BrickBreaker() {
        map = new int[3][7];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = 540 / 7;
        brickHeight = 150 / 3;

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        BrickBreaker gamePlay = new BrickBreaker();

        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Brick Breaker Game");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePlay);
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        // Graphics2D object
        Graphics2D g2d = (Graphics2D) g;

        // Background
        g2d.setColor(Color.black);
        g2d.fillRect(1, 1, 692, 592);

        // Drawing map
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    g2d.setColor(Color.white);
                    g2d.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g2d.setStroke(new BasicStroke(3));
                    g2d.setColor(Color.black);
                    g2d.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }

        // Borders
        g2d.setColor(Color.yellow);
        g2d.fillRect(0, 0, 3, 592);
        g2d.fillRect(0, 0, 692, 3);
        g2d.fillRect(691, 0, 3, 592);

        // Scores
        g2d.setColor(Color.white);
        g2d.setFont(new Font("serif", Font.BOLD, 25));
        g2d.drawString("" + score, 590, 30);

        // Paddle
        g2d.setColor(Color.green);
        g2d.fillRect(playerX, 550, 100, 8);

        // Ball
        g2d.setColor(Color.yellow);
        g2d.fillOval(ballPosX, ballPosY, 20, 20);

        // Game over
        if (ballPosY > 570) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g2d.setColor(Color.red);
            g2d.setFont(new Font("serif", Font.BOLD, 30));
            g2d.drawString("Game Over, Scores: " + score, 190, 300);

            g2d.setFont(new Font("serif", Font.BOLD, 20));
            g2d.drawString("Press Enter to Restart", 230, 350);
        }

        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            // Ball and paddle collision
            if (new Rectangle2D.Float(ballPosX, ballPosY, 20, 20).intersects(new Rectangle2D.Float(playerX, 550, 100, 8))) {
                ballDirY = -ballDirY;
            }

            // Check map collision with the ball
            A: for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j] > 0) {
                        int brickX = j * brickWidth + 80;
                        int brickY = i * brickHeight + 50;

                        Rectangle2D rect = new Rectangle2D.Float(brickX, brickY, brickWidth, brickHeight);
                        Rectangle2D ballRect = new Rectangle2D.Float(ballPosX, ballPosY, 20, 20);

                        if (ballRect.intersects(rect)) {
                            map[i][j] = 0;
                            totalBricks--;
                            score += 5;

                            if (ballPosX + 19 <= rect.getX() || ballPosX + 1 >= rect.getX() + rect.getWidth()) {
                                ballDirX = -ballDirX;
                            } else {
                                ballDirY = -ballDirY;
                            }

                            break A;
                        }
                    }
                }
            }

            ballPosX += ballDirX;
            ballPosY += ballDirY;

            if (ballPosX < 0) {
                ballDirX = -ballDirX;
            }
            if (ballPosY < 0) {
                ballDirY = -ballDirY;
            }
            if (ballPosX > 670) {
                ballDirX = -ballDirX;
            }

            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballDirX = -1;
                ballDirY = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;

                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[0].length; j++) {
                        map[i][j] = 1;
                    }
                }

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
