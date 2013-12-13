/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fontysvenlo.cwb;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openide.text.Annotation;

/**
 *
 * @author hom
 */
public class AnnotationRegistryTest {

    final AnnotationRegistry<Annotation> registry = AnnotationRegistry.getInstance();

    /**
     * Test of addAnnotation method, of class AnnotationRegistry.
     */
    @Test
    public void testAddAnnotation() throws InstantiationException, IllegalAccessException {
        System.out.println("addAnnotation");
        ExamAnnotation an = SolutionStartAnnotation.class.newInstance().setText("test");
        String relFilePath = "somefilename.java";
        int lineNumber = 0;
        int count = registry.registerCount();
        registry.addAnnotation(an, relFilePath, lineNumber);
        assertEquals("count should increase", 1 + count, registry.registerCount());
    }

    /**
     * Test of getAnnotations method, of class AnnotationRegistry.
     */
    @Test
    public void testGetAnnotations() throws InstantiationException, IllegalAccessException {
        System.out.println("getAnnotations");
        Class anClass = SolutionStartAnnotation.class;
        ExamAnnotation san = new SolutionStartAnnotation().setText("Start here");
        String relFilePath1 = "somefilepath.java";
        registry.addAnnotation(san, relFilePath1, 1);
        List<Annotation> expResult = new ArrayList<>();
        expResult.add(san);
        List<Annotation> result = registry.getAnnotations(anClass, relFilePath1);
        assertEquals(expResult, result);
        san = new SolutionStartAnnotation().setText("Start here");
        expResult.add(san);
        registry.addAnnotation(san, relFilePath1, 2);
        result = registry.getAnnotations(anClass, relFilePath1);
        assertEquals(expResult, result);

        String relFilePath2 = "somefilepath2.java";
        expResult = new ArrayList<>();
        san = new SolutionStartAnnotation().setText("Start here");
        expResult.add(san);
        registry.addAnnotation(san, relFilePath2, 2);
        result = registry.getAnnotations(anClass, relFilePath2);
        assertEquals(expResult, result);
    }

}
