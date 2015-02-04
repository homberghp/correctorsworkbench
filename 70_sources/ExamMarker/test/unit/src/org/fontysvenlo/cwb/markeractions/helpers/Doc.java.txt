/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fontysvenlo.cwb.registry;

/**
 *
 * @author hom
 */
class TextMock {

    private String text;

    public String getText() {
        return text;
    }

    public TextMock setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " t:" + text;
    }

}

class TextMockA extends TextMock {
}

class TextMockB extends TextMock {
}

class TextMockC extends TextMock {
}
