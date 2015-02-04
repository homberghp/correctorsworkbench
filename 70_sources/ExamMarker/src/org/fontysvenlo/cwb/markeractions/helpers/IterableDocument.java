/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fontysvenlo.cwb.markeractions.helpers;

import java.util.Iterator;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.openide.text.NbDocument;

/**
 * Forward or backward iterable document. The returned Iterator does not
 * implement remove().
 */
class IterableDocument implements Iterable<String> {
    private final StyledDocument doc;
    /**
     * Maintains position. Value of Integer.MAX_VALUE
     */
    private final int position;
    private final int direction;

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            // init from
            int lineNumber = NbDocument.findLineNumber(doc, position);

            @Override
            public boolean hasNext() {
                boolean result = false;
                if (lineNumber == Integer.MAX_VALUE || lineNumber == -Integer.MAX_VALUE) {
                    return result;
                }
                // try to advance, on exception, flag end
                int tmpLineNumber = lineNumber + direction;
                try {
                    // try location
                    NbDocument.findLineOffset(doc, tmpLineNumber);
                    // if getting here, loc is ok.
                    result = true;
                } catch (IndexOutOfBoundsException iob) {
                    // stop trying next time
                    lineNumber = direction * Integer.MAX_VALUE;
                }
                return result;
            }

            @Override
            public String next() {
                String result = null;
                lineNumber += direction;
                int offset, offset2, length;
                offset = NbDocument.findLineOffset(doc, lineNumber);
                offset2 = Math.max(NbDocument.findLineOffset(doc, lineNumber + 1), doc.getLength());
                length = offset2 - offset;
                try {
                    result = doc.getText(offset, length);
                } catch (BadLocationException ex) {
                    lineNumber = direction * Integer.MAX_VALUE;
                }
                return result;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported.");
            }
        };
    }

    /**
     * Creates iterable document from starting position and direction.
     *
     * @param doc the doc to search
     * @param position the starting position
     * @param direction increase (+1) or decrease (-1)
     * @throws NullPointerException doc is null
     * @throws IllegalArgumentException when direction not one of (-1,1).
     */
    public IterableDocument(StyledDocument doc, final int position, int direction) {
        if (1 != Math.abs(direction)) {
            throw new IllegalArgumentException("illegal direction arg");
        }
        if (null == doc) {
            throw new NullPointerException("refusing to iterate null ");
        }
        this.doc = doc;
        this.position = position;
        this.direction = direction;
    }

}
