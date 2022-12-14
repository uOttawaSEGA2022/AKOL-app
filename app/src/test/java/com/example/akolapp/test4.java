package com.example.akolapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class test4 {
    @Test
    public void robotCheck() {
        //here to check for bots we ask the client to write first 10 letters and skip e
        assertEquals(false, botCheck("abcdefghi"));
        assertEquals(true,botCheck("abcdfghij"));
    }
    public boolean botCheck(String text){
        if(text.equals("abcdfghij"))return true;
        else return false;
    }
}
