/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fontysvenlo.cwb.annotations;

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
    
    /**
     * Set the short text. Typically called directly after construction time.
     * @param text
     * @return this
     */
    public ExamAnnotation setText(String text){
        this.text = text;
        return this;
    }

    /**
     * All annotations should return a short description, used as tool-tip.
     * @return the text for this annotation.
     */
    @Override
    public String getShortDescription() {
        return text;
    }
}
