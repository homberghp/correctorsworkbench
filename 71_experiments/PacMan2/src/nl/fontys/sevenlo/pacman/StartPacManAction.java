/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sevenlo.pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import pacman.Board;

@ActionID(
        category = "Tools",
        id = "nl.fontys.sevenlo.pacman.StartPacManAction")
@ActionRegistration(
        iconBase = "pacman/pacpix/pacman.png",
        displayName = "#CTL_StartPacManAction")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 0),
    @ActionReference(path = "Toolbars/File", position = 0),
    @ActionReference(path = "Shortcuts", name = "D-M")
})
@Messages("CTL_StartPacManAction=StartPacMan")
public final class StartPacManAction implements ActionListener {

    private final Board context;

    public StartPacManAction(Board context) {
        this.context = context;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        context.GameInit();
    }
}
