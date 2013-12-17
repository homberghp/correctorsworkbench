/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fontysvenlo.cwb.markeractions.helpers;

import org.fontysvenlo.cwb.annotations.ExamAnnotation;
import org.fontysvenlo.cwb.registry.AnnotationRegistry;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import static org.fontysvenlo.cwb.markeractions.helpers.SolutionMarkerUtils.*;
import org.openide.text.Annotation;
import org.openide.text.NbDocument;

/**
 * Provide common functions for cwb action. Super of editor actions if cwb.
 *
 * @author hom
 */
public abstract class MarkerAction implements ActionListener {

    protected final DataObject context;
    protected String endTag;
    protected String startTag;
    protected String endAnnotationTooltip;
    protected String startAnnotationTooltip;
    protected final Class<? extends ExamAnnotation> startAnnotationClass;
    protected final Class<? extends ExamAnnotation> endAnnotationClass;

    /**
     * Create Marker action with start and end annotation.
     *
     * @param context in netbeans ide
     * @param startAnnotationClass start editor annotation
     * @param endAnnotationClass start editor annotation
     */
    public MarkerAction(DataObject context,
            Class<? extends ExamAnnotation> startAnnotationClass,
            Class<? extends ExamAnnotation> endAnnotationClass) {
        this.context = context;
        this.startAnnotationClass = startAnnotationClass;
        this.endAnnotationClass = endAnnotationClass;
    }

    /**
     * Get the document associated with the data object from the netbeans
     * context. Note that the editor cookie is looked up and opened.
     *
     * @param d data object, the entry from netbeans IDE
     * @return the Styled document
     * @throws IOException
     */
    private StyledDocument getStyledDoc(DataObject d) throws IOException {
        EditorCookie ec = d.getLookup().lookup(EditorCookie.class);
        ec.open();
        final StyledDocument doc = ec.openDocument();
        return doc;
    }

    /**
     * Get the data object associated to file.
     *
     * @param f the file
     * @return the data object
     * @throws DataObjectNotFoundException
     */
    private DataObject getDataObjectForFile(File f) throws
            DataObjectNotFoundException {
        FileObject fo = FileUtil.toFileObject(f);
        DataObject d = DataObject.find(fo);
        return d;
    }

    /**
     * Get the file for the context.
     *
     * @return
     */
    private File getFile() {
        String path = context.getPrimaryFile().getPath();
        File f = new File(path);
        return f;
    }

    /**
     * Get the editor cookie for the data object.
     *
     * @param d data object
     * @return the editor cookie
     */
    private EditorCookie getEditorCookie(DataObject d) {
        return d.getLookup().lookup(EditorCookie.class);
    }

