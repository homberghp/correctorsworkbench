package org.fontysvenlo.cwb.markeractions.helpers;

import org.fontysvenlo.cwb.ExamMarkerPanel;
import org.fontysvenlo.cwb.annotations.SolutionEndAnnotation;
import org.fontysvenlo.cwb.annotations.SolutionStartAnnotation;
import org.openide.loaders.DataObject;
import org.openide.util.NbPreferences;
import static org.fontysvenlo.cwb.CWBSettings.defaultSolutionStartTag;
import static org.fontysvenlo.cwb.CWBSettings.defaultSolutionEndTag;
/**
 * Common base class for Solution actions Add and Remove.
 * @author hom
 */
public abstract class SolutionAction extends MarkerAction {

    private static final String defaultStartAnnotationToolTip="Exam Solution Starts Here";
    private static final String defaultEndAnnotationToolTip="Exam Solution Ends Here";
    /**
     * Associate a solution action to a a context and start and end annotations.
     * @param context 
     */
    public SolutionAction(DataObject context) {
        super(context, SolutionStartAnnotation.class, SolutionEndAnnotation.class);
        startTag = NbPreferences.forModule(ExamMarkerPanel.class).get("startTag", defaultSolutionStartTag);
        endTag = NbPreferences.forModule(ExamMarkerPanel.class).get("endTag", defaultSolutionEndTag);
        startAnnotationTooltip = NbPreferences.forModule(ExamMarkerPanel.class).get("SolStartTT",defaultStartAnnotationToolTip);
        endAnnotationTooltip = NbPreferences.forModule(ExamMarkerPanel.class).get("SolEndTT",defaultEndAnnotationToolTip);
    }
}
