package org.fontysvenlo.cwb;

/**
 * Default settings
 *
 * @author hom
 */
public class CWBSettings {

    public static final String defaultSolutionStartTag
            = "//Start Solution::replacewith:://TODO";
    public static final String defaultSolutionEndTag
            = "//End Solution::replacewith::";
    public static final String defaultTaskStartTag
            = "//<editor-fold defaultstate=\"expanded\" desc=\"PRO2_2; __STUDENT_ID__ ;WEIGHT 1\">";
    public static final String defaultTaskEndTag = "//</editor-fold>";
    public static final String defaultNoWorkEndTag = defaultTaskEndTag;
    public static final String defaultNoWorkTag
            = "//<editor-fold defaultstate=\"collapsed\" desc=\"no work here\">";
}
