package org.fontysvenlo.cwb;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.StyledDocument;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle.Messages;
import static org.fontysvenlo.cwb.SolutionMarkerUtils.*;
/*
 * log findings here.
 * Caret has a dot (current pos) and a mark.
 * If mark and dot are same, there is no selection.
 * if mark and dot differ, the selection equals the difference between mark and dot.
 * All counted in character 0 based, so lines endings are not relevant.
 * Useful links:  <a href='http://bits.netbeans.org/dev/javadoc/org-openide-text/org/openide/text/doc-files/api.html'>Open IDE editor Api</a>

 */

@ActionID(
        category = "Tools",
        id = "org.fontysvenlo.cwb.MarkTaskRemoveAction")
@ActionRegistration(
        displayName = "#CTL_MarkTaskRemove")
@ActionReferences({
    @ActionReference(path = "Editors/Popup", position = 11)
})
@Messages("CTL_MarkTaskRemove=Removes all task marks from a document")
public final class MarkTaskRemoveAction extends MarkerAction {

    public MarkTaskRemoveAction(DataObject context) {
        super(context);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            DataObject d = getDataObjectForFile(getFile());
            StyledDocument doc = getStyledDoc(d);
            removeSolutionMarkers(d,doc);
        } catch (IOException | IndexOutOfBoundsException ex) {
            logger.log(Level.INFO, "action failed with ", ex);
        }
    }

    private static final Logger logger = Logger.getLogger(
            MarkTaskRemoveAction.class.getName());
}
