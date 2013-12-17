package org.fontysvenlo.cwb.markeractions.helpers;

import org.fontysvenlo.cwb.SolutionMarkerPanel;
import org.fontysvenlo.cwb.annotations.TaskStartAnnotation;
import org.fontysvenlo.cwb.annotations.TaskEndAnnotation;
import org.openide.loaders.DataObject;
import org.openide.util.NbPreferences;
import static org.fontysvenlo.cwb.CWBSettings.defaultTaskStartTag;
import static org.fontysvenlo.cwb.CWBSettings.defaultTaskEndTag;

/**
 * Mark text in editor with special comments and editor annotations.
 *
 * @author Richard van den Ham
 * @author Pieter van den Hombergh
 */
public abstract class TaskAction extends MarkerAction {

    private static final String defaultStartAnnotationToolTip = "Exam Task Starts Here";
    private static final String defaultEndAnnotationToolTip = "Exam Task Ends Here";

    /**
     * Configure Task parameters such as texts and annotations.
     *
     * @param context published object
     */
    public TaskAction(DataObject context) {
        super(context, TaskStartAnnotation.class, TaskEndAnnotation.class);
        startTag = NbPreferences.forModule(SolutionMarkerPanel.class).get("startTag", defaultTaskStartTag);
        endTag = NbPreferences.forModule(SolutionMarkerPanel.class).get("endTag", defaultTaskEndTag);
        startAnnotationTooltip = NbPreferences.forModule(SolutionMarkerPanel.class).get("TaskStartTT", defaultStartAnnotationToolTip);
        endAnnotationTooltip = NbPreferences.forModule(SolutionMarkerPanel.class).get("TaskEndTT", defaultEndAnnotationToolTip);

    }

}
