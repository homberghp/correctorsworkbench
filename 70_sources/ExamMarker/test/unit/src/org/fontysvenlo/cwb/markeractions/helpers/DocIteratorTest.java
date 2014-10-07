/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fontysvenlo.cwb.markeractions.helpers;

import java.util.Iterator;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openide.util.Exceptions;

/**
 *
 * @author hom
 */
public class DocIteratorTest {

    public DocIteratorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testIterator() {
        StyledDocument doc = new DefaultStyledDocument();
        try {

            String docText = readFile("Doc.java.txt");
           // doc = new DefaultStyledDocument(docText, null);
            String[] lines = docText.split("\n");
            for (String l : lines) {
                System.out.println("l = " + l);
            }
            IterableDocument idoc = new IterableDocument(doc, 0, 1);
//            for (String s : idoc) {
//                System.out.println("s = [" + s.trim() + "]");
//            }
            Iterator<String> itr = idoc.iterator();
            for (int i = 0; i < lines.length && itr.hasNext(); i++) {
                String nextString = itr.next();
                System.out.println("lines["+i+"] = " + lines[i]+"\nnextString = " + nextString);
                assertEquals("line and doc line are not equal", lines[i], nextString);
            }
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    static String readFile(String filepath) throws Exception {
        java.net.URL url = DocIteratorTest.class.getResource(filepath);
        System.out.println("url = " + url);
        java.nio.file.Path resPath = java.nio.file.Paths.get(url.toURI());
        return new String(java.nio.file.Files.readAllBytes(resPath), "UTF8");
    }

}
