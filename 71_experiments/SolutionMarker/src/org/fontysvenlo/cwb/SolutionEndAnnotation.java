/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fontysvenlo.cwb;

/**
 *
 * @author hom
 */

public class SolutionEndAnnotation extends ExamAnnotation {

    @Override
    public String getAnnotationType() {
        return "org-fontysvenlo-cwb-solution-end";
    }

    @Override
    public String getShortDescription() {
        return text;
    }
    
}
