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
import org.junit.Before;
import org.openide.text.Annotation;

/**
 *
 * @author hom
 */
public class AnnotationRegistryTest {

    final AnnotationRegistry registry = AnnotationRegistry.getInstance();

    @Before
    public void setUp(){
        System.out.println("clear registry");
        registry.clear();
    }
    /**
     * Test of addAnnotation method, of class AnnotationRegistry.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    @Test
    public void testAddAnnotation() throws InstantiationException,
            IllegalAccessException {
        System.out.println("addAnnotation");
        TextMock an1 = TextMockA.class.newInstance().setText(
                "test");
        TextMock an2 = TextMockB.class.newInstance().setText(
                "test");
        String relFilePath1 = "somefilename.java";
        String relFilePath2 = "otherfilename.java";
        int lineNumber = 0;
        int count = registry.registerCount();
        registry.addAnnotation(an1, relFilePath1, lineNumber);
        assertEquals("count should increase", 1 + count, registry.
                registerCount());
        
        count = registry.registerCount();
        int typeCount = registry.typeCount();
        registry.addAnnotation(an2, relFilePath2, lineNumber);
        assertEquals("count should increase", 1 + count, registry.
                registerCount());
        assertEquals("number of types should be 2",1+typeCount,registry.typeCount());
        System.out.println(registry.toString());
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
        Class<? extends TextMock> anClass = TextMockC.class;
        TextMock san = anClass.newInstance().setText("Start here0");
        String relFilePath1 = "somefilepath.java";
        registry.addAnnotation(san, relFilePath1, 1);
        List<TextMock> expResult = new ArrayList<>();
        expResult.add(san);
        List<TextMock> list = registry.getAnnotations(anClass, relFilePath1);
        assertEquals(expResult, list);
        san = new TextMockC().setText("Start here1");
        expResult.add(san);
        registry.addAnnotation(san, relFilePath1, 2);
        list = registry.getAnnotations(anClass, relFilePath1);
        assertEquals(expResult, list);

        String relFilePath2 = "somefilepath2.java";
        expResult = new ArrayList<>();
        san = new TextMockC().setText("Start here2");
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
