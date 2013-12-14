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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.text.Caret;
import javax.swing.text.StyledDocument;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import static org.fontysvenlo.cwb.markeractions.helpers.SolutionMarkerUtils.*;
import org.openide.text.Annotation;

/**
 *
 * @author hom
 */
public abstract class MarkerAction implements ActionListener {

    protected final DataObject context;
//    protected ExamAnnotation endAnnotation;
//    protected ExamAnnotation startAnnotation;
    protected String endTag;
    protected String startTag;
    protected String endAnnotationTooltip;
    protected String startAnnotationTooltip;
    protected final Class<? extends ExamAnnotation> startAnnotationClass;
    protected final Class<? extends ExamAnnotation> endAnnotationClass;

    public MarkerAction(DataObject context, Class<? extends ExamAnnotation> startAnnotationClass,
            Class<? extends ExamAnnotation> endAnnotationClass) {
        this.context = context;
        this.startAnnotationClass = startAnnotationClass;
        this.endAnnotationClass = endAnnotationClass;
    }

    protected StyledDocument getStyledDoc(DataObject d) throws IOException {
        EditorCookie ec = d.getLookup().lookup(EditorCookie.class);
        ec.open();
        final StyledDocument doc = ec.openDocument();
        return doc;
    }

    protected DataObject getDataObjectForFile(File f) throws DataObjectNotFoundException {
        FileObject fo = FileUtil.toFileObject(f);
        DataObject d = DataObject.find(fo);
        return d;
    }

    protected File getFile() {
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
    protected EditorCookie getEditorCookie(DataObject d) {
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
     * Wrap selected text and add editor annotations.
     *
     * @throws DataObjectNotFoundException
     * @throws IOException
     */
    protected void wrapAndAnnotate() throws DataObjectNotFoundException,
            IOException, InstantiationException, IllegalAccessException {
        DataObject d = getDataObjectForFile(getFile());
        EditorCookie ec = getEditorCookie(d);
        StyledDocument doc = getStyledDoc(d);
        ec.open();
        for (JEditorPane pane : ec.getOpenedPanes()) {
            Caret caret = pane.getCaret();
            wrapSelected(doc, caret, startTag, endTag);
            ExamAnnotation sA = startAnnotationClass.newInstance().setText(startAnnotationTooltip);
            ExamAnnotation eA = endAnnotationClass.newInstance().setText(endAnnotationTooltip);
            annotateRegion(d, doc, caret, sA, eA);
        }
    }

    protected void removeMarkers() throws IOException, DataObjectNotFoundException {
        DataObject d = getDataObjectForFile(getFile());
        StyledDocument doc = getStyledDoc(d);
        removeSolutionMarkers(d, doc);
    }
    /**
     * Worker as in Template method pattern. Thrifty at throwing exceptions.
     * Things may go wrong on occasion. 
     * @throws IOException
     * @throws IndexOutOfBoundsException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    protected abstract void doWork() throws IOException, IndexOutOfBoundsException, InstantiationException, IllegalAccessException;

    /**
     * Reaction to GUI event.
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            doWork();
        } catch (IOException | IndexOutOfBoundsException |InstantiationException | IllegalAccessException ex) {
            getLogger().log(Level.INFO, "action failed with ", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public int removeSolutionMarkers(DataObject d, final StyledDocument doc) {
        String fileName = FileUtil.getFileDisplayName(d.getPrimaryFile());
        AnnotationRegistry registry = AnnotationRegistry.getInstance();
        int result = 0;
        List<Annotation> an1List = registry.getAnnotations(startAnnotationClass, fileName);
        result += an1List.size();
        for (Annotation a : an1List) {
            a.detach();
        }
        List<Annotation> an2List = registry.getAnnotations(endAnnotationClass, fileName);
        result += an2List.size();
        for (Annotation a : an2List) {
            a.detach();
        }
        return result;
    }

}
