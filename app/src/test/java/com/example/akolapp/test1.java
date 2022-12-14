package com.example.akolapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class test1 {
    @Test
    public void TextAdded() {
        assertEquals(true, addsomeText("hello"));
        assertEquals(false,addsomeText(""));
    }
    public boolean addsomeText(String text){
        return !text.isEmpty();
    }


}