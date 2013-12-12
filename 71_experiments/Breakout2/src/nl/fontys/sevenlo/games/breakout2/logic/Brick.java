package nl.fontys.sevenlo.games.breakout2.logic;

import org.openide.util.ImageUtilities;


public class Brick extends Sprite {

    String brickie = "images/brickie.png";

    boolean destroyed;


    public Brick(int x, int y) {
      this.x = x;
      this.y = y;

      image = ImageUtilities.loadImage(brickie);

      width = image.getWidth(null);
      heigth = image.getHeight(null);

      destroyed = false;
    }

    public boolean isDestroyed()
    {
      return destroyed;
    }

    public void setDestroyed(boolean destroyed)
    {
      this.destroyed = destroyed;
    }

}