/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.fontysvenlo.cwb;

/**
 *
 * @author hom
 */
public class PrePostFactory {
    private static int preCount=0;
    private static int postCount =0;
    
    private int preNumber =++preCount;
    private int postNumber = ++postCount;
    
    private final String preFmt;
    private final String postFmt;

    public PrePostFactory( String pre, String post) {
        this.preFmt = pre;
        this.postFmt = post;
    }
    
    String nextPre(){
        return String.format( preFmt, ++preCount );
    }
}
