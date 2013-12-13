package org.fontysvenlo.cwb;

import org.openide.loaders.DataObject;
import org.openide.util.NbPreferences;

/**
 * Common base class for Solution actions Add and Remove.
 * @author hom
 */
public abstract class SolutionAction extends MarkerAction {

    private static final String defaultStartTag = "//Start Solution::replacewith:://TODO ";
    private static final String defaultEndTag = "//End Solution::replacewith::fail(\"test not implemented\");";
    private static final String defaultStartAnnotationToolTip="Exam Solution Starts Here";
    private static final String defaultEndAnnotationToolTip="Exam Solution Ends Here";
    public SolutionAction(DataObject context) {
        super(context, SolutionStartAnnotation.class, SolutionEndAnnotation.class);
        startTag = NbPreferences.forModule(SolutionMarkerPanel.class).get("startTag", defaultStartTag);
        endTag = NbPreferences.forModule(SolutionMarkerPanel.class).get("endTag", defaultEndTag);
        startAnnotationTooltip = NbPreferences.forModule(SolutionMarkerPanel.class).get("SolStartTT",defaultStartAnnotationToolTip);
        endAnnotationTooltip = NbPreferences.forModule(SolutionMarkerPanel.class).get("SolEndTT",defaultEndAnnotationToolTip);
    }
}
