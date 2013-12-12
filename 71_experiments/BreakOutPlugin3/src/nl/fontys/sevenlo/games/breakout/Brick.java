package nl.fontys.sevenlo.games.breakout;

import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;


public class Brick extends Sprite {

    String brickie = "nl/fontys/sevenlo/games/breakout/images/brickie.png";

    boolean destroyed;


    public Brick(int x, int y) {
      this.x = x;
      this.y = y;

      ImageIcon ii = ImageUtilities.loadImageIcon(brickie,false);
      image = ii.getImage();

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