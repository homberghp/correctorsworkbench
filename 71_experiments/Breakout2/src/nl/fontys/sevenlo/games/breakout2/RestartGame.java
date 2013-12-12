/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sevenlo.games.breakout2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import nl.fontys.sevenlo.games.breakout2.logic.Board;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Tools",
        id = "nl.fontys.sevenlo.games.breakout2.RestartGame"
)
@ActionRegistration(
        displayName = "#CTL_RestartGame"
)
@ActionReference(path = "Menu/File", position = 850, separatorBefore = 825, separatorAfter = 875)
@Messages("CTL_RestartGame=Restart game")
public final class RestartGame implements ActionListener {

    private final Board board;

    public RestartGame(Board board) {
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        board.restart();
    }
}
