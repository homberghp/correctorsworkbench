package nl.fontys.sevenlo.games.breakout;

import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;
import static nl.fontys.sevenlo.games.breakout.Commons.*;

public class Ball extends Sprite {

   private int xdir;
   private int ydir;

   protected String ball = "nl/fontys/sevenlo/games/breakout/images/ball.png";

   public Ball() {

     xdir = 1;
     ydir = -1;

     ImageIcon ii = ImageUtilities.loadImageIcon(ball,false);
     image = ii.getImage();

     width = image.getWidth(null);
     heigth = image.getHeight(null);

     resetState();
    }


    public void move()
    {
      x += xdir;
      y += ydir;

      if (x == 0) {
        setXDir(1);
      }

      if (x == BALL_RIGHT) {
        setXDir(-1);
      }

      if (y == 0) {
        setYDir(1);
      }
    }

    public void resetState() 
    {
      x = 230;
      y = 355;
    }

    public void setXDir(int x)
    {
      xdir = x;
    }

    public void setYDir(int y)
    {
      ydir = y;
    }

    public int getYDir()
    {
      return ydir;
    }
}
