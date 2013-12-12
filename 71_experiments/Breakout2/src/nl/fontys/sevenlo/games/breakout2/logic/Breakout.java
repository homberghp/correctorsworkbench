package nl.fontys.sevenlo.games.breakout2.logic;

import javax.swing.JFrame;

public class Breakout extends JFrame {

    //http://zetcode.com/tutorials/javagamestutorial/breakout/
    
    public Breakout()
    {
        add(new Board());
        setTitle("Breakout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Commons.WIDTH, Commons.HEIGTH);
        setLocationRelativeTo(null);
        setIgnoreRepaint(true);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Breakout();
    }
}