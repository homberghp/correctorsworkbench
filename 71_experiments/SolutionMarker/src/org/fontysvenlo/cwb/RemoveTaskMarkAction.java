/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fontysvenlo.cwb;

import java.io.IOException;
import org.openide.loaders.DataObject;

/**
 *
 * @author hom
 */
public class RemoveTaskMarkAction extends TaskAction {

    public RemoveTaskMarkAction(DataObject context) {
        super(context);
    }

    @Override
    protected void doWork() throws IOException, IndexOutOfBoundsException {
        this.removeMarkers();
    }
    
}
