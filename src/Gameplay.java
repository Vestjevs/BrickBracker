import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements ActionListener, KeyListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private Timer timer;
    private int delay = 8;

    private MapGenerator mapGenerator;


    Gameplay() {
        mapGenerator = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        //background
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592);

        //drawing map
        mapGenerator.draw((Graphics2D) graphics);

        //borders
        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(0, 0, 3, 592);

        //scores
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Rosemary", Font.ITALIC, 25));
        graphics.drawString("" + score, 590, 30);

        //paddle
        graphics.setColor(Color.green);
        graphics.fillRect(playerX, 550, 100, 8);

        //ball
        graphics.setColor(Color.yellow);
        graphics.fillOval(ballPosX, ballPosY, 20, 20);

        if (totalBricks <= 0) {
            this.play = false;
            this.ballXdir = 0;
            this.ballYdir = 0;
            graphics.setColor(Color.cyan);
            graphics.setFont(new Font("Rosemary", Font.ITALIC, 30));
            graphics.drawString("You won, scores : " + this.score, 260, 300);

            graphics.setFont(new Font("Rosemary", Font.ITALIC, 30));
            graphics.drawString("Press Enter to restart", 230, 350);

        }

        if (ballPosY > 570) {
            this.play = false;
            this.ballXdir = 0;
            this.ballYdir = 0;
            graphics.setColor(Color.cyan);
            graphics.setFont(new Font("Rosemary", Font.ITALIC, 30));
            graphics.drawString("Game over, scores : " + this.score, 190, 300);

            graphics.setFont(new Font("Rosemary", Font.ITALIC, 30));
            graphics.drawString("Press Enter to restart", 230, 350);


        }

        graphics.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        timer.start();
        if (this.play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                this.ballYdir = -this.ballYdir;
            }
            A:
            for (int i = 0; i < mapGenerator.getRowMap(); i++) {
                for (int j = 0; j < mapGenerator.getColMap(); j++) {
                    if (mapGenerator.getMapValue(i, j) > 0) {
                        int brickX = j * mapGenerator.getBrickWidth() + 80;
                        int brickY = i * mapGenerator.getBrickHeight() + 50;
                        int brickWidth = mapGenerator.getBrickWidth();
                        int brickHeight = mapGenerator.getBrickHeight();

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballResctangle = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rectangle;

                        if (ballResctangle.intersects(brickRect)) {
                            mapGenerator.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + 19 <= brickRect.x || ballPosY + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            this.ballPosX += this.ballXdir;
            this.ballPosY += this.ballYdir;
            if (this.ballPosX < 0) {
                this.ballXdir = -this.ballXdir;
            } else if (this.ballPosY < 0) {
                this.ballYdir = -this.ballYdir;
            } else if (this.ballPosX > 670) {
                this.ballXdir = -this.ballXdir;
            }
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {


    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (this.playerX > 600) {
                this.playerX = 600;
            } else {
                moveRight();
            }
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            if (this.playerX < 10) {
                this.playerX = 10;
            } else {
                moveLeft();
            }
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                this.play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballYdir = -2;
                ballXdir = -1;
                score = 0;
                playerX = 310;
                totalBricks = 21;
                mapGenerator = new MapGenerator(3, 7);

                repaint();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    private void moveLeft() {
        this.play = true;
        playerX -= 20;
    }

    private void moveRight() {
        this.play = true;
        playerX += 20;
    }
}
