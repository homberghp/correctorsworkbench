/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fontysvenlo.cwb;

import org.openide.text.Annotation;

/**
 *
 * @author hom
 */

public class SolutionEndAnnotation extends Annotation {

    private final String text;
    
    SolutionEndAnnotation( String here_starts_your_solution ) {
        this.text=here_starts_your_solution;
        
    }

    @Override
    public String getAnnotationType() {
        return "org-nb-modules-cwb-solution-end";
    }

    @Override
    public String getShortDescription() {
        return text;
    }
    
}
