/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sevenlo.games.breakout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "nl.fontys.sevenlo.games.breakout.StartAction"
)
@ActionRegistration(
        iconBase = "nl/fontys/sevenlo/games/breakout/breakout.png",
        displayName = "#CTL_StartAction"
)
@ActionReference(path = "Menu/File", position = 0)
@Messages("CTL_StartAction=Start Breakout game")
public final class StartAction implements ActionListener {

    private final Board context;

    public StartAction(Board context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       // TODO implement action body
        context.gameInit();
    }
}
