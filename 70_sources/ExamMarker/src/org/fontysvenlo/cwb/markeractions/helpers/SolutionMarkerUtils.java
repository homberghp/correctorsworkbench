package org.fontysvenlo.cwb.markeractions.helpers;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import org.fontysvenlo.cwb.annotations.ExamAnnotation;
import org.fontysvenlo.cwb.annotations.SolutionEndAnnotation;
import org.fontysvenlo.cwb.annotations.SolutionStartAnnotation;
import org.fontysvenlo.cwb.registry.AnnotationRegistry;
import org.openide.cookies.LineCookie;
import org.openide.filesystems.FileUtil;
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
     * for the user. The annotations are registered in the
     * {@link AnnotationRegistry}
     *
     * @param d data object to lookup cookies for
     * @param doc the style document being edited
     * @param firstLast the position of the cursor and /or selection
     */
    public static void annotateRegion( DataObject d, final StyledDocument doc,
            final int[] firstLast, final ExamAnnotation startAnnotation,
            final ExamAnnotation endAnnotation ) {
        LineCookie cookie = d.getLookup().lookup( LineCookie.class );
        Line.Set lineSet = cookie.getLineSet();
        int regionStart = Math.min( firstLast[ 0 ], firstLast[ 1 ] );
        int regionEnd = Math.max( firstLast[ 0 ], firstLast[ 1 ] );
//        System.out.println(caretToString(caret));
        String fileName = FileUtil.getFileDisplayName( d.getPrimaryFile() );
        final Line firstLine = lineSet.getCurrent( NbDocument.findLineNumber(
                doc,
                regionStart ) );
        final Line lastLine = lineSet.getCurrent( NbDocument.
                findLineNumber( doc,
                                regionEnd ) );
        startAnnotation.attach( firstLine );
        startAnnotation.moveToFront();

        AnnotationRegistry registy = AnnotationRegistry.getInstance();
        registy.addAnnotation( startAnnotation, fileName, regionStart );
        endAnnotation.attach( lastLine );
        endAnnotation.moveToFront();
        registy.addAnnotation( endAnnotation, fileName, regionEnd );
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
        final int insertionPoint1 = roundToLineStart( doc, Math.min( caret.
                                                      getDot(), caret.getMark() ) );
        final int insertionPoint2 = roundToNextLineStart( doc, Math.max( caret.
                                                          getDot(), caret
                                                                         .getMark() ) );
        final int oldRegionLength = insertionPoint2 - insertionPoint1;
        final String[] prefixLines = prefix.trim().split( "\n" );
        final String[] postfixLines = postfix.trim().split( "\n" );
        NbDocument.runAtomic( doc, new Runnable() {
            @Override
            public void run() {
                try {
                    // do postfix first, so the starting point of the selection will not change,
                    // so we can still validly insert the prefix in the 2nd step.
                    String indent = findIndent( doc, Math.min( caret.getDot(),
                                                               caret.getMark() ) );
                    String indentedPrefix = indent + stringsJoin( "\n" + indent,
                                                                  prefixLines );
                    String indentedPostfix = indent
                            + stringsJoin( "\n" + indent,
                                           postfixLines );
                    doc.insertString( insertionPoint2, indentedPostfix + "\n",
                                      SimpleAttributeSet.EMPTY );
                    doc.insertString( insertionPoint1, indentedPrefix + "\n",
                                      SimpleAttributeSet.EMPTY );
                    // the following lines put mark at begin of region and dot at end.
                    caret.setDot( insertionPoint1 );
                    caret.moveDot( insertionPoint1 + prefix.length()
                            + oldRegionLength );
                    System.out.println( caretToString( caret ) );
                } catch ( BadLocationException e ) {
                    exc[ 0 ] = e;
                }
            }
        } );
        if ( exc[ 0 ] != null ) {
            logger.log( Level.INFO, "insert failed with ", exc[ 0 ] );
        }
    }

    /**
     * Remove the wrappers around Caret. The prefix is search from the caret
     * backwards (decreasing line number), the postfix is search from the caret
     * forward. The removal only takes place is bot prefix and postfix are found
     * surrounding caret.
     *
     * @param doc to search
     * @param caret the start position for the search
     * @param prefix the prefix the text is wrapped with
     * @param postfix the postfix the text is wrapped with
     */
    public static void unwrapSelected( final StyledDocument doc,
            final Caret caret,
            final String prefix, final String postfix ) {

    }
    private static final Logger logger = Logger.getLogger(
            SolutionMarkerUtils.class.getName() );

    public static String caretToString( Caret c ) {
        return c.getClass().getCanonicalName() + " dot:" + c.getDot()
                + ", mark:" + c.getMark();
    }

    /**
     * Get start of line position for a position.
     *
     * @param doc document for position
     * @param position location, zero based from start of doc
     * @return the start of line position in doc on which position is located
     */
    public static int roundToLineStart( final StyledDocument doc,
            final int position ) {
        return ( position - NbDocument.findLineColumn( doc, position ) );
    }

    /**
     * Tries to match indent of original selection.
     *
     * @param doc to edit
     * @param position location from mark
     * @return a whitespace string
     */
    public static String findIndent( final StyledDocument doc,
            final int position )
            throws BadLocationException {
        StringBuilder indent = new StringBuilder();
        int currentLineNo = NbDocument.findLineNumber( doc, position );
        int l1Offset = NbDocument.findLineOffset( doc, currentLineNo );
        int l2Offset = NbDocument.findLineOffset( doc, currentLineNo + 1 );
        int stopPos = Math.min( l2Offset, doc.getLength() );
        String ws = doc.getText( l1Offset, 1 );

        while ( ws.matches( "^\\s$" ) && l1Offset < stopPos ) {
            indent.append( ws );
            l1Offset++;
            ws = doc.getText( l1Offset, 1 );
        }
        return indent.toString();
    }

    /**
     * Get start of next line
     *
     * @param doc to edit
     * @param position from where
     * @return position in the doc of next line start
     */
    public static int roundToNextLineStart( final StyledDocument doc,
            final int position ) {
        return NbDocument.findLineOffset( doc, NbDocument.findLineNumber( doc,
                                                                          position )
                                          + 1 );
    }

    public static String stringsJoin( String glue, String[] parts ) {
        StringBuilder result = new StringBuilder( parts[ 0 ] );
        for ( int i = 1; i < parts.length; i++ ) {
            result.append( glue ).append( parts[ i ] );
        }
        return result.toString();
    }

    /**
     * Remove all edit flags (left gutter icons, right column colour marks)
     * markers from doc.
     *
     * @param doc document to work on
     * @return the number of annotations removed.
     */
    public static int removeSolutionMarkers( DataObject d,
            final StyledDocument doc ) {
        String fileName = FileUtil.getFileDisplayName( d.getPrimaryFile() );
        Class<?> anC1 = SolutionStartAnnotation.class;
        Class<?> anC2 = SolutionEndAnnotation.class;
        AnnotationRegistry registry = AnnotationRegistry.getInstance();
        int result = 0;
        List<Annotation> an1List = registry.getAnnotations( anC1, fileName );
        result += an1List.size();
        for ( Annotation a : an1List ) {
            a.detach();
        }
        List<Annotation> an2List = registry.getAnnotations( anC2, fileName );
        result += an2List.size();
        for ( Annotation a : an2List ) {
            a.detach();
        }
        return result;
    }

    /**
     * Forward or backward iterable document. The returned Iterator does not
     * implement remove().
     */
    static class IterableDocument implements Iterable<String> {

        private final StyledDocument doc;
        /**
         * Maintains position. Value of Integer.MAX_VALUE
         */
        private final int position;
        private final int direction;

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                // init from
                int lineNumber = NbDocument.findLineNumber( doc, position );

                @Override
                public boolean hasNext() {
                    boolean result = false;
                    if ( lineNumber == Integer.MAX_VALUE || lineNumber
                            == -Integer.MAX_VALUE ) {
                        return result;
                    }
                    // try to advance, on exception, flag end
                    int tmpLineNumber = lineNumber + direction;
                    try {
                        // try location
                        NbDocument.findLineOffset( doc, tmpLineNumber );
                        // if getting here, loc is ok.
                        result = true;
                    } catch ( IndexOutOfBoundsException iob ) {
                        // stop trying next time
                        lineNumber = direction * Integer.MAX_VALUE;
                    }
                    return result;
                }

                @Override
                public String next() {
                    String result = null;
                    lineNumber += direction;
                    int offset, offset2, length;
                    offset = NbDocument.findLineOffset( doc, lineNumber );
                    offset2 = Math.max( NbDocument.findLineOffset( doc,
                                                                   lineNumber
                                                                   + 1 ), doc
                                        .getLength() );
                    length = offset2 - offset;
                    try {
                        result = doc.getText( offset, length );
                    } catch ( BadLocationException ex ) {
                        lineNumber = direction * Integer.MAX_VALUE;
                    }
                    return result;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException( "Not supported." );
                }
            };
        }

        /**
         * Creates iterable document from starting position and direction.
         *
         * @param doc the doc to search
         * @param position the starting position
         * @param direction increase (+1) or decrease (-1)
         * @throws NullPointerException doc is null
         * @throws IllegalArgumentException when direction not one of (-1,1).
         */
        public IterableDocument( StyledDocument doc, final int position,
                int direction ) {
            if ( 1 != Math.abs( direction ) ) {
                throw new IllegalArgumentException( "illegal direction arg" );
            }
            if ( null == doc ) {
                throw new NullPointerException( "refusing to iterate null " );
            }
            this.doc = doc;
            this.position = position;
            this.direction = direction;
        }

    }

}
