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
        category = "Bugtracking",
        id = "nl.fontys.sevenlo.games.breakout2.DecreaseSpeedAction"
)
@ActionRegistration(
        displayName = "#CTL_DecreaseSpeedAction"
)
@ActionReference(path = "Menu/File", position = 867)
@Messages("CTL_DecreaseSpeedAction=Decrease Speed")
public final class DecreaseSpeedAction implements ActionListener {

    private final Board context;

    public DecreaseSpeedAction(Board context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
       context.decelerate();
    }
}
