package nl.fontys.sevenlo.games.breakout2.logic;

import java.awt.event.KeyEvent;
import org.openide.util.ImageUtilities;


public class Paddle extends Sprite implements Commons {

    String paddle = "images/paddle.png";

    int dx;

    public Paddle() {

        image = ImageUtilities.loadImage(paddle);

        width = image.getWidth(null);
        heigth = image.getHeight(null);

        resetState();

    }

    public void move() {
        x += dx;
        if (x <= 2) 
          x = 2;
        if (x >= Commons.PADDLE_RIGHT)
          x = Commons.PADDLE_RIGHT;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;

        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

    public void resetState() {
        x = 200;
        y = 360;
    }
}
