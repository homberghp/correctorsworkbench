/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fontysvenlo.cwb;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.StyledDocument;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;

/**
 *
 * @author hom
 */
public abstract class MarkerAction implements ActionListener {

    protected final DataObject context;

    public MarkerAction(DataObject context) {
        this.context = context;
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
     * @param d data object
     * @return the editor cookie
     */
    protected EditorCookie getEditorCookie(DataObject d) {
        return d.getLookup().lookup(EditorCookie.class);
    }

    /**
     * Get a logger for this class.
     * @return a logger
     */
    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    /**
     * Log a message with level and exception.
     * @param l info level
     * @param msg message
     * @param t throwable
     */
    protected void log(Level l, String msg, Throwable t) {
        getLogger().log(l, msg, t);
    }

}
