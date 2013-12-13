/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fontysvenlo.cwb;

import java.io.IOException;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle;

/**
 *
 * @author hom
 */
@ActionID(
        category = "Tools",
        id = "org.fontysvenlo.cwb.AddSolutionMarkAction")
@ActionRegistration(
        displayName = "#CTL_AddSolutionMarkAction")
@ActionReferences({
    @ActionReference(path = "Editors/Popup", position = 10)
})
@NbBundle.Messages("CTL_AddSolutionMarkAction=Mark Region as Solution")

public class AddSolutionMarkAction extends SolutionAction {

    public AddSolutionMarkAction(DataObject context) {
        super(context);
    }

    @Override
    protected void doWork() throws IOException, IndexOutOfBoundsException, 
            InstantiationException, IllegalAccessException {
        this.wrapAndAnnotate();
    }
}
