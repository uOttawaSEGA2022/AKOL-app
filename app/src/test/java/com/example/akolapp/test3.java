package com.example.akolapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class test3 {
    @Test
    public void priceCheck() {
        assertEquals(false, priceValid(-15));
        assertEquals(false,priceValid(0));
        assertEquals(true,priceValid(15));
    }
    public boolean priceValid(int price){
        if(price>0)return true;
        else return false;
    }
}
