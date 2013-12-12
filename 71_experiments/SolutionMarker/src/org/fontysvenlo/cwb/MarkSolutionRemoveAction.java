package org.fontysvenlo.cwb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.text.Caret;
import javax.swing.text.StyledDocument;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle.Messages;
import org.openide.cookies.EditorCookie;
import static org.fontysvenlo.cwb.SolutionMarkerUtils.*;
import org.openide.util.NbPreferences;
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
        id = "org.fontysvenlo.cwb.MarkSolutionRemoveAction")
@ActionRegistration(
        displayName = "#CTL_MarkSolutionRemove")
@ActionReferences({
    @ActionReference(path = "Editors/Popup", position = 11)
})
@Messages("CTL_MarkSolutionRemove=Removes all marks from a document")
public final class MarkSolutionRemoveAction implements ActionListener {

    private final DataObject context;

    public MarkSolutionRemoveAction(DataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            StringBuilder actionLog = new StringBuilder();
            String path = context.getPrimaryFile().getPath();
            File f = new File(path);
            FileObject fo = FileUtil.toFileObject(f);
            DataObject d = DataObject.find(fo);
            EditorCookie ec = d.getLookup().lookup(EditorCookie.class);
            ec.open();

            final StyledDocument doc = ec.openDocument();
            removeSolutionMarkers(d,doc);
            logger.log(Level.INFO, actionLog.toString(), (Throwable) null);
        } catch (IOException | IndexOutOfBoundsException ex) {
            logger.log(Level.INFO, "action failed with ", ex);
        }
    }

    private static final Logger logger = Logger.getLogger(
            MarkSolutionRemoveAction.class.getName());

}
