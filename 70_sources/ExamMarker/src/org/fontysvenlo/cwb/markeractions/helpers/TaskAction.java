package org.fontysvenlo.cwb.markeractions.helpers;

import org.fontysvenlo.cwb.SolutionMarkerPanel;
import org.fontysvenlo.cwb.annotations.TaskStartAnnotation;
import org.fontysvenlo.cwb.annotations.TaskEndAnnotation;
import org.openide.loaders.DataObject;
import org.openide.util.NbPreferences;
/*
 * log findings here.
 * Caret has a dot (current pos) and a mark.
 * If mark and dot are same, there is no selection.
 * if mark and dot differ, the selection equals the difference between mark and dot.
 * All counted in character 0 based, so lines endings are not relevant.
 * Useful links:  <a href='http://bits.netbeans.org/dev/javadoc/org-openide-text/org/openide/text/doc-files/api.html'>Open IDE editor Api</a>
 */

/**
 * Mark text in editor with special comments and editor annotations.
 *
 * @author Richard van den Ham
 * @author Pieter van den Hombergh
 */
public abstract class TaskAction extends MarkerAction {

    private final static String defaultStartTag = "//<editor-fold defaultstate=\"expanded\" desc=\"SEN1_1; MAX 5; __STUDENT_ID__ ;POINTS 0\">";
    private final static String defaultEndTag = "//</editor-fold>";
    private static final String defaultStartAnnotationToolTip = "Exam Task Starts Here";
    private static final String defaultEndAnnotationToolTip = "Exam Task Ends Here";

    /**
     * Configure Task parameters such as texts and annotations.
     *
     * @param context published object
     */
    public TaskAction(DataObject context) {
        super(context, TaskStartAnnotation.class, TaskEndAnnotation.class);
        startTag = NbPreferences.forModule(SolutionMarkerPanel.class).get("startTag", defaultStartTag);
        endTag = NbPreferences.forModule(SolutionMarkerPanel.class).get("endTag", defaultEndTag);
        startAnnotationTooltip = NbPreferences.forModule(SolutionMarkerPanel.class).get("TaskStartTT", defaultStartAnnotationToolTip);
        endAnnotationTooltip = NbPreferences.forModule(SolutionMarkerPanel.class).get("TaskEndTT", defaultEndAnnotationToolTip);

    }

}
