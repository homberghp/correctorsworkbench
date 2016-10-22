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

/**
 *
 * @author hom
 */
@ActionID(
         category = "Tools",
         id = "org.fontysvenlo.cwb.markeractions.AddNoWorkMarkAction" )
@ActionRegistration(
         displayName = "#CTL_AddNoWorkMarkAction",
         iconBase = "org/fontysvenlo/cwb/icons/nowork.png"
)
@ActionReferences( {
    @ActionReference( path = "Editors/Popup", position = 100 )
} )
@NbBundle.Messages( "CTL_AddNoWorkMarkAction=Mark Region with no work here" )

public class AddNoWorkMarkAction extends SolutionAction {

    public AddNoWorkMarkAction( DataObject context ) {
        super( context );
    }

    //Start Solution::replacewith:://TODO
    @Override
    protected void doWork() throws IOException, IndexOutOfBoundsException,
                                   InstantiationException,
                                   IllegalAccessException {
        this.wrapAndAnnotate();
    }
    //End Solution::replacewith::
}
//</editor-fold>
