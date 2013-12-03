/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fontysvenlo.cwb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import org.openide.cookies.LineCookie;
import org.openide.loaders.DataObject;
import org.openide.text.Annotation;
import org.openide.text.Line;
import org.openide.text.NbDocument;

/**
 *
 * @author hom
 */
public class SolutionMarkerUtils {

    /**
     * Add editor solution annotations to the editor window. Note that these
     * annotations are not java annotations but visible markup to serve as clue
     * for the user.
     *
     * @param d data object to lookup cookies for
     * @param doc the style document being edited
     * @param caret the position of the cursor and /or selection
     */
    public static void annotateRegion( DataObject d, final StyledDocument doc,
            Caret caret ) {
        LineCookie cookie = d.getLookup().lookup( LineCookie.class );
        Line.Set lineSet = cookie.getLineSet();
        int regionStart = Math.min( caret.getDot(), caret.getMark() );
        int regionEnd = Math.max( caret.getDot(), caret.getMark() );
        System.out.println( caretToString( caret ) );
        final Line firstLine = lineSet.getCurrent( NbDocument.findLineNumber( 
                doc,
                regionStart ) );
        final Line lastLine = lineSet.getCurrent( NbDocument.
                findLineNumber(doc,
                        regionEnd ) );
        final Annotation ann1
                = new SolutionStartAnnotation( "Your solution starts here" );
        final Annotation ann2
                = new SolutionEndAnnotation( "Your solution ends here" );
        ann1.attach( firstLine );
        ann1.moveToFront();
        ann2.attach( lastLine );
        ann2.moveToFront();
    }

    /**
     * Wrap the selected text with prefix and postfix, such that the resulting
     * text is prefix+select+postfix. This resulting text replaces the selected
     * text. The method does nothing if there is no selection
     * (i.e.Caret.dot==Caret.mark).
     *
     * If the operation is successful, dot is at the beginning of the annotated
     * block, mark is at the end of the annotated block.
     *
     * @param doc to insert in
     * @param caret the selection object specifying dot and mark
     * @param prefix text to put in front of selection.
     * @param postfix text to put at back of selection.
     * @throws BadLocationException
     */
    public static void wrapSelected( final StyledDocument doc, final Caret caret,
            final String prefix, final String postfix ) {
        // This array is needed to be able to pass to the Runnable a final 
        // reference to something.
        final BadLocationException[] exc = new BadLocationException[]{ null };
        if ( caret.getDot() == caret.getMark() ) {
            return;
        }
        final int insertionPoint1 = roundToLineStart(doc, Math.min( caret.getDot(), caret.getMark() ));
        final int insertionPoint2 = roundToNextLineStart(doc, Math.max( caret.getDot(), caret.getMark() ));
        final int oldRegionLength = ( insertionPoint2 - insertionPoint1 );
        final String indent = findIndent(doc, Math.min( caret.getDot(), caret.getMark() ));
        NbDocument.runAtomic( doc, new Runnable() {
            @Override
            public void run() {
                try {
                    // do postfix first, so the starting point of the selection will not change,
                    // so we can still validly insert the prefix in the 2nd step.
                    doc.insertString( insertionPoint2, indent+postfix,
                            SimpleAttributeSet.EMPTY );
                    doc.insertString( insertionPoint1, indent+prefix,
                            SimpleAttributeSet.EMPTY );
                    // the following lines put mark at begin of region and dot at end.
                    caret.setDot( insertionPoint1 );
                    caret.moveDot( insertionPoint1 + prefix.length()
                            + oldRegionLength );
                    System.out.println( caretToString( caret ) );
                } catch ( BadLocationException e ) {
                    exc[0] = e;
                }
            }
        } );
        if ( exc[0] != null ) {
            logger.log( Level.INFO, "insert failed with ", exc[0] );
        }
    }
    private static final Logger logger = Logger.getLogger(
            SolutionMarkerUtils.class.getName() );

    public static String caretToString( Caret c ) {
        return c.getClass().getCanonicalName() + " dot:" + c.getDot()
                + ", mark:" + c.getMark();
    }

    public static int roundToLineStart(final StyledDocument doc, final int position){
        return (position -NbDocument.findLineColumn(doc, position));
    }
    private static final String whiteSpace = "                                             ";
    /**
     * Tries to matche indent of original selection
     * @param doc to edit
     * @param position location from mark
     * @return a whitespace string
     */
    public static String findIndent(final StyledDocument doc, final int position){
        return whiteSpace.substring(0,NbDocument.findLineColumn(doc, position));
    }

    /**
     * Get start of next line
     * @param doc to edit
     * @param position from where
     * @return position in the doc of next line start
     */
    public static int roundToNextLineStart(final StyledDocument doc, final int position){
        return (NbDocument.findLineOffset(doc, NbDocument.findLineNumber(doc, position)+1));
    }
}
