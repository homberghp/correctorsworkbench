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
        id = "nl.fontys.sevenlo.games.breakout2.IncreaseSpeedAction"
)
@ActionRegistration(
        displayName = "#CTL_IncreaseSpeedAction"
)
@ActionReference(path = "Menu/File", position = 860)
@Messages("CTL_IncreaseSpeedAction=Increase Speed")
public final class IncreaseSpeedAction implements ActionListener {

    private final Board context;

    public IncreaseSpeedAction(Board context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.accelerate();
    }
}