    /**
     * Get a logger for this class.
     *
     * @return a logger
     */
    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    /**
     * Log a message with level and exception.
     *
     * @param l info level
     * @param msg message
     * @param t throwable
     */
    protected void log(Level l, String msg, Throwable t) {
        getLogger().log(l, msg, t);
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
     * @return array of int containing the first and last line of the marked
     * block. The last line position points to the last line of the postfix of
     * the annotated block
     * @throws BadLocationException
     */
    public int[] wrapSelected(final StyledDocument doc, final Caret caret,
            final String prefix, final String postfix) {
        // This array is needed to be able to pass to the Runnable a final 
        // reference to something.
        final int[] firstLast = new int[]{0, 0};
        final BadLocationException[] exc = new BadLocationException[]{null};
        if (caret.getDot() == caret.getMark()) {
            return firstLast;
        }
        final int insertionPoint1 = roundToLineStart(doc, Math.min(caret.
                getDot(), caret.getMark()));
        final int insertionPoint2 = roundToNextLineStart(doc, Math.max(caret.
                getDot(), caret.getMark()));
        final int oldRegionLength = insertionPoint2 - insertionPoint1;
        final String[] prefixLines = prefix.trim().split("\n");
        final String[] postfixLines = postfix.trim().split("\n");
        firstLast[0]= insertionPoint1;
        NbDocument.runAtomic(doc, new Runnable() {
            @Override
            public void run() {
                try {
                    // do postfix first, so the starting point of the selection will not change,
                    // so we can still validly insert the prefix in the 2nd step.
                    String indent = findIndent(doc, Math.min(caret.getDot(),
                            caret.getMark()));
                    String indentedPrefix = indent + stringsJoin("\n" + indent,
                            prefixLines);
                    String indentedPostfix = indent + stringsJoin("\n" + indent,
                            postfixLines);
                    doc.insertString(insertionPoint2, indentedPostfix + "\n",
                            SimpleAttributeSet.EMPTY);
                    doc.insertString(insertionPoint1, indentedPrefix + "\n",
                            SimpleAttributeSet.EMPTY);
                    // the following lines put mark at begin of region and dot at end.
                    caret.setDot(insertionPoint1);
                    caret.moveDot(insertionPoint1 + prefix.length()
                            + oldRegionLength);
                    System.out.println(caretToString(caret));
                    firstLast[1]=caret.getDot()+indentedPostfix.length();
                } catch (BadLocationException e) {
                    exc[0] = e;
                }
            }
        });
        if (exc[0] != null) {
            getLogger().log(Level.INFO, "insert failed with ", exc[0]);
        }
        return firstLast;
    }

    /**
     * Wrap selected text and add editor annotations.
     *
     * @throws DataObjectNotFoundException
     * @throws IOException
     */
    protected final void wrapAndAnnotate() throws DataObjectNotFoundException,
            IOException, InstantiationException, IllegalAccessException {
        DataObject d = getDataObjectForFile(getFile());
        EditorCookie ec = getEditorCookie(d);
        StyledDocument doc = getStyledDoc(d);
        ec.open();
        for (JEditorPane pane : ec.getOpenedPanes()) {
            Caret caret = pane.getCaret();
            int [] firstLast;
            firstLast = wrapSelected(doc, caret, startTag, endTag);
            ExamAnnotation sA = startAnnotationClass.newInstance().setText(
                    startAnnotationTooltip);
            ExamAnnotation eA = endAnnotationClass.newInstance().setText(
                    endAnnotationTooltip);
            annotateRegion(d, doc, firstLast, sA, eA);
        }
    }

    protected final void removeMarkers() throws IOException,
            DataObjectNotFoundException {
        DataObject d = getDataObjectForFile(getFile());
        removeAnnotations(d);
    }

    /**
     * Worker as in Template method pattern. Thrifty at throwing exceptions.
     * Things may go wrong on occasion.
     *
     * @throws IOException
     * @throws IndexOutOfBoundsException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    protected abstract void doWork() throws Exception;

    /**
     * Reaction to GUI event.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            doWork();
        } catch (Exception ex) {
            getLogger().log(Level.INFO, "action failed with ", ex);
        }
    }

    /**
     * Remove annotations from document. Incomplete.
     *
     * @param d data object from nb platform
     * @param doc editor
     * @return the number of removed markers.
     */
    public int removeAnnotations(DataObject d) {
        String fileName = FileUtil.getFileDisplayName(d.getPrimaryFile());
        AnnotationRegistry registry = AnnotationRegistry.getInstance();
        int result = 0;
        List<Annotation> an1List = registry.getAnnotations(startAnnotationClass,
                fileName);
        result += an1List.size();
        List<Annotation> remoList = new ArrayList<>();
        for (Annotation a : an1List) {
            log(Level.INFO, "removing annotation " + a, null);
            a.detach();
            remoList.add(a);
        }
        registry.removeAnnotations(fileName, remoList);
        remoList.clear();
        List<Annotation> an2List = registry.getAnnotations(endAnnotationClass,
                fileName);
        result += an2List.size();
        for (Annotation a : an2List) {
            log(Level.INFO, "rmoving annotation " + a, null);
            a.detach();
            remoList.add(a);
        }
        registry.removeAnnotations(fileName, remoList);
        return result;
    }
}
