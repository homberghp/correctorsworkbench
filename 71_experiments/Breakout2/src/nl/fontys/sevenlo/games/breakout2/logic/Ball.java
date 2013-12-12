package nl.fontys.sevenlo.games.breakout2.logic;

import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;


public class Ball extends Sprite implements Commons {

   private int xdir;
   private int ydir;

   protected String ball = "nl/fontys/sevenlo/games/breakout2/logic/images/ball.png";

   public Ball() {

     xdir = 1;
     ydir = -1;
     
     image = ImageUtilities.loadImage(ball);

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
