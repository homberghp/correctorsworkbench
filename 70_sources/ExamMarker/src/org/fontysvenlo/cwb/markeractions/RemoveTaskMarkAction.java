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
 *
 * @author hom
 */
@ActionID(
        category = "Tools",
        id = "org.fontysvenlo.cwb.markeractions.RemoveTaskMarkAction")
@ActionRegistration(
        displayName = "#CTL_RemoveTaskMarkAction")
@ActionReferences({
    @ActionReference(path = "Editors/Popup", position = 11)
})
@NbBundle.Messages("CTL_RemoveTaskMarkAction=Removes all task marks from a document")
public class RemoveTaskMarkAction extends TaskAction {

    public RemoveTaskMarkAction(DataObject context) {
        super(context);
    }

    @Override
    protected void doWork() throws IOException, IndexOutOfBoundsException {
        this.removeMarkers();
    }
    
}
