/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fontysvenlo.cwb.annotations;

/**
 *
 * @author hom
 */

public class SolutionStartAnnotation extends ExamAnnotation {

    @Override
    public String getAnnotationType() {
        //Start Solution::replacewith:://TODO
        return "org-fontysvenlo-cwb-annotations-solution-start";
    }
        //End Solution::replacewith::
}
