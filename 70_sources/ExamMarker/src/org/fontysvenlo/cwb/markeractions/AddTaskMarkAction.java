/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fontysvenlo.cwb.markeractions;

import java.io.IOException;
import org.fontysvenlo.cwb.markeractions.helpers.TaskAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle;

/**
 * Action to Mark a set of lines as a task for the exam.
 * @author hom
 */
@ActionID(
        category = "Tools",
        id = "org.fontysvenlo.cwb.markeractions.AddTaskMarkAction")
@ActionRegistration(
        displayName = "#CTL_AddTaskMark",
        iconBase = "org/fontysvenlo/cwb/icons/workinprogress.png")
@ActionReferences({
    @ActionReference(path = "Editors/Popup", position = 10)
})
@NbBundle.Messages("CTL_AddTaskMark=Mark Region as Task")
public class AddTaskMarkAction extends TaskAction {

    public AddTaskMarkAction(DataObject context) {
        super(context);
    }
    @Override
    protected void doWork() throws IOException, IndexOutOfBoundsException, 
            InstantiationException, IllegalAccessException {
        this.wrapAndAnnotate();
    }
}
