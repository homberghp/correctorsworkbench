package nl.fontys.sevenlo.games.breakout;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;
import static nl.fontys.sevenlo.games.breakout.Commons.*;
import org.openide.util.ImageUtilities;

public class Board extends JPanel  {

    Image ii;
    Timer timer;
    String message = "Game Over";
    String bgFile ="nl/fontys/sevenlo/games/breakout/images/fantasy-space-background-walls.png";
    Ball ball;
    Paddle paddle;
    Brick bricks[];

    boolean ingame = true;
    int timerId;

    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        bricks = new Brick[30];
        setDoubleBuffered(true);
        timer = new Timer(10,new ScheduleTask());
        bgImage = ImageUtilities.loadImage(bgFile);
    }

    public void addNotify() {
        super.addNotify();
        gameInit();
    }

    public void gameInit() {

        ball = new Ball();
        paddle = new Paddle();

        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                k++;
            }
        }
        ingame =true;
        timer.start();
    }

    private Image bgImage;
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0,0,Commons.WIDTH, Commons.HEIGHT, this);
//        g.setColor(Color.DARK_GRAY);
//        g.fillRect(0,0,Commons.WIDTH, Commons.HEIGHT);

        if (ingame) {
            g.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                    ball.getWidth(), ball.getHeight(), this);
            g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                    paddle.getWidth(), paddle.getHeight(), this);

            for (int i = 0; i < 30; i++) {
                if (!bricks[i].isDestroyed()) {
                    g.drawImage(bricks[i].getImage(), bricks[i].getX(),
                            bricks[i].getY(), bricks[i].getWidth(),
                            bricks[i].getHeight(), this);
                }
            }
        } else {
            
            Font font = new Font("Verdana", Font.BOLD, 18);
            FontMetrics metr = this.getFontMetrics(font);

            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(message,
                    (Commons.WIDTH - metr.stringWidth(message)) / 2,
                    Commons.WIDTH / 2);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }
    }

    class ScheduleTask implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ball.move();
            paddle.move();
            checkCollision();
            repaint();
        }
    }

    public void stopGame() {
        ingame = false;
        timer.start();
    }

    public void checkCollision() {

        if (ball.getRect().getMaxY() > Commons.BOTTOM) {
            stopGame();
        }

        for (int i = 0, j = 0; i < 30; i++) {
            if (bricks[i].isDestroyed()) {
                j++;
            }
            if (j == 30) {
                message = "Victory";
                stopGame();
            }
        }

        if ((ball.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {
                ball.setXDir(-1);
                ball.setYDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {
                ball.setXDir(-1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos >= second && ballLPos < third) {
                ball.setXDir(0);
                ball.setYDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {
                ball.setXDir(1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos > fourth) {
                ball.setXDir(1);
                ball.setYDir(-1);
            }

        }

        for (int i = 0; i < 30; i++) {
            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                Point pointRight
                        = new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom
                        = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed()) {
                    if (bricks[i].getRect().contains(pointRight)) {
                        ball.setXDir(-1);
                    } else if (bricks[i].getRect().contains(pointLeft)) {
                        ball.setXDir(1);
                    }

                    if (bricks[i].getRect().contains(pointTop)) {
                        ball.setYDir(1);
                    } else if (bricks[i].getRect().contains(pointBottom)) {
                        ball.setYDir(-1);
                    }

                    bricks[i].setDestroyed(true);
                }
            }
        }
    }
}
