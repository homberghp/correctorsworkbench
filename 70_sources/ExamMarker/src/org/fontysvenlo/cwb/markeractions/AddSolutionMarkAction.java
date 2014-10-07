/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fontysvenlo.cwb.markeractions;

import java.io.IOException;
import org.fontysvenlo.cwb.markeractions.helpers.SolutionAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle;

//<editor-fold defaultstate="expanded" desc="PRO2_2; MAX 10; __STUDENT_ID__ ;POINTS 0">
/**
 *
 * @author hom
 */
@ActionID(
        category = "Tools",
        id = "org.fontysvenlo.cwb.markeractions.AddSolutionMarkAction")
@ActionRegistration(
        displayName = "#CTL_AddSolutionMarkAction",
        iconBase = "org/fontysvenlo/cwb/icons/solution.png")
@ActionReferences({
    @ActionReference(path = "Editors/Popup", position = 10)
})
@NbBundle.Messages("CTL_AddSolutionMarkAction=Mark Region as Solution")

public class AddSolutionMarkAction extends SolutionAction {

    public AddSolutionMarkAction(DataObject context) {
        super(context);
    }

    //Start Solution::replacewith:://TODO
    @Override
    protected void doWork() throws IOException, IndexOutOfBoundsException, 
            InstantiationException, IllegalAccessException {
        this.wrapAndAnnotate();
    }
    //End Solution::replacewith::
}
//</editor-fold>
