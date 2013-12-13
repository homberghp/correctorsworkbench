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
public abstract class ExamAnnotation extends Annotation {
    protected String text;
    @Override
    public String toString(){
        return getAnnotationType()+" msg "+getShortDescription();
    }
    
    public ExamAnnotation setText(String text){
        this.text = text;
        return this;
    }
}
