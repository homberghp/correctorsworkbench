package org.fontysvenlo.cwb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.SimpleAttributeSet;
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
import org.openide.cookies.LineCookie;
import org.openide.text.Annotation;
import org.openide.text.Line;
import static org.openide.text.Line.*;
import org.openide.text.NbDocument;
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
        id = "org.fontysvenlo.cwb.MarkSolutionAction" )
@ActionRegistration(
         displayName = "#CTL_MarkSolution" )
@ActionReferences( {
    @ActionReference( path = "Editors/Popup", position = 10 )
} )
@Messages( "CTL_MarkSolution=Mark Region as Solution" )
public final class MarkSolutionAction implements ActionListener {

    private final DataObject context;

    public MarkSolutionAction( DataObject context ) {
        this.context = context;
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        try {
            String path = context.getPrimaryFile().getPath();

            File f = new File( path );
            FileObject fo = FileUtil.toFileObject( f );
            DataObject d = DataObject.find( fo );
            EditorCookie ec = d.getLookup().lookup( EditorCookie.class );
            ec.open();

            final StyledDocument doc = ec.openDocument();

            int start = NbDocument.findLineOffset( doc, 2 );
            int end = NbDocument.findLineOffset( doc, 3 );
            String contents = doc.getText( start, end - start );

            ec.getLineSet().getCurrent( 2 ).show( ShowOpenType.NONE,
                    ShowVisibilityType.FOCUS );

            StringBuilder sb = new StringBuilder();
            for ( JEditorPane pane : ec.getOpenedPanes() ) {
                sb.append( pane.getSelectedText() );
                Caret caret = pane.getCaret();
                sb.append( "\n dot at " ).append( caret.getDot() ).
                        append( "\n mark at " ).append( caret.getMark() );
                wrapSelected( doc, caret, "//PRE\n", "//POST\n" );
                annotateRegion( d, doc, caret );
            }
            logger.log(Level.INFO, sb.toString(), (Throwable)null);
        } catch ( IOException | IndexOutOfBoundsException | BadLocationException ex ) {
            logger.log( Level.INFO, "action failed with ", ex );
        }
    }

    private static final Logger logger = Logger.getLogger(
            MarkSolutionAction.class.getName() );
   
}
