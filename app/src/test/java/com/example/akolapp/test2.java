package com.example.akolapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class test2 {
    @Test
    public void passwordIsValid() {
        assertEquals(false, passwordCheck("hello"));
        assertEquals(true,passwordCheck("qwdbiaid464"));
    }
    public boolean passwordCheck(String text){
        if(text.length()>=8)return true;
        else return false;
    }
}
