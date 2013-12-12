/*
 * To change this template, choose Tools | Templates
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
        category = "Build",
        id = "nl.fontys.sevenlo.games.breakout.StartBreakoutAction")
@ActionRegistration(
        displayName = "#CTL_StartBreakoutAction")
@ActionReference(path = "Menu/File", position = 0)
@Messages("CTL_StartBreakoutAction=Start Breakout")
public final class StartBreakoutAction implements ActionListener {

    private final Board context;

    public StartBreakoutAction(Board context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.restartGame();
    }
}
