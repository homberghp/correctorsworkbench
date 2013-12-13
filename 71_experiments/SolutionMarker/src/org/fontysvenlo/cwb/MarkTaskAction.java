package org.fontysvenlo.cwb;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.JEditorPane;
import javax.swing.text.Caret;
import javax.swing.text.StyledDocument;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
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
        id = "org.fontysvenlo.cwb.MarkTaskAction")
@ActionRegistration(
        displayName = "#CTL_MarkTask")
@ActionReferences({
    @ActionReference(path = "Editors/Popup", position = 10)
})
@Messages("CTL_MarkTask=Mark Region as Task")
public final class MarkTaskAction extends MarkerAction {

    private static String defaultStartTag = "//<editor-fold defaultstate=\"expanded\" desc=\"SEN1_1; MAX 5; __STUDENT_ID__ ;POINTS 0\">";
    private static String defaultEndTag = "//</editor-fold>";
    private String startTag;
    private String endTag;
    private final DataObject context;

    //StartSolution
    public MarkTaskAction(DataObject context) {
        super(context);
        this.context = context;
        startTag = NbPreferences.forModule(SolutionMarkerPanel.class).get("startTag", defaultStartTag);
        endTag = NbPreferences.forModule(SolutionMarkerPanel.class).get("endTag", defaultEndTag);
    }
    //EndSolution

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            DataObject d = getDataObjectForFile(getFile());
            EditorCookie ec = getEditorCookie(d);
            StyledDocument doc = getStyledDoc(d);
            ec.open();
            for (JEditorPane pane : ec.getOpenedPanes()) {
                Caret caret = pane.getCaret();
                wrapSelected(doc, caret, startTag, endTag);
                annotateRegion(d, doc, caret);
            }
        } catch (IOException | IndexOutOfBoundsException ex) {
            getLogger().log(Level.INFO, "action failed with ", ex);
        }
    }
}
