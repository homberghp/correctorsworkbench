/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sevenlo.games.tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import tetris.Board;

@ActionID(
        category = "Tools",
        id = "nl.fontys.sevenlo.games.tetris.RestartTetrisAction"
)
@ActionRegistration(
        displayName = "#CTL_RestartTetrisAction"
)
@ActionReference(path = "Menu/File", position = 0)
@Messages("CTL_RestartTetrisAction=Restart Tetris")
public final class RestartTetrisAction implements ActionListener {

    private final Board context;

    public RestartTetrisAction(Board context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        this.context.start();
    }
}
