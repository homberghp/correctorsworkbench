/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fontysvenlo.cwb.registry;

import java.util.ArrayList;
import java.util.List;
import org.fontysvenlo.cwb.annotations.ExamAnnotation;
import org.fontysvenlo.cwb.annotations.SolutionStartAnnotation;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openide.text.Annotation;

/**
 *
 * @author hom
 */
public class AnnotationRegistryTest {

    final AnnotationRegistry registry = AnnotationRegistry.getInstance();

    /**
     * Test of addAnnotation method, of class AnnotationRegistry.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    @Test
    public void testAddAnnotation() throws InstantiationException,
            IllegalAccessException {
        System.out.println("addAnnotation");
        ExamAnnotation an = SolutionStartAnnotation.class.newInstance().setText(
                "test");
        String relFilePath = "somefilename.java";
        int lineNumber = 0;
        int count = registry.registerCount();
        registry.addAnnotation(an, relFilePath, lineNumber);
        assertEquals("count should increase", 1 + count, registry.
                registerCount());
    }

    /**
     * Test of getAnnotations method, of class AnnotationRegistry.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    @Test
    public void testGetAnnotations() throws InstantiationException,
            IllegalAccessException {
        System.out.println("getAnnotations");
        Class anClass = SolutionStartAnnotation.class;
        ExamAnnotation san = new SolutionStartAnnotation().setText("Start here");
        String relFilePath1 = "somefilepath.java";
        registry.addAnnotation(san, relFilePath1, 1);
        List<Annotation> expResult = new ArrayList<>();
        expResult.add(san);
        List<Annotation> list = registry.getAnnotations(anClass, relFilePath1);
        assertEquals(expResult, list);
        san = new SolutionStartAnnotation().setText("Start here");
        expResult.add(san);
        registry.addAnnotation(san, relFilePath1, 2);
        list = registry.getAnnotations(anClass, relFilePath1);
        assertEquals(expResult, list);

        String relFilePath2 = "somefilepath2.java";
        expResult = new ArrayList<>();
        san = new SolutionStartAnnotation().setText("Start here");
        expResult.add(san);
        registry.addAnnotation(san, relFilePath2, 2);
        list = registry.getAnnotations(anClass, relFilePath2);
        assertEquals(expResult, list);
        // try to muck with the list 
        try {
            list.add(san);
            fail("should refuse");
        } catch (UnsupportedOperationException usoe) {
            assertNotNull("all is well");
        }
    }

}
