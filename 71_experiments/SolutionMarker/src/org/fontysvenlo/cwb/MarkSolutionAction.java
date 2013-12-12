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
        id = "org.fontysvenlo.cwb.MarkSolutionAction")
@ActionRegistration(
        displayName = "#CTL_MarkSolution")
@ActionReferences({
    @ActionReference(path = "Editors/Popup", position = 10)
})
@Messages("CTL_MarkSolution=Mark Region as Solution")
public final class MarkSolutionAction implements ActionListener {

    private static String defaultStartTag = "//PRE";
    private static String defaultEndTag = "//POST";
    private String startTag = "//PRE";
    private String endTag = "//POST";
    private final DataObject context;

                                                    //StartSolution
    public MarkSolutionAction(DataObject context) {
        this.context = context;
        startTag = NbPreferences.forModule(SolutionMarkerPanel.class).get("startTag", defaultStartTag);
        endTag = NbPreferences.forModule(SolutionMarkerPanel.class).get("endTag", defaultEndTag);
    }
                                                    //EndSolution

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

            for (JEditorPane pane : ec.getOpenedPanes()) {
                actionLog.append(pane.getSelectedText());
                Caret caret = pane.getCaret();
                actionLog.append("\n dot at ").append(caret.getDot()).
                        append("\n mark at ").append(caret.getMark());
                wrapSelected(doc, caret, startTag , endTag );
                annotateRegion(d, doc, caret);
            }
            logger.log(Level.INFO, actionLog.toString(), (Throwable) null);
        } catch (IOException | IndexOutOfBoundsException ex) {
            logger.log(Level.INFO, "action failed with ", ex);
        }
    }

    private static final Logger logger = Logger.getLogger(
            MarkSolutionAction.class.getName());

}
