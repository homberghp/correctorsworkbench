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
        id = "nl.fontys.sevenlo.games.breakout.IncreaseSpeed")
@ActionRegistration(
        displayName = "#CTL_IncreaseSpeed")
@ActionReference(path = "Menu/File", position = -100)
@Messages("CTL_IncreaseSpeed=Increase Speed Of Ball")
public final class IncreaseSpeed implements ActionListener {

    private final Board context;

    public IncreaseSpeed(Board context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.increaseSpeed();
    }
}
