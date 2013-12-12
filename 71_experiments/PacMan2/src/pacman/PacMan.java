package pacman;

import javax.swing.JFrame;

public class PacMan extends JFrame {

    //http://zetcode.com/tutorials/javagamestutorial/pacman/
    //https://github.com/FamiliarStranger-/cs56-games-pacman/find/master
    
    public PacMan() {
        add(new Board());
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PacMan();
    }
    
}
